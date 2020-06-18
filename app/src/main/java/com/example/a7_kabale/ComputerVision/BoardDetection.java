package com.example.a7_kabale.ComputerVision;

import com.example.a7_kabale.logic.Card;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class BoardDetection {
    /*  Index order of the contours.
        0 - H
        1 - W
        2..5 - F1-F4
        6..12 - T1-T7
     */

    public BoardDetection(){
    }
        //TODO Extend process image to compare Cardrecognition with the found fields.
    public static ArrayList<ArrayContourObject> processImage(Mat img){
        Mat blur = new Mat();
        Mat grey = new Mat();
        Mat canny = new Mat();
        Mat persimg = new Mat();
        Mat persblur = new Mat();
        Mat pershsv = new Mat();
        Mat persmask = new Mat();
        Mat cnthiarchy = new Mat();

        Comparator<MatOfPoint> comp = (o1, o2) -> {

            double o1x = o1.get(0, 0)[0];
            double o1y = o1.get(0, 0)[1];
            double o2x = o2.get(0, 0)[0];
            double o2y = o2.get(0, 0)[1];
            int cmp = 0;
            int resulty = (int) (o1y - o2y);
            if (resulty >= 20 || resulty <= -20)
                cmp = resulty;

            //int cmp = Integer.compare((int)o1y, (int)o2y);
            if (cmp != 0) {
                return cmp;
            }

            return (int) (o1x - o2x);

        };

        ArrayList<MatOfPoint> fields = new ArrayList<>();
        List<MatOfPoint> contours = new ArrayList<>();

        //1. Process picture to find and isolate the board.

        Imgproc.GaussianBlur(img, blur, new Size(5, 5), 0);

        Imgproc.cvtColor(blur, grey, Imgproc.COLOR_RGBA2GRAY);

        Imgproc.Canny(grey, canny, 10, 70);

        Imgproc.findContours(canny,contours,cnthiarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint maxcnt = findMaxContour(contours);

        MatOfPoint2f approx = approxContourAsRect(maxcnt);

        approx = sortApproxContour(approx);

        //2. Perspective warp the board to an picture of only the board.
        //Size imgsize = img.size();
        double d = ((double) img.rows()) * (297.0/210.0); // Size som er ligeså høj som photo, men er samme ratio som et a4 papir
        Size imgsize = new Size(d, img.rows());

        //Create an transform matrix of the wished size. 1500x1500.
        Mat dst = Mat.zeros(4,2,CvType.CV_32F);
        dst.put(0,0,0); dst.put(0,1,0);
        dst.put(1,0,imgsize.width-1); dst.put(1,1,0);
        dst.put(2,0,imgsize.width-1); dst.put(2,1,imgsize.height-1);
        dst.put(3,0,0); dst.put(3,1,imgsize.height-1);

        Mat warpMat = Imgproc.getPerspectiveTransform(approx, dst);


        Imgproc.warpPerspective(img, persimg, warpMat, imgsize);

        //3. Find fields and sort them in the proper order.
        Imgproc.GaussianBlur(persimg, persblur, new Size(5, 5), 0);
        Imgproc.cvtColor(persblur, persblur, Imgproc.COLOR_RGBA2BGR);
        Imgproc.cvtColor(persblur, pershsv, Imgproc.COLOR_BGR2HSV);
        contours.clear();
        Core.inRange(pershsv,  new Scalar(90,25, 25), new Scalar(150, 255, 255), persmask);
        Imgproc.findContours(persmask,contours,new Mat(),Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);

        for(MatOfPoint cont : contours){
            double area = Imgproc.contourArea(cont);
            if (area >= 6000){
                MatOfPoint2f apcontour = approxContourAsRect(cont);
                apcontour = sortApproxContour(apcontour);
                fields.add(new MatOfPoint(apcontour.toArray()));
            }
        }
        //TODO Check size of list. Should be 13 else there was an error.

        Collections.sort(fields, comp);
        persimg.copyTo(img);
        ArrayList<ArrayContourObject> contlist = new ArrayList<>();
        for (MatOfPoint cont : fields){
            contlist.add(new ArrayContourObject(cont));
        }
        return contlist;
    }

    private static MatOfPoint findMaxContour(List<MatOfPoint> contours){
        MatOfPoint maxcnt = new MatOfPoint();
        double maxarea = 0;
        for(int i = 0; i < contours.size(); i++){
            double area = Imgproc.contourArea(contours.get(i));
            if (area >= maxarea) {
                maxarea = area;
                maxcnt = contours.get(i);
            }
        }
        return maxcnt;
    }
    /*
    private MatOfPoint2f approxContourAsRect(MatOfPoint contour){

        MatOfPoint2f m2f = new MatOfPoint2f(contour.toArray());
        MatOfPoint2f approx = new MatOfPoint2f();
        double epsilon = 0.01 * Imgproc.arcLength(m2f, true);

        //If are contour has less vertices then 4, then we cannot approximate and we will never be able to isolate the board.
        if(m2f.total() < 4) return null;
        //Find an epsilon which approximates the contour to an rectangle.
        //The higher the epsilon the less vertices. Epsilon can't be zero or below.
        if(m2f.total() > 4) {
            while (approx.total() != 4){
                Imgproc.approxPolyDP(m2f, approx, epsilon, true);
                if(approx.total() > 4){
                    epsilon += 1;
                } else if( approx.total() < 4){
                    epsilon -= 1;
                }
                if (epsilon <= 0) return null;
            }

        }

        System.out.println("Vertices in approx = " + approx.total());
        System.out.println("Epsilon = " + epsilon);
        return approx;
    }
    */

     //This has the potential to be stuck in infinite loop, trying to find an epsilon which approximates to 4. This epsilon might not exist.
    //TODO Create watchdog thread to close the process and force reset.
    private static MatOfPoint2f approxContourAsRect(MatOfPoint contour){
        Thread watchdog = new Thread();
        MatOfPoint2f m2f = new MatOfPoint2f(contour.toArray());
        MatOfPoint2f approx = new MatOfPoint2f();
        double epsilon = 0.01 * Imgproc.arcLength(m2f, true);
        double lepsilon = 0, repsilon = 0;
        Boolean increased = null;
        //If are contour has less vertices then 4, then we cannot approximate and we will never be able to isolate the board.
        if(m2f.total() < 4) {
            System.err.println("ERR: Can't approx when contour is already less than 4 vertices");
            return null;
        }
        //Find an epsilon which approximates the contour to an rectangle.
        //The higher the epsilon the less vertices. Epsilon can't be zero or below.
        if(m2f.total() > 4) {

            Imgproc.approxPolyDP(m2f, approx, epsilon, true);
            if(approx.total() > 4){
                lepsilon = epsilon;
                repsilon = 2*epsilon;
                while (approx.total() > 4){
                    Imgproc.approxPolyDP(m2f, approx, repsilon, true);
                    repsilon *= 2;
                    if(approx.total() == 4) return approx;
                }
            } else if (approx.total() < 4) {
                repsilon = epsilon;
                lepsilon = 2/epsilon;
                while (approx.total() < 4){
                    Imgproc.approxPolyDP(m2f, approx, lepsilon, true);
                    lepsilon /= 2;
                    if(approx.total() == 4) return approx;
                }
            } else {
                return approx;
            }

            while (repsilon > lepsilon){

                    double midepsilon = lepsilon + (repsilon - lepsilon) / 2;
                    Imgproc.approxPolyDP(m2f, approx, midepsilon, true);

                    System.out.printf("Total = %d\nEpsilon = %f\n", approx.total(),midepsilon);
                    // If the element is present at the
                    // middle itself
                    if (approx.total() == 4)
                        return approx;

                    // If element is smaller than mid, then
                    // it can only be present in left subarray
                    if (approx.total() > 4){
                        lepsilon = midepsilon;
                    } else {
                        repsilon = midepsilon;
                    }

                if (epsilon <= 0){
                    System.err.println("ERR: Epsilon was less than zero");
                    return null;
                }
            }
        }
        System.out.println("Vertices in approx = " + approx.total());
        System.out.println("Epsilon = " + epsilon);
        return approx;
    }


    //inspired by: https://www.pyimagesearch.com/2014/08/25/4-point-opencv-getperspective-transform-example/
    private static MatOfPoint2f sortApproxContour(MatOfPoint2f approx){
        Point[] pntarr = new Point[4];
        Point[] approxarr = approx.toArray();
        List<Double> sumarr = new ArrayList<>();
        List<Double> diffarr = new ArrayList<>();
        //There is always four vertices
        if(approx.total() != 4){
            System.err.println("ERR: There should be 4 vertices in the list to sort it!");
            return null;
        }
        /*
         * Form of the sort:
         * 0: top-left corner will have the smallest sum.
         * 1: top-right corner will have the smallest difference.
         * 2: bottom-right corner will have the largest sum.
         * 3: bottom-left corner will have the largest difference.
         * */

        //Calculate sum of each point.
        //Calculate difference of each point.
        for (int i = 0; i < approxarr.length; i++){
            sumarr.add(approxarr[i].x + approxarr[i].y);
            diffarr.add(approxarr[i].y - approxarr[i].x);
        }
        //sort for sum
        pntarr[0] = approxarr[sumarr.indexOf(Collections.min(sumarr))];
        pntarr[1] = approxarr[diffarr.indexOf(Collections.min(diffarr))];
        pntarr[2] = approxarr[sumarr.indexOf(Collections.max(sumarr))];
        pntarr[3] = approxarr[diffarr.indexOf(Collections.max(diffarr))];

        return new MatOfPoint2f(pntarr);

    }

    private static void printContour(MatOfPoint2f contour){
        for(int i = 0; i < contour.rows(); i++)
            for(int j = 0; j < contour.cols(); j++)
                System.out.printf("( %d , %d ) = %f %f \n", i, j, contour.get(i, j)[0], contour.get(i, j)[1]);
    }

    public static ArrayList<ArrayList<Card>> cardSegmenter(ArrayList<ArrayContourObject> contours, ArrayList<Card> Cards) {
        ArrayList<ArrayList<Card>> cardList = new ArrayList<ArrayList<Card>>();
        cardList.add(null);
        ArrayList<Card> topDeckCard = new ArrayList<>();
        ArrayList<Card> foundationDeckDiamonds = new ArrayList<>();
        ArrayList<Card> foundationDeckHearts = new ArrayList<>();
        ArrayList<Card> foundationDeckClubs = new ArrayList<>();
        ArrayList<Card> foundationDeckSpades = new ArrayList<>();
        ArrayList<Card> tableauRow1 = new ArrayList<>();
        ArrayList<Card> tableauRow2 = new ArrayList<>();
        ArrayList<Card> tableauRow3 = new ArrayList<>();
        ArrayList<Card> tableauRow4 = new ArrayList<>();
        ArrayList<Card> tableauRow5 = new ArrayList<>();
        ArrayList<Card> tableauRow6 = new ArrayList<>();
        ArrayList<Card> tableauRow7 = new ArrayList<>();
        cardList.add(1, topDeckCard);
        cardList.add(2, foundationDeckDiamonds);
        cardList.add(3, foundationDeckHearts);
        cardList.add(4, foundationDeckClubs);
        cardList.add(5, foundationDeckSpades);
        cardList.add(6, tableauRow1);
        cardList.add(7, tableauRow2);
        cardList.add(8, tableauRow3);
        cardList.add(9, tableauRow4);
        cardList.add(10, tableauRow5);
        cardList.add(11, tableauRow6);
        cardList.add(12, tableauRow7);

        for (int i = 1; i < contours.size(); i++) {
            ArrayContourObject cont = contours.get(i);
            for (Card card : Cards) {
                Point topLeft = card.getRect().tl();
                Point contTL = cont.topLeft();
                Point contBR = cont.bottomRight();

                //If fra https://www.geeksforgeeks.org/check-if-a-point-lies-on-or-inside-a-rectangle-set-2/
                if (topLeft.x > contTL.x && topLeft.x < contBR.x
                        && topLeft.y > contTL.y && topLeft.y < contBR.y) {
                    cardList.get(contours.indexOf(cont)).add(card);
                    //Cards.remove(card);
                }
            }

            if (cardList.get(contours.indexOf(cont)).isEmpty()) {
                Card dummy = new Card(new Rect(cont.getCenterX(), cont.getCenterY(), 1, 1));
                cardList.get(contours.indexOf(cont)).add(dummy);
            }

        }

        if (!Cards.isEmpty()) {
            System.err.println("Something is very wrong. Some recognized cards were not inside contours!");
        }

        return cardList;
    }
}

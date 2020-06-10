package com.example.a7_kabale;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YOLOProcessor {
    Net darkNet;
    Size darkNetSize = new Size(1536, 1536);

    public void initDarknet(File storage) {
        String weight = storage.getPath() + "/data/cards.weights";
        String cfg = storage.getPath() + "/data/cards.cfg";
        darkNet = Dnn.readNetFromDarknet(cfg, weight);
    }

    public ArrayList<RecognizedCard> getCards(Mat mat) {
        //Mat input skal være RGB og ikke RGBA - ellers crasher vi..
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2RGB);

        Mat blob = Dnn.blobFromImage(mat, (float) 1 / 255, darkNetSize, new Scalar(0), false, false);
        darkNet.setInput(blob);

        //Resten af koden i denne metode baseret på opencv answers tråd:
        //https://answers.opencv.org/question/205657/darknet-yolo-extract-data-from-dnnforward/
        List<Mat> result = new ArrayList<>();
        darkNet.forward(result, getOutputNames(darkNet));

        int cols = mat.cols();
        int rows = mat.rows();

        float threshold = 0.1f;
        List<Integer> clsIds = new ArrayList<>(); //Class ID
        List<Float> confs = new ArrayList<>(); //Konfidens (hvor stor procent match i float-værdi)
        List<Rect> rects = new ArrayList<>(); //Liste over færdige firkanter

        for (int i = 0; i < result.size(); ++i) {
            // each row is a candidate detection, the 1st 4 numbers are
            // [center_x, center_y, width, height], followed by (N-4) class probabilities
            Mat level = result.get(i);
            for (int j = 0; j < level.rows(); ++j) {
                Mat row = level.row(j);
                Mat scores = row.colRange(5, level.cols());
                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float) mm.maxVal;
                Point classIdPoint = mm.maxLoc;
                if (confidence > threshold) {
                    int centerX = (int) (row.get(0, 0)[0] * mat.cols());
                    int centerY = (int) (row.get(0, 1)[0] * mat.rows());
                    int width = (int) (row.get(0, 2)[0] * mat.cols());
                    int height = (int) (row.get(0, 3)[0] * mat.rows());
                    int left = centerX - width / 2;
                    int top = centerY - height / 2;

                    clsIds.add((int) classIdPoint.x);
                    confs.add((float) confidence);
                    rects.add(new Rect(left, top, width, height));
                }
            }
        }

        // Apply non-maximum suppression procedure.
        float nmsThresh = 0.5f;
        MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));
        Rect[] boxesArray = rects.toArray(new Rect[0]);
        MatOfRect boxes = new MatOfRect(boxesArray);
        MatOfInt indices = new MatOfInt();
        Dnn.NMSBoxes(boxes, confidences, threshold, nmsThresh, indices);

        // Generate list of spotted objects
        ArrayList<RecognizedCard> recognizedCards = new ArrayList<>();

        int[] ind = indices.toArray();
        for (int i = 0; i < ind.length; ++i) {
            int idx = ind[i];
            RecognizedCard card = new RecognizedCard(clsIds.get(idx), confs.get(idx), rects.get(idx));
            recognizedCards.add(card);
        }

        return recognizedCards;
    }

    public Mat DrawMatFromList(Mat mat, ArrayList<RecognizedCard> recognizedCards) {
        for (int i = 0; i < recognizedCards.size(); i++) {
            Rect rect = recognizedCards.get(i).getRect();
            Imgproc.rectangle(mat, rect.tl(), rect.br(), new Scalar(0, 0, 255), 4);
            Imgproc.putText(mat, recognizedCards.get(i).getClassName(), rect.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 2, new Scalar(255,0,0), 3);
        }

        return mat;
    }

    private static List<String> getOutputNames(Net net) {
        List<String> names = new ArrayList<>();

        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();

        for (int item : outLayers) {
            names.add(layersNames.get(item - 1));
        }

        return names;
    }


}

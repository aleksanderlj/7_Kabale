package com.example.a7_kabale;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CardRecognizer {
        private Activity callingActivity;
        private Context context;
        private ImageView screenImage;
        private int currentImage = 1;
        private Bitmap imgBitmap;
        private Mat imageMat;
        private File storage;

        public CardRecognizer(Activity activity) {
            callingActivity = activity;
            context = activity.getApplicationContext();
            storage = context.getExternalFilesDir(null);
        }

        public void doThings() {
        screenImage = (ImageView) callingActivity.findViewById(R.id.imageView1);
        Button button = (Button) callingActivity.findViewById(R.id.button);
        Button openCVbtn = (Button) callingActivity.findViewById(R.id.buttonOpenCV);


        imageMat = new Mat();
        screenImage.setImageResource(R.mipmap.spillekort1);
        imgBitmap = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spillekort1);
        Utils.bitmapToMat(imgBitmap, imageMat);
        Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_RGBA2RGB);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                switch (currentImage) {
                    case 1:
                        currentImage++;
                        screenImage.setImageResource(R.mipmap.spillekort1);
                        imgBitmap = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spillekort1);
                        break;
                    case 2:
                        currentImage++;
                        screenImage.setImageResource(R.mipmap.spillekort2);
                        imgBitmap = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spillekort2);
                        break;
                    case 3:
                        currentImage++;
                        screenImage.setImageResource(R.mipmap.spillekort3);
                        imgBitmap = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spillekort3);
                        break;
                    case 4:
                        currentImage = 1;
                        screenImage.setImageResource(R.mipmap.spillekort4);
                        imgBitmap = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spillekort4);
                        break;
                }

                Utils.bitmapToMat(imgBitmap, imageMat);
                Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_RGBA2RGB);
            }
        });

        openCVbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TextView text = (TextView) callingActivity.findViewById(R.id.textView);
                downloadAssets();
                getCards();




                Bitmap bmp = Bitmap.createBitmap(imageMat.cols(), imageMat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(imageMat, bmp);
                screenImage.setImageBitmap(bmp);

                text.setText(storage.getPath() + "/data/cards.cfg");

            }
        });
    }

    public void downloadAssets() {
            File propertyFile = new File(storage.getPath() + "/props.properties");
            if (!propertyFile.exists()) {
                try {
                    propertyFile.createNewFile();
                    //Skriv eventuelt noget til properties som versionsnummer af weights etc. etc.

                    //Download alt her
                    DownloadManager dlm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

                    DownloadManager.Request weightsRequest = makeRequest("https://waii.dk/upload/uploads/cards.weights", "cards.weights");
                    DownloadManager.Request cfgRequest = makeRequest("https://waii.dk/upload/uploads/cards.cfg", "cards.cfg");

                    dlm.enqueue(weightsRequest);
                    dlm.enqueue(cfgRequest);

                } catch (Exception e) {
                    System.err.println("Something went wrong with asset downloads!");
                }

                } else {
                System.out.println("Files exists!");
            }
    }

    private DownloadManager.Request makeRequest(String url, String outName) {
        File requestedFile = new File(storage.getPath() + "/data/" + outName);
        if (requestedFile.exists()) {
            requestedFile.delete();
        }

        Uri Download_Uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setTitle("Downloading");
        request.setDescription("Downloading configuration files for detection");
        request.setDestinationInExternalFilesDir(context, "data", outName);
        request.setVisibleInDownloadsUi(false);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        return request;
    }

    public void getCards() {
        double threshhold = 0.2;
        String weight = storage.getPath() + "/data/cards.weights";
        String cfg = storage.getPath() + "/data/cards.cfg";
        Net net = Dnn.readNetFromDarknet(cfg, weight);


        //Mat input skal være RGB og ikke RGBA - ellers crasher vi..
        Size sz = new Size(1246, 1246);
        Mat blob = Dnn.blobFromImage(imageMat, 0.00392, sz, new Scalar(0), true, false);

        net.setInput(blob);

        Mat detections = net.forward();
        Mat frame = imageMat;
        int cols = frame.cols();
        int rows = frame.rows();

        //Copy paste herfra
        detections = detections.reshape(1, (int)detections.total() / 7);

        /*for (int i = 0; i < detections.rows(); ++i) {
            double confidence = detections.get(i, 2)[0];
            if (confidence > threshhold) {
                int classId = (int)detections.get(i, 1)[0];

                int left   = (int)(detections.get(i, 3)[0] * cols);
                int top    = (int)(detections.get(i, 4)[0] * rows);
                int right  = (int)(detections.get(i, 5)[0] * cols);
                int bottom = (int)(detections.get(i, 6)[0] * rows);

                // Draw rectangle around detected object.
                Imgproc.rectangle(frame, new Point(left, top), new Point(right, bottom),
                        new Scalar(0, 255, 0));
                String label = classNames[classId] + ": " + confidence;
                int[] baseLine = new int[1];
                Size labelSize = Imgproc.getTextSize(label, Core.FONT_HERSHEY_SIMPLEX, 0.5, 1, baseLine);

                // Draw background for label.
                Imgproc.rectangle(frame, new Point(left, top - labelSize.height),
                        new Point(left + labelSize.width, top + baseLine[0]),
                        new Scalar(255, 255, 255), Core.FILLED);
                // Write class name and confidence.
                Imgproc.putText(frame, label, new Point(left, top),
                        Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 0));
            }
        }*/

        //Net net = Dnn.readNetFromDarknet(cfg, weight);

        //resize billede
        //Size sz = new Size(1246, 1246);
        //Mat blob = Dnn.blobFromImage(imageMat, 0.00392, sz, new Scalar(0), true, false);
        //net.setInput(blob);

        //Lav lister
        //List<Mat> result = new ArrayList<Mat>();
        //List<String> outBlobNames = getOutputNames(net);

        //net.forward(result, outBlobNames);
        /*
        //Hvor meget skal det ligne før vi godtager?
        float confThreshold = 0.6f;

        List<Integer> clsIds = new ArrayList<>();
        List<Float> confs = new ArrayList<>();
        List<Rect> rects = new ArrayList<>();

         */


    }

    private static final String[] classNames = {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14",
            "15", "16", "17",
            "18", "19", "20", "21",
            "22", "23", "24", "25",
            "26", "27", "28", "29",
            "30", "31", "32", "33",
            "34", "35", "36", "37",
            "38", "39", "40", "41",
            "42", "43", "44", "45",
            "46", "47", "48", "49",
            "50", "51", "52" };

    private static List<String> getOutputNames(Net net) {
        List<String> names = new ArrayList<>();

        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();

        for (int item : outLayers) {
            names.add(layersNames.get(item - 1));
        }

        return names;
    }

    public Mat drawMatOnCards(Mat input) {
        Mat outputMat = new Mat();
        return outputMat;
    }


}

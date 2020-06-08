package com.example.a7_kabale;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import org.opencv.android.Utils;
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

public class CardRecognizer {
        private Activity callingActivity;
        private Context context;
        private ImageView screenImage;
        private int currentImage = 1;
        private Bitmap imgBitmap;
        private Mat imageMat;
        private File storage;
        Net darkNet;
        Size darkNetSize = new Size(1536, 1536);

        public CardRecognizer(Activity activity) {
            callingActivity = activity;
            context = activity.getApplicationContext();
            storage = context.getExternalFilesDir(null);
            initDarknet();
        }

        public void doThings() {
        screenImage = (ImageView) callingActivity.findViewById(R.id.imageView1);
        Button button = (Button) callingActivity.findViewById(R.id.button);
        Button openCVbtn = (Button) callingActivity.findViewById(R.id.buttonOpenCV);


        imageMat = new Mat();
        screenImage.setImageResource(R.mipmap.spillekort1);
        imgBitmap = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spillekort1);
        Utils.bitmapToMat(imgBitmap, imageMat);

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
            }
        });

        openCVbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TextView text = (TextView) callingActivity.findViewById(R.id.textView);
                Mat newImageMat = getCards(imageMat);

                Bitmap bmp = Bitmap.createBitmap(newImageMat.cols(), newImageMat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(newImageMat, bmp);
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

    public void initDarknet() {
        downloadAssets();
        String weight = storage.getPath() + "/data/cards.weights";
        String cfg = storage.getPath() + "/data/cards.cfg";
        darkNet = Dnn.readNetFromDarknet(cfg, weight);
    }

    public Mat getCards(Mat mat) {
        //Mat input skal være RGB og ikke RGBA - ellers crasher vi..
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2RGB);

        Mat blob = Dnn.blobFromImage(mat, 0.00392, darkNetSize, new Scalar(0), true, false);
        darkNet.setInput(blob);
        List<Mat> result = new ArrayList<>();
        List<String> outBlobNames = getOutputNames(darkNet);

        darkNet.forward(result, outBlobNames);

        int cols = mat.cols();
        int rows = mat.rows();

        float threshold = 0.1f;
        List<Integer> clsIds = new ArrayList<>();
        List<Float> confs = new ArrayList<>();
        List<Rect> rects = new ArrayList<>();
        for (int i = 0; i < result.size(); ++i)
        {
            // each row is a candidate detection, the 1st 4 numbers are
            // [center_x, center_y, width, height], followed by (N-4) class probabilities
            Mat level = result.get(i);
            for (int j = 0; j < level.rows(); ++j)
            {
                Mat row = level.row(j);
                Mat scores = row.colRange(5, level.cols());
                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float)mm.maxVal;
                Point classIdPoint = mm.maxLoc;
                if (confidence > threshold)
                {
                    int centerX = (int)(row.get(0,0)[0] * mat.cols());
                    int centerY = (int)(row.get(0,1)[0] * mat.rows());
                    int width   = (int)(row.get(0,2)[0] * mat.cols());
                    int height  = (int)(row.get(0,3)[0] * mat.rows());
                    int left    = centerX - width  / 2;
                    int top     = centerY - height / 2;

                    clsIds.add((int)classIdPoint.x);
                    confs.add((float)confidence);
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

        // Draw result boxes:
        int [] ind = indices.toArray();
        for (int i = 0; i < ind.length; ++i)
        {
            int idx = ind[i];
            Rect box = boxesArray[idx];
            Imgproc.rectangle(mat, box.tl(), box.br(), new Scalar(0,0,255), 2);
            System.out.println(box);
        }

        return mat;
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

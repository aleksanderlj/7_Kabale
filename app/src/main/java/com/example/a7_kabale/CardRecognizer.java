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
import org.opencv.core.Mat;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
        String weight = storage.getPath() + "/data/cards.weights";
        String cfg = storage.getPath() + "/data/cards.cfg";

        Net net = Dnn.readNetFromDarknet(cfg, weight);
        

    }

    public Mat drawMatOnCards(Mat input) {
        Mat outputMat = new Mat();
        return outputMat;
    }


}

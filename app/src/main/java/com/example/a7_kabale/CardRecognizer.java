package com.example.a7_kabale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.util.ArrayList;

public class CardRecognizer {
    private Activity callingActivity;
    private Context context;
    private ImageView screenImage;
    private int currentImage = 1;
    private Bitmap imgBitmap;
    private Mat imageMat;
    private File storage;
    private YOLOProcessor yoloProcessor;

    public CardRecognizer(Activity activity) {
        callingActivity = activity;
        context = activity.getApplicationContext();
        storage = context.getExternalFilesDir(null);

        AssetDownloader assetDownloader = new AssetDownloader(context);
        assetDownloader.downloadAssets();

        //Assets skal downloades før vi kan initialisere darknet - vi skal helst implementere noget ventenoget her
        yoloProcessor = new YOLOProcessor();
        yoloProcessor.initDarknet(storage);
    }

    /*
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
                ArrayList<RecognizedCard> cardList = yoloProcessor.getCards(imageMat);

                for (RecognizedCard item : cardList) {
                    System.out.println("Fundet kort:\r\n");
                    System.out.println("Værdi: " + item.getClassID() + "\n\r");
                    System.out.println("Navn: " + item.getClassName() + "\n\r");
                    System.out.println("Konfidens: " + item.getConf() + "\n\r");
                    System.out.println("Firkant: " + item.getRect() + "\n\r");
                }

                Mat newImageMat = yoloProcessor.DrawMatFromList(imageMat, cardList);

                Bitmap bmp = Bitmap.createBitmap(newImageMat.cols(), newImageMat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(newImageMat, bmp);
                screenImage.setImageBitmap(bmp);

                text.setText(storage.getPath() + "/data/cards.cfg");

            }
        });
    }

     */
}

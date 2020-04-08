package com.example.a7_kabale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class CardRecognizer {
        private Activity callingActivity;
        private ImageView screenImage;
        private int currentImage = 1;
        private Bitmap imgBitmap;
        private Bitmap spades, hearts, diamonds, clubs;
        private Mat imageMat, editedImageMat;
        private Mat spadesMat, heartsMat, diamondsMat, clubsMat;
        private int matchMethod=Imgproc.TM_CCOEFF;

        public CardRecognizer(Activity activity) {
            callingActivity = activity;

            //Init openCV
            editedImageMat = new Mat();
            spadesMat = new Mat();
            heartsMat = new Mat();
            diamondsMat = new Mat();
            clubsMat = new Mat();
            spades = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.spades);
            hearts = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.hearts);
            diamonds = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.diamonds);
            clubs = BitmapFactory.decodeResource(callingActivity.getResources(), R.mipmap.clubs);
            Utils.bitmapToMat(spades, spadesMat);
            Utils.bitmapToMat(hearts, heartsMat);
            Utils.bitmapToMat(diamonds, diamondsMat);
            Utils.bitmapToMat(clubs, clubsMat);
        }

        public void doThings() {
        screenImage = (ImageView) callingActivity.findViewById(R.id.imageView1);
        Button button = (Button) callingActivity.findViewById(R.id.button);
        Button openCVbtn = (Button) callingActivity.findViewById(R.id.buttonOpenCV);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                imageMat = new Mat();
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
                String result = "Der er gået noget galt hvis det her står.";
                //result = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.mipmap.spillekort2).toString();
                TextView text = (TextView) callingActivity.findViewById(R.id.textView);

                int color = getCardColor();
                result = getCardSuit(color);
                result += " " + colorIdToString(color);
                result += " " + getCardValue();

                Mat tmp = new Mat();
                Bitmap bmp = Bitmap.createBitmap(tmp.cols(), tmp.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(tmp, bmp);
                screenImage.setImageBitmap(bmp);

                text.setText(result);
            }
        });

    }

    private int getCardColor() {
        return 0;
    }

    private String getCardValue() {
        //OCR Genkendelse her!
        return "Value";
    }

    private String getCardSuit(int color) {
        //Her kan det være en god idé at matche kuløren ud fra et andet billede med OpenCVs template matching.
        // Det største match må være kuløren.
        Imgproc.matchTemplate(imageMat, heartsMat, editedImageMat, matchMethod);
        Core.MinMaxLocResult mmr = Core.minMaxLoc(editedImageMat);
        Point matchLoc=mmr.maxLoc;
        Imgproc.rectangle(imageMat, matchLoc, new Point(matchLoc.x + heartsMat.cols(),
                matchLoc.y + heartsMat.rows()), new Scalar(255, 255, 255));

        return "Spades";
    }

    private String colorIdToString(int color) {
        return color > 0 ? "Red" : "Black";
    }


}

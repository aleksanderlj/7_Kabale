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
import org.opencv.core.Mat;

public class CardRecognizer {
        private Activity callingActivity;
        private ImageView screenImage;
        private int currentImage = 1;
        private Mat imageMat, editedImageMat;
        private Bitmap imgBitmap;

        public CardRecognizer(Activity activity) {
            callingActivity = activity;
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
                result = colorIdToString(color);
                result += " " + getCardValue();
                result += " " + getCardSuit(color);

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
        return "Spades";
    }

    private String colorIdToString(int color) {
        return color > 0 ? "Red" : "Black";
    }


}

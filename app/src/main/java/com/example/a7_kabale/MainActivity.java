package com.example.a7_kabale;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!OpenCVLoader.initDebug()) {
            System.out.println("Fuck");
        } else {
            CardRecognizer recognizer = new CardRecognizer(this);
            recognizer.doThings();
        }

    }



}

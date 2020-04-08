package com.example.a7_kabale.OpenCVCamera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a7_kabale.R;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2 {

    JavaCameraView camera;
    Mat picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        OpenCVLoader.initDebug();

        camera = findViewById(R.id.camera_view);
        camera.setCameraIndex(0);
        camera.setCvCameraViewListener(this);
        camera.enableView();

        Button capture_btn = findViewById(R.id.capture_btn);
        capture_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.capture_btn:

                break;
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        picture = new Mat(width, height, CvType.CV_16UC4);
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        picture = inputFrame.rgba();
        return picture;
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.disableView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.enableView();
    }

    @Override
    protected void onDestroy() {
        camera.disableView();
        super.onDestroy();
    }
}

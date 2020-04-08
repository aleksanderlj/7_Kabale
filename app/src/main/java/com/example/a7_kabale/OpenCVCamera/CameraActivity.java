package com.example.a7_kabale.OpenCVCamera;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a7_kabale.R;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2 {

    Button close_btn;
    ImageView preview;
    JavaCameraView camera;
    Mat video, frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        OpenCVLoader.initDebug();
        preview = findViewById(R.id.image_preview);
        close_btn = findViewById(R.id.closepreview_btn);

        camera = findViewById(R.id.camera_view);
        camera.setCameraIndex(0);
        camera.setCvCameraViewListener(this);
        camera.enableView();

        Button capture_btn = findViewById(R.id.capture_btn);
        capture_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Bitmap bm;
        switch (v.getId()){
            case R.id.capture_btn:
                frame = getFrame();
                bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(frame, bm);
                preview.setImageBitmap(bm);
                preview.setVisibility(View.VISIBLE);
                close_btn.setVisibility(View.VISIBLE);
                close_btn.bringToFront();
                break;

            case R.id.closepreview_btn:
                preview.setVisibility(View.GONE);
                close_btn.setVisibility(View.GONE);
                break;
        }
    }

    public Mat getFrame(){
        return video.clone();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        video = new Mat(width, height, CvType.CV_16UC4);
    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        video = inputFrame.rgba();
        return video;
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

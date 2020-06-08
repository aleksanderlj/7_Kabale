package com.example.a7_kabale.OpenCVCamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a7_kabale.MoveHistoryActivity;
import com.example.a7_kabale.R;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2 {

    Button close_btn, capture_btn, confirm_btn, history_btn;
    ImageView preview;
    TextView instructionTextView;
    JavaCameraView camera;
    Mat video, frame;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        OpenCVLoader.initDebug();
        preview = findViewById(R.id.image_preview);
        close_btn = findViewById(R.id.closepreview_btn);
        capture_btn = findViewById(R.id.capture_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        history_btn = findViewById(R.id.history_btn);
        instructionTextView = findViewById(R.id.instructionTextView);

        camera = findViewById(R.id.camera_view);
        camera.setCameraIndex(0);
        camera.setCvCameraViewListener(this);
        camera.enableView();

        capture_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        history_btn.setOnClickListener(this);

        i = new Intent(this, MoveHistoryActivity.class);
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
                capture_btn.setVisibility(View.GONE);
                history_btn.setVisibility(View.GONE);
                close_btn.setVisibility(View.VISIBLE);
                close_btn.bringToFront();
                confirm_btn.setVisibility(View.VISIBLE);
                instructionTextView.setText("Create instructions based on this image?");

                //TODO: evt. indsæt bitmap i lokal database
                break;

            case R.id.closepreview_btn:
                preview.setVisibility(View.GONE);
                close_btn.setVisibility(View.GONE);
                confirm_btn.setVisibility(View.GONE);
                capture_btn.setVisibility(View.VISIBLE);
                history_btn.setVisibility(View.VISIBLE);
                instructionTextView.setText("Capture image for next instruction.");
                break;

            case R.id.confirm_btn:
                //TODO: Check om board state har ændret sig?
                //TODO: Giv instruktion i TextView + opdater historik

                preview.setVisibility(View.GONE);
                close_btn.setVisibility(View.GONE);
                confirm_btn.setVisibility(View.GONE);
                capture_btn.setVisibility(View.VISIBLE);
                history_btn.setVisibility(View.VISIBLE);
                instructionTextView.setText("Move H6 to C7.");
                break;

            case R.id.history_btn:
                startActivity(i);
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

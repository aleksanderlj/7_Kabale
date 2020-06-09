package com.example.a7_kabale.OpenCVCamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a7_kabale.Database.AppDatabase;
import com.example.a7_kabale.Database.DatabaseBuilder;
import com.example.a7_kabale.Database.Entity.Instruction;
import com.example.a7_kabale.MoveHistoryActivity;
import com.example.a7_kabale.R;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2 {

    AppDatabase db;
    Button close_btn, capture_btn, confirm_btn;
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
        Executors.newSingleThreadExecutor().execute(() -> {
            db = DatabaseBuilder.get(this);
            db.instructionDAO().nuke(); //TODO remove this?
        });
        preview = findViewById(R.id.image_preview);
        close_btn = findViewById(R.id.closepreview_btn);
        capture_btn = findViewById(R.id.capture_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        instructionTextView = findViewById(R.id.instructionTextView);

        camera = findViewById(R.id.camera_view);
        camera.setCameraIndex(0);
        camera.setCvCameraViewListener(this);
        camera.enableView();

        capture_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        instructionTextView.setOnClickListener(this);

        i = new Intent(this, MoveHistoryActivity.class);
    }

    @Override
    public void onClick(View v) {
        Bitmap bm;
        String s;
        switch (v.getId()){
            case R.id.capture_btn:
                frame = getFrame();
                bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(frame, bm);
                preview.setImageBitmap(bm);


                capture_btn.setVisibility(View.GONE);
                preview.setVisibility(View.VISIBLE);
                close_btn.setVisibility(View.VISIBLE);
                confirm_btn.setVisibility(View.VISIBLE);
                s = "Create instructions based on this image?";
                instructionTextView.setText(s);

                close_btn.bringToFront();
                confirm_btn.bringToFront();
                close_btn.setElevation(20);
                confirm_btn.setElevation(20);

                //TODO: evt. indsæt bitmap i lokal database
                break;

            case R.id.closepreview_btn:
                s = "retry";
                close_btn.setText(s);
                preview.setVisibility(View.GONE);
                close_btn.setVisibility(View.GONE);
                confirm_btn.setVisibility(View.GONE);
                capture_btn.setVisibility(View.VISIBLE);
                s = "Capture image for next instruction.";
                instructionTextView.setText(s);
                break;

            case R.id.confirm_btn:
                //TODO: Check om board state har ændret sig?
                //TODO: Giv instruktion i TextView + opdater historik

                frame = drawArrow(frame, 200, 200, 500, 500);
                bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(frame, bm);
                preview.setImageBitmap(bm);

                s = "Next";
                close_btn.setText(s);
                confirm_btn.setVisibility(View.GONE);
                setInstruction("Move H6 to C7");
                break;

            case R.id.instructionTextView:
                startActivity(i);
        }
    }

    public void setInstruction(String text){
        Instruction ins = new Instruction(text);
        instructionTextView.setText(ins.getText());

        Executors.newSingleThreadExecutor().execute(() -> {
            db = DatabaseBuilder.get(this);
            db.instructionDAO().insert(ins);
        });
    }

    public Mat drawArrow(Mat image, int x1, int y1, int x2, int y2){
        Point p1 = new Point(x1,y1);
        Point p2 = new Point(x2, y2);
        Scalar color = new Scalar(255,0,0,255);

        Imgproc.arrowedLine(image, p1, p2, color, 3);

        return image;
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

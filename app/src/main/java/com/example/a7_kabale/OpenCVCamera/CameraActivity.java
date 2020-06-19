package com.example.a7_kabale.OpenCVCamera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a7_kabale.ComputerVision.ArrayContourObject;
import com.example.a7_kabale.ComputerVision.BoardDetection;
import com.example.a7_kabale.Database.AppDatabase;
import com.example.a7_kabale.Database.DatabaseBuilder;
import com.example.a7_kabale.Database.Entity.Instruction;
import com.example.a7_kabale.RecyclerView.MoveHistoryActivity;
import com.example.a7_kabale.R;
import com.example.a7_kabale.logic.Card;
import com.example.a7_kabale.logic.GameEngine;
import com.example.a7_kabale.yolo.AssetDownloader;
import com.example.a7_kabale.yolo.YOLOProcessor;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2 {

    AppDatabase db;
    Button close_btn, capture_btn, confirm_btn, overlay_btn;
    ImageView preview;
    TextView instructionTextView;
    JavaCameraView camera;
    Mat video, frame, overlayFrame;
    Intent i;
    Button historyButton;
    YOLOProcessor yoloProcessor;
    ArrayList<ArrayContourObject> fields;
    ArrayList<MatOfPoint> overlayFields;
    Bitmap bm, bmOverlay;
    GameEngine ge;
    BoardDetection bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        System.loadLibrary("opencv_java4");
        OpenCVLoader.initDebug();

        //Assets skal downloades før vi kan initialisere darknet - vi skal helst implementere noget ventenoget her
        yoloProcessor = new YOLOProcessor();
        yoloProcessor.initDarknet(this.getExternalFilesDir(null));

        preview = findViewById(R.id.image_preview);
        close_btn = findViewById(R.id.closepreview_btn);
        capture_btn = findViewById(R.id.capture_btn);
        confirm_btn = findViewById(R.id.confirm_btn);
        instructionTextView = findViewById(R.id.instructionTextView);
        historyButton = findViewById(R.id.history_btn);
        overlay_btn = findViewById(R.id.overlay_btn);
        fields = null;
        overlayFields = null;

        camera = findViewById(R.id.camera_view);
        camera.setCameraPermissionGranted();
        camera.setCameraIndex(0);
        camera.setCvCameraViewListener(this);
        camera.enableView();

        capture_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        overlay_btn.setOnClickListener(this);

        ge = new GameEngine();
        ge.initiateGame();
        bd = new BoardDetection();

        i = new Intent(this, MoveHistoryActivity.class);

        setStateRecording();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.capture_btn:

                frame = getFrame();
                overlayFields = bd.processImage(frame);
                fields = bd.convertMatOfPoint2ArrayContourObject(overlayFields);
                if (fields == null){
                    System.out.println("Error in processImage: Fields was null.");
                    // Reset to former state.
                }

                bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(frame, bm);
                preview.setImageBitmap(bm);

                setStatePictureTaken();
                break;

            case R.id.closepreview_btn:
                setStateRecording();
                break;

            case R.id.confirm_btn:
                //TODO: Check om board state har ændret sig?
                //TODO: Check om instruktioner kan gives baseret på billedet. Hvis fejl, spørg om nyt billede.

                ProgressDialog dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);

                Executors.newSingleThreadExecutor().execute(() -> {
                    try {

                        ArrayList<Card> recognizedCards = yoloProcessor.getCards(frame);
                        ArrayList<ArrayList<Card>> cardList = bd.cardSegmenter(fields, recognizedCards);
                        //Get answer here!!!
                        ge.updateGameState(cardList);

                        //TODO LAV PILE PÅ frame HER
                        bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(frame, bm);

                        overlayFrame = frame.clone();
                        Imgproc.drawContours(overlayFrame, overlayFields, -1, new Scalar(255, 255, 0, 255), 5);
                        overlayFrame = yoloProcessor.DrawMatFromList(overlayFrame, recognizedCards);
                        overlayFrame = drawArrow(overlayFrame, 200, 200, 500, 500);

                        bmOverlay = Bitmap.createBitmap(overlayFrame.cols(), overlayFrame.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(overlayFrame, bmOverlay);

                        runOnUiThread(() -> {
                            preview.setImageBitmap(bm);
                            setStateShowInstruction();
                            setInstruction("Move H6 to C7");
                            dialog.dismiss();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Can't see any cards, try again", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
                    }
                });

                break;

            case R.id.history_btn:
                startActivity(i);
                break;

            case R.id.overlay_btn:
                preview.setImageBitmap(bmOverlay);
                break;
        }
    }

    public void setInstruction(String text) {
        Instruction ins = new Instruction(text);
        instructionTextView.setText(ins.getText());

        Executors.newSingleThreadExecutor().execute(() -> {
            db = DatabaseBuilder.get(this);
            db.instructionDAO().insert(ins);
        });
    }

    public Mat drawArrow(Mat image, int x1, int y1, int x2, int y2) {
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        Scalar color = new Scalar(255, 0, 0, 255);

        Imgproc.arrowedLine(image, p1, p2, color, 3);

        return image;
    }

    public Mat getFrame() {
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
        bringButtonsToFront();
        super.onResume();
        camera.enableView();
    }

    @Override
    protected void onDestroy() {
        camera.disableView();
        super.onDestroy();
    }

    private void setStateRecording() {
        camera.enableView();
        String s = "retry";
        close_btn.setText(s);
        preview.setVisibility(View.GONE);
        close_btn.setVisibility(View.GONE);
        confirm_btn.setVisibility(View.GONE);
        capture_btn.setVisibility(View.VISIBLE);
        overlay_btn.setVisibility(View.GONE);
        s = "Capture image for next instruction.";
        instructionTextView.setText(s);

        bringButtonsToFront();
    }

    private void setStatePictureTaken() {
        camera.disableView();
        preview.setVisibility(View.VISIBLE);
        close_btn.setVisibility(View.VISIBLE);
        confirm_btn.setVisibility(View.VISIBLE);
        capture_btn.setVisibility(View.GONE);
        overlay_btn.setVisibility(View.GONE);
        String s = "Create instructions based on this image?";
        instructionTextView.setText(s);

        bringButtonsToFront();
    }

    private void setStateShowInstruction() {
        camera.disableView();
        String s2 = "Next";
        close_btn.setText(s2);
        preview.setVisibility(View.VISIBLE);
        close_btn.setVisibility(View.VISIBLE);
        confirm_btn.setVisibility(View.GONE);
        capture_btn.setVisibility(View.GONE);
        overlay_btn.setVisibility(View.VISIBLE);

        bringButtonsToFront();
    }

    private void bringButtonsToFront() {
        historyButton.bringToFront();
        confirm_btn.bringToFront();
        close_btn.bringToFront();
        overlay_btn.bringToFront();
        historyButton.setElevation(20);
        confirm_btn.setElevation(20);
        close_btn.setElevation(20);
        overlay_btn.setElevation(20);
    }
}

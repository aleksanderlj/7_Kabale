package com.example.a7_kabale.OpenCVCamera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import com.example.a7_kabale.yolo.YOLOProcessor;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CameraBridgeViewBase.CvCameraViewListener2 {

    AppDatabase db;
    Button close_btn, capture_btn, confirm_btn, overlay_btn, revert_btn;
    ImageView preview, darkBorder;
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
    String instructionFromLogic;
    Point p1, p2;
    boolean cameraOn, drawArrow;
    ArrayList<ArrayList<Card>> cardList;
    TextView revertText;

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
        revert_btn = findViewById(R.id.revert_btn);
        instructionTextView = findViewById(R.id.instructionTextView);
        historyButton = findViewById(R.id.history_btn);
        overlay_btn = findViewById(R.id.overlay_btn);
        darkBorder = findViewById(R.id.dark_border);
        revertText = findViewById(R.id.revertText);
        fields = null;
        overlayFields = null;

        camera = findViewById(R.id.camera_view);
        camera.setCameraPermissionGranted();
        camera.setCameraIndex(0);
        camera.setCvCameraViewListener(this);
        enableCamera();

        capture_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        overlay_btn.setOnClickListener(this);
        revert_btn.setOnClickListener(this);

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
                if (overlayFields == null) {
                    System.out.println("Error in processImage: Fields was null.");
                    Toast.makeText(this, "Can't find board, please try again", Toast.LENGTH_SHORT).show();
                    setStateRecording();
                } else {
                    fields = bd.convertMatOfPoint2ArrayContourObject(overlayFields);

                    bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(frame, bm);
                    preview.setImageBitmap(bm);

                    setStatePictureTaken();

                    ProgressDialog dialog = ProgressDialog.show(this, "Loading", "Please wait...", true);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        try {

                            ArrayList<Card> recognizedCards = yoloProcessor.getCards(frame);
                            cardList = bd.cardSegmenter(fields, recognizedCards);

                            overlayFrame = frame.clone();
                            Imgproc.drawContours(overlayFrame, overlayFields, -1, new Scalar(255, 255, 0, 255), 5);
                            overlayFrame = yoloProcessor.DrawMatFromList(overlayFrame, recognizedCards);

                            bmOverlay = Bitmap.createBitmap(overlayFrame.cols(), overlayFrame.rows(), Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(overlayFrame, bmOverlay);

                            runOnUiThread(() -> {
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
                }

                break;

            case R.id.closepreview_btn:
                setStateRecording();
                break;

            case R.id.confirm_btn:
                //TODO: Check om board state har ændret sig?
                //TODO: Check om instruktioner kan gives baseret på billedet. Hvis fejl, spørg om nyt billede.

                getInstructionFromLogic(ge.updateGameState(cardList));
                if (drawArrow) {
                    frame = drawArrow(frame);
                }
                bm = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(frame, bm);
                preview.setImageBitmap(bm);
                setStateShowInstruction();
                setInstruction(instructionFromLogic);

                break;

            case R.id.history_btn:
                startActivity(i);
                break;

            case R.id.overlay_btn:
                preview.setImageBitmap(bmOverlay);
                break;

            case R.id.revert_btn:
                Executors.newSingleThreadExecutor().execute(() -> {
                    db = DatabaseBuilder.get(this);
                    List<Instruction> instructions = db.instructionDAO().getAll();
                    db.instructionDAO().delete(instructions.get(instructions.size()-1));
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Reverted the last move.", Toast.LENGTH_SHORT).show();
                    });
                });
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

    public Mat drawArrow(Mat image) {
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
        if (cameraOn) {
            disableCamera();
            cameraOn = true;
        } else {
            disableCamera();
        }
    }

    @Override
    protected void onResume() {
        bringButtonsToFront();
        super.onResume();
        if (cameraOn) {
            enableCamera();
        }
    }

    @Override
    protected void onDestroy() {
        disableCamera();
        super.onDestroy();
    }

    private void setStateRecording() {
        enableCamera();
        preview.setVisibility(View.GONE);
        close_btn.setVisibility(View.GONE);
        confirm_btn.setVisibility(View.GONE);
        capture_btn.setVisibility(View.VISIBLE);
        overlay_btn.setVisibility(View.GONE);
        revert_btn.setVisibility(View.VISIBLE);
        revertText.setText("Revert");
        revertText.setVisibility(View.VISIBLE);
        String s = "Capture image for next instruction.";
        instructionTextView.setText(s);

        bringButtonsToFront();
    }

    private void setStatePictureTaken() {
        disableCamera();
        String s = "retry";
        close_btn.setText(s);
        preview.setVisibility(View.VISIBLE);
        close_btn.setVisibility(View.VISIBLE);
        confirm_btn.setVisibility(View.VISIBLE);
        capture_btn.setVisibility(View.GONE);
        overlay_btn.setVisibility(View.VISIBLE);
        revert_btn.setVisibility(View.GONE);
        revertText.setVisibility(View.VISIBLE);
        revertText.setText("Overlay");
        s = "Create instructions based on this image?";
        instructionTextView.setText(s);

        bringButtonsToFront();
    }

    private void setStateShowInstruction() {
        disableCamera();
        String s2 = "Next";
        close_btn.setText(s2);
        preview.setVisibility(View.VISIBLE);
        close_btn.setVisibility(View.VISIBLE);
        confirm_btn.setVisibility(View.GONE);
        capture_btn.setVisibility(View.GONE);
        overlay_btn.setVisibility(View.GONE);
        revert_btn.setVisibility(View.GONE);
        revertText.setVisibility(View.GONE);

        bringButtonsToFront();
    }

    private void bringButtonsToFront() {
        historyButton.bringToFront();
        confirm_btn.bringToFront();
        close_btn.bringToFront();
        overlay_btn.bringToFront();
        capture_btn.bringToFront();
        revert_btn.bringToFront();
        historyButton.setElevation(20);
        confirm_btn.setElevation(20);
        close_btn.setElevation(20);
        overlay_btn.setElevation(20);
        capture_btn.setElevation(20);
        revert_btn.setElevation(20);
    }

    private void disableCamera() {
        cameraOn = false;
        darkBorder.setVisibility(View.VISIBLE);
        camera.disableView();
    }

    private void enableCamera() {
        cameraOn = true;
        darkBorder.setVisibility(View.GONE);
        camera.enableView();
    }

    private void getInstructionFromLogic(Card[] returnCards) {
        String i = "";
        if (returnCards.length == 1) {
            drawArrow = false;
            // kig på suit af index 0
            switch (returnCards[0].getValue()) {
                case 14:
                    i = "Turn the top deck.";
                    break;
                case 15:
                    i = "Game won!";
                    break;
                case 16:
                    i = "Game lost.";
                    break;
            }
        } else {
            drawArrow = true;
            try {
                getPointsFromLogic(returnCards);
            } catch (Exception e){
                e.printStackTrace();
                drawArrow = false;
            }

            if (returnCards[1].getValue() == 0) {
                String tRow = returnCards[1].getSuit();
                i = "Move " + getCardName(returnCards[0]) + " to " + tRow;
            } else {
                i = "Move " + getCardName(returnCards[0]) + " to " + getCardName(returnCards[1]);
            }
        }
        instructionFromLogic = i;
    }

    private void getPointsFromLogic(Card[] cards) {
        Card card1 = cards[0];
        Card card2 = cards[1];
        p1 = getMiddleOfCard(card1.getRect());
        p2 = getMiddleOfCard(card2.getRect());
    }

    private Point getMiddleOfCard(Rect rect) {
        double x = rect.tl().x + (rect.br().x - rect.tl().x) / 2;
        double y = rect.tl().y + (rect.br().y - rect.tl().y) / 2;
        return new Point(x, y);
    }

    private String getCardName(Card card) {
        String s = "";
        switch (card.getValue()) {
            case 1:
                s = "Ace of ";
                break;
            case 2:
                s = "Two of ";
                break;
            case 3:
                s = "Three of ";
                break;
            case 4:
                s = "Four of ";
                break;
            case 5:
                s = "Five of ";
                break;
            case 6:
                s = "Six of ";
                break;
            case 7:
                s = "Seven of ";
                break;
            case 8:
                s = "Eight of ";
                break;
            case 9:
                s = "Nine of ";
                break;
            case 10:
                s = "Ten of ";
                break;
            case 11:
                s = "Jack of ";
                break;
            case 12:
                s = "Queen of ";
                break;
            case 13:
                s = "King of ";
                break;
        }
        s += card.getSuit();

        switch (card.getSuit()) {
            case "Diamonds":
                s += " ♦";
                break;
            case "Hearts":
                s += " ♥";
                break;
            case "Clubs":
                s += " ♣";
                break;
            case "Spades":
                s += " ♠";
                break;
        }

        return s;
    }
}

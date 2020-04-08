package com.example.a7_kabale.AndroidCamera;

import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a7_kabale.R;

public class AndroidCameraActivity extends AppCompatActivity implements View.OnClickListener {
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_android);

        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout frame = findViewById(R.id.camera_frame);
        frame.addView(mPreview);

        setPictureCallback();

        Button capture_btn = findViewById(R.id.capture_btn);
        capture_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.capture_btn:
                //mCamera.takePicture(null, null, picture);
                break;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){
        }
        return c;
    }

    private void setPictureCallback(){
        mPicture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                //File output = getOu
            }
        };
    }

    private void createPictureFile(){
        //File dir = new File(Environment.getExternalStoragePublicDirectory());
    }
}

// TODO https://developer.android.com/guide/topics/media/camera#manifest
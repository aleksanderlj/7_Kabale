package com.example.a7_kabale;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    private Camera mCamera;
    private CameraPreview mPreview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout frame = findViewById(R.id.camera_frame);
        frame.addView(mPreview);
    }

    @Override
    public void onClick(View v) {

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
}

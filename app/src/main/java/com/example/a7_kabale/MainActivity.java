package com.example.a7_kabale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer wauw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wauw = MediaPlayer.create(this, R.raw.wow);

        // Permissions
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);

        Button start_btn = findViewById(R.id.start_btn);
        start_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_btn:
                wauw.start();
                Intent i = new Intent(this, CameraActivity.class);
                startActivity(i);
                break;
        }
    }
}

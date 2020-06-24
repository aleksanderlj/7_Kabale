package com.example.a7_kabale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a7_kabale.Database.*;
import com.example.a7_kabale.OpenCVCamera.CameraActivity;
import com.example.a7_kabale.logic.GameEngine;
import com.example.a7_kabale.yolo.AssetDownloader;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MediaPlayer wauw;
    private Button newGameButton;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wauw = MediaPlayer.create(this, R.raw.wow);

        newGameButton = findViewById(R.id.newGame_btn);

        getPermissions();

        // TODO get files from web
        AssetDownloader assetDownloader = new AssetDownloader(this);
        //assetDownloader.downloadAssets();
        assetDownloader.initLocalAssets();

        newGameButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, CameraActivity.class);
        switch (v.getId()){
            case R.id.newGame_btn:
                Executors.newSingleThreadExecutor().execute(() -> {
                    wauw.start();
                    db = DatabaseBuilder.get(this);
                    db.instructionDAO().nuke();
                    startActivity(i);
                });
                break;
        }
    }

    private void getPermissions(){
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        for(String s : permissions){
            if(ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{s}, 1);
        }
    }
}

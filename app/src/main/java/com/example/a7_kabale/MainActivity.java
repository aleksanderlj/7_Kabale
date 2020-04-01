package com.example.a7_kabale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a7_kabale.logic.GameEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameEngine gm = new GameEngine();
        gm.initiateGame();
    }
}

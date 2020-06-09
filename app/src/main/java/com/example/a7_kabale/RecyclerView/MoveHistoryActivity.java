package com.example.a7_kabale.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a7_kabale.Database.AppDatabase;
import com.example.a7_kabale.Database.DatabaseBuilder;
import com.example.a7_kabale.Database.Entity.Instruction;
import com.example.a7_kabale.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MoveHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private AppDatabase db;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<Instruction> instructions;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_history);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        instructions = new ArrayList<>();

        Executors.newSingleThreadExecutor().execute(() -> {
            db = DatabaseBuilder.get(this);
            instructions = db.instructionDAO().getAll();
            adapter = new RecyclerAdapter(instructions, this);
            recyclerView.setAdapter(adapter);
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
        }
    }
}

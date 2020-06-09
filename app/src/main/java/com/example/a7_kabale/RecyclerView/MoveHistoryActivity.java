package com.example.a7_kabale.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.a7_kabale.R;
import com.example.a7_kabale.RecyclerView.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class MoveHistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<HistoryItem> historyItems;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_history);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        historyItems = new ArrayList<>();

        for (int i = 0; i<=10; i++){
            HistoryItem historyItem = new HistoryItem("Move H7 to C8.");
            historyItems.add(historyItem);
        }

        adapter = new RecyclerAdapter(historyItems, this);
        recyclerView.setAdapter(adapter);

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

package com.example.a7_kabale.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a7_kabale.R;
import com.example.a7_kabale.RecyclerView.model.HistoryItem;

import java.util.List;

// https://www.youtube.com/watch?v=gGFvbvkZiMs
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public RecyclerAdapter(List<HistoryItem> historyItems, Context context) {
        this.historyItems = historyItems;
        this.context = context;
    }

    private List<HistoryItem> historyItems;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryItem historyItem = historyItems.get(position);
        holder.historyText.setText(historyItem.getHistoryText());
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView historyText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyText = (TextView) itemView.findViewById(R.id.historyText);
        }
    }
}

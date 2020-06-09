package com.example.a7_kabale.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a7_kabale.Database.Entity.Instruction;
import com.example.a7_kabale.R;

import java.util.List;

// https://www.youtube.com/watch?v=gGFvbvkZiMs
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    public RecyclerAdapter(List<Instruction> instructions, Context context) {
        this.instructions = instructions;
        this.context = context;
    }

    private List<Instruction> instructions;
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
        Instruction instruction = instructions.get(position);
        holder.historyText.setText(instruction.getText());
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView historyText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyText = (TextView) itemView.findViewById(R.id.historyText);
        }
    }
}

package com.example.a7_kabale.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Instruction {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "instruction_id")
    private long instruction_id;

    @ColumnInfo(name = "text")
    private String text;

    public Instruction(){}

    @Ignore
    public Instruction(String text){
        this.text = text;
    }

    public long getInstruction_id() {
        return instruction_id;
    }

    public void setInstruction_id(long instruction_id) {
        this.instruction_id = instruction_id;
    }
}

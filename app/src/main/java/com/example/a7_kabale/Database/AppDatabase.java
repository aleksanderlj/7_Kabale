package com.example.a7_kabale.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.a7_kabale.Database.DAO.InstructionDAO;
import com.example.a7_kabale.Database.Entity.Instruction;

@Database(entities = {Instruction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract InstructionDAO instructionDAO();
}

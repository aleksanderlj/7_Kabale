package com.example.a7_kabale.Database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseBuilder {
    private static AppDatabase db;

    public static AppDatabase get(Context context){
        if(db == null){
            db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "VikingeDB").fallbackToDestructiveMigration().build();
        }
        return db;
    }
}

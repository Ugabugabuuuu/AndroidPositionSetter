package com.example.lab9;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Marker.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MarkerDao markerDao();
}
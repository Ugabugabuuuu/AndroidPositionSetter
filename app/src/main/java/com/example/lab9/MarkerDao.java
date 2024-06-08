package com.example.lab9;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MarkerDao{
    @Insert
    void insertCord(Marker marker);
    @Query("SELECT * FROM marker")
    List<Marker> getAll();

    @Query("SELECT * FROM marker WHERE uid IN (:markerIds)")
    List<Marker> loadAllByIds(int[] markerIds);

@Query("SELECT * FROM marker WHERE longitude LIKE :longitude AND " +
        "latitude LIKE :latitude LIMIT 1")
    Marker findByName(String longitude, String latitude);

    @Insert
    void insertAll(Marker... markers);

    @Delete
    void delete(Marker marker);
    @Query("DELETE FROM marker")
    void deleteAll();
}

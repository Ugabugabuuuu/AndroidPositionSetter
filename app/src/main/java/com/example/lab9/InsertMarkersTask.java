package com.example.lab9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InsertMarkersTask {
    private static final Executor executor = Executors.newSingleThreadExecutor();

    public static void insertCoordinates(final Marker marker) {
        CompletableFuture.runAsync(() -> {
            MainActivity.db.markerDao().insertCord(marker);
        }, executor);
    }
}

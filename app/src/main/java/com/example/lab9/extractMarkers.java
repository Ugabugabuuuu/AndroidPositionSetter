package com.example.lab9;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class extractMarkers {
    private static final Executor executor = Executors.newSingleThreadExecutor();

    public static void extractMarkers() {
        CompletableFuture.runAsync(() -> {
            MainActivity.db.markerDao().getAll();
        }, executor);
    }
}

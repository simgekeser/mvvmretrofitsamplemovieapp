package com.example.mvvmretrofitjavamovieapp;

import com.example.mvvmretrofitjavamovieapp.repositories.MovieRepository;
import com.example.mvvmretrofitjavamovieapp.request.MovieApiClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecuters {

    private static AppExecuters instance;

    public static AppExecuters getInstance() {
        if(instance==null){
            instance = new AppExecuters();
        }
        return instance;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}

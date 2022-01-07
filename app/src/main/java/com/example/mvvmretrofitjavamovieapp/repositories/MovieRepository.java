package com.example.mvvmretrofitjavamovieapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmretrofitjavamovieapp.models.MovieModel;
import com.example.mvvmretrofitjavamovieapp.request.MovieApiClient;
import com.example.mvvmretrofitjavamovieapp.utils.MovieApi;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    public static MovieRepository getInstance() {
        if(instance==null){
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();}

}

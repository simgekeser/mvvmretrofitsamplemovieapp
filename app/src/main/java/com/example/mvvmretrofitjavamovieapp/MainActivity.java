package com.example.mvvmretrofitjavamovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mvvmretrofitjavamovieapp.models.MovieModel;
import com.example.mvvmretrofitjavamovieapp.request.Service;
import com.example.mvvmretrofitjavamovieapp.response.MovieSearchResponse;
import com.example.mvvmretrofitjavamovieapp.utils.Credentials;
import com.example.mvvmretrofitjavamovieapp.utils.MovieApi;
import com.example.mvvmretrofitjavamovieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn;
    //ViewModel
    private MovieListViewModel movieListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        btn.setOnClickListener(v -> {
            //GetRetrofitResponse();
            GetRestrofitResponseById();
        });

    }

    //observing any data changes
    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(this,
                movieModels -> {
                    //Observing any data change


                });
    }

    private void GetRetrofitResponse() {
        MovieApi movieApi = Service.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
                Credentials.API_KEY,
                "Action",
                "1"
        );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200) {
                    Log.v("TAG", "the response" + response.body().toString());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    for (MovieModel movie : movies) {
                        Log.v("TAG", "the list" + movie.getTitle());
                    }
                } else {
                    try {
                        Log.v("TAG", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }

    private void GetRestrofitResponseById() {
        MovieApi movieApi = Service.getMovieApi();

        Call<MovieModel> responseCall = movieApi
                .getMovie(
                        550,
                        Credentials.API_KEY
                );

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response.code() == 200) {
                    Log.v("TAG", "the response" + response.body().toString());

                    MovieModel movie = response.body();
                    Log.v("TAG", "the list response: " + movie.getTitle());
                } else {
                    try {
                        Log.v("TAG", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }
}
package com.example.mvvmretrofitjavamovieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmretrofitjavamovieapp.AppExecuters;
import com.example.mvvmretrofitjavamovieapp.models.MovieModel;
import com.example.mvvmretrofitjavamovieapp.response.MovieSearchResponse;
import com.example.mvvmretrofitjavamovieapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public void searchMovie(String query,int pageNumber) {

        if(retrieveMoviesRunnable!=null){
            retrieveMoviesRunnable=null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query,pageNumber);

        final Future myHandler = AppExecuters.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecuters.getInstance().networkIO().schedule(() -> {
                myHandler.cancel(true);
            }, 3000, TimeUnit.MILLISECONDS);
        }

    class RetrieveMoviesRunnable implements Runnable {

            private String query;
            private int pageNumber;
            boolean cancelRequest;

            public RetrieveMoviesRunnable(String query, int pageNumber) {
                this.query = query;
                this.pageNumber = pageNumber;
                this.cancelRequest = false;
            }

            @Override
            public void run() {

                try {
                    Response response = getMovies(query, pageNumber).execute();
                    if (cancelRequest) {
                        return;
                    }
                    if (response.code() == 200) {
                        List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());

                        if (pageNumber == 1) {
                            mMovies.postValue(list);
                        } else {
                            List<MovieModel> currentMovies = mMovies.getValue();
                            currentMovies.addAll(list);
                            mMovies.postValue(currentMovies);
                        }
                    } else {
                        Log.v("TAG", "Error :" + response.errorBody().string());
                        mMovies.postValue(null);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    mMovies.postValue(null);
                }


            }

            private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
                return Service.getMovieApi()
                        .searchMovie(
                                Credentials.API_KEY,
                                query,
                                String.valueOf(pageNumber));
            }

            private void cancelRequest() {
                Log.v("TAG", "Cancelling request");
                cancelRequest = true;
            }

        }


    }

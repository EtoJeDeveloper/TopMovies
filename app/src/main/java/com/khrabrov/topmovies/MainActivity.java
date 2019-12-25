package com.khrabrov.topmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.khrabrov.topmovies.adapter.MovieAdapter;
import com.khrabrov.topmovies.model.MoviePage;
import com.khrabrov.topmovies.model.MoviePageResult;
import com.khrabrov.topmovies.network.GetMovieDataService;
import com.khrabrov.topmovies.network.NetworkConnectionInterceptor;
import com.khrabrov.topmovies.network.RetrofitInstance;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static int PAGE = 1;
    private static String API_KEY = "2f46eaf6917adf4aaac5aae677967598";
    private static String LANGUAGE = "en-US";
    private LinearLayout linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear = findViewById(R.id.linear);

        //Замета цвета progressBar
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#b1bfca"), PorterDuff.Mode.SRC_IN);

        progressBar.setVisibility(View.VISIBLE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetMovieDataService getMovieDataService = RetrofitInstance.getRetrofitInstance(this).create(GetMovieDataService.class);
        Call<MoviePage> call = getMovieDataService.getPopularMovies(API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MoviePage>() {
            @Override
            public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                MoviePage moviePage = response.body();
                List<MoviePageResult> listOfMovies = null;
                if (moviePage != null) {
                    listOfMovies = moviePage.getResults();
                }

                recyclerView.setAdapter(new MovieAdapter(listOfMovies));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MoviePage> call, Throwable t) {
                //Обработка отсутствия интернета
                if(t instanceof NetworkConnectionInterceptor.NoConnectivityException) {
                    Snackbar.make(linear,"No Internet Connection!", Snackbar.LENGTH_LONG).show();
                }
            }
        });


    }
}

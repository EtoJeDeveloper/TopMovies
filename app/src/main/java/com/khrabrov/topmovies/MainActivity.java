package com.khrabrov.topmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.khrabrov.topmovies.Adapter.MovieAdapter;
import com.khrabrov.topmovies.Model.MoviePage;
import com.khrabrov.topmovies.Model.Result;
import com.khrabrov.topmovies.Network.GetMovieDataService;
import com.khrabrov.topmovies.Network.RetrofitInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static int PAGE = 1;
    private static String API_KEY = "2f46eaf6917adf4aaac5aae677967598";
    private static String LANGUAGE = "en-US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetMovieDataService getMovieDataService = RetrofitInstance.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MoviePage> call = getMovieDataService.getPopularMovies(API_KEY, LANGUAGE, PAGE);
        call.enqueue(new Callback<MoviePage>() {
            @Override
            public void onResponse(Call<MoviePage> call, Response<MoviePage> response) {
                MoviePage moviePage = response.body();
                List<Result> listOfMovies = null;
                if (moviePage != null) {
                    listOfMovies = moviePage.getResults();
                }
                recyclerView.setAdapter(new MovieAdapter(listOfMovies));

            }

            @Override
            public void onFailure(Call<MoviePage> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }
}

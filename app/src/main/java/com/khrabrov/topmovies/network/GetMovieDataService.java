package com.khrabrov.topmovies.network;


import com.khrabrov.topmovies.model.MoviePage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetMovieDataService {
    @GET("movie/popular/")
    Call<MoviePage> getPopularMovies(
            @Query("api_key") String userKey,
            @Query("language") String language,
            @Query("page") int page);
}

//https://api.themoviedb.org/3/movie/popular?api_key=2f46eaf6917adf4aaac5aae677967598&language=en-US&page=1
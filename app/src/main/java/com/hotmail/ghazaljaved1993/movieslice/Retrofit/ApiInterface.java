package com.hotmail.ghazaljaved1993.movieslice.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.hotmail.ghazaljaved1993.movieslice.data.GenreMovies;
import com.hotmail.ghazaljaved1993.movieslice.data.GenreResponse;
import com.hotmail.ghazaljaved1993.movieslice.data.MoviesResponse;
import com.hotmail.ghazaljaved1993.movieslice.data.TVResponse;

/**
 * Created by Ghazal on 1/11/2017.
 */

public interface ApiInterface {

    @GET("movie/{category}")
    Call<MoviesResponse> getTopRatedMovies(@Path("category") String category,@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);


    @GET("tv/{category}")
    Call<TVResponse> getTopRatedTV(@Path("category") String category, @Query("api_key") String apiKey);


    @GET("tv/{id}")
    Call<TVResponse> getTvDetails(@Path("id") int id, @Query("api_key") String apiKey);


    @GET("genre/movie/list")
    Call<GenreResponse> getGenre(@Query("api_key") String apiKey);

    @GET("genre/{genre_id}/movies")
    Call<GenreMovies> getGenreMovies(@Path("genre_id") int id, @Query("api_key") String apiKey);


    @GET("search/movie")
    Call<MoviesResponse> getSearchMovie(@Query("query") String name,@Query("api_key") String apiKey);

}

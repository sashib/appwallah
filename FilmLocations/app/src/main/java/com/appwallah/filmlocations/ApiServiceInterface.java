package com.appwallah.filmlocations;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by sbommakanty on 7/8/17.
 */

public interface ApiServiceInterface {

    @GET
    Call<List<FilmLocation>> getFilmLocations(@Url String url);

    @GET
    Call<ResponseBody> getGeoCode(@Url String url, @Query("address") String address, @Query("key") String key);


}

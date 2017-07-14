package com.appwallah.filmlocations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sbommakanty on 7/8/17.
 */

public class ApiService {

    public static final String API_URL = "https://data.sfgov.org/resource/wwmu-gmzc.json/";
    public static final String GEO_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    public static final String GEO_API_KEY = "AIzaSyAM-60eTagA7PL8YyZbsf9QeEUS6Qqfcto";

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        return client;
    }
    public static ApiServiceInterface getApiService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ApiServiceInterface.class);
    }
}
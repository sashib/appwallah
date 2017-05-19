package com.appwallah.ideawallah.api;

import com.appwallah.ideawallah.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sbommakanty on 5/18/17.
 */

public class IdeawallahApiService {

    public static final String API_URL = "https://ideawallah.azurewebsites.net/";

    private static OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();
        return client;
    }
    public static IdeawallahApiServiceInterface getApiService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(IdeawallahApiServiceInterface.class);
    }
}

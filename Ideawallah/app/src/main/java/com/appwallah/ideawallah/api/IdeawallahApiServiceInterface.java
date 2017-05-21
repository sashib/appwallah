package com.appwallah.ideawallah.api;

import com.appwallah.ideawallah.models.HashTag;
import com.appwallah.ideawallah.models.Idea;
import com.appwallah.ideawallah.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sbommakanty on 5/18/17.
 */

public interface IdeawallahApiServiceInterface {

    @GET("hashtags")
    Call<List<HashTag>> getHashTags(@Header("X-Access-Token") String token);

    @GET("ideas")
    Call<List<Idea>> getIdeas(@Header("X-Access-Token") String token, @Query("limit") String limit, @Query("page") String page);

    @GET("ideas/{hashtag}")
    Call<List<Idea>> getIdeasByHashTag(@Header("X-Access-Token") String token, @Path("hashtag") String hashtag, @Query("limit") String limit, @Query("page") String page);

    @Headers("Accept: application/json")
    @POST("users")
    Call<User> createUser(@Header("X-Access-Token") String token, @Body User user);

    @Headers("Accept: application/json")
    @POST("ideas")
    Call<Idea> createIdea(@Header("X-Access-Token") String token, @Body Idea idea);

}

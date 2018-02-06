package com.example.miyu1.loginapp.data.remote;
import com.example.miyu1.loginapp.data.model.Post;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
/**
 * Created by nickd on 11/1/17.
 */

public interface APIService {
    @Headers("Content-Type: application/json")
    @POST("login")
    Call<Post> savePost(@Body JSONObject body);

    @Headers("Content-Type: application/json")
    @POST("getUser")
    Call<Post> findUser(@Body JSONObject body);
}

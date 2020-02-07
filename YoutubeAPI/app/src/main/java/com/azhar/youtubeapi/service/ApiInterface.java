package com.azhar.youtubeapi.service;

import com.azhar.youtubeapi.model.Videos;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("/youtube/v3/search")
    Call<Videos> getVideos(@QueryMap Map<String, String> params);
}

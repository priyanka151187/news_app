package com.e.newsapp.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {



    @Retry
    @GET("v2/everything?q=bitcoin&from=2020-11-30&sortBy=publishedAt&apiKey=a1718951c9b0483cb79a8328c86f7474")
    Call<JsonObject> NewsList();
}

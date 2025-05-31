package com.kilavuzhilmi.news_app.services

import com.kilavuzhilmi.news_app.model.NewResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService{
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country: String = "tr",


        @Query("category")
        category: String = "general",


        @Query("apiKey")
        apiKey: String

    ): NewResponse

}
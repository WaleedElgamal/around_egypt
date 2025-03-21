package com.example.aroundegypt.data.remote

import com.example.aroundegypt.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ExperienceApiService {
    @GET("/api/v2/experiences")
    suspend fun getRecommendedExperiences(@Query("filter[recommended]") recommended: Boolean): ApiResponse

    @GET("/api/v2/experiences")
    suspend fun getRecentExperiences(): ApiResponse

    @GET("/api/v2/experiences")
    suspend fun searchExperiences(@Query("filter[title]") searchText: String): ApiResponse

    @POST("/api/v2/experiences/{id}/like")
    suspend fun likeExperience(@Path("id") id: String): Response<Unit>
}
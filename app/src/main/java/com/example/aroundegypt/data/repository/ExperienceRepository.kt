package com.example.aroundegypt.data.repository

import android.content.Context
import android.util.Log
import com.example.aroundegypt.data.local.database.AppDatabase
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.data.remote.ApiClient
import com.example.aroundegypt.data.utils.isNetworkAvailable
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

private const val TAG = "ExperienceRepository"
class ExperienceRepository(
    private val context: Context
)
{
    private val experienceDao = AppDatabase.getInstance(context).experienceDao()
    suspend fun fetchRecommendedExperiences() : List<Experience>{
        return try {
            if (isNetworkAvailable(context))
            {
                val response = ApiClient.apiService.getRecommendedExperiences(recommended = true)
                experienceDao.insertAll(response.data)
                response.data
            }
            else
            {
                val response = experienceDao.getRecommendedExperiences()
                response
            }
        }catch (e: Exception){
            Log.e(TAG, "Error fetching recommended experiences: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun fetchRecentExperiences() : List<Experience>{
        return try {
            if (isNetworkAvailable(context))
            {
                val response = ApiClient.apiService.getRecentExperiences()
                experienceDao.insertAll(response.data)
                response.data
            }
            else
            {
                val response = experienceDao.getRecentExperiences()
                println(response)
                response
            }
        }catch (e: Exception){
            Log.e(TAG, "Error fetching recent experiences: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun fetchSearchExperiences(title: String) : List<Experience>{
        return try {
            val response = ApiClient.apiService.searchExperiences(searchText = title)
            response.data
        }catch (e: Exception){
            Log.e(TAG, "Error fetching search experiences: ${e.message}", e)
            emptyList()
        }
    }


    suspend fun likeExperience(id: String) : Response<Unit> {
        return try {
             ApiClient.apiService.likeExperience(id = id)
        }catch (e: Exception){
            Log.e(TAG, "Error fetching like experience: ${e.message}", e)
            Response.error(500, "Error fetching like experience".toResponseBody(null))
        }
    }
}
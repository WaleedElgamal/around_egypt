package com.example.aroundegypt.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aroundegypt.data.model.Experience

@Dao
interface ExperienceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(experiences: List<Experience>)

    @Query("SELECT * FROM experiences WHERE recommended = 1")
    suspend fun getRecommendedExperiences(): List<Experience>

    @Query("SELECT * FROM experiences")
    suspend fun getRecentExperiences(): List<Experience>
}
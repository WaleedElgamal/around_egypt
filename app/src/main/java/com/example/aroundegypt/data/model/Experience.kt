package com.example.aroundegypt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ApiResponse(
    val data: List<Experience>
)
@Entity(tableName = "experiences")
data class Experience(
    @PrimaryKey val id: String,
    var title: String,
    var cover_photo: String,
    var description: String,
    var views_no: Int,
    var likes_no: Int,
    var recommended: Int,
    var city: Map<String,Any>
)
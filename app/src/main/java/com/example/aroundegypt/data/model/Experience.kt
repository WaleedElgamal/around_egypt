package com.example.aroundegypt.data.model

data class ApiResponse(
    val data: List<Experience>
)
data class Experience(
    val id: String,
    var title: String,
    var cover_photo: String,
    var description: String,
    var views_no: Int,
    var likes_no: Int,
    var recommended: Int
)
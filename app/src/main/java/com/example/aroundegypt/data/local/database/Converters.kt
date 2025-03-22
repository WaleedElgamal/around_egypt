package com.example.aroundegypt.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromMap(value: Map<String, Any>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMap(value: String?): Map<String, Any>? {
        val mapType = object : TypeToken<Map<String, Any>>() {}.type
        return Gson().fromJson(value, mapType)
    }
}

package com.example.lab8

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ItemConverter {
    companion object{

        @TypeConverter
        @JvmStatic
        fun inventarioToString(list: List<Item>): String{
            val gson = Gson()
            val type = object: TypeToken<List<Item>>() {}.type
            return gson.toJson(list, type)
        }

        @TypeConverter
        @JvmStatic
        fun stringToInventario(json: String): List<Item>{
            val gson = Gson()
            val type = object: TypeToken<List<Item>>() {}.type
            return gson.fromJson(json, type)
        }
    }
}
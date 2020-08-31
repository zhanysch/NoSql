package com.example.asynctascretrofit.model.LocalRoom

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.asynctascretrofit.model.Current.Coord
import com.example.asynctascretrofit.model.Current.Sys
import com.example.asynctascretrofit.model.Current.Weather
import com.example.asynctascretrofit.model.ForecastDays.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// в дата класс возникает ошибка для т.к имеется val daily: List<ForCastDailyFourth> такие значение поэтому создан конвертер
object TypeConverter {

    //Object
    @JvmStatic
    @TypeConverter
    fun CoordtoString(model: CoordThird): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun coordtoObject(text: String): CoordThird? {
        if (TextUtils.isEmpty(text))
            return null
            return Gson().fromJson(text, CoordThird::class.java)
    }


    @JvmStatic
    @TypeConverter
    fun citytoString(model: CityModelSecond): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun citytoObject(text: String): CityModelSecond? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, CityModelSecond::class.java)
    }

    @JvmStatic
    @TypeConverter
    fun ForcastModelOnetoString(model: ForcastModelOne): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun ForcastModelOnetoObject(text: String): ForcastModelOne? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, ForcastModelOne::class.java)
    }
    @JvmStatic
    @TypeConverter
    fun ForCastDailyFourthtoString(model: ForCastDailyFourth): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun ForCastDailyFourthtoObject(text: String): ForCastDailyFourth? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, ForCastDailyFourth::class.java)
    }

    @JvmStatic
    @TypeConverter
    fun ForTempFifthtoString(model: ForTempFifth): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun ForTempFifthtoObject(text: String): ForTempFifth? {
        if (TextUtils.isEmpty(text))
            return null
        return Gson().fromJson(text, ForTempFifth::class.java)
    }
    @JvmStatic
    @TypeConverter
    fun listForCastDailyFourthToString(model: List<ForCastDailyFourth>): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun listForCastDailyFourthToObject(text: String?): List<ForCastDailyFourth>? {
        if (text == null) return mutableListOf()
        val typeToken = object : TypeToken<List<ForCastDailyFourth>>() {}.type
        return Gson().fromJson(text, typeToken)
    }
    // //array of object
    @JvmStatic
    @TypeConverter
    fun weatherToString(model: List<Weather>): String {
        return Gson().toJson(model)
    }

    @JvmStatic
    @TypeConverter
    fun weatherToObject(text: String?): List<Weather>? {
        if (text == null) return mutableListOf()
        val typeToken = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson(text, typeToken)
    }
}
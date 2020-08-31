package com.example.asynctascretrofit.model.ForecastDays

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.asynctascretrofit.model.Current.Weather
import com.google.gson.annotations.SerializedName
@Entity
class ForTempFifth (
    @PrimaryKey
    val id: Int,
    val day : Double,
    val min : Double,
    val max : Double,
    val night : Double,
    val eve : Double,
    val morn : Double,
    @SerializedName("weather") val weather : List<Weather> // берем из класса current weather  для отображ картинки
)
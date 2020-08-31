package com.example.asynctascretrofit.model.ForecastDays

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityModelSecond (
    @PrimaryKey
    val id : Int,
    val name : String,
    val country : String,
    val population : Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int,
    val coord: CoordThird
)
package com.example.asynctascretrofit.model.ForecastDays

data class CityModelSecond (
    val id : Int,
    val name : String,
    val country : String,
    val population : Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int,
    val coord: CoordThird
)
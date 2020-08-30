package com.example.asynctascretrofit.model.ForecastDays

data class ForcastModelOne(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val city: CityModelSecond,
    val list: List<ForCastItemNumbeZeroFourth>  // zero потому что идет по позиции 0 . 1 .2... итд по позиции
)
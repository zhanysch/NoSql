package com.example.asynctascretrofit.data


import com.example.asynctascretrofit.model.Current.CurrentWeather
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String

    ) : Call<CurrentWeather>


    @GET("data/2.5/forecast")
    fun forecast(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units : String
    ) : Call<ForcastModelOne>










}
package com.example.asynctascretrofit.model.data


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

    @GET("data/2.5/weather")
    fun getWeatherbycoordianates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appid: String,
        @Query("units") units : String
    ) : Call<CurrentWeather>


    @GET("data/2.5/forecast")    // на 5 дней
    fun forecast(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units : String
    ) : Call<ForcastModelOne>


    @GET("data/2.5/onecall")    // на 7 дней
    fun onecall(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude : String,
        @Query("appid") appid: String,
        @Query("units") units : String
    ) : Call<ForcastModelOne>














}
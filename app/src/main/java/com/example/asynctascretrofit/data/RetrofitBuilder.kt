package com.example.asynctascretrofit.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private var service : WeatherService? = null

    fun getService() : WeatherService? {
        if ( service == null )
            service = buildRetrofit()

        return service
    }

    private fun  buildRetrofit(): WeatherService {     //делает запрос в интернет

        val service =
            Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService:: class.java)


        return service

    }


  //  data/2.5/weather?q=New%20York&appid=f06c3e4844cbfcd45efad66f15112ca2
}
package com.example.asynctascretrofit

import CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asynctascretrofit.data.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitBuilder.getService()
            ?.getWeather("New York" , getString(R.string.api_key))
            ?.enqueue(object : Callback<CurrentWeather>{
                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {


                }

                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful && response.body() != null ){
                        val data = response.body()
                        weather.text = data?.main?.temp.toString() // main temp из папки model
                    }

                }
            })
    }
}
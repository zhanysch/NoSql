package com.example.asynctascretrofit

import com.example.asynctascretrofit.model.Current.CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asynctascretrofit.data.RetrofitBuilder
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.model.ForecastDays.RvAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val adapter = RvAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receycler.adapter = adapter



        RetrofitBuilder.getService()
            ?.getWeather("New York" , getString(R.string.api_key), "metric")
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

        RetrofitBuilder.getService()?.forecast("Vancouver",getString(R.string.api_key),"metric")
            ?.enqueue(object  : Callback<ForcastModelOne>{
                override fun onResponse(
                    call: Call<ForcastModelOne>,
                    response: Response<ForcastModelOne>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        adapter.update(response.body()?.list)   // list это class -> ForCastItemNumbeZeroFourth
                    }
                }

                override fun onFailure(call: Call<ForcastModelOne>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }



}
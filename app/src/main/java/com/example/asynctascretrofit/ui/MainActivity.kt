package com.example.asynctascretrofit.ui

import com.example.asynctascretrofit.model.Current.CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.asynctascretrofit.R
import com.example.asynctascretrofit.data.RetrofitBuilder
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.model.ForecastDays.RvAdapter
import com.example.asynctascretrofit.utilites.ConnectionUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cloud.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val adapter = RvAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cloud)

        formatDate()

       /*
        receycler.adapter = adapter*/

        val isHasNetwork  = ConnectionUtils.isNetworkAvialable(this)
        if (!isHasNetwork){
           showSnackbar()
        }

        RetrofitBuilder.getService()
            ?.getWeather("New York" , getString(R.string.api_key), "metric")
            ?.enqueue(object : Callback<CurrentWeather>{
                override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                   // Toast.makeText(applicationContext , t.localizedMessage,Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<CurrentWeather>,
                    response: Response<CurrentWeather>
                ) {
                    if (response.isSuccessful && response.body() != null ){
                        val data = response.body()
                        numberThird.text = data?.main?.temp.toString() // main temp из папки model
                    } else{
                        Toast.makeText(applicationContext , "no Data",Toast.LENGTH_LONG).show()
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
                   Log.d("fgfdgdfgdg","fdsgdgssd")
                }
            })
    }

    private fun formatDate(){   // с помошью этой функции задаем число дня
        val sfDay = SimpleDateFormat("d", Locale.getDefault())
        val date = Date()
        val day = sfDay.format(date)
        DateSecond.text = day

        val sfMohnts = SimpleDateFormat("MMMM\nyyyy", Locale.getDefault())
        val mohnts = sfMohnts.format(date)
        Date.text=mohnts
    }

    private fun showSnackbar(){
        Snackbar.make(parentlayout, "нет соединения", Snackbar.LENGTH_INDEFINITE)
            .setAction("обновить") {
                if (!ConnectionUtils.isNetworkAvialable(this)) {
                    showSnackbar()
                }
            }.show()
    }

    private fun MainSnackbar(){
        
    }
}
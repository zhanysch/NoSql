package com.example.asynctascretrofit

import com.example.asynctascretrofit.model.Current.CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.asynctascretrofit.data.RetrofitBuilder
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.model.ForecastDays.RvAdapter
import kotlinx.android.synthetic.main.activity_main.*
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
       /* receycler.adapter = adapter

        Btngo.setOnClickListener {
            forecastWeather(Edit.text.toString())
        }
*/


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
                        numberThird.text = data?.main?.temp.toString() // main temp из папки model
                    }
                }
            })

       /* RetrofitBuilder.getService()?.forecast("Vancouver",getString(R.string.api_key),"metric")
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
*/
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

/* fun forecastWeather(city : String){
     RetrofitBuilder
         .getService()?.forecast( city,getString(R.string.api_key),"metric")
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
               Log.d("fdsfsfs","ggdgdfgd")
             }
         })
 }*/
}
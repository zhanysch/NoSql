package com.example.asynctascretrofit.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import com.example.asynctascretrofit.model.Current.CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.asynctascretrofit.R
import com.example.asynctascretrofit.WeatherApp
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne
import com.example.asynctascretrofit.model.data.RetrofitBuilder
import com.example.asynctascretrofit.utilites.ConnectionUtils
import com.example.asynctascretrofit.utilites.PermissionUtils
import com.google.android.gms.location.LocationServices
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
        MainSnackbarfirst()

        receycler.adapter = adapter

        if ( PermissionUtils.checkLocationPermission(this) ){
            LoadLocattion()
        }
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

    private fun showSnackbar(){   //(1)
        Snackbar.make(parentlayout, "нет соединения", Snackbar.LENGTH_INDEFINITE) // parentlayout это id xml верстки cloud layout, иначе snackbar не работает
            .setAction("обновить") {                                       // чтоб работал snackbar необходимо давать id layout
                if (!ConnectionUtils.isNetworkAvialable(this)) {
                    showSnackbar()
                }
            }.show()
    }

    private fun MainSnackbarfirst(){   // (2)
        val isHasNetwork  = ConnectionUtils.isNetworkAvialable(this)
        if (!isHasNetwork){
            showSnackbar()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults [1] == PackageManager.PERMISSION_GRANTED)
                LoadLocattion()


        }
    }


    private fun LoadLocattion(){  //(3!!)
        val fpc = LocationServices.getFusedLocationProviderClient(applicationContext)  // для геолокации
        //чтоб код получае разрешения на использование геолокации создаем object PermissionUtils в папке utilities
        fpc.lastLocation.addOnSuccessListener {
            LoadByLocation(it)
            LoadByLocationSecond(it)
        } .addOnFailureListener{

        }
    }

    fun LoadByLocation(location: Location){
        RetrofitBuilder.getService()?.getWeatherbycoordianates(location.latitude.toString(), location.longitude.toString(),
            getString(R.string.api_key), "metric")?.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val city = response.body()?.name
                val temp = response.body()?.main?.temp
                val feels =response.body()?.main?.feels_like
                val min = response.body()?.main?.temp_min
                val max = response.body()?.main?.temp_max
                val pressure = response.body()?.main?.pressure
                val humidity = response.body()?.main?.humidity
                val cloud = response.body()?.clouds?.all
                val wind = response.body()?.wind?.speed
                val sunrise = response.body()?.timezone
              //  WeatherApp.getApp()?.getDB()?.getDao()?.add(response.body()) 1:34:25,in

                LocationSecond.text = city.toString()
                numberTWo.text = max?.toInt().toString()
                numberThird.text=min?.toInt().toString()
                mb.text = pressure.toString()
                Sw.text = wind.toString()
                Percent.text = humidity.toString()
                numberOne.text = cloud.toString()

            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d("blabla","blabla")

            }

        })
    }


    fun LoadByLocationSecond(location: Location) {
        RetrofitBuilder.getService()?.onecall(
            location.latitude.toString(),
            location.longitude.toString(),
            "hourly,current,minutely",
            getString(R.string.api_key),
            "metric"
        )?.enqueue(object : Callback<ForcastModelOne> {
            override fun onResponse(
                call: Call<ForcastModelOne>,
                response: Response<ForcastModelOne>
            ) {
                 if (response.isSuccessful && response.body() != null){  // troubles
                   adapter.update(response.body()?.list)

            }

            override fun onFailure(call: Call<ForcastModelOne>, t: Throwable) {
                Log.d("blabla","blabla")
            }
        })
    }
}

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
import com.squareup.picasso.Picasso
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

    private fun formatDate(){   // с помошью этой функции задаем число дня  //(000000000)
        val sfDay = SimpleDateFormat("d", Locale.getDefault())
        val date = Date()
        val day = sfDay.format(date)
        DateSecond.text = day

        val sfMohnts = SimpleDateFormat("MMMM\nyyyy", Locale.getDefault())
        val mohnts = sfMohnts.format(date)
        Date.text=mohnts
    }

    private fun showSnackbar(){  /// (№ 1111111111 snackbar)
        Snackbar.make(parentlayout, "нет соединения", Snackbar.LENGTH_INDEFINITE) // parentlayout это id xml верстки cloud layout, иначе snackbar не работает
            .setAction("обновить") {                                       // чтоб работал snackbar необходимо давать id layout
                if (!ConnectionUtils.isNetworkAvialable(this)) {
                    showSnackbar()
                }
            }.show()
    }

    private fun MainSnackbarfirst(){   // (№ 1111111111 snackbar)
        val isHasNetwork  = ConnectionUtils.isNetworkAvialable(this)
        if (!isHasNetwork){
            showSnackbar()
        }
    }



    override fun onRequestPermissionsResult(  // (№4444 location)
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // (№4444 location)
        if (requestCode == PermissionUtils.LOCATION_REQUEST_CODE ){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults [1] == PackageManager.PERMISSION_GRANTED)
                LoadLocattion()


        }
    }


    private fun LoadLocattion(){  // (№4444 location)
        val fpc = LocationServices.getFusedLocationProviderClient(applicationContext)  // для геолокации
        //чтоб код получае разрешения на использование геолокации создаем object PermissionUtils в папке utilities
        fpc.lastLocation.addOnSuccessListener {
            LoadByLocation(it)
            LoadByLocationSecond(it)
        } .addOnFailureListener{

        }
    }

    fun LoadByLocation(location: Location){   // (№4444 location)
        RetrofitBuilder.getService()?.getWeatherbycoordianates(location.latitude.toString(), location.longitude.toString(),
            getString(R.string.api_key), "metric")?.enqueue(object : Callback<CurrentWeather>{
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val city = response.body()?.name
                val temp =   getString(R.string._18,response.body()?.main?.temp?.toInt().toString())                        //response.body()?.main?.temp
                val feels =response.body()?.main?.feels_like
                val min = getString(R.string._18,response.body()?.main?.temp_min?.toInt().toString())   //response.body()?.main?.temp_min
                val max = getString(R.string._18,response.body()?.main?.temp_max?.toInt().toString()) //response.body()?.main?.temp_max
                val pressure = getString(R.string._1010mb,response.body()?.main?.pressure.toString())
                val humidity= getString(R.string._81,response.body()?.main?.humidity)
                val cloud = getString(R.string._81,response.body()?.clouds?.all)
                val wind = getString(R.string.sw_4m_s,response.body()?.wind?.speed?.toInt().toString())//response.body()?.wind?.speed
                val sunrise = formatDate(response.body()?.sys?.sunrise)                //response.body()?.sys?.sunrise
                val sunset = formatDate(response.body()?.sys?.sunset)
                val image = response.body()?.weather?.first()?.icon
                val descript = response.body()?.weather?.first()?.description
              //  WeatherApp.getApp()?.getDB()?.getDao()?.add(response.body()) 1:34:25,in

                LocationSecond.text = city.toString()
                numberTWo.text = max
                numberThird.text=min
                mb.text = pressure
                Sw.text = wind
                Percent.text = humidity
                numberOne.text = temp
                PercentSecond.text=cloud
                hour.text = sunrise
                hourSecond.text=sunset
                LittleCloud.text=descript.toString()  // 11
                Picasso.get().load("http://openweathermap.org/img/w/$image.png").into(cloudmain)
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d("blabla","blabla")

            }

        })
    }


    fun formatDate(date: Int?): String {
        val newdata = date?.toLong()?:0
        return SimpleDateFormat("H:mm", Locale.getDefault()).format(Date(newdata * 1000))
    }


    fun LoadByLocationSecond(location: Location) {         // (№4444 location)
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
                 if (response.isSuccessful && response.body() != null) {  // troubles
                     adapter.update(response.body()?.daily)
                 }

            }

            override fun onFailure(call: Call<ForcastModelOne>, t: Throwable) {
                Log.d("blabla","blabla")
            }
        })
    }
}

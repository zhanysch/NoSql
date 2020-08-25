package com.example.asynctascretrofit.ui

import android.content.pm.PackageManager
import android.location.Location
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
        RetrobuildOne()

       /*
        receycler.adapter = adapter*/

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

    private fun RetrobuildOne(){
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
    }

    private fun RetrobuildTwo(){
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
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
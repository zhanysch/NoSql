package com.example.asynctascretrofit.ui

import android.content.pm.PackageManager
import android.location.Location
import com.example.asynctascretrofit.model.Current.CurrentWeather
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val adapter = RvAdapter()
    val service = RetrofitBuilder.getService()

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

    private fun  LocalGeo(location: Location){
        GlobalScope.launch {
            kotlin.runCatching {
                val res = RetrofitBuilder.getService()
                    ?.onecallGeo( location.latitude.toString(),
                        location.longitude.toString(),
                        "hourly,current,minutely",
                        getString(R.string.api_key),
                        "metric")
                FullViews(res)
                Log.d("blabla", "blabla")
            }.onFailure {
                Log.d("blabla", "blabla")
            }
        }

    }

    private fun FullViews(res: ForcastModelOne?) {
        runOnUiThread {
            if(res !=null) {
                adapter.update(res.daily)
            }

        }
    }

    private fun Local(location: Location){  //!!!  1.555     // 1) для работы coruntine

        GlobalScope.launch {
            kotlin.runCatching {
                val result = service
                    ?.getWeatherbycoordianatesCoruntines(location.latitude.toString(), location.longitude.toString(),
                        getString(R.string.api_key), "metric")
                fillViews(result)

                Log.d("dgdgfdg","dgdgdfgd")
            }.onFailure {
                Log.d("dgdgfdg","dgdgdfgd")
            }
        }
    }

    private fun fillViews(result: CurrentWeather?) { //2.555
        runOnUiThread {

            LocationSecond.text = result?.name.toString()
            numberOne.text = getString(R.string._18,result?.main?.temp?.toInt().toString())
            numberThird.text = getString(R.string._18,result?.main?.temp_min?.toInt().toString())
            numberTWo.text = getString(R.string._18,result?.main?.temp_max?.toInt().toString())
            mb.text = getString(R.string._1010mb,result?.main?.pressure.toString())
            Percent.text = getString(R.string._81,result?.main?.humidity)
            PercentSecond.text= getString(R.string._81,result?.clouds?.all)
            Sw.text = getString(R.string.sw_4m_s,result?.wind?.speed?.toInt().toString())

            hour.text = formatDate(result?.sys?.sunrise)
            hourSecond.text=formatDate(result?.sys?.sunset)

            LittleCloud.text=result?.weather?.first()?.description
            val image = result?.weather?.first()?.icon
            Picasso.get().load("http://openweathermap.org/img/w/$image.png").into(cloudmain)

        }
    }

    fun formatDate(date: Int?): String {
        val newdata = date?.toLong()?:0 // ?:0 <- оператор элвиса ,  длтгчтб (date: Int?) date : int? может быть null , a строчка
        // 113 туда для умножения нужен обьязательно не null и выходила ошибка поэтому , даем оператор элвиса
        return SimpleDateFormat("H:mm", Locale.getDefault()).format(Date(newdata * 1000)) //  Locale.getDefault()) это часовой пояс котор установлен на телефоне
        // он будет давать данные исходя из часового пояся
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
            if(it != null){
                Local(it)      //!!!  2.555
                LocalGeo(it)
            }
            else {
                Log.d("gdgdgdgfd","dfgdgdfgdgdfg")
            }

        } .addOnFailureListener{

        }
    }
}

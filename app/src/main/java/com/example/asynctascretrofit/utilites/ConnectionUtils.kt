package com.example.asynctascretrofit.utilites

import android.content.Context
import android.net.ConnectivityManager

object ConnectionUtils {
    fun isNetworkAvialable(context: Context) : Boolean{  // делаем проверку интернет
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val one = cm.activeNetworkInfo !=null
        val two  =  cm.activeNetworkInfo?.isConnected ?: false //?: false дефолт значн, чтоб прилож не вылетело
        return one && two
    }
}
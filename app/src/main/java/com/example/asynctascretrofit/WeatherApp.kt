package com.example.asynctascretrofit

import android.app.Application
import androidx.room.Room
import com.example.asynctascretrofit.model.LocalRoom.AppDataBaseAbstrct

class WeatherApp : Application() {

    private var db : AppDataBaseAbstrct? = null

    override fun onCreate() {
        super.onCreate()

        app= this

        db = Room.databaseBuilder(applicationContext
             , AppDataBaseAbstrct::class.java,DB_Name)
            .allowMainThreadQueries()
            .build()
    }

    fun getDB() = db

    companion object {
       private var app: WeatherApp? = null
        fun getApp() = app
        private const val DB_Name = "MY_DB"
    }
}
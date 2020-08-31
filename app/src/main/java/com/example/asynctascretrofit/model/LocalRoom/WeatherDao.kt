package com.example.asynctascretrofit.model.LocalRoom

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne

@Dao
interface WeatherDao {
    @Insert
    fun add(data : ForcastModelOne)

    @Query("SELECT * FROM FORCASTMODELONE")
    fun getAll(): List<ForcastModelOne>

    @Query("DELETE FROM FORCASTMODELONE")
    fun deleteAll()

}
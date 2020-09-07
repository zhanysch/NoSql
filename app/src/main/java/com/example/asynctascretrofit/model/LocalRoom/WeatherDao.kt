package com.example.asynctascretrofit.model.LocalRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.asynctascretrofit.model.ForecastDays.ForcastModelOne

@Dao
interface WeatherDao {
    @Insert
    fun add(data : ForcastModelOne)

    @Query("SELECT * FROM FORCASTMODELONE")
    fun getAll(): LiveData<List<ForcastModelOne>> //LiveData это обновление на БД, тоесть получаем данн есть ли или нет там данных

    @Query("DELETE FROM FORCASTMODELONE")
    fun deleteAll()

    @Transaction  // @Transaction выполн команды след:
    fun addForcast(data : ForcastModelOne){
        deleteAll()
        add(data)
    }


}
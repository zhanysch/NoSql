package com.example.asynctascretrofit.model.ForecastDays

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForcastModelOne(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val daily: List<ForCastDailyFourth>
    // zero потому что идет по позиции 0 . 1 .2... итд по позиции
)
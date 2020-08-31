package com.example.asynctascretrofit.model.ForecastDays

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoordThird (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val lat: Double,
    val ion : Double
)
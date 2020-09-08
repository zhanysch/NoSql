package com.example.asynctascretrofit.model.LocalRoom


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.asynctascretrofit.model.ForecastDays.*


@Database (entities = [CityModelSecond::class, CoordThird::class,ForCastDailyFourth::class,
    ForcastModelOne::class,ForTempFifth::class], version = 3, exportSchema = false)
@TypeConverters(value = [TypeConverter::class])
abstract class AppDataBaseAbstrct : RoomDatabase() {
    abstract fun getDao(): WeatherDao

}

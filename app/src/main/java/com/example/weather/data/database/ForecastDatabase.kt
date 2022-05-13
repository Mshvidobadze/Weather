package com.example.weather.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weather.data.data_providers.ForecastsDao
import com.example.weather.data.models.CurrentForecastModel
import com.example.weather.data.models.ForecastsModel
import com.example.weather.helper.util.Converters


@Database(entities = [ForecastsModel::class, CurrentForecastModel::class], version = 2,
    autoMigrations = [AutoMigration (from = 1, to = 2)])
@TypeConverters(Converters::class)
abstract class ForecastDatabase: RoomDatabase() {

    abstract fun forecastDao(): ForecastsDao

}
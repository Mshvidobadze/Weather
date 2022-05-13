package com.example.weather.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weather.R
import com.example.weather.data.data_providers.BASE_URL
import com.example.weather.data.data_providers.RemoteDataProvider
import com.example.weather.data.database.ForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(context: Context): String {
        return context.getString(R.string.base_url)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        @Named(BASE_URL) baseUrl: String
    ): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideForecastApi(
        retrofit: Retrofit
    ): RemoteDataProvider{
        return retrofit.create(RemoteDataProvider::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(app: Application): ForecastDatabase{
        return Room.databaseBuilder(app, ForecastDatabase::class.java, "forecast_database")
            .build()
    }

}
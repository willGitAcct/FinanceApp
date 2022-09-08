package com.example.financeapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financeapp.data.local.StockDatabase
import com.example.financeapp.data.remote.StockApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//makes sure dependencies live as long as the application does
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi{
        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stockdb.db"
        ).build()
    }
}
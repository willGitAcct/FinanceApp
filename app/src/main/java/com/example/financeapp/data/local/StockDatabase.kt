package com.example.financeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

//abstract for Room purposes
@Database(
    entities = [CompanyListEntity::class],
    version = 1
)

abstract class StockDatabase: RoomDatabase() {
    //room will use as info about the database, then generate boilerplate code
    abstract val dao: StockDao
}
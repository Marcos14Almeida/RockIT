package com.example.rockit.Model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rockit.Model.Dao.BandDao
import com.example.rockit.Model.To.BandTO

@Database(entities = [BandTO::class], version = 1, exportSchema = false)
abstract class RockItDatabase: RoomDatabase() {
    abstract fun bandDatabaseDao(): BandDao
}
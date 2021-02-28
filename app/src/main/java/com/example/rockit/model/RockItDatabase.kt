package com.example.rockit.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rockit.model.Dao.BandDao
import com.example.rockit.model.To.BandTO

@Database(entities = [BandTO::class], version = 1, exportSchema = false)
abstract class RockItDatabase: RoomDatabase() {
    abstract fun bandDatabaseDao(): BandDao
}
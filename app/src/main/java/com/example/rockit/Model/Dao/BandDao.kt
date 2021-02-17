package com.example.rockit.Model.Dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface BandDao {
    @Query("SELECT * from Band WHERE insertionDate = :key")
    fun get(key: String): BandDao?
}
package com.example.rockit.model.Dao

import androidx.room.Dao
import androidx.room.Query
import com.example.rockit.model.To.BandTO

@Dao
interface BandDao {
    @Query("SELECT * from Band WHERE name = :key")
    fun get(key: String): BandTO?
}
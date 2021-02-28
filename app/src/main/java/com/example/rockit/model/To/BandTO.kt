package com.example.rockit.model.To

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Band")
class BandTO {

    @PrimaryKey(autoGenerate = false)
    var name: String = ""

    var rating: Int = 0
}
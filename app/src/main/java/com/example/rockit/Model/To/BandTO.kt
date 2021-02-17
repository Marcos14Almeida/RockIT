package com.example.rockit.Model.To

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Band")
class BandTO {

    @PrimaryKey(autoGenerate = false)
    var name: String = ""
}
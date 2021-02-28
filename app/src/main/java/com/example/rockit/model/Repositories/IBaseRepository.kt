package com.example.rockit.model.Repositories

import com.example.rockit.model.To.BandTO

interface IBaseRepository {

    suspend fun getBandFromLocalDb(bandId: String) : BandTO?
}
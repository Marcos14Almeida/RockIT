package com.example.rockit.model.Repositories

import com.example.rockit.model.Dao.BandDao
import com.example.rockit.model.To.BandTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Todo: Break in more repositories as the application grows
class BaseRepository(
        // pass the daos here
        private val bandDao: BandDao
): IBaseRepository {

    override suspend fun getBandFromLocalDb(bandId: String) :BandTO? {
        return withContext(Dispatchers.IO) {
            bandDao.get(bandId)
        }
    }
}
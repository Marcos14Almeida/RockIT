package com.example.rockit.Model.Repositories

import com.example.rockit.Model.Dao.BandDao

// Todo: Break in more repositories as the application grows
class BaseRepository(
        // pass the daos here
        private val exerciseDao: BandDao
): IBaseRepository {
}
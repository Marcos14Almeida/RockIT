package com.example.rockit.Model

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.rockit.Model.Repositories.BaseRepository
import com.example.rockit.Model.Repositories.IBaseRepository

object ServiceLocator {
    private var database: RockItDatabase? = null
    @Volatile
    var repository: BaseRepository? = null
        @VisibleForTesting set

    private val lock = Any()

    fun provideRepository(context: Context): IBaseRepository {
        synchronized(this) {
            return repository
                    ?: createRepository(
                            context
                    )
        }
    }

    private fun createRepository(context: Context): IBaseRepository {
        val database = database
                ?: createDataBase(
                        context
                )
        val newRepo = BaseRepository(database.bandDatabaseDao())
        repository = newRepo
        return newRepo
    }

    private fun createDataBase(context: Context): RockItDatabase {
        val result = Room.databaseBuilder(
                context.applicationContext,
                RockItDatabase::class.java, "database.db"
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }
    }
}
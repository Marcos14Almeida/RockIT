package com.example.rockit.pages.band

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rockit.model.Repositories.IBaseRepository


class BandPageViewModelFactory(
        private val bandRepository: IBaseRepository,
        private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BandPageViewModel::class.java)) {
            return BandPageViewModel(bandRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
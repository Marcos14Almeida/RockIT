package com.example.rockit.pages.band

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.rockit.model.Repositories.IBaseRepository
import com.example.rockit.model.To.BandTO
import kotlinx.coroutines.*

class BandPageViewModel(
        private val bandRepository: IBaseRepository,
        application: Application): AndroidViewModel(application)  {

    private var viewModeJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModeJob)

    private var band: BandTO? = null

    fun initBandViewModel(bandId: String) {
        getBand(bandId)
    }

    private fun getBand(bandId: String) {
        // use coroutines to not hang the ui thread
        uiScope.launch {
            band = bandRepository.getBandFromLocalDb(bandId)
        }
    }

}
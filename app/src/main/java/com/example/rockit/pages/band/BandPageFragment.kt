package com.example.rockit.pages.band

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.rockit.model.ServiceLocator
import com.example.rockit.R
import com.example.rockit.databinding.BandFragmentBinding

// Todo: Refactor to use navigation components
class BandPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: BandFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.band_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val repository = ServiceLocator.provideRepository(application)

        val viewModelFactory = BandPageViewModelFactory(repository, application)
        //todo: change to not use deprecated class
        val bandViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(BandPageViewModel::class.java)

        // todo: get the parameters from the arguments passed
        bandViewModel.initBandViewModel("")

        binding.viewModel = bandViewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}
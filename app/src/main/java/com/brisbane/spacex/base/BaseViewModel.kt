package com.brisbane.spacex.base

import androidx.lifecycle.ViewModel
import com.brisbane.spacex.injection.component.DaggerViewModelInjector
import com.brisbane.spacex.injection.component.ViewModelInjector
import com.brisbane.spacex.injection.module.NetworkModule
import com.brisbane.spacex.ui.launch.LaunchListViewModel
import com.brisbane.spacex.ui.launch.LaunchViewModel
import com.brisbane.spacex.ui.launch.details.LaunchDetailViewModel

abstract class BaseViewModel: ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LaunchListViewModel -> injector.inject(this)
            is LaunchViewModel -> injector.inject(this)
            is LaunchDetailViewModel -> injector.inject(this)
        }
    }
}

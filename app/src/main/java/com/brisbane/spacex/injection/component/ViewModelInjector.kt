package com.brisbane.spacex.injection.component

import com.brisbane.spacex.injection.module.NetworkModule
import com.brisbane.spacex.ui.launch.LaunchListViewModel
import com.brisbane.spacex.ui.launch.LaunchViewModel
import com.brisbane.spacex.ui.launch.details.LaunchDetailViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified LaunchListViewModel.
     * @param launchListViewModel LaunchListViewModel in which to inject the dependencies
     */
    fun inject(launchListViewModel: LaunchListViewModel)
    /**
     * Injects required dependencies into the specified LaunchViewModel.
     * @param launchViewModel LaunchViewModel in which to inject the dependencies
     */
    fun inject(launchViewModel: LaunchViewModel)

    /**
     * Injects required dependencies into the specified LaunchDetailViewModel.
     * @param launchDetailViewModel LaunchDetailViewModel in which to inject the dependencies
     */
    fun inject(launchDetailViewModel: LaunchDetailViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}
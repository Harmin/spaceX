package com.brisbane.spacex.ui.launch.details

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.brisbane.spacex.R
import com.brisbane.spacex.base.BaseViewModel
import com.brisbane.spacex.model.Launch
import com.brisbane.spacex.network.LaunchApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LaunchDetailViewModel: BaseViewModel() {
    @Inject
    lateinit var launchApi: LaunchApi
    val launch: MutableLiveData<Launch> = MutableLiveData()

    private lateinit var subscription: Disposable

    override fun onCleared() {
        super.onCleared()
        if (::subscription.isInitialized) {  subscription.dispose() }
    }


    fun loadLaunch(flightNumber: Int){
        subscription = launchApi.getLaunchDetails(flightNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveLaunchDetailsSuccess(result[0]) },
                { error -> Log.println(Log.ERROR, "err:", error.message) }
            )
    }

    private fun onRetrieveLaunchDetailsSuccess(launchDetails: Launch){
       launch.value = launchDetails
    }
}
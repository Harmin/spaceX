package com.brisbane.spacex.ui.launch

import androidx.lifecycle.MutableLiveData
import com.brisbane.spacex.base.BaseViewModel
import com.brisbane.spacex.model.Launch
import com.brisbane.spacex.utils.formatTo
import com.brisbane.spacex.utils.toDate

class LaunchViewModel : BaseViewModel() {
    private val launchName = MutableLiveData<String>()
    private val launchDetails = MutableLiveData<String>()
    private val launchDate = MutableLiveData<String>()
    private val launchSuccess = MutableLiveData<Boolean>()

    fun bind(launch: Launch){
        launchName.value = launch.mission_name
        launchDate.value = launch.launch_date_utc.toDate().formatTo("dd MMM yyyy")
        launchDetails.value = launch.details
        launchSuccess.value = launch.launch_success
    }

    fun getLaunchName():MutableLiveData<String>{
        return launchName
    }

    fun getLaunchDate(): MutableLiveData<String> {
        return launchDate
    }

    fun getLaunchDetails(): MutableLiveData<String> {
        return launchDetails
    }

    fun getLaunchSuccess():MutableLiveData<Boolean>{
        return launchSuccess
    }
}
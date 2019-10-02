package com.brisbane.spacex.ui.launch

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

class LaunchListViewModel : BaseViewModel() {
    @Inject
    lateinit var launchApi: LaunchApi
    val sortOrder: MutableLiveData<String> = MutableLiveData()
    val filterOptions: MutableLiveData<Map<String, Boolean>> = MutableLiveData()
    val launchListAdapter: LaunchListAdapter = LaunchListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadLaunches() }

    private lateinit var subscription: Disposable

    init{
        sortOrder.value = "launch_year";
        filterOptions.value = HashMap();
        loadLaunches()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun loadLaunches(){
        subscription = launchApi.getLaunches(sortOrder.value, filterOptions.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveLaunchListStart() }
            .doOnTerminate { onRetrieveLaunchListFinish() }
            .subscribe(
                { result -> onRetrieveLaunchListSuccess(result) },
                { error -> onRetrieveLaunchListError(error) }
            )
    }

    private fun onRetrieveLaunchListStart(){
        loadingVisibility.value = View.VISIBLE
    }

    private fun onRetrieveLaunchListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveLaunchListSuccess(launchList:List<Launch>){
        val data: ArrayList<Any>;
        if(sortOrder.value == "launch_year"){
            data = groupLaunchesByYear(launchList)
        } else {
            data = groupLaunchesByFirstAlphabetOfName(launchList)
        }
        launchListAdapter.updateLaunchList(data)
    }

    private fun groupLaunchesByYear(launchList: List<Launch>): ArrayList<Any> {
        val data: ArrayList<Any> =  ArrayList();
        val byYear = launchList.groupBy({ it.launch_year }, { it })
        for ((k, v) in byYear) {
            data.add(k)
            data.addAll(v)
        }

        return data
    }

    private fun groupLaunchesByFirstAlphabetOfName(launchList: List<Launch>): ArrayList<Any> {
        val data: ArrayList<Any> =  ArrayList();
        val byFirstAlphabetOfName = launchList.groupBy({ it.mission_name[0].toString() }, { it })
        for ((k, v) in byFirstAlphabetOfName) {
            data.add(k)
            data.addAll(v)
        }

        return data
    }

    private fun onRetrieveLaunchListError(error: Throwable) {
        errorMessage.value = R.string.launch_error
    }
}
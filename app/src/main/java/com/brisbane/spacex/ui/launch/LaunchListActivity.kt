package com.brisbane.spacex.ui.launch

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.brisbane.spacex.R
import com.brisbane.spacex.databinding.ActivityLaunchListBinding
import com.brisbane.spacex.model.Launch
import com.brisbane.spacex.ui.launch.details.LaunchDetailActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make

class LaunchListActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, LaunchListAdapter.OnItemClickListener {

    private lateinit var binding: ActivityLaunchListBinding
    private lateinit var viewModel: LaunchListViewModel
    private var errorSnackbar: Snackbar? = null
    var filterByArray = arrayOf("no filter", "launch_success", "gridfins", "legs", "capsule_reuse")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_list)
        viewModel = ViewModelProviders.of(this).get(LaunchListViewModel::class.java)
        viewModel.launchListAdapter.setOnItemClickListener(this)
        binding.viewModel = viewModel

        setObservers();
        setRecyclerView();
        setFilterSpinner();
    }

    private fun setObservers() {
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        viewModel.sortOrder.observe(this, Observer {
            viewModel.loadLaunches()
        })

        viewModel.filterOptions.observe(this, Observer {
            viewModel.loadLaunches()
        })
    }

    private fun setRecyclerView() {
        binding.launchList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setFilterSpinner() {
        binding.filterBySpinner!!.setOnItemSelectedListener(this)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filterByArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.filterBySpinner!!.setAdapter(arrayAdapter)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = !(item.isChecked)
        when (item.itemId) {
            R.id.sort_by_date -> {
                viewModel.sortOrder.value = "launch_year";
                return true
            }
            R.id.sort_by_name -> {
                viewModel.sortOrder.value = "mission_name";
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val data = HashMap<String, Boolean>()
        if(filterByArray[position] != "no filter")  data.put(filterByArray[position], true)

        viewModel.filterOptions.value = data
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(launch: Launch) {
        val intent = Intent(this, LaunchDetailActivity::class.java)
        intent.putExtra("flight_number", launch.flight_number)
        startActivity(intent)
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }
}

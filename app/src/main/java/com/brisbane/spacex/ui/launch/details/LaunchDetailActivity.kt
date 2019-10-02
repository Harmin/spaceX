package com.brisbane.spacex.ui.launch.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brisbane.spacex.R
import com.brisbane.spacex.databinding.ActivityLaunchDetailBinding

class LaunchDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityLaunchDetailBinding
    private lateinit var viewModel: LaunchDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launch_detail)
        viewModel = ViewModelProviders.of(this).get(LaunchDetailViewModel::class.java)
        binding.viewModel = viewModel

        val flightNumber: Int = intent.getIntExtra("flight_number", 0)
        viewModel.loadLaunch(flightNumber)

        setObservers();
    }

    private fun setObservers() {
        viewModel.launch.observe(this, Observer {
            binding.launchName.text = it.mission_name
            binding.launchDetails.text = it.details
            binding.wikiDetails.text = it.links.wikipedia
        })
    }
}

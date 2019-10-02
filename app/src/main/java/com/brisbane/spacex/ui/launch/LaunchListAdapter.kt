package com.brisbane.spacex.ui.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.brisbane.spacex.R
import com.brisbane.spacex.databinding.ItemHeaderBinding
import com.brisbane.spacex.databinding.ItemLaunchBinding
import com.brisbane.spacex.model.Launch


class LaunchListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val LABEL = 0
        private const val HEADER = 1
    }

    private lateinit var launchList:List<Any>
    lateinit var clickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        clickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(launch: Launch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER -> LaunchHeaderViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_header, parent, false))
            else -> LaunchItemViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_launch, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LaunchItemViewHolder) {
            holder.bind(launchList[position] as Launch, clickListener)
        } else if(holder is LaunchHeaderViewHolder) {
            holder.binding.headerView.text = launchList[position].toString()
        }
    }

    override fun getItemCount(): Int {
        return if(::launchList.isInitialized) launchList.size else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(launchList[position] is String) {
            HEADER
        } else {
            LABEL
        }
    }

    fun updateLaunchList(launchList:List<Any>){
        this.launchList = launchList
        notifyDataSetChanged()
    }

    class LaunchHeaderViewHolder(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root)

    class LaunchItemViewHolder(private val binding: ItemLaunchBinding): RecyclerView.ViewHolder(binding.root){
        private val viewModel = LaunchViewModel()

        fun bind(launch:Launch, clickListener: OnItemClickListener){
            viewModel.bind(launch)
            binding.viewModel = viewModel
            binding.root.setOnClickListener({ _ -> clickListener.onItemClick(launch) })
        }
    }
}

package com.example.greentrip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.PointItemBinding
import com.example.greentrip.databinding.RewardItemBinding
import com.example.greentrip.models.PointsResponse
import com.example.greentrip.models.RewardResponse
import com.example.greentrip.ui.main.points.MainPointsFragmentDirections
import com.example.greentrip.ui.main.rewards.RewardsFragmentDirections

class RewardsAdapter() : ListAdapter<RewardResponse.Data, RewardsAdapter.viewholder>(RewardsAdapter){

    companion object : DiffUtil.ItemCallback<RewardResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: RewardResponse.Data,
            newItem: RewardResponse.Data
        ): Boolean {

            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: RewardResponse.Data,
            newItem: RewardResponse.Data
        ): Boolean {
            return oldItem._id == newItem._id
        }

    }

    class viewholder(var binding: RewardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RewardResponse.Data) {

            binding.txtDesc.text = data.description
            binding.txtTitle.text = data.title
            binding.txtPoint.text = data.costPoints.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
    val binding =
        RewardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
       holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            val action = RewardsFragmentDirections.actionRewardsFragmentToRewardDetailsFragment(getItem(position)._id.toString())
            it.findNavController().navigate(action)
        }
    }

}
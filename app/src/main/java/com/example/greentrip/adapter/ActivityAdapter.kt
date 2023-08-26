package com.example.greentrip.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.greentrip.R
import com.example.greentrip.constants.Constants
import com.example.greentrip.databinding.PointItemBinding
import com.example.greentrip.models.ActivityResponse
import com.example.greentrip.models.PointsResponse
import com.example.greentrip.models.SpecificPointResponse
import com.example.greentrip.ui.main.activity.AllActivityFragmentDirections
import com.example.greentrip.ui.main.points.MainPointsFragmentDirections
import com.example.greentrip.ui.main.points.PointDetailsFragmentDirections

class ActivityAdapter() :
    ListAdapter<ActivityResponse.Data, ActivityAdapter.viewholder>(ActivityAdapter) {

    companion object : DiffUtil.ItemCallback<ActivityResponse.Data>() {
        override fun areItemsTheSame(
            oldItem: ActivityResponse.Data,
            newItem: ActivityResponse.Data
        ): Boolean {

            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: ActivityResponse.Data,
            newItem: ActivityResponse.Data
        ): Boolean {
            return oldItem._id == newItem._id
        }

    }

    class viewholder(var binding: PointItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ActivityResponse.Data) {
//            val image = "${Constants.BASEURL}img/pointImg/${data.pointOfInterest?.photo}"
//            Glide.with(binding.root).load(image).into(binding.pointImg)

            Glide.with(binding.root).load(data.pointOfInterest?.photo).into(binding.pointImg)
            binding.txtDesc.text = data.pointOfInterest?.address
            binding.txtName.text = data.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding =
            PointItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            val action =
                AllActivityFragmentDirections.actionAllActivityFragmentToActivityConfirmReservationFragment(
                    getItem(position).id.toString()
                )
            it.findNavController().navigate(action)

        }
    }

}
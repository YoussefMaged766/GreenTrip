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
import com.example.greentrip.databinding.ReservationItemBinding
import com.example.greentrip.models.PointsResponse
import com.example.greentrip.models.ReservationResponse
import com.example.greentrip.models.UserResponse
import com.example.greentrip.ui.main.points.MainPointsFragmentDirections
import com.example.greentrip.ui.main.reservation.AllReservationFragmentDirections
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationAdapter() :
    ListAdapter<UserResponse.Data.Data.Booking, ReservationAdapter.viewholder>(ReservationAdapter) {

    companion object : DiffUtil.ItemCallback<UserResponse.Data.Data.Booking>() {
        override fun areItemsTheSame(
            oldItem: UserResponse.Data.Data.Booking,
            newItem: UserResponse.Data.Data.Booking
        ): Boolean {

            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: UserResponse.Data.Data.Booking,
            newItem: UserResponse.Data.Data.Booking
        ): Boolean {
            return oldItem._id == newItem._id
        }

    }

    class viewholder(var binding: ReservationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserResponse.Data.Data.Booking) {
            binding.txtDesc.text = convertDateFormat(data.createdAt.toString())

            if (data.activity?.name != null || data.point?.name != null) {
                binding.txtName.text = data.activity?.name?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                    ?: data.point?.name?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.ROOT
                        ) else it.toString()
                    }

            }
        }

        private fun convertDateFormat(inputDate: String): String {
            val inputFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding =
            ReservationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(getItem(position))
        holder.binding.btnMore.setOnClickListener {
            val action =
                AllReservationFragmentDirections.actionAllReservationFragmentToReservationInfoFragment(
                    getItem(position)._id.toString(),
                    getItem(position).status.toString()
                )
            it.findNavController().navigate(action)
        }
    }

}
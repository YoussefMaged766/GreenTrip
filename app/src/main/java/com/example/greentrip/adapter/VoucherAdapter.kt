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
import com.example.greentrip.databinding.VoucherItemBinding
import com.example.greentrip.models.PointsResponse
import com.example.greentrip.models.UserResponse
import com.example.greentrip.models.VoucherResponse
import com.example.greentrip.ui.main.points.MainPointsFragmentDirections
import com.example.greentrip.ui.main.vouchers.AllVouchersFragmentDirections

class VoucherAdapter() : ListAdapter<UserResponse.Data.Data.Voucher, VoucherAdapter.viewholder>(VoucherAdapter){

    companion object : DiffUtil.ItemCallback<UserResponse.Data.Data.Voucher>() {
        override fun areItemsTheSame(
            oldItem:UserResponse.Data.Data.Voucher,
            newItem: UserResponse.Data.Data.Voucher
        ): Boolean {

            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(
            oldItem: UserResponse.Data.Data.Voucher,
            newItem: UserResponse.Data.Data.Voucher
        ): Boolean {
            return oldItem._id == newItem._id
        }

    }

    class viewholder(var binding: VoucherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data:UserResponse.Data.Data.Voucher ) {
            binding.txtDesc.text = data.reward?.description
            binding.txtName.text = data.reward?.title
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
    val binding =
        VoucherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewholder(binding)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
       holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            val action = AllVouchersFragmentDirections.actionAllVouchersFragmentToVoucherQRFragment(getItem(position)._id.toString())
            it.findNavController().navigate(action)
        }
    }

}
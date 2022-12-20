package com.eduside.alfagift.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eduside.alfagift.data.local.db.entities.BeritaVo
import com.eduside.alfagift.databinding.ItemListChannelBeritaItemBinding
import javax.inject.Inject

class MainActivityAdapter @Inject constructor() :
    ListAdapter<BeritaVo, MainActivityAdapter.ViewHolder>(ListDiffUtill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListChannelBeritaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.tvNama.text = data.sumber
//        Glide
//            .with(holder.itemView.context)
//            .load(data.image)
//            .centerCrop()
//            .placeholder(R.drawable.ic_defaultimage)
//            .into(holder.binding.imgKambing)
//        holder.binding.cvContainer.setOnClickListener {
//            EventBus.getDefault().post(ItemDataKambingEvent(data))
//        }
    }

    class ViewHolder(itemBinding: ItemListChannelBeritaItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemListChannelBeritaItemBinding = itemBinding
    }

    class ListDiffUtill : DiffUtil.ItemCallback<BeritaVo>() {
        override fun areItemsTheSame(oldItem: BeritaVo, newItem: BeritaVo): Boolean {
            return newItem.id== oldItem.id
        }

        override fun areContentsTheSame(oldItem: BeritaVo, newItem: BeritaVo): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }
}
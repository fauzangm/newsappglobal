package com.eduside.alfagift.ui.listBerita

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eduside.alfagift.data.local.db.entities.BeritaVo
import com.eduside.alfagift.databinding.ItemListBeritaItemBinding
import com.eduside.alfagift.databinding.ItemListChannelBeritaItemBinding
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class ListBeritaAdapter @Inject constructor() :
    ListAdapter<BeritaVo, ListBeritaAdapter.ViewHolder>(ListDiffUtill()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListBeritaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.tvTittle.text = data.title

        Glide
            .with(holder.itemView.context)
            .load(data.urlToImage)
            .centerCrop()
            .into(holder.binding.icon)
        holder.binding.cvContainer.setOnClickListener {
            EventBus.getDefault().post(ItemDataBeritaEvent(data))
        }
    }

    class ViewHolder(itemBinding: ItemListBeritaItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemListBeritaItemBinding = itemBinding
    }

    class ListDiffUtill : DiffUtil.ItemCallback<BeritaVo>() {
        override fun areItemsTheSame(oldItem: BeritaVo, newItem: BeritaVo): Boolean {
            return newItem.title== oldItem.title
        }

        override fun areContentsTheSame(oldItem: BeritaVo, newItem: BeritaVo): Boolean {
            return areItemsTheSame(oldItem, newItem)
        }
    }
}
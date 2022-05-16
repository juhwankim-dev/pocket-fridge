package com.andback.pocketfridge.present.views.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.NotificationEntity
import com.andback.pocketfridge.databinding.ItemNotificationListBinding

class NotificationRVAdapter: RecyclerView.Adapter<NotificationRVAdapter.ViewHolder>() {
    private val items = arrayListOf<NotificationEntity>()

    inner class ViewHolder(val binding: ItemNotificationListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NotificationEntity) {
            binding.notification = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNotificationListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(list: List<NotificationEntity>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}
package com.andback.pocketfridge.present.views.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.databinding.ItemFridgeListBinding


class FridgeListAdapter : RecyclerView.Adapter<FridgeListAdapter.FridgeViewHolder>() {
    private val list = ArrayList<FridgeEntity>()
    private var id = 0

    interface ItemClickListener {
        fun onClick(data: FridgeEntity)
    }
    lateinit var itemClickListener: ItemClickListener


    inner class FridgeViewHolder(val binding: ItemFridgeListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FridgeEntity) {
            when(data.isOwner) {
                true  -> binding.ivFridgeListINotOwner.visibility = View.INVISIBLE
                false -> binding.ivFridgeListINotOwner.visibility = View.VISIBLE
            }
            binding.tvFridgeListIName.text = data.name
            when {
                id == -1      -> binding.ivFridgeListICurrent.visibility = View.GONE
                data.id == id -> binding.ivFridgeListICurrent.visibility = View.VISIBLE
                data.id != id -> binding.ivFridgeListICurrent.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeViewHolder {
        val binding = ItemFridgeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FridgeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FridgeViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            binding.root.setOnClickListener {
                itemClickListener.onClick(list[position])
            }
        }
    }

    fun setList(list: List<FridgeEntity>, id: Int) {
        this.list.clear()
        this.list.addAll(list)
        this.id = id
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size
}
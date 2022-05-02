package com.andback.pocketfridge.present.views.main.fridge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemIngreListBinding
import com.andback.pocketfridge.domain.model.Ingredient

class IngreRVAdapter: RecyclerView.Adapter<IngreRVAdapter.ViewHolder>() {
    private val list = arrayListOf<Ingredient>()
    lateinit var itemClickListener: ItemClickListener
    lateinit var itemLongClickListener: ItemLongClickListener

    inner class ViewHolder(val binding: ItemIngreListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                if(::itemClickListener.isInitialized) {
                    itemClickListener.onClick(list[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                return@setOnLongClickListener if(::itemLongClickListener.isInitialized) {
                    itemLongClickListener.onLongClick(list[adapterPosition])
                    true
                } else false
            }
        }

        fun bind(data: Ingredient) {
            binding.ingredient = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngreListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    interface ItemClickListener {
        fun onClick(data: Ingredient)
    }

    interface ItemLongClickListener {
        fun onLongClick(data: Ingredient)
    }
}
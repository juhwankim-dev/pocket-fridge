package com.andback.pocketfridge.present.views.main.fridge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemIngreListBinding
import com.andback.pocketfridge.domain.model.Ingredient

class IngreRVAdapter: RecyclerView.Adapter<IngreRVAdapter.ViewHolder>() {
    private val list = arrayListOf<Ingredient>()
    private val filteredList = arrayListOf<Ingredient>()
    lateinit var itemClickListener: ItemClickListener
    lateinit var itemLongClickListener: ItemLongClickListener


    inner class ViewHolder(val binding: ItemIngreListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                if(::itemClickListener.isInitialized) {
                    itemClickListener.onClick(filteredList[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                return@setOnLongClickListener if(::itemLongClickListener.isInitialized) {
                    itemLongClickListener.onLongClick(filteredList[adapterPosition])
                    true
                } else false
            }
        }

        fun bind(data: Ingredient) {
            binding.ingredient = data
            binding.leftday = when {
                data.leftDay > 0 -> {
                    "D+${data.leftDay}"
                }
                data.leftDay == 0 -> {
                    "D-Day"
                }
                else -> {
                    "D${data.leftDay}"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngreListBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount() = filteredList.size

    fun setItems(list: List<Ingredient>) {
        val sortedList = list.sortedBy { it.leftDay }
        this.list.clear()
        this.filteredList.clear()
        this.list.addAll(sortedList)
        this.filteredList.addAll(sortedList)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(data: Ingredient)
    }

    interface ItemLongClickListener {
        fun onLongClick(data: Ingredient)
    }

    fun getFilter(storage: CharSequence, categoryId: Int) {
        val newFilteredList = if (categoryId in 1..100) {
            list.filter { it.storage.value == storage.toString() && it.mainCategory == categoryId }
        } else {
            list.filter { it.storage.value == storage.toString() }
        }
        filteredList.clear()
        filteredList.addAll(newFilteredList)
        notifyDataSetChanged()
    }
}
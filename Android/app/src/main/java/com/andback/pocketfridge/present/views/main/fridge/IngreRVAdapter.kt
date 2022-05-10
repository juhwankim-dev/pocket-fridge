package com.andback.pocketfridge.present.views.main.fridge

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemIngreListBinding
import com.andback.pocketfridge.domain.model.Ingredient

class IngreRVAdapter: RecyclerView.Adapter<IngreRVAdapter.ViewHolder>(), Filterable {
    private val list = arrayListOf<Ingredient>()
    lateinit var itemClickListener: ItemClickListener
    lateinit var itemLongClickListener: ItemLongClickListener
    private var filteredList = arrayListOf<Ingredient>()


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
        this.list.clear()
        this.filteredList.clear()
        this.list.addAll(list)
        this.filteredList.addAll(list)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(data: Ingredient)
    }

    interface ItemLongClickListener {
        fun onLongClick(data: Ingredient)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val strKey = p0.toString()

                if (strKey.isBlank()) {
                    filteredList = list
                } else {
                    val newFilteredList = arrayListOf<Ingredient>()

                    for (item in list) {
                        if (item.name.contains(strKey)) {
                            newFilteredList.add(item)
                        }
                    }
                    filteredList = newFilteredList
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults) {
                filteredList = results.values as ArrayList<Ingredient>
                notifyDataSetChanged()
            }
        }
    }
}
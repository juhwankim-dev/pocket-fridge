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
    private val SORT_BY_EXP = 0
    private val REVERSER_SORT_BY_EXP = 1
    private val SORT_BY_KOR = 2
    private val REVERSER_SORT_BY_KOR = 3

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
                val category = p0.toString().toInt()

                filteredList = when(category) {
                    in 1..100 -> {
                        val newFilteredList = arrayListOf<Ingredient>()
                        list.filter { it.mainCategory == category }.forEach { newFilteredList.add(it) }
                        newFilteredList
                    }
                    else -> {
                        list
                    }
                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults) {
                filteredList = results.values as ArrayList<Ingredient>
                notifyDataSetChanged()
            }
        }
    }

    fun sortList(sortType: Int) {
        when(sortType) {
            SORT_BY_EXP -> filteredList.sortByDescending { it.leftDay }
            REVERSER_SORT_BY_EXP -> filteredList.sortBy { it.leftDay }
            SORT_BY_KOR -> filteredList.sortBy { it.name }
            REVERSER_SORT_BY_KOR -> filteredList.sortByDescending { it.name }
        }

        notifyDataSetChanged()
    }
}
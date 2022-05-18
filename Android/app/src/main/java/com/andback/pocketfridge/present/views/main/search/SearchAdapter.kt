package com.andback.pocketfridge.present.views.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.databinding.ItemSearchResultBinding
import com.andback.pocketfridge.domain.model.Ingredient

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>(), Filterable {
    private val fridges = ArrayList<FridgeEntity>()
    private val list = ArrayList<Ingredient>()
    private val filteredList = arrayListOf<Ingredient>()

    interface ItemClickListener {
        fun onClick(data: Ingredient, isOwner: Boolean)
    }
    lateinit var itemClickListener: ItemClickListener


    inner class ViewHolder(val binding: ItemSearchResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Ingredient, fridgeName: String) {
            binding.ingredient = data
            binding.fridge = fridgeName
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
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val fridge = fridges.find { it.id == filteredList[position].fridgeId }!!
            bind(filteredList[position], fridge.name)
            binding.root.setOnClickListener {
                itemClickListener.onClick(filteredList[position], fridge.isOwner)
            }
        }
    }

    override fun getItemCount() = filteredList.size

    fun setList(fridges: List<FridgeEntity>, ingredients: List<Ingredient>) {
        this.fridges.clear()
        list.clear()
        filteredList.clear()
        this.fridges.addAll(fridges)
        list.addAll(ingredients)
        filteredList.addAll(ingredients)
        sortList(SORT_BY_EXP)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val key = p0.toString()

                val newFilterList = if (key.isNotBlank()) {
                    list.filter { it.name.contains(key) }
                } else {
                    list
                }
                return FilterResults().apply { values = newFilterList }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults) {
                filteredList.clear()
                filteredList.addAll(results.values as ArrayList<Ingredient>)
                notifyDataSetChanged()
            }

        }
    }

    fun sortList(sortType: Int) {
        when (sortType) {
            SORT_BY_EXP -> filteredList.sortByDescending { it.leftDay }
            REVERSER_SORT_BY_EXP -> filteredList.sortBy { it.leftDay }
            SORT_BY_KOR -> filteredList.sortBy { it.name }
            REVERSER_SORT_BY_KOR -> filteredList.sortByDescending { it.name }
        }
        notifyDataSetChanged()
    }

    companion object {
        const val SORT_BY_EXP = 0
        const val REVERSER_SORT_BY_EXP = 1
        const val SORT_BY_KOR = 2
        const val REVERSER_SORT_BY_KOR = 3
    }
}
package com.andback.pocketfridge.present.views.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.databinding.ItemSubCategoryListBinding

private const val TAG = "SubCategoryRVAdapter_debuk"
class SubCategoryRVAdapter: RecyclerView.Adapter<SubCategoryRVAdapter.ViewHolder>(), Filterable {
    private var list = arrayListOf<SubCategoryEntity>()
    private val filteredList = arrayListOf<SubCategoryEntity>()
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(val binding: ItemSubCategoryListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(subCategory: SubCategoryEntity) {
            binding.subCategory = subCategory
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSubCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(filteredList[position])
            itemView.setOnClickListener {
                if(this@SubCategoryRVAdapter::itemClickListener.isInitialized) {
                    itemClickListener.onClick(filteredList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${list.size}")
        return filteredList.size
    }

    interface ItemClickListener {
        fun onClick(subCategoryEntity: SubCategoryEntity)
    }

    fun setItems(list: List<SubCategoryEntity>) {
        this.list.clear()
        this.list.addAll(list)
        this.filteredList.clear()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(input: CharSequence?): FilterResults {
                val key = input?.toString()

                val newFilterList = if (key != null && key.isNotBlank()) {
                    list.filter { it.subCategoryName.contains(key) }
                } else {
                    list
                }
                return FilterResults().apply { values = newFilterList }
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults) {
                filteredList.clear()
                filteredList.addAll(results.values as ArrayList<SubCategoryEntity>)
                notifyDataSetChanged()
            }

        }
    }
}
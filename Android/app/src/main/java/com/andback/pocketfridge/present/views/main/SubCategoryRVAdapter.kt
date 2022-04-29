package com.andback.pocketfridge.present.views.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.databinding.ItemSubCategoryListBinding

private const val TAG = "SubCategoryRVAdapter_debuk"
class SubCategoryRVAdapter: RecyclerView.Adapter<SubCategoryRVAdapter.ViewHolder>() {
    private var list = arrayListOf<SubCategoryEntity>()
    lateinit var itemClickListener: ItemClickListener

    inner class ViewHolder(val binding: ItemSubCategoryListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(subCategory: SubCategoryEntity) {
            binding.subCategory = subCategory
            Log.d(TAG, "bind: ${subCategory.subCategoryName}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSubCategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            itemView.setOnClickListener {
                if(this@SubCategoryRVAdapter::itemClickListener.isInitialized) {
                    itemClickListener.onClick(list[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${list.size}")
        return list.size
    }

    interface ItemClickListener {
        fun onClick(subCategoryEntity: SubCategoryEntity)
    }

    fun setItems(list: List<SubCategoryEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}
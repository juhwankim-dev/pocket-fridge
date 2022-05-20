package com.andback.pocketfridge.present.views.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.databinding.ItemModalSelectListBinding

class SearchModalAdapter(val spinnerList: Array<String>) : RecyclerView.Adapter<SearchModalAdapter.ViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener
    private var checkedIndex = 0

    inner class ViewHolder(private val binding: ItemModalSelectListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(spinnerText: String) {
            binding.spinnerText = spinnerText
            binding.isChecked = (checkedIndex == layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemModalSelectListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bindInfo(spinnerList[position])

            //클릭연결
            itemView.setOnClickListener {
                checkedIndex = layoutPosition
                itemClickListener.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int = spinnerList.size

    interface ItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}
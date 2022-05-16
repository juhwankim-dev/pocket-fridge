package com.andback.pocketfridge.present.views.main.mypage.fridgemanage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.ShareUserEntity
import com.andback.pocketfridge.databinding.ItemShareMemberBinding
import com.bumptech.glide.Glide

class ShareMemberAdapter(private val isFridgeOwner: Boolean) : RecyclerView.Adapter<ShareMemberAdapter.ViewHolder>() {
    private val list = ArrayList<ShareUserEntity>()

    interface ItemClickListener {
        fun onClick(data: ShareUserEntity)
    }
    lateinit var itemClickListener: ItemClickListener



    inner class ViewHolder(val binding: ItemShareMemberBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ShareUserEntity) {
            binding.tvShareMemberINickname.text = data.nickname
            binding.tvShareMemberIEmail.text = data.email
            Glide.with(binding.root)
                .load(data.pic)
                .error(R.drawable.ic_profile_default)
                .fallback(R.drawable.ic_profile_default)
                .circleCrop()
                .into(binding.ivShareMemberIProfile)

            when (isFridgeOwner) {
                true  -> binding.ibShareMemberIOut.visibility = View.VISIBLE
                false -> binding.ibShareMemberIOut.visibility = View.GONE
            }
            when (data.isOwner) {
                true  ->  {
                    binding.ivShareMemberIOwner.visibility = View.VISIBLE
                    binding.ibShareMemberIOut.visibility = View.GONE
                }
                false -> binding.ivShareMemberIOwner.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShareMemberBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(list[position])
            binding.ibShareMemberIOut.setOnClickListener {
                itemClickListener.onClick(list[position])
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun setList(list: List<ShareUserEntity>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

}
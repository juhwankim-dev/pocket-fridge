package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentFridgeBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.Storage
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FridgeFragment : BaseFragment<FragmentFridgeBinding>(R.layout.fragment_fridge) {
    private lateinit var rvAdapter: IngreRVAdapter
    private val viewModel: FridgeViewModel by activityViewModels()
    private val detailViewModel: IngreDetailViewModel by activityViewModels()
    private lateinit var profileImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setToolbar()
        setRecyclerView()
        setObservers()
    }

    private fun setToolbar() {
        val menu = binding.tbFridgeF.menu
        val menuItem = menu.getItem(1)

        // 프로필 이미지 뷰 초기화, 클릭 리스너 할당
        profileImageView = menuItem.actionView.findViewById<ImageView?>(R.id.iv_menu_fridge_profile).apply {
            setOnClickListener {
                findNavController().navigate(R.id.action_fridgeFragment_to_myPageFragment)
            }
        }

        // 알림 아이콘 클릭 처리
        binding.tbFridgeF.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.notification_menu_fridge -> {
                    // TODO: 알림 fragment로 이동
                    true
                }
                else -> false
            }
        }

    }

    private fun setRecyclerView() {
        rvAdapter = IngreRVAdapter().apply {
            itemClickListener = object : IngreRVAdapter.ItemClickListener {
                override fun onClick(data: Ingredient) {
                    Log.d(TAG, "onClick: ${data}")
                    detailViewModel.selectIngre(data)
                    findNavController().navigate(R.id.action_fridgeFragment_to_ingreDetailFragment)
                }
            }
            itemLongClickListener = object : IngreRVAdapter.ItemLongClickListener {
                override fun onLongClick(data: Ingredient) {
                    Log.d(TAG, "onLongClick: ${data}")
                    showDeleteDialog(data)
                }
            }
        }

        binding.rvFridgeF.apply {
            adapter = rvAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setObservers() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                ingreList.observe(owner) {
                    rvAdapter.setItems(it)
                }
                // toolbar의 imageView에 프로필 이미지 적용
                user.observe(owner) {
                    if(::profileImageView.isInitialized) {
                        Glide.with(profileImageView).load(it.picture).circleCrop().into(profileImageView)
                    }
                }
            }
        }
    }

    private fun showDeleteDialog(ingre: Ingredient) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_ingre_title))
            .setMessage("${ingre.name}")
            .setCancelable(true)
            .setPositiveButton(resources.getString(R.string.delete_button)) { dialog, which ->
                viewModel.deleteIngreById(ingre.id)
            }
            .show()
    }
    
    companion object {
        private const val TAG = "FridgeFragment_debuk"
    }
}
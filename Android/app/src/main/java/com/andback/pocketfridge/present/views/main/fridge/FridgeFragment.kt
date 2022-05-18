package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.databinding.FragmentFridgeBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.FridgeListAdapter
import com.andback.pocketfridge.present.utils.Storage
import com.google.android.material.chip.Chip
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FridgeFragment : BaseFragment<FragmentFridgeBinding>(R.layout.fragment_fridge) {
    private lateinit var rvAdapter: IngreRVAdapter
    private val viewModel: FridgeViewModel by activityViewModels()
    private val detailViewModel: IngreDetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.getFridges()
        viewModel.getMainCategory()
        setToolbar()
        setRecyclerView()
        setObservers()
        setChipGroup()
        setTabLayout()
    }

    private fun setTabLayout() {
        val tabLayout = binding.tabLayoutFridgeF
        tabLayout.addTab(tabLayout.newTab().setText(Storage.Fridge.value))
        tabLayout.addTab(tabLayout.newTab().setText(Storage.Freeze.value))
        tabLayout.addTab(tabLayout.newTab().setText(Storage.Room.value))
    }

    private fun setToolbar() {

        // 알림 아이콘 클릭 처리
        binding.tbFridgeF.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.notification_menu_fridge -> {
                    findNavController().navigate(R.id.action_fridgeFragment_to_notificationFragment)
                    true
                }
                else -> false
            }
        }

        // 냉장고 타이틀 클릭 처리
        binding.llFridgeF.setOnClickListener {
            FridgeListBottomSheet(
                viewModel.fridges.value!!,
                viewModel.selectedFridge.value!!.id
            ).apply {
                fridgeAdapter.itemClickListener = object : FridgeListAdapter.ItemClickListener {
                    override fun onClick(data: FridgeEntity) {
                        viewModel.updateSelectedFridgeThenGetIngreList(data.id)
                        dismiss()
                    }
                }
            }.show(requireActivity().supportFragmentManager, FridgeListBottomSheet.TAG)
        }
    }

    private fun setRecyclerView() {
        rvAdapter = IngreRVAdapter().apply {
            itemClickListener = object : IngreRVAdapter.ItemClickListener {
                override fun onClick(data: Ingredient) {
                    Log.d(TAG, "onClick: ${data}")
                    detailViewModel.selectIngre(data)
                    findNavController().navigate(
                        FridgeFragmentDirections.actionFridgeFragmentToIngreDetailFragment(
                            viewModel.selectedFridge.value!!.isOwner
                        )
                    )
                }
            }
            itemLongClickListener = object : IngreRVAdapter.ItemLongClickListener {
                override fun onLongClick(data: Ingredient) {
                    Log.d(TAG, "onLongClick: ${data}")
                    if (viewModel.selectedFridge.value!!.isOwner) {
                        showDeleteDialog(data)
                    }
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

                selectedFridge.observe(owner) {
                    binding.tvFridgeFName.setText(it.name)
                }
            }

            mainCategoryList.observe(viewLifecycleOwner) {
                it.forEach { category ->
                    val chip = layoutInflater.inflate(R.layout.custom_chip_view, binding.cgFridgeFFilter, false) as Chip
                    chip.id = View.generateViewId()
                    chip.text = category.mainCategoryName
                    binding.cgFridgeFFilter.addView(chip)
                }
            }

            hasNewNotification.observe(viewLifecycleOwner) {
                if(it == true) {
                    // TODO: 알림 아이콘 빨간점 보이기
                } else {
                    // TODO: 알림 아이콘 빨간점 삭제
                }
            }
        }
    }

    private fun setChipGroup() {
        binding.cgFridgeFFilter.setOnCheckedChangeListener { group, checkedId ->
            rvAdapter.filter?.filter("$checkedId")
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
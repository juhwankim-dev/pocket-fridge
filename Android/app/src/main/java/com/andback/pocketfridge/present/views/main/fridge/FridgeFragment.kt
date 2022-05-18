package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.view.View
import androidx.core.view.size
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

class FridgeFragment : BaseFragment<FragmentFridgeBinding>(R.layout.fragment_fridge) {
    private lateinit var rvAdapter: IngreRVAdapter
    private val viewModel: FridgeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.getFridges()
        viewModel.getMainCategory()
        setToolbar()
        setTabLayout()
        setRecyclerView()
        setObservers()
        setFilter()
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
        binding.tvFridgeFName.setOnClickListener {
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

    private fun setTabLayout() {
        binding.tabLayoutFridgeF.apply {
            addTab(this.newTab().setText(Storage.Fridge.value))
            addTab(this.newTab().setText(Storage.Freeze.value))
            addTab(this.newTab().setText(Storage.Room.value))
        }
    }

    private fun setRecyclerView() {
        rvAdapter = IngreRVAdapter().apply {
            itemClickListener = object : IngreRVAdapter.ItemClickListener {
                override fun onClick(data: Ingredient) {
                    findNavController().navigate(
                        FridgeFragmentDirections.actionFridgeFragmentToIngreDetailFragment(
                            viewModel.selectedFridge.value!!.isOwner,
                            data
                        )
                    )
                }
            }
            itemLongClickListener = object : IngreRVAdapter.ItemLongClickListener {
                override fun onLongClick(data: Ingredient) {
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
                    rvAdapter.getFilter(Storage.Fridge.value, 0)
                }

                selectedFridge.observe(owner) {
                    binding.tvFridgeFName.text = it.name
                }
            }

            mainCategoryList.observe(viewLifecycleOwner) {
                if (binding.cgFridgeFFilter.size > 1)
                    binding.cgFridgeFFilter.removeViews(1, binding.cgFridgeFFilter.size-1)

                it.forEach { category ->
                    val chip = layoutInflater.inflate(R.layout.custom_chip_view, binding.cgFridgeFFilter, false) as Chip
                    chip.id = category.mainCategoryId
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

    private fun setFilter() {
        var storage = Storage.Fridge.value
        var categoryId = 0

        binding.cgFridgeFFilter.setOnCheckedChangeListener { group, checkedId ->
            categoryId = checkedId
            rvAdapter.getFilter(storage, checkedId)
        }

        binding.tabLayoutFridgeF.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                storage = tab.text.toString()
                rvAdapter.getFilter(tab.text!!, categoryId)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
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
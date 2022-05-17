package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.util.Log
import android.view.View
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
    private lateinit var profileImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onStart() {
        super.onStart()
        binding.spinnerFridgeF.setSelection(0)
    }

    private fun init() {
        viewModel.getFridges()
        viewModel.getMainCategory()
        setToolbar()
        setRecyclerView()
        setObservers()
        setSearchBar()
        setChipGroup()
        setSpinner()
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
                    findNavController().navigate(R.id.action_fridgeFragment_to_notificationFragment)
                    true
                }
                else -> false
            }
        }

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
                // toolbar의 imageView에 프로필 이미지 적용
                user.observe(owner) {
                    if(::profileImageView.isInitialized) {
                        Glide.with(profileImageView).load(it.picture).circleCrop().into(profileImageView)
                    }
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

    private fun setSearchBar() {
        binding.tilFridgeF.editText?.addTextChangedListener {
            rvAdapter.filter.filter(it.toString())
        }
    }

    private fun setChipGroup() {
        binding.cgFridgeFFilter.setOnCheckedChangeListener { group, checkedId ->
            rvAdapter.filter?.filter("$checkedId")
        }
    }

    private fun setSpinner() {
        val sortAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.spinner_list))
        binding.spinnerFridgeF.adapter = sortAdapter
        binding.spinnerFridgeF.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                rvAdapter.sortList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                rvAdapter.sortList(0)
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
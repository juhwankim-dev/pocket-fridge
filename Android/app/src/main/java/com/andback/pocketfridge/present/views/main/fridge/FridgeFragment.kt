package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentFridgeBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.Storage

class FridgeFragment : BaseFragment<FragmentFridgeBinding>(R.layout.fragment_fridge) {
    private lateinit var rvAdapter: IngreRVAdapter
    private val viewModel: FridgeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setRecyclerView()
        setObservers()
    }

    private fun setRecyclerView() {
        rvAdapter = IngreRVAdapter().apply {
            itemClickListener = object : IngreRVAdapter.ItemClickListener {
                override fun onClick(data: Ingredient) {
                    Log.d(TAG, "onClick: ${data}")
                    // TODO: 디테일 fragment로 이동
                }
            }
            itemLongClickListener = object : IngreRVAdapter.ItemLongClickListener {
                override fun onLongClick(data: Ingredient) {
                    Log.d(TAG, "onLongClick: ${data}")
                    // TODO: 삭제 다이얼로그 호출
                }
            }
        }
        // 더미 데이터
        val list = mutableListOf(Ingredient(1, 0, "2022-05-01", "2022-05-03", "돼지고기", -1, Storage.Fridge))
        for(i in 0..5) {
            list.add(Ingredient(1, 0, "2022-05-01", "2022-05-03", "돼지고기", -1, Storage.Fridge))
        }
        rvAdapter.setItems(list)

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
            }
        }
    }
    
    companion object {
        private const val TAG = "FridgeFragment_debuk"
    }
}
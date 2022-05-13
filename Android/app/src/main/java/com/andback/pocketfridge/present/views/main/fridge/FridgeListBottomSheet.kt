package com.andback.pocketfridge.present.views.main.fridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.databinding.FragmentFridgeListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FridgeListBottomSheet(private val list: List<FridgeEntity>, private val curId: Int) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFridgeListBinding
    val fridgeAdapter = FridgeListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fridge_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerview()
        setEvent()
    }

    private fun setRecyclerview() {
        binding.rvFridgeListF.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = fridgeAdapter
        }
        fridgeAdapter.setList(list, curId)
    }

    private fun setEvent() {
        binding.ibFridgeListFClose.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "FridgeListBottomSheet"
    }
}
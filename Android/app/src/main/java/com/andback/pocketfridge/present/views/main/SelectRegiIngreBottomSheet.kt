package com.andback.pocketfridge.present.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentSelectRegiIngreBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SelectRegiIngreBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSelectRegiIngreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_regi_ingre, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
package com.andback.pocketfridge.present.views.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentSelectRegiIngreBinding
import com.andback.pocketfridge.present.views.main.ingreupload.IngreUploadFragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBottomSheetFClose.setOnClickListener {
            dismiss()
        }
        binding.llBottomSheetFBarcode.setOnClickListener {
            findNavController().navigate(R.id.barcodeScanFragment)
            dismiss()
        }
        binding.llBottomSheetFUploadIngre.setOnClickListener {
            findNavController().navigate(R.id.ingreUploadFragment)
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}
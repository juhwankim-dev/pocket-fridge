package com.andback.pocketfridge.present.views.main.barcode

import android.os.Bundle
import android.view.View
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentBarcodeScanBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.MainActivity


class BarcodeScanFragment : BaseFragment<FragmentBarcodeScanBinding>(R.layout.fragment_barcode_scan) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvent()
    }

    override fun onStart() {
        super.onStart()
        // TODO 하단 네비 바 숨기기
    }

    override fun onStop() {
        super.onStop()
        // TODO 하단 네비 바 나타내기

    }

    private fun setEvent() {
        binding.tvBarcodeScanFCancel.setOnClickListener {
            // TODO 뒤로가기
        }
    }
}
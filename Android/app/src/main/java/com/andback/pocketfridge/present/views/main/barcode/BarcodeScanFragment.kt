package com.andback.pocketfridge.present.views.main.barcode

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentBarcodeScanBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.gun0912.tedpermission.rx2.TedPermission
import java.lang.Exception


class BarcodeScanFragment : BaseFragment<FragmentBarcodeScanBinding>(R.layout.fragment_barcode_scan) {
    private val TAG = "BarcodeFragment_debuk"


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCameraPermission()
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

    private fun setCameraPermission() {
        TedPermission.create()
            .setPermissions(Manifest.permission.CAMERA)
            .request()
            .subscribe(
                { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                        startCamera()
                    } else {
                        // TODO : 권한 얻기에 실패했을 때 동작처리
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                },
                { throwable ->
                    // TODO : 오류 처리
                })
    }

    private fun setEvent() {
        binding.tvBarcodeFCancel.setOnClickListener {
            // TODO 뒤로가기
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.prevBarcodeFCamera.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)

            } catch (e: Exception) {
                Log.e(TAG, "camera binding failed", e)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }
}
package com.andback.pocketfridge.present.views.main.barcode

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentBarcodeScanBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.MainActivity
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.gun0912.tedpermission.rx2.TedPermission
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class BarcodeScanFragment : BaseFragment<FragmentBarcodeScanBinding>(R.layout.fragment_barcode_scan) {
    private val TAG = "BarcodeFragment_debuk"
    private var cameraExecutor: ExecutorService? = null
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    private var cameraProvider: ProcessCameraProvider? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        setCameraPermission()
        setEvent()
    }

    override fun onStart() {
        super.onStart()
        (context as MainActivity).hideBottomNav(true)
    }

    override fun onStop() {
        super.onStop()
        (context as MainActivity).hideBottomNav(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraProvider?.unbindAll()
        cameraProviderFuture?.cancel(true)
        cameraExecutor?.shutdown()
    }

    @SuppressLint("CheckResult")
    private fun setCameraPermission() {
        TedPermission.create()
            .setPermissions(Manifest.permission.CAMERA)
            .request()
            .subscribe(
                { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                        setUpCamera()
                    } else {
                        // TODO : 권한 얻기에 실패했을 때 동작처리
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                },
                { throwable ->
                    showToastMessage("문제가 발생하였습니다. 종료 후 다시 실행해주세요.")
                    Log.e(TAG, "setCameraPermission: ${throwable.message}")
                })
    }

    private fun setEvent() {
        binding.tvBarcodeFCancel.setOnClickListener {
            // TODO 뒤로가기
        }
    }

    private fun setUpCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture!!.addListener({
            bindUseCases()
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindUseCases() {
        // Used to bind the lifecycle of cameras to the lifecycle owner
        cameraProvider = cameraProviderFuture!!.get()
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.prevBarcodeFCamera.surfaceProvider)
            }
        val imageAnalyzer = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(cameraExecutor!!, ImageAnalysis.Analyzer { imageProxy ->
                    scanBarcodes(imageProxy)
                })
            }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            // Unbind use cases before rebinding
            cameraProvider!!.unbindAll()

            // Bind use cases to camera
            cameraProvider!!.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageAnalyzer)

        } catch (e: Exception) {
            Log.e(TAG, "camera binding failed", e)
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun scanBarcodes(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_EAN_8,
                    Barcode.FORMAT_EAN_13)
                .build()
            val scanner = BarcodeScanning.getClient(options)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    readBarcodeData(barcodes)
                }
                .addOnFailureListener {
                    Log.e(TAG, "scanBarcodes: ${it.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    private fun readBarcodeData(barcodes: List<Barcode>) {
        val list = mutableListOf<String>()
        for (barcode in barcodes) {
            when (barcode.format) {
                Barcode.FORMAT_EAN_8 -> {

                }
                Barcode.FORMAT_EAN_13 -> {

                }
            }
            list.add(barcode.rawValue.toString())
            Log.d(TAG, "scanBarcodes: ${barcode.rawValue}")
        }
        if (list.size > 0)
            loadIngresFromBarcodes(list)
    }

    // TODO : 제품 정보 가져와서 식재료 등록으로 전달
    private fun loadIngresFromBarcodes(barcodes: List<String>) {
        showToastMessage(barcodes.toString())
        cameraExecutor!!.shutdownNow()
    }
}
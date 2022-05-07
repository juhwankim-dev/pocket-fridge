package com.andback.pocketfridge.present.views.main.barcode

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentBarcodeScanBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.gun0912.tedpermission.rx2.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class BarcodeScanFragment : BaseFragment<FragmentBarcodeScanBinding>(R.layout.fragment_barcode_scan) {
    private val TAG = "BarcodeFragment_debuk"
    private var cameraExecutor: ExecutorService? = null
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    private var cameraProvider: ProcessCameraProvider? = null

    private val viewModel: BarcodeScanViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        setCameraPermission()
        setEvent()
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
                        setObserve()
                        setUpCamera()
                    } else {
                        showToastMessage(resources.getString(R.string.permission_error))
                    }
                },
                { throwable ->
                    showToastMessage(resources.getString(R.string.permission_error))
                    Log.e(TAG, "setCameraPermission: ${throwable.message}")
                })
    }

    private fun setEvent() {
        binding.tvBarcodeScanFCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvBarcodeScanFSelfInput.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setView(R.layout.fragment_barcode_self_input)
                .show()
                .also { alertDialog ->
                    if (alertDialog == null) {
                        return@also
                    }

                    val etInput = alertDialog.findViewById<EditText>(R.id.et_barcode_inputF)
                    val tvCancel = alertDialog.findViewById<TextView>(R.id.tv_barcode_inputF_cancel)
                    val tvAccept = alertDialog.findViewById<TextView>(R.id.tv_barcode_inputF_accept)

                    tvCancel.setOnClickListener {
                        alertDialog.dismiss()
                    }
                    tvAccept.setOnClickListener {
                        if (etInput.text.isNotBlank()) {
                            loadIngresFromBarcodes(etInput.text.toString())
                            alertDialog.dismiss()
                        }
                    }
                }
        }
    }

    private fun setObserve() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                productName.observe(owner) {
                    if (it.isNotBlank()) {
                        findNavController().navigate(
                            BarcodeScanFragmentDirections.actionBarcodeScanFragmentToIngreUploadFragment(it)
                        )

                    } else {
                        showToastMessage(resources.getString(R.string.barcode_scan_fail))
                        findNavController().navigate(
                            R.id.action_barcodeScanFragment_to_ingreUploadFragment
                        )
                    }
                }
                networkError.observe(owner) {
                    if (it == true) {
                        showToastMessage(resources.getString(R.string.network_error))
                    }
                }
            }
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
        // TODO : 이후에 여러개 처리하도록 구현
        if (barcodes.isNotEmpty()) {
            loadIngresFromBarcodes(barcodes[0].rawValue.toString())
        }
    }

    private fun loadIngresFromBarcodes(barcode: String) {
        viewModel.getProductNameFromBarcode(barcode)
        cameraExecutor!!.shutdownNow()
    }
}
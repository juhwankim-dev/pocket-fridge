package com.andback.pocketfridge.present.views.main.mypage.useredit

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.UserEditEntity
import com.andback.pocketfridge.databinding.FragmentUserEditBinding
import com.andback.pocketfridge.present.config.ApplicationClass.Companion.storageRef
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.SignUpChecker
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.rx2.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class UserEditFragment : BaseFragment<FragmentUserEditBinding>(R.layout.fragment_user_edit) {
    private val viewModel: UserEditViewModel by viewModels()
    private var selectedFileUri: Uri? = null
    var isValidNickname = false
    var isValidPw = false
    var email = "example@gmail.com"

    private val getCommentMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedFileUri = result.data?.data!!
            setProfileImage(binding.ivUserEditFPicture, selectedFileUri)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        viewModel.getUser()
        checkPermission()
        initEvent()
        initViewModel()
    }

    private fun initViewModel() {
        with(viewModel) {
            personalInfo.observe(viewLifecycleOwner) {
                binding.etUserEditFNickname.setText(it.nickname)
                email = it.email
            }
        }
    }

    private fun initEvent() {
        binding.tbUserEditF.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivUserEditFPicture.setOnClickListener {
            selectImageFromGallery()
        }

        binding.etUserEditFNickname.addTextChangedListener { newText ->
            SignUpChecker.validateNickname(newText.toString()).apply {
                binding.tilUserEditFNickname.error = resources.getString(stringId)
                isValidNickname = isValid
            }
        }

        binding.etUserEditFPw.addTextChangedListener { newText ->
            SignUpChecker.validatePw(newText.toString()).apply {
                binding.tilUserEditFPw.error = resources.getString(stringId)
                isValidPw = isValid
            }
        }

        binding.btnUserEditFAccept.setOnClickListener {
            viewModel.isLoading.value = true
            if((isValidNickname && binding.etUserEditFPw.text.toString() == "") ||
                (isValidNickname && isValidPw)) {
                // 갤러리로부터 사진을 선택하지 않은 경우 (프로필 변경하지 않는 경우)
                if(selectedFileUri == null) {
                    updateUserWithExistedUrl()
                }

                // 갤러리에서 사진을 선택한 경우 (프로필 변경)
                else {
                    uploadProfileImage()
                }
            } else {
                viewModel.isLoading.value = false
                showToastMessage(resources.getString(R.string.user_edit_empty_error))
            }
        }
    }

    private fun updateUserWithExistedUrl() {
        viewModel.updateUser(
            UserEditEntity(
                binding.etUserEditFNickname.text.toString(),
                binding.etUserEditFPw.text.toString(),
                viewModel.personalInfo.value?.picture
            )
        )
    }

    private fun uploadProfileImage() {
        CoroutineScope(Dispatchers.IO).launch {
            storageRef.child("profile").child(email).child(email).putBytes(getDownsizedBitmap()).addOnCompleteListener {
                updateUserWithNewUrl()
            }.addOnFailureListener {
                showToastMessage(resources.getString(R.string.network_error))
                viewModel.isLoading.value = false
            }
        }
    }

    private fun updateUserWithNewUrl() {
        storageRef.child("profile").child(email).child(email).downloadUrl.addOnCompleteListener { uri ->
            viewModel.updateUser(
                UserEditEntity(
                    binding.etUserEditFNickname.text.toString(),
                    binding.etUserEditFPw.text.toString(),
                    uri.result.toString()
                )
            )
        }.addOnFailureListener {
            showToastMessage(resources.getString(R.string.network_error))
        }
    }

    private fun getDownsizedBitmap(): ByteArray {
        var bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedFileUri)
        var stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream)
        return stream.toByteArray()
    }

    private fun selectImageFromGallery() {
        var intent = Intent().apply {
            type = "image/*"
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/png", "image/jpeg", "image/gif", "video/mp4")
            )
            action = Intent.ACTION_PICK
        }

        getCommentMedia.launch(intent)
    }

    private fun setProfileImage(view: ImageView, src: Uri?) {
        if(src != null) {
            Glide.with(view.context)
                .load(src)
                .circleCrop()
                .placeholder(R.drawable.ic_person_24)
                .error(R.drawable.ic_person_24)
                .into(view)
        } else {
            view.setBackgroundResource(R.drawable.ic_person_24)
        }
    }

    @SuppressLint("CheckResult")
    private fun checkPermission() {
        TedPermission.create()
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .request()
            .subscribe(
                { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {

                    } else {
                        findNavController().popBackStack()
                    }
                },
                { _ ->

                })
    }
}
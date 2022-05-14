package com.andback.pocketfridge.present.views.main.mypage


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.databinding.FragmentDialogInputBinding
import com.andback.pocketfridge.databinding.FragmentFridgeManageBinding
import com.andback.pocketfridge.databinding.FragmentFridgeManageOptionBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.FridgeListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FridgeManageFragment : BaseFragment<FragmentFridgeManageBinding>(R.layout.fragment_fridge_manage) {
    private val fmAdapter = FridgeListAdapter()
    private val viewModel: FridgeManageViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setObserve()
        setEvent()
    }

    private fun setObserve() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                // TODO: 냉장고 목록 가져오기
                fridges.observe(owner) {
                    if (it.isEmpty()) {
                        binding.rvFridgeManageF.visibility = View.GONE
                        binding.llFridgeManageFEmpty.visibility = View.VISIBLE
                    } else {
                        binding.rvFridgeManageF.visibility = View.VISIBLE
                        binding.llFridgeManageFEmpty.visibility = View.GONE
                        fmAdapter.setList(it, -1)
                    }
                }

                // TODO: 냉장고 이름 수정 적용
                // TODO: 냉장고 삭제 적용
            }
        }
    }

    private fun setView() {
        binding.rvFridgeManageF.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = fmAdapter
        }
    }

    private fun setEvent() {
        binding.llFridgeManageFShareFridge.setOnClickListener {
            showShareFridgeDialog()
        }
        binding.llFridgeManageFAddFridge.setOnClickListener {
            showAddFridgeDialog()
        }
        fmAdapter.itemClickListener = object : FridgeListAdapter.ItemClickListener {
            override fun onClick(data: FridgeEntity) {
                showOptionDialog()
            }
        }
    }

    private fun showShareFridgeDialog() {
        // TODO: 공유 xml 만들고 dialog 띄우기
    }

    private fun showAddFridgeDialog() {
        val dialogBinding = FragmentDialogInputBinding.inflate(LayoutInflater.from(requireContext()))

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) {
                    return@also
                }

                dialogBinding.tvDialogTitle.text = resources.getString(R.string.add_fridge)

                dialogBinding.tvDialogInputFCancel.setOnClickListener {
                    alertDialog.dismiss()
                }
                dialogBinding.tvDialogInputFAccept.setOnClickListener {
                    if (dialogBinding.etDialogInputF.text.isNotBlank()) {
                        // TODO : 냉장고 추가
                        alertDialog.dismiss()
                    }
                }
            }
    }

    private fun showOptionDialog() {
        val dialogBinding = FragmentFridgeManageOptionBinding.inflate(LayoutInflater.from(requireActivity()))

        BottomSheetDialog(requireContext()).apply {
            setContentView(dialogBinding.root)

            dialogBinding.ibFridgeManageOptionFCancel.setOnClickListener {
                dismiss()
            }
            dialogBinding.llFridgeManageOptionFShowMember.setOnClickListener {
                // TODO : 공유원들 보여주는 창 띄우기
                dismiss()
            }
            dialogBinding.llFridgeManageOptionFEditName.setOnClickListener {
                // TODO : 이름 수정 다이알로그 띄우기
                dismiss()
            }
            dialogBinding.llFridgeManageOptionFDelete.setOnClickListener {
                // TODO : 삭제 다이알로그 띄우기
                dismiss()
            }

            show()
        }
    }
}
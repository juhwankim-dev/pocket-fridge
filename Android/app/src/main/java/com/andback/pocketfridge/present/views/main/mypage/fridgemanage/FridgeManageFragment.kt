package com.andback.pocketfridge.present.views.main.mypage.fridgemanage


import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
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
                tstMsg.observe(owner) {
                    showToastMessage(it)
                }
                tstErrorMsg.observe(owner) {
                    showToastMessage(it)
                }
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
        binding.llFridgeManageFAddFridge.setOnClickListener {
            showFridgeNameDialog()
        }
        fmAdapter.itemClickListener = object : FridgeListAdapter.ItemClickListener {
            override fun onClick(data: FridgeEntity) {
                showOptionDialog(data)
            }
        }
    }

    private fun showFridgeNameDialog(data: FridgeEntity? = null) {
        val dialogBinding = FragmentDialogInputBinding.inflate(LayoutInflater.from(requireContext()))
        val title = if (data != null) {
            resources.getString(R.string.edit_fridge_name)
        } else {
            resources.getString(R.string.add_fridge)
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .show()
            .also { alertDialog ->
                if (alertDialog == null) {
                    return@also
                }

                dialogBinding.tvDialogTitle.text = title
                dialogBinding.etDialogInputF.apply {
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
                    data?.let { setText(data.name) }
                }

                dialogBinding.tvDialogInputFCancel.setOnClickListener {
                    alertDialog.dismiss()
                }
                dialogBinding.tvDialogInputFAccept.setOnClickListener {
                    val name = dialogBinding.etDialogInputF.text.toString()

                    if (name.isNotBlank()) {
                        if (data != null) {
                            if (name == data.name) {
                                showToastMessage(resources.getString(R.string.not_change))
                            } else {
                                viewModel.updateFridgeName(data.id, dialogBinding.etDialogInputF.text.toString())
                                alertDialog.dismiss()
                            }
                        } else {
                            viewModel.createFridge(dialogBinding.etDialogInputF.text.toString())
                            alertDialog.dismiss()
                        }
                    }
                }
            }
    }

    private fun showOptionDialog(fridge: FridgeEntity) {
        val dialogBinding = FragmentFridgeManageOptionBinding.inflate(LayoutInflater.from(requireActivity()))

        BottomSheetDialog(requireContext()).apply {
            setContentView(dialogBinding.root)

            if (fridge.isOwner == false) {
                dialogBinding.llFridgeManageOptionFEditName.visibility = View.GONE
            }

            dialogBinding.ibFridgeManageOptionFCancel.setOnClickListener {
                dismiss()
            }
            dialogBinding.llFridgeManageOptionFShowMember.setOnClickListener {
                showShareFridgeDialog()
                dismiss()
            }
            dialogBinding.llFridgeManageOptionFEditName.setOnClickListener {
                showFridgeNameDialog(fridge)
                dismiss()
            }
            dialogBinding.llFridgeManageOptionFDelete.setOnClickListener {
                val title = if (fridge.isOwner) getString(R.string.delete_fridge_dialog_title_owner, fridge.name)
                            else getString(R.string.delete_fridge_dialog_title, fridge.name)

                AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setMessage("")
                    .setNegativeButton(R.string.cancel, null)
                    .setPositiveButton(R.string.accept) { dialog, which ->
                        viewModel.deleteFridge(fridge.id)
                    }
                    .show()
                dismiss()
            }

            show()
        }
    }

    private fun showShareFridgeDialog() {
        // TODO: 공유 xml 만들고 dialog 띄우기
    }
}
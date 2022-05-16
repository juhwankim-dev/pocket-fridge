package com.andback.pocketfridge.present.views.main.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentNotificationBinding
import com.andback.pocketfridge.present.config.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>(R.layout.fragment_notification) {
    private val viewModel: NotificationViewModel by viewModels()
    private val rvAdapter = NotificationRVAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    private fun init() {
        setRecyclerView()
        setObserver()
        setToolbar()
    }

    private fun setObserver() {
        binding.lifecycleOwner?.let { owner ->
            with(viewModel) {
                notificationList.observe(owner) {
                    if(it.isNotEmpty()) {
                        rvAdapter.updateItems(it)
                        dismissEmptyUi()
                    } else {
                        showEmptyUi()
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.rvNotificationF.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    private fun setToolbar() {
        binding.tbNotificationF.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun showEmptyUi() {
// TODO: 리스트 비어있을 때 
    }

    private fun dismissEmptyUi() {
// TODO: 리스트 빈 화면 dismiss 
    }

}
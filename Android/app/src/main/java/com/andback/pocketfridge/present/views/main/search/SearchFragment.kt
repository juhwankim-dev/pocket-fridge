package com.andback.pocketfridge.present.views.main.search


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentSearchBinding
import com.andback.pocketfridge.databinding.FragmentSortListBinding
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.views.main.recipe.RecipeItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val rvAdapter = SearchAdapter()
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setObserve()
        setRecyclerView()
        setSort()
        setSearchBar()
    }

    private fun setView() {
        binding.etSearchF.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_24, 0, 0, 0)
        viewModel.getFridges()
    }

    private fun setRecyclerView() {
        binding.rvSearchF.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = rvAdapter
            itemAnimator = null
        }
        rvAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                binding.tvIngreSearchFCount.text = resources.getString(R.string.search_count, rvAdapter.itemCount)
            }
        })
        rvAdapter.itemClickListener = object : SearchAdapter.ItemClickListener {
            override fun onClick(data: Ingredient, isOwner: Boolean) {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToIngreDetailFragment(isOwner, data)
                )
            }
        }
    }

    private fun setObserve() {
        with(viewModel) {
            binding.lifecycleOwner?.let { owner ->
                ingreList.observe(owner) {
                    rvAdapter.setList(fridges.value!!, it)
                    binding.tvIngreSearchFCount.text = resources.getString(R.string.search_count, rvAdapter.itemCount)
                }
                isLoading.observe(owner) {
                    when(it) {
                        true -> {
                            binding.sflSearchF.startShimmer()
                            binding.sflSearchF.visibility = View.VISIBLE
                            binding.rvSearchF.visibility = View.GONE
                        }
                        false -> {
                            binding.sflSearchF.stopShimmer()
                            binding.sflSearchF.visibility = View.GONE
                            binding.rvSearchF.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setSort() {
        val list = resources.getStringArray(R.array.spinner_list)
        val searchModalAdapter = SearchModalAdapter(list)

        binding.tvSearchFSort.text = list[SearchAdapter.SORT_BY_EXP]

        binding.llSearchFSort.setOnClickListener {
            val dialogBinding = FragmentSortListBinding.inflate(LayoutInflater.from(requireContext()))

            BottomSheetDialog(requireContext()).apply {
                setContentView(dialogBinding.root)

                dialogBinding.ibSortListFClose.setOnClickListener {
                    dismiss()
                }
                dialogBinding.rvSortListF.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = searchModalAdapter
                    addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

                    searchModalAdapter.setItemClickListener(object : SearchModalAdapter.ItemClickListener{
                        override fun onClick(position: Int) {
                            rvAdapter.sortList(position)
                            binding.tvSearchFSort.text = list[position]
                            dismiss()
                        }
                    })
                }
            }.show()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setSearchBar() {
        binding.etSearchF.addTextChangedListener {
            if (it != null && it.isNotEmpty()) {
                binding.etSearchF.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_24,
                    0,
                    R.drawable.ic_search_et_right_custom,
                    0)
            } else {
                binding.etSearchF.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_24,
                    0,
                    0,
                    0)
            }

            rvAdapter.filter.filter(it)

        }
        binding.etSearchF.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                if(binding.etSearchF.compoundDrawables[2] != null) {
                    if(motionEvent.x >=
                        (binding.etSearchF.right
                                - binding.etSearchF.left
                                - binding.etSearchF.compoundDrawables[2].bounds.width()
                        )
                    ) {
                        if (binding.etSearchF.text.toString().isNotBlank()) {
                            binding.etSearchF.setText("")
                        }
                    }
                }
            }
            false
        }
    }

    companion object {
        const val TAG = "SearchFragment"
    }
}
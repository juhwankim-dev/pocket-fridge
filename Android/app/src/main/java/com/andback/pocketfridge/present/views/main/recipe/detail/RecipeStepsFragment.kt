package com.andback.pocketfridge.present.views.main.recipe.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentRecipeStepsBinding

class RecipeStepsFragment : Fragment() {
    lateinit var binding: FragmentRecipeStepsBinding
    private val viewModel: CookSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_steps, container, false)
        var step = arguments?.getInt("step")
        if(step != null) {
            binding.step = "step ${step + 1}"
            binding.recipeStep = viewModel.recipeSteps.value?.get(step)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        initEvent()
    }

    private fun initEvent() {
        // 마이크 버튼을 클릭하면 음성 비서가 응답
        // 원래는 음성비서를 부르면 -> command에 "xx아"가 들어가고 -> 응답을 함
        // 버튼을 누르면 -> command에 "xx아"를 직접 넣어주어서 -> 응답을 하게 만듦
       // binding.ivRecipeStepsFCircle.setOnClickListener {
            // todo 버튼 눌렀을 때 음성비서 작동하게 하는건 다음에 수정. 규모가 큰 수정이 될듯.
            //assistant.command = resources.getStringArray(R.array.ipa_name_list)[0]
        //}
    }
}
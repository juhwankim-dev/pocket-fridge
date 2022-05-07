package com.andback.pocketfridge.present.views.main.recipe.detail

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentCookModeBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.STTUtil
import com.andback.pocketfridge.present.utils.TTSUtil
import com.gun0912.tedpermission.rx2.TedPermission
import kotlin.properties.Delegates

class CookModeFragment : BaseFragment<FragmentCookModeBinding>(R.layout.fragment_cook_mode) {
    lateinit var adapter: CookModePagerAdapter
    private val viewModel: CookSharedViewModel by activityViewModels()
    lateinit var tts: TTSUtil
    lateinit var stt: STTUtil
    val isListening = MutableLiveData<Boolean>()
    val assistant = Assistant()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
        initView()
        initEvent()
        binding.fragment = this
    }

    @SuppressLint("CheckResult")
    private fun checkPermission() {
        TedPermission.create()
            .setPermissions(Manifest.permission.RECORD_AUDIO)
            .request()
            .subscribe(
                { tedPermissionResult ->
                    if (tedPermissionResult.isGranted) {
                        initSTT()
                        initTTS()
                    } else {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                },
                { _ ->

                })
    }

    private fun initView() {
        binding.vpCookModeF.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        adapter = CookModePagerAdapter(requireActivity())

        for(i in 0 until viewModel.recipeSteps.value!!.size) {
            val bundle = Bundle()
            bundle.putInt("step", i)

            var fragment = RecipeStepsFragment()
            fragment.arguments = bundle

            adapter.addFrag(fragment)
        }

        binding.vpCookModeF.adapter = adapter

    }

    private fun initEvent() {
        binding.vpCookModeF.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.title = "${viewModel.selectedRecipe.foodName} (${position + 1}/${viewModel.recipeSteps.value!!.size})"
            }
        })
    }

    private fun initSTT() {
        stt = STTUtil(requireContext(), requireContext().packageName, assistant)
        stt.initSTT()
    }

    private fun initTTS() {
        tts = TTSUtil(requireContext(), stt, isListening)
        tts.initTTS()
    }

    // command 만으로는 Util에 값을 넘길 수 없기 때문에 inner class로 감쌌음
    inner class Assistant {
        var command: String by Delegates.observable("") {
                _, oldValue, newValue ->

            // if(비서를 부르는 메시지라면) speak(대답)
            if (isCallMsg(newValue)) {
                tts.speak(resources.getString(R.string.ipa_ready))
            }

            // if(현재 "다음"을 말했고 && 이전에 비서를 불렀었다면) speak(대답)
            else if (newValue == "다음" && isCallMsg(oldValue)) {
                tts.speak(resources.getString(R.string.ipa_next))
                binding.vpCookModeF.apply {
                    if(currentItem < viewModel.recipeSteps.value!!.size)
                    currentItem += 1
                }
            }

            // 그 외
            // ex) 비서를 부르거나 다음을 말하지 않은 경우
            // ex) 다음을 말했으나 이전에 비서를 부르지 않은 경우
            else {
                val mainHandler = Handler(requireContext().mainLooper)
                val myRunnable = Runnable {
                    // 다음 음성 호출을 기다림
                    stt.startListening()
                    // 듣고 있지 않음으로 설정(= 음성 파형 보여주지 않음, xml에 설정되어 있음)
                    isListening.postValue(false)
                }
                mainHandler.post(myRunnable)
            }
        }
    }

    // 영훈아, 영후나, 영운아 등
    // "xx아"와 비슷한 말들을 string에 저장해두었고
    // 이와 일치하는 음성이 들어오면 음성 비서를 호출한 것으로 판단 (return true)
    private fun isCallMsg(s: String): Boolean {
        return resources.getStringArray(R.array.ipa_name_list).any { s.indexOf(it) > -1 }
    }

    override fun onDestroyView() {
        tts.textToSpeech.stop()
        tts.textToSpeech.shutdown()
        stt.stopListening()
        super.onDestroyView()
    }
}
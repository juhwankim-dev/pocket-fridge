package com.andback.pocketfridge.present.views.main.recipe.detail

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.andback.pocketfridge.R
import com.andback.pocketfridge.databinding.FragmentRecipeStepsBinding
import com.andback.pocketfridge.present.config.BaseFragment
import com.andback.pocketfridge.present.utils.STTUtil
import com.andback.pocketfridge.present.utils.TTSUtil
import com.gun0912.tedpermission.rx2.TedPermission
import kotlin.properties.Delegates

class RecipeStepsFragment : BaseFragment<FragmentRecipeStepsBinding>(R.layout.fragment_recipe_steps) {
    lateinit var tts: TTSUtil
    lateinit var stt: STTUtil
    private val assistant = Assistant()
    val isListening = MutableLiveData<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        checkPermission()
        initEvent()
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

    private fun initEvent() {
        // 마이크 버튼을 클릭하면 음성 비서가 응답
        // 원래는 음성비서를 부르면 -> command에 "xx아"가 들어가고 -> 응답을 함
        // 버튼을 누르면 -> command에 "xx아"를 직접 넣어주어서 -> 응답을 하게 만듦
        binding.ivRecipeStepsFCircle.setOnClickListener {
            assistant.command = resources.getStringArray(R.array.ipa_name_list)[0]
        }
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
package com.andback.pocketfridge.present.views.main.recipe.detail

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
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

    private fun initSTT() {
        stt = STTUtil(requireContext(), requireContext().packageName, Command())
        stt.initSTT()
    }

    private fun initTTS() {
        tts = TTSUtil(requireContext(), stt)
        tts.initTTS()
    }

    inner class Command {
        var command: String by Delegates.observable("") {
                _, oldValue, newValue ->
            if (isCallMsg(newValue)) {
                tts.speak(resources.getString(R.string.ipa_ready))
            } else if (newValue == "다음" && isCallMsg(oldValue)) {
                tts.speak(resources.getString(R.string.ipa_next))
            } else {
                val mainHandler = Handler(requireContext().mainLooper)
                val myRunnable = Runnable {
                    stt.startListening()
                }
                mainHandler.post(myRunnable)
            }
        }
    }

    private fun isCallMsg(s: String): Boolean {
        return resources.getStringArray(R.array.ipa_name_list).any { s.indexOf(it) > -1 }
    }

    override fun onDestroy() {
        tts.textToSpeech.stop()
        tts.textToSpeech.shutdown()
        stt.stopListening()
        super.onDestroy()
    }
}
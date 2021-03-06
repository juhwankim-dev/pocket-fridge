package com.andback.pocketfridge.present.views.main.recipe.detail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.properties.Delegates

class CookModeFragment : BaseFragment<FragmentCookModeBinding>(R.layout.fragment_cook_mode) {
    lateinit var adapter: CookModePagerAdapter
    private val viewModel: CookSharedViewModel by activityViewModels()
    lateinit var tts: TTSUtil
    lateinit var stt: STTUtil
    val isListening = MutableLiveData<Boolean>()
    val assistant = Assistant()

    // ์กฐ๋ ์ผ์
    private lateinit var sensorManager: SensorManager
    private var lightSensor : Sensor? = null
    private var isSensorAvailable = true
    private val listener = LightSensorListener()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermission()
        initView()
        initEvent()
        initSensor()
        binding.fragment = this
    }

    private fun initSensor() {
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (lightSensor == null){
            isSensorAvailable = false
        }
    }

    override fun onStart() {
        super.onStart()
        if(isSensorAvailable == true)
            sensorManager.registerListener(listener, lightSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onStop() {
        super.onStop()
        if(isSensorAvailable == true)
            sensorManager.unregisterListener(listener)
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

        val steps = viewModel.recipeSteps.value!!.size
        binding.pbCookModeF.max = steps
        for(i in 0 until steps) {
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
                binding.pbCookModeF.progress = position + 1
            }
        })
        binding.ivCookModeFBack.setOnClickListener {
            requireActivity().onBackPressed()
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

    // command ๋ง์ผ๋ก๋ Util์ ๊ฐ์ ๋๊ธธ ์ ์๊ธฐ ๋๋ฌธ์ inner class๋ก ๊ฐ์์
    inner class Assistant {
        var command: String by Delegates.observable("") {
                _, oldValue, newValue ->

            // if(๋น์๋ฅผ ๋ถ๋ฅด๋ ๋ฉ์์ง๋ผ๋ฉด) speak(๋๋ต)
            if (isCallMsg(newValue)) {
                tts.speak(resources.getString(R.string.ipa_ready))
            }

            // if(ํ์ฌ "๋ค์"์ ๋งํ๊ณ? && ์ด์?์ ๋น์๋ฅผ ๋ถ๋?์๋ค๋ฉด) speak(๋๋ต)
            else if (newValue == "๋ค์" && isCallMsg(oldValue)) {
                binding.vpCookModeF.apply {
                    if(currentItem < viewModel.recipeSteps.value!!.size - 1) {
                        tts.speak(resources.getString(R.string.ipa_next))
                        currentItem += 1
                    } else {
                        tts.speak(resources.getString(R.string.ipa_last))
                    }

                }
            }

            // if(ํ์ฌ "์ด์?"์ ๋งํ๊ณ? && ์ด์?์ ๋น์๋ฅผ ๋ถ๋?์๋ค๋ฉด) speak(๋๋ต)
            else if (newValue == "์ด์?" && isCallMsg(oldValue)) {
                binding.vpCookModeF.apply {
                    if(currentItem > 0) {
                        tts.speak(resources.getString(R.string.ipa_before))
                        currentItem -= 1
                    } else {
                        tts.speak(resources.getString(R.string.ipa_first))
                    }
                }
            }

            // ๊ทธ ์ธ
            // ex) ๋น์๋ฅผ ๋ถ๋ฅด๊ฑฐ๋ ๋ค์์ ๋งํ์ง ์์ ๊ฒฝ์ฐ
            // ex) ๋ค์์ ๋งํ์ผ๋ ์ด์?์ ๋น์๋ฅผ ๋ถ๋ฅด์ง ์์ ๊ฒฝ์ฐ
            else {
                val mainHandler = Handler(requireContext().mainLooper)
                val myRunnable = Runnable {
                    // ๋ค์ ์์ฑ ํธ์ถ์ ๊ธฐ๋ค๋ฆผ
                    stt.startListening()
                    // ๋ฃ๊ณ? ์์ง ์์์ผ๋ก ์ค์?(= ์์ฑ ํํ ๋ณด์ฌ์ฃผ์ง ์์, xml์ ์ค์?๋์ด ์์)
                    isListening.postValue(false)
                }
                mainHandler.post(myRunnable)
            }
        }
    }

    // ์ํ์, ์ํ๋, ์์ด์ ๋ฑ
    // "xx์"์ ๋น์ทํ ๋ง๋ค์ string์ ์?์ฅํด๋์๊ณ?
    // ์ด์ ์ผ์นํ๋ ์์ฑ์ด ๋ค์ด์ค๋ฉด ์์ฑ ๋น์๋ฅผ ํธ์ถํ ๊ฒ์ผ๋ก ํ๋จ (return true)
    private fun isCallMsg(s: String): Boolean {
        return resources.getStringArray(R.array.ipa_name_list).any { s.indexOf(it) > -1 }
    }

    override fun onDestroyView() {
        tts.textToSpeech.stop()
        tts.textToSpeech.shutdown()
        stt.stopListening()
        super.onDestroyView()
    }

    inner class LightSensorListener: SensorEventListener {
        private var cnt = 0
        private val DELTA_THRESHOLD = 60F
        private var oldValue = 0F
        private var job: Job? = null

        // ์ด๋์์ง๋ ๊ฒฝ์ฐ
        private fun isOverThreshold(new: Float, old: Float): Boolean {
            return old - new > DELTA_THRESHOLD
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor == lightSensor){
                Log.d(TAG, "onSensorChanged: ${event?.values?.get(0)}")
                val current = event?.values?.get(0)
                current?.let {
                    if(isOverThreshold(it, oldValue)) {
                        cnt++
                        Log.d(TAG, "onSensorChanged: $cnt")
                        if(job == null || job!!.isActive == false) {
                            startCoroutine()
                        }
                    }
                    oldValue = it
                }
            }
        }

        private fun startCoroutine() {
            job?.cancel()
            job = CoroutineScope(Dispatchers.Default).launch {
                Log.d(TAG, "startCoroutine: start")
                delay(1500L)
                if(cnt == 1) {
                    //๋ค์์ผ๋ก ์ด๋
                    Log.d(TAG, "startCoroutine: ๋ค์์ผ๋ก")
                    binding.vpCookModeF.apply {
                        if(currentItem < viewModel.recipeSteps.value!!.size - 1) {
                            currentItem += 1
                        }
                    }
                } else if(cnt > 1) {
                    // ์ด์?์ผ๋ก ์ด๋
                    Log.d(TAG, "startCoroutine: ์ด์?์ผ๋ก")
                    binding.vpCookModeF.apply {
                        if(currentItem > 0) {
                            currentItem -= 1
                        }
                    }
                }

                cnt = 0
                Log.d(TAG, "startCoroutine: end")
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }

    companion object {
        private const val TAG = "CookModeFragment_debuk"
    }
}
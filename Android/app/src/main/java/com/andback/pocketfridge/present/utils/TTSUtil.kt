package com.andback.pocketfridge.present.utils

import android.content.Context
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.lifecycle.MutableLiveData
import com.andback.pocketfridge.R
import java.util.*

class TTSUtil(val context: Context, val stt: STTUtil?, val isListening: MutableLiveData<Boolean>) {
    lateinit var textToSpeech: TextToSpeech
    // "시리야"처럼 음성비서를 호출하면 true, 그 외 음성이 들어오면 false
    private var isReady = false

    fun initTTS() {
        textToSpeech = TextToSpeech(context) {
            @Override
            fun onInit(status: Int) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.language = Locale.KOREA
                }
            }
        }

        // Utterance = 발언
        // 발언의 진행 단계를 알려주는 리스너
        textToSpeech.setOnUtteranceProgressListener(object: UtteranceProgressListener() {
            // 발언이 시작되기 전 호출
            override fun onStart(p0: String?) { }

            // 발언을 마친 후 호출
            override fun onDone(p0: String?) {
                val mainHandler = Handler(context.mainLooper)
                val myRunnable = Runnable {
                    // "네 말씀하세요" 발언이 끝난 후 사용자로부터 음성 입력을 받기 위해 stt를 시작
                    stt!!.startListening()

                    // 음성 비서를 호출했다면(= isReady가 true면) 듣는 중으로 상태 변경(= isListening을 true로)
                    // 호출하지 않았다면 false가 들어감
                    // 여기서 `듣는 중`이란 화면에 파형이 나오며 사용자에게 듣는 중임을 알리기 위한 변수임
                    // 음성비서는 항상 듣고 있으니 의미를 혼동하지 말것
                    isListening.postValue(isReady)
                }
                mainHandler.post(myRunnable)
            }

            override fun onError(p0: String?) { }
        })
    }

    fun speak(text: String) {
        // "시리야"처럼 음성 비서를 호출하면 isReady를 true로 하는게 맞으나
        // stt의 값을 가져오기에는 구조가 복잡해지기 때문에
        // "시리야"를 부르면 응답하는 멘트인 "네 말씀해주세요"로 isReady를 설정
        isReady = (text == context.resources.getString(R.string.ipa_ready))

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // 4번째 값에 null을 넣으면 setOnUtteranceProgressListener가 호출되지 않기 때문에
            // 임의의 값을 설정함
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "temp")
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}
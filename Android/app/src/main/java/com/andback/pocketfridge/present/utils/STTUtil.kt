package com.andback.pocketfridge.present.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.andback.pocketfridge.present.views.main.recipe.detail.CookModeFragment

class STTUtil(val context: Context, val packageName: String, val assistant: CookModeFragment.Assistant) {
    var speechRecognizer: SpeechRecognizer? = null
    lateinit var sttIntent: Intent

    fun initSTT() {
        stopListening()

        sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(sttListener)
            startListening(sttIntent)
        }
    }

    // 음성 듣기 시작
    // 시리와 같은 기능을 위해서 반복해서 호출해줘야 함
    fun startListening() {
        speechRecognizer!!.startListening(sttIntent)
    }

    private val sttListener: RecognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
            // 말하기 시작할 준비가되면 호출
        }

        override fun onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        override fun onRmsChanged(rmsdB: Float) {
            // 입력받는 소리의 크기를 알려줌
        }

        override fun onBufferReceived(buffer: ByteArray) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        override fun onEndOfSpeech() {
            // 음성이 끝났을 때 호출 (결과에 따라 OnError 혹은 onResults 호출)
        }

        override fun onError(error: Int) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            val message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트웍 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER 가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
            Log.d("STTUtil", "onError: $message")

            // 에러가 나더라도 다시 음성 듣기를 시작한다.
            // 원래 음성은 한 번 듣기가 기본이지만, 시리처럼 호출하여 쓰기 위해 항시 듣기 상태를 유지시킨다.
            startListening()
        }

        override fun onResults(results: Bundle) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어들이 들어감.
            // ex) 아버지가 / 방에 / 들어가신다.
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

            // 띄어쓰기 없이 한 문장으로 이어줌
            var resultStr = ""
            for (i in 0 until matches!!.size) {
                resultStr += matches[i]
            }

            // 아무말도 하지 않았다면 리턴
            if (resultStr.isEmpty()) return

            // 사용자의 명령어를 관찰하고 있는 Observable인 command에 값을 할당
            // LiveData로 치면 setValue
            assistant.command = resultStr
        }

        override fun onPartialResults(partialResults: Bundle) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }

        override fun onEvent(eventType: Int, params: Bundle) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    }

    // Fragment를 떠날 때 호출할 것
    fun stopListening() {
        if (speechRecognizer != null) {
            speechRecognizer!!.destroy()
            speechRecognizer!!.cancel()
            speechRecognizer = null
        }
    }
}
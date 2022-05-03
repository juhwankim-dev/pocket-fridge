package com.andback.pocketfridge.present.utils

import android.content.Context
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*

class TTSUtil(val context: Context, val stt: STTUtil?) {
    lateinit var textToSpeech: TextToSpeech

    fun initTTS() {
        textToSpeech = TextToSpeech(context) {
            @Override
            fun onInit(status: Int) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.language = Locale.KOREA
                }
            }
        }

        textToSpeech.setOnUtteranceProgressListener(object: UtteranceProgressListener() {
            override fun onStart(p0: String?) { }

            override fun onDone(p0: String?) {
                val mainHandler = Handler(context.mainLooper)
                val myRunnable = Runnable {
                    stt!!.startListening()
                }
                mainHandler.post(myRunnable)
            }

            override fun onError(p0: String?) { }
        })
    }

    fun speak(text: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "temp")
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}
package com.jarvis.ai.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.*

class VoiceService : Service(), RecognitionListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private val binder = VoiceBinder()
    private val serviceScope = CoroutineScope(Job() + Dispatchers.Main)

    private var isListening = false
    private var listeners = mutableListOf<VoiceListener>()

    interface VoiceListener {
        fun onVoiceStart()
        fun onVoiceEnd()
        fun onResult(transcript: String)
        fun onPartialResult(transcript: String)
        fun onError(error: String)
    }

    inner class VoiceBinder : Binder() {
        fun getService(): VoiceService = this@VoiceService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("VoiceService", "Service created")
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)
    }

    fun startListening() {
        if (!isListening) {
            isListening = true
            val intent = Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, "pt-BR")
            intent.putExtra(android.speech.RecognizerIntent.EXTRA_MAX_RESULTS, 5)
            speechRecognizer.startListening(intent)
            notifyListeners { it.onVoiceStart() }
        }
    }

    fun stopListening() {
        if (isListening) {
            isListening = false
            speechRecognizer.stopListening()
            notifyListeners { it.onVoiceEnd() }
        }
    }

    fun addListener(listener: VoiceListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: VoiceListener) {
        listeners.remove(listener)
    }

    private fun notifyListeners(action: (VoiceListener) -> Unit) {
        listeners.forEach { action(it) }
    }

    override fun onReadyForSpeech(params: android.os.Bundle?) {
        Log.d("VoiceService", "Ready for speech")
    }

    override fun onBeginningOfSpeech() {
        Log.d("VoiceService", "Beginning of speech")
    }

    override fun onRmsChanged(rmsdB: Float) {
        // Audio level changed
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.d("VoiceService", "Buffer received")
    }

    override fun onEndOfSpeech() {
        Log.d("VoiceService", "End of speech")
    }

    override fun onError(error: Int) {
        val errorMessage = when (error) {
            SpeechRecognizer.ERROR_AUDIO -> "Erro de áudio"
            SpeechRecognizer.ERROR_CLIENT -> "Erro do cliente"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permissão negada"
            SpeechRecognizer.ERROR_NETWORK -> "Erro de rede"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Timeout de rede"
            SpeechRecognizer.ERROR_NO_MATCH -> "Nenhuma correspondência"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Reconhecedor ocupado"
            SpeechRecognizer.ERROR_SERVER -> "Erro do servidor"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "Timeout de fala"
            else -> "Erro desconhecido"
        }
        Log.e("VoiceService", "Error: $errorMessage")
        notifyListeners { it.onError(errorMessage) }
        isListening = false
    }

    override fun onResults(results: android.os.Bundle?) {
        val matches = results?.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            val transcript = matches[0]
            Log.d("VoiceService", "Result: $transcript")
            notifyListeners { it.onResult(transcript) }
        }
        isListening = false
    }

    override fun onPartialResults(partialResults: android.os.Bundle?) {
        val matches = partialResults?.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            val transcript = matches[0]
            Log.d("VoiceService", "Partial: $transcript")
            notifyListeners { it.onPartialResult(transcript) }
        }
    }

    override fun onEvent(eventType: Int, params: android.os.Bundle?) {
        Log.d("VoiceService", "Event: $eventType")
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
        serviceScope.cancel()
    }
}

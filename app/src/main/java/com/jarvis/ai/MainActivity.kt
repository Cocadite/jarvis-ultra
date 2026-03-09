package com.jarvis.ai

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jarvis.ai.pages.HomePage
import com.jarvis.ai.pages.ManagerPage
import com.jarvis.ai.ui.theme.JarvisTheme
import android.content.Intent
import android.util.Log
import java.util.*

class MainActivity : ComponentActivity(), RecognitionListener, TextToSpeech.OnInitListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var textToSpeech: TextToSpeech
    private var isListening = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Speech Recognition
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)

        // Initialize Text-to-Speech
        textToSpeech = TextToSpeech(this, this)

        setContent {
            JarvisTheme {
                JarvisApp(
                    onMicrophoneClick = { startListening() },
                    onMicrophoneStop = { stopListening() },
                    isListening = isListening
                )
            }
        }
    }

    @Composable
    fun JarvisApp(
        onMicrophoneClick: () -> Unit,
        onMicrophoneStop: () -> Unit,
        isListening: Boolean
    ) {
        var currentPage by remember { mutableStateOf("home") }
        var transcript by remember { mutableStateOf("") }
        var response by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0a0e27))
        ) {
            when (currentPage) {
                "home" -> HomePage(
                    isListening = isListening,
                    onMicrophoneClick = onMicrophoneClick,
                    onMicrophoneStop = onMicrophoneStop,
                    onManagerClick = { currentPage = "manager" },
                    transcript = transcript,
                    response = response
                )
                "manager" -> ManagerPage()
            }
        }
    }

    private fun startListening() {
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
            Log.d("MainActivity", "Started listening")
        }
    }

    private fun stopListening() {
        if (isListening) {
            isListening = false
            speechRecognizer.stopListening()
            Log.d("MainActivity", "Stopped listening")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("pt", "BR")
            Log.d("MainActivity", "TextToSpeech initialized")
        }
    }

    override fun onReadyForSpeech(params: Bundle?) {
        Log.d("MainActivity", "Ready for speech")
    }

    override fun onBeginningOfSpeech() {
        Log.d("MainActivity", "Beginning of speech")
    }

    override fun onRmsChanged(rmsdB: Float) {}

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.d("MainActivity", "Buffer received")
    }

    override fun onEndOfSpeech() {
        Log.d("MainActivity", "End of speech")
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
        Log.e("MainActivity", "Error: $errorMessage")
        isListening = false
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            val transcript = matches[0]
            Log.d("MainActivity", "Result: $transcript")
            processCommand(transcript)
        }
        isListening = false
    }

    override fun onPartialResults(partialResults: Bundle?) {
        val matches = partialResults?.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
        if (!matches.isNullOrEmpty()) {
            Log.d("MainActivity", "Partial: ${matches[0]}")
        }
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.d("MainActivity", "Event: $eventType")
    }

    private fun processCommand(command: String) {
        val response = when {
            command.contains("spotify", ignoreCase = true) -> "🎵 Tocando música no Spotify"
            command.contains("gmail", ignoreCase = true) -> "📧 Abrindo Gmail"
            command.contains("maps", ignoreCase = true) -> "🗺️ Abrindo Google Maps"
            command.contains("weather", ignoreCase = true) -> "🌤️ Mostrando clima"
            command.contains("calendar", ignoreCase = true) -> "📅 Abrindo calendário"
            else -> "✅ Comando recebido: $command"
        }

        textToSpeech.speak(response, TextToSpeech.QUEUE_FLUSH, null)
        Log.d("MainActivity", "Response: $response")
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer.destroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}

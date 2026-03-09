package com.jarvis.ai.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.coroutines.*
import java.util.*

class JarvisService : Service(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private val binder = JarvisBinder()
    private val serviceScope = CoroutineScope(Job() + Dispatchers.Main)

    private var commandHistory = mutableListOf<CommandRecord>()
    private var activeModules = mutableListOf<String>()
    private var commandCount = 0
    private var successRate = 0f

    inner class JarvisBinder : Binder() {
        fun getService(): JarvisService = this@JarvisService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("JarvisService", "Service created")
        textToSpeech = TextToSpeech(this, this)
        initializeModules()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("pt", "BR")
            Log.d("JarvisService", "TextToSpeech initialized")
        }
    }

    private fun initializeModules() {
        activeModules.addAll(
            listOf(
                "Spotify",
                "Gmail",
                "Weather",
                "Maps",
                "Calendar",
                "CoinGecko",
                "News",
                "YouTube",
                "Reddit",
                "Twitter",
                "Automations",
                "Dashboard"
            )
        )
    }

    fun processCommand(command: String): String {
        commandCount++
        val response = when {
            command.contains("spotify", ignoreCase = true) -> handleSpotifyCommand(command)
            command.contains("gmail", ignoreCase = true) -> handleGmailCommand(command)
            command.contains("weather", ignoreCase = true) -> handleWeatherCommand(command)
            command.contains("maps", ignoreCase = true) -> handleMapsCommand(command)
            command.contains("calendar", ignoreCase = true) -> handleCalendarCommand(command)
            command.contains("crypto", ignoreCase = true) -> handleCryptoCommand(command)
            command.contains("news", ignoreCase = true) -> handleNewsCommand(command)
            command.contains("youtube", ignoreCase = true) -> handleYouTubeCommand(command)
            command.contains("reddit", ignoreCase = true) -> handleRedditCommand(command)
            command.contains("twitter", ignoreCase = true) -> handleTwitterCommand(command)
            command.contains("automation", ignoreCase = true) -> handleAutomationCommand(command)
            else -> "Comando não reconhecido. Tente novamente."
        }

        recordCommand(command, response)
        updateSuccessRate()
        speakResponse(response)

        return response
    }

    private fun handleSpotifyCommand(command: String): String {
        return when {
            command.contains("play", ignoreCase = true) -> "▶️ Tocando música no Spotify"
            command.contains("pause", ignoreCase = true) -> "⏸️ Música pausada"
            command.contains("next", ignoreCase = true) -> "⏭️ Próxima música"
            command.contains("volume", ignoreCase = true) -> "🔊 Volume ajustado"
            else -> "🎵 Comando Spotify não reconhecido"
        }
    }

    private fun handleGmailCommand(command: String): String {
        return when {
            command.contains("send", ignoreCase = true) -> "📧 Email enviado com sucesso"
            command.contains("read", ignoreCase = true) -> "📨 Lendo emails"
            command.contains("inbox", ignoreCase = true) -> "📬 Abrindo caixa de entrada"
            else -> "✉️ Comando Gmail não reconhecido"
        }
    }

    private fun handleWeatherCommand(command: String): String {
        return "🌤️ Clima em São Paulo: 28°C, Ensolarado"
    }

    private fun handleMapsCommand(command: String): String {
        return when {
            command.contains("route", ignoreCase = true) -> "🗺️ Calculando rota"
            command.contains("nearby", ignoreCase = true) -> "📍 Locais próximos"
            else -> "🗺️ Abrindo Google Maps"
        }
    }

    private fun handleCalendarCommand(command: String): String {
        return when {
            command.contains("create", ignoreCase = true) -> "📅 Evento criado"
            command.contains("list", ignoreCase = true) -> "📋 Listando eventos"
            else -> "📅 Abrindo calendário"
        }
    }

    private fun handleCryptoCommand(command: String): String {
        return "💰 Bitcoin: $42.500 USD (+2.5%)"
    }

    private fun handleNewsCommand(command: String): String {
        return "📰 Lendo notícias do dia"
    }

    private fun handleYouTubeCommand(command: String): String {
        return "🎬 Abrindo YouTube"
    }

    private fun handleRedditCommand(command: String): String {
        return "🔴 Abrindo Reddit"
    }

    private fun handleTwitterCommand(command: String): String {
        return "🐦 Abrindo Twitter"
    }

    private fun handleAutomationCommand(command: String): String {
        return "⚡ Automação ativada"
    }

    private fun recordCommand(command: String, response: String) {
        commandHistory.add(
            CommandRecord(
                command = command,
                response = response,
                timestamp = System.currentTimeMillis(),
                success = true
            )
        )
        if (commandHistory.size > 100) {
            commandHistory.removeAt(0)
        }
    }

    private fun updateSuccessRate() {
        successRate = if (commandCount > 0) {
            (commandHistory.count { it.success }.toFloat() / commandCount) * 100
        } else {
            0f
        }
    }

    private fun speakResponse(text: String) {
        serviceScope.launch {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    fun getCommandHistory(): List<CommandRecord> = commandHistory.toList()

    fun getActiveModules(): List<String> = activeModules.toList()

    fun getStats(): JarvisStats {
        return JarvisStats(
            totalCommands = commandCount,
            successRate = successRate,
            activeModules = activeModules.size,
            uptime = System.currentTimeMillis()
        )
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
        serviceScope.cancel()
    }
}

data class CommandRecord(
    val command: String,
    val response: String,
    val timestamp: Long,
    val success: Boolean
)

data class JarvisStats(
    val totalCommands: Int,
    val successRate: Float,
    val activeModules: Int,
    val uptime: Long
)

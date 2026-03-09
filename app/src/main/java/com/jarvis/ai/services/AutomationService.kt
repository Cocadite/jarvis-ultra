package com.jarvis.ai.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.util.*

class AutomationService : Service() {

    private val binder = AutomationBinder()
    private val serviceScope = CoroutineScope(Job() + Dispatchers.Main)

    private var automations = mutableListOf<Automation>()
    private var executionHistory = mutableListOf<ExecutionRecord>()

    inner class AutomationBinder : Binder() {
        fun getService(): AutomationService = this@AutomationService
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("AutomationService", "Service created")
        initializeDefaultAutomations()
        startAutomationScheduler()
    }

    private fun initializeDefaultAutomations() {
        automations.addAll(
            listOf(
                Automation(
                    id = "1",
                    name = "Acordar",
                    description = "Tocar música + Ler notícias + Mostrar clima",
                    triggers = listOf("time:07:00"),
                    actions = listOf("spotify:play", "news:read", "weather:show"),
                    enabled = true
                ),
                Automation(
                    id = "2",
                    name = "Trabalho",
                    description = "Silenciar + Abrir Gmail + Mostrar calendário",
                    triggers = listOf("time:09:00"),
                    actions = listOf("device:silent", "gmail:open", "calendar:show"),
                    enabled = true
                ),
                Automation(
                    id = "3",
                    name = "Pausa",
                    description = "Tocar música relaxante",
                    triggers = listOf("time:12:00"),
                    actions = listOf("spotify:play_relaxing"),
                    enabled = true
                ),
                Automation(
                    id = "4",
                    name = "Saída",
                    description = "Ativar Bluetooth + Mostrar trânsito",
                    triggers = listOf("time:18:00"),
                    actions = listOf("device:bluetooth_on", "maps:traffic"),
                    enabled = true
                ),
                Automation(
                    id = "5",
                    name = "Noite",
                    description = "Modo escuro + Modo silencioso + Backup automático",
                    triggers = listOf("time:22:00"),
                    actions = listOf("device:dark_mode", "device:silent", "backup:auto"),
                    enabled = true
                )
            )
        )
    }

    private fun startAutomationScheduler() {
        serviceScope.launch {
            while (isActive) {
                checkAndExecuteAutomations()
                delay(60000) // Check every minute
            }
        }
    }

    private suspend fun checkAndExecuteAutomations() {
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)
        val timeString = String.format("%02d:%02d", currentHour, currentMinute)

        automations.filter { it.enabled }.forEach { automation ->
            automation.triggers.forEach { trigger ->
                if (trigger.startsWith("time:") && trigger.endsWith(timeString)) {
                    executeAutomation(automation)
                }
            }
        }
    }

    private fun executeAutomation(automation: Automation) {
        Log.d("AutomationService", "Executing automation: ${automation.name}")

        automation.actions.forEach { action ->
            val result = when {
                action.startsWith("spotify:") -> handleSpotifyAction(action)
                action.startsWith("gmail:") -> handleGmailAction(action)
                action.startsWith("news:") -> handleNewsAction(action)
                action.startsWith("weather:") -> handleWeatherAction(action)
                action.startsWith("device:") -> handleDeviceAction(action)
                action.startsWith("maps:") -> handleMapsAction(action)
                action.startsWith("backup:") -> handleBackupAction(action)
                action.startsWith("calendar:") -> handleCalendarAction(action)
                else -> "Ação desconhecida"
            }

            recordExecution(automation.id, action, result)
        }
    }

    private fun handleSpotifyAction(action: String): String {
        return when {
            action.contains("play") -> "▶️ Tocando música"
            action.contains("pause") -> "⏸️ Música pausada"
            action.contains("relaxing") -> "🎵 Tocando música relaxante"
            else -> "🎵 Ação Spotify executada"
        }
    }

    private fun handleGmailAction(action: String): String {
        return when {
            action.contains("open") -> "📧 Gmail aberto"
            action.contains("send") -> "📧 Email enviado"
            else -> "✉️ Ação Gmail executada"
        }
    }

    private fun handleNewsAction(action: String): String {
        return "📰 Lendo notícias"
    }

    private fun handleWeatherAction(action: String): String {
        return "🌤️ Clima: 28°C, Ensolarado"
    }

    private fun handleDeviceAction(action: String): String {
        return when {
            action.contains("silent") -> "🔇 Modo silencioso ativado"
            action.contains("bluetooth_on") -> "🔵 Bluetooth ativado"
            action.contains("dark_mode") -> "🌙 Modo escuro ativado"
            else -> "⚙️ Ação dispositivo executada"
        }
    }

    private fun handleMapsAction(action: String): String {
        return "🗺️ Google Maps aberto"
    }

    private fun handleBackupAction(action: String): String {
        return "💾 Backup automático realizado"
    }

    private fun handleCalendarAction(action: String): String {
        return "📅 Calendário aberto"
    }

    private fun recordExecution(automationId: String, action: String, result: String) {
        executionHistory.add(
            ExecutionRecord(
                automationId = automationId,
                action = action,
                result = result,
                timestamp = System.currentTimeMillis()
            )
        )
        if (executionHistory.size > 1000) {
            executionHistory.removeAt(0)
        }
    }

    fun getAutomations(): List<Automation> = automations.toList()

    fun createAutomation(automation: Automation) {
        automations.add(automation)
        Log.d("AutomationService", "Automation created: ${automation.name}")
    }

    fun updateAutomation(automation: Automation) {
        val index = automations.indexOfFirst { it.id == automation.id }
        if (index >= 0) {
            automations[index] = automation
            Log.d("AutomationService", "Automation updated: ${automation.name}")
        }
    }

    fun deleteAutomation(automationId: String) {
        automations.removeAll { it.id == automationId }
        Log.d("AutomationService", "Automation deleted: $automationId")
    }

    fun getExecutionHistory(): List<ExecutionRecord> = executionHistory.toList()

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}

data class Automation(
    val id: String,
    val name: String,
    val description: String,
    val triggers: List<String>,
    val actions: List<String>,
    val enabled: Boolean
)

data class ExecutionRecord(
    val automationId: String,
    val action: String,
    val result: String,
    val timestamp: Long
)

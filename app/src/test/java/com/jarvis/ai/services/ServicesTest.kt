package com.jarvis.ai.services

import org.junit.Test
import org.junit.Assert.*

class ServicesTest {

    // INTEGRATION SERVICE TESTS
    @Test
    fun testSpotifyPlay() {
        val service = IntegrationService()
        val result = service.spotifyPlay()
        assertTrue(result.contains("Tocando"))
    }

    @Test
    fun testGmailSend() {
        val service = IntegrationService()
        val result = service.gmailSendEmail("test@example.com", "Test", "Body")
        assertTrue(result.contains("enviado"))
    }

    @Test
    fun testMapsRoute() {
        val service = IntegrationService()
        val result = service.mapsGetRoute("A", "B")
        assertTrue(result.contains("Rota"))
    }

    @Test
    fun testWeatherCurrent() {
        val service = IntegrationService()
        val result = service.weatherCurrent("São Paulo")
        assertTrue(result.contains("°C"))
    }

    // SECURITY SERVICE TESTS
    @Test
    fun testBiometricEnable() {
        val service = SecurityService()
        val result = service.enableBiometric()
        assertTrue(result.contains("ativada"))
    }

    @Test
    fun testPINSet() {
        val service = SecurityService()
        val result = service.setPIN("1234")
        assertTrue(result.contains("sucesso"))
    }

    @Test
    fun testEncryption() {
        val service = SecurityService()
        val result = service.encryptData("test")
        assertTrue(result.contains("criptografados"))
    }

    // SYNC SERVICE TESTS
    @Test
    fun testGoogleDriveSync() {
        val service = SyncService()
        val result = service.syncGoogleDrive()
        assertTrue(result.contains("Google Drive"))
    }

    @Test
    fun testBackup() {
        val service = SyncService()
        val result = service.performBackup()
        assertTrue(result.contains("sucesso"))
    }

    @Test
    fun testOfflineMode() {
        val service = SyncService()
        val result = service.enableOfflineMode()
        assertTrue(result.contains("offline"))
    }

    // AI SERVICE TESTS
    @Test
    fun testNLP() {
        val service = AIService()
        val result = service.processNaturalLanguage("test")
        assertTrue(result.contains("linguagem"))
    }

    @Test
    fun testIntentClassification() {
        val service = AIService()
        val result = service.classifyIntent("test")
        assertTrue(result.contains("classificada"))
    }

    @Test
    fun testPrediction() {
        val service = AIService()
        val result = service.predictNextCommand()
        assertTrue(result.contains("previsto"))
    }

    // ANALYTICS SERVICE TESTS
    @Test
    fun testDashboard() {
        val service = AnalyticsService()
        val result = service.getDashboard()
        assertTrue(result.contains("Dashboard"))
    }

    @Test
    fun testDailyReport() {
        val service = AnalyticsService()
        val result = service.getDailyReport()
        assertTrue(result.contains("Relatório"))
    }

    @Test
    fun testTrends() {
        val service = AnalyticsService()
        val result = service.analyzeTrends()
        assertTrue(result.contains("Tendência"))
    }

    // ADVANCED UI TESTS
    @Test
    fun testTabletMode() {
        val service = AdvancedUIService()
        val result = service.enableTabletMode()
        assertTrue(result.contains("tablet"))
    }

    @Test
    fun testTheme() {
        val service = AdvancedUIService()
        val result = service.setTheme("dark")
        assertTrue(result.contains("Tema"))
    }

    // ADVANCED INTEGRATION TESTS
    @Test
    fun testAlexa() {
        val service = AdvancedIntegrationService()
        val result = service.integrateAlexa()
        assertTrue(result.contains("Alexa"))
    }

    @Test
    fun testSlack() {
        val service = AdvancedIntegrationService()
        val result = service.integrateSlack()
        assertTrue(result.contains("Slack"))
    }

    // SMART HOME TESTS
    @Test
    fun testLights() {
        val service = SmartHomeService()
        val result = service.controlLights(80, "white")
        assertTrue(result.contains("Luzes"))
    }

    @Test
    fun testTemperature() {
        val service = SmartHomeService()
        val result = service.setTemperature(22.5f)
        assertTrue(result.contains("Temperatura"))
    }

    @Test
    fun testLock() {
        val service = SmartHomeService()
        val result = service.controlLock(true)
        assertTrue(result.contains("trancada"))
    }

    @Test
    fun testAlarm() {
        val service = SmartHomeService()
        val result = service.controlAlarm(true)
        assertTrue(result.contains("armado"))
    }
}

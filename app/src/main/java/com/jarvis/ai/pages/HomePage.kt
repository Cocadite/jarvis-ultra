package com.jarvis.ai.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomePage(
    isListening: Boolean,
    onMicrophoneClick: () -> Unit,
    onMicrophoneStop: () -> Unit,
    onManagerClick: () -> Unit,
    transcript: String,
    response: String
) {
    var commandHistory by remember { mutableStateOf(listOf<String>()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0a0e27))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Jarvis",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFba86fc)
                    )
                    Text(
                        "Seu Assistente de IA",
                        fontSize = 12.sp,
                        color = Color(0xFF999999)
                    )
                }
                IconButton(onClick = onManagerClick) {
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Manager",
                        tint = Color(0xFF03dac6),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        // Microphone Button
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Color(0xFF1a1f3a).copy(alpha = 0.8f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val scale by animateFloatAsState(if (isListening) 1.2f else 1f)

                    Button(
                        onClick = if (isListening) onMicrophoneStop else onMicrophoneClick,
                        modifier = Modifier
                            .size(120.dp)
                            .scale(scale),
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isListening) Color(0xFFFF5252) else Color(0xFFba86fc)
                        )
                    ) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Microphone",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        if (isListening) "🎤 Ouvindo..." else "Clique para falar",
                        fontSize = 14.sp,
                        color = if (isListening) Color(0xFFFF5252) else Color(0xFF03dac6),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // Transcript
        if (transcript.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1a1f3a).copy(alpha = 0.8f)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF03dac6).copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Você disse:",
                            fontSize = 12.sp,
                            color = Color(0xFF999999),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            transcript,
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Response
        if (response.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF2a2f4a).copy(alpha = 0.8f)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFba86fc).copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Jarvis respondeu:",
                            fontSize = 12.sp,
                            color = Color(0xFFba86fc),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            response,
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Status Cards
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusCard("Módulos", "12", "🔧", Modifier.weight(1f))
                StatusCard("Comandos", "42", "📊", Modifier.weight(1f))
                StatusCard("Taxa", "98%", "✅", Modifier.weight(1f))
            }
        }

        // Quick Actions
        item {
            Text(
                "Ações Rápidas",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton("🎵 Spotify", "Tocar música")
                QuickActionButton("📧 Gmail", "Enviar email")
                QuickActionButton("🗺️ Maps", "Abrir navegação")
                QuickActionButton("🌤️ Weather", "Ver clima")
            }
        }

        // Recent Commands
        if (commandHistory.isNotEmpty()) {
            item {
                Text(
                    "Comandos Recentes",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            items(commandHistory.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1a1f3a).copy(alpha = 0.6f)
                    )
                ) {
                    Text(
                        commandHistory[index],
                        fontSize = 12.sp,
                        color = Color(0xFFb3b3b3),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, icon: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1a1f3a).copy(alpha = 0.8f)
        ),
        border = BorderStroke(1.dp, Color(0xFF03dac6).copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(icon, fontSize = 20.sp)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFFba86fc))
            Text(title, fontSize = 10.sp, color = Color(0xFF999999))
        }
    }
}

@Composable
fun QuickActionButton(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1a1f3a).copy(alpha = 0.8f)
        ),
        border = BorderStroke(1.dp, Color(0xFF03dac6).copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(description, fontSize = 11.sp, color = Color(0xFF999999))
            }
            Icon(
                Icons.Filled.Mic,
                contentDescription = title,
                tint = Color(0xFF03dac6),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

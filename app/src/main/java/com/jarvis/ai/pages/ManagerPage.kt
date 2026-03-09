package com.jarvis.ai.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class APIModule(
    val id: String,
    val name: String,
    val description: String,
    val enabled: Boolean,
    val version: String,
    val author: String,
    val icon: String
)

data class InstalledModule(
    val id: String,
    val name: String,
    val version: String,
    val enabled: Boolean,
    val size: String,
    val lastUpdated: String
)

@Composable
fun ManagerPage() {
    var selectedTab by remember { mutableStateOf(0) }
    var availableAPIs by remember {
        mutableStateOf(
            listOf(
                APIModule("spotify", "Spotify", "Controle de música", true, "1.0.0", "Jarvis", "🎵"),
                APIModule("gmail", "Gmail", "Gerenciar emails", true, "1.0.0", "Jarvis", "📧"),
                APIModule("weather", "Weather", "Informações de clima", true, "1.0.0", "Jarvis", "🌤️"),
                APIModule("maps", "Google Maps", "Navegação e rotas", true, "1.0.0", "Jarvis", "🗺️"),
                APIModule("calendar", "Google Calendar", "Gerenciar eventos", true, "1.0.0", "Jarvis", "📅"),
                APIModule("crypto", "CoinGecko", "Preços de criptomoedas", true, "1.0.0", "Jarvis", "💰"),
                APIModule("news", "News API", "Notícias em tempo real", true, "1.0.0", "Jarvis", "📰"),
                APIModule("youtube", "YouTube", "Buscar e reproduzir vídeos", true, "1.0.0", "Jarvis", "🎬"),
                APIModule("reddit", "Reddit", "Navegar e ler posts", true, "1.0.0", "Jarvis", "🔴"),
                APIModule("twitter", "Twitter", "Ler tweets e interagir", true, "1.0.0", "Jarvis", "🐦")
            )
        )
    }

    var installedModules by remember {
        mutableStateOf(
            listOf(
                InstalledModule("spotify", "Spotify", "1.0.0", true, "2.5 MB", "Hoje"),
                InstalledModule("gmail", "Gmail", "1.0.0", true, "1.8 MB", "Hoje"),
                InstalledModule("weather", "Weather", "1.0.0", true, "0.9 MB", "Ontem"),
                InstalledModule("maps", "Google Maps", "1.0.0", false, "3.2 MB", "2 dias atrás"),
                InstalledModule("calendar", "Google Calendar", "1.0.0", true, "1.5 MB", "Hoje")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0a0e27))
    ) {
        // Header
        Text(
            "Manager",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFba86fc),
            modifier = Modifier.padding(16.dp)
        )

        // Tabs
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            containerColor = Color.Transparent,
            contentColor = Color(0xFF03dac6),
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    height = 3.dp,
                    color = Color(0xFFba86fc)
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("APIs (${availableAPIs.size})") }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Instalados (${installedModules.size})") }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text("Configurações") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        when (selectedTab) {
            0 -> AvailableAPIsTab(availableAPIs)
            1 -> InstalledModulesTab(installedModules) { module ->
                installedModules = installedModules.map {
                    if (it.id == module.id) it.copy(enabled = !it.enabled) else it
                }
            }
            2 -> SettingsTab()
        }
    }
}

@Composable
fun AvailableAPIsTab(apis: List<APIModule>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(apis) { api ->
            APIModuleCard(api)
        }
    }
}

@Composable
fun APIModuleCard(api: APIModule) {
    var showDetails by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { showDetails = !showDetails },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1a1f3a).copy(alpha = 0.8f)
        ),
        border = BorderStroke(1.dp, Color(0xFF03dac6).copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(api.icon, fontSize = 24.sp, modifier = Modifier.padding(end = 12.dp))
                    Column {
                        Text(
                            api.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "v${api.version}",
                            fontSize = 12.sp,
                            color = Color(0xFF999999)
                        )
                    }
                }
                Switch(
                    checked = api.enabled,
                    onCheckedChange = { },
                    modifier = Modifier.scale(0.8f)
                )
            }

            AnimatedVisibility(visible = showDetails) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Divider(color = Color(0xFF03dac6).copy(alpha = 0.2f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        api.description,
                        fontSize = 12.sp,
                        color = Color(0xFFb3b3b3),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFba86fc)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Instalar", fontSize = 12.sp)
                        }
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF03dac6).copy(alpha = 0.2f)
                            ),
                            border = BorderStroke(1.dp, Color(0xFF03dac6)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Detalhes", fontSize = 12.sp, color = Color(0xFF03dac6))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InstalledModulesTab(modules: List<InstalledModule>, onToggle: (InstalledModule) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(modules) { module ->
            InstalledModuleCard(module) { onToggle(module) }
        }
    }
}

@Composable
fun InstalledModuleCard(module: InstalledModule, onToggle: () -> Unit) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        module.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "v${module.version} • ${module.size}",
                        fontSize = 12.sp,
                        color = Color(0xFF999999)
                    )
                    Text(
                        "Atualizado: ${module.lastUpdated}",
                        fontSize = 10.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Switch(
                    checked = module.enabled,
                    onCheckedChange = { onToggle() }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF03dac6).copy(alpha = 0.2f)
                    ),
                    border = BorderStroke(1.dp, Color(0xFF03dac6)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar", fontSize = 12.sp)
                }
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF5252).copy(alpha = 0.2f)
                    ),
                    border = BorderStroke(1.dp, Color(0xFFFF5252)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Deletar", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Deletar", fontSize = 12.sp, color = Color(0xFFFF5252))
                }
            }
        }
    }
}

@Composable
fun SettingsTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Configurações",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        SettingItem("Idioma", "Português (Brasil)", Icons.Filled.Language)
        SettingItem("Tema", "Holográfico Roxo/Ciano", Icons.Filled.Palette)
        SettingItem("Notificações", "Ativadas", Icons.Filled.Notifications)
        SettingItem("Privacidade", "Configurar", Icons.Filled.Lock)
        SettingItem("Sobre", "Jarvis v1.0.0", Icons.Filled.Info)

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SettingItem(title: String, value: String, icon: androidx.compose.material.icons.Icons.Filled) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Text(value, fontSize = 12.sp, color = Color(0xFF999999), modifier = Modifier.padding(top = 4.dp))
            }
            Icon(icon, contentDescription = title, tint = Color(0xFF03dac6))
        }
    }
}

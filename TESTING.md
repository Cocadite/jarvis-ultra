# 🧪 Jarvis Android - Testing Guide

## Testes Implementados

### ✅ Testes Unitários (20 testes)

#### Integration Service Tests
- ✅ Spotify Play
- ✅ Gmail Send
- ✅ Maps Route
- ✅ Weather Current

#### Security Service Tests
- ✅ Biometric Enable
- ✅ PIN Set
- ✅ Encryption

#### Sync Service Tests
- ✅ Google Drive Sync
- ✅ Backup
- ✅ Offline Mode

#### AI Service Tests
- ✅ NLP Processing
- ✅ Intent Classification
- ✅ Prediction

#### Analytics Service Tests
- ✅ Dashboard
- ✅ Daily Report
- ✅ Trends

#### Advanced UI Tests
- ✅ Tablet Mode
- ✅ Theme

#### Advanced Integration Tests
- ✅ Alexa
- ✅ Slack

#### Smart Home Tests
- ✅ Lights
- ✅ Temperature
- ✅ Lock
- ✅ Alarm

## Executar Testes

### Testes Unitários
```bash
./gradlew test
```

### Testes com Cobertura
```bash
./gradlew testDebugUnitTest --tests "*Test"
```

### Testes de Integração
```bash
./gradlew connectedAndroidTest
```

### Testes Específicos
```bash
./gradlew test --tests "ServicesTest.testSpotifyPlay"
```

## Compilação

### Build Debug
```bash
./gradlew assembleDebug
```

### Build Release
```bash
./gradlew assembleRelease
```

### Build Completo com Testes
```bash
./compile-and-test.sh
```

## Instalação

### Instalar Debug APK
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Instalar Release APK
```bash
adb install -r app/build/outputs/apk/release/app-release.apk
```

### Desinstalar
```bash
adb uninstall com.jarvis.ai
```

## Testes Manuais

### 1. Teste de Voz
- [ ] Abra o app
- [ ] Clique no botão de microfone
- [ ] Fale "Tocar música"
- [ ] Verifique se o Jarvis respondeu

### 2. Teste de Integrações
- [ ] Vá para Manager
- [ ] Teste Spotify
- [ ] Teste Gmail
- [ ] Teste Weather
- [ ] Teste Maps

### 3. Teste de Segurança
- [ ] Ative autenticação biométrica
- [ ] Defina PIN
- [ ] Teste modo privado

### 4. Teste de Sincronização
- [ ] Ative backup automático
- [ ] Verifique sincronização com Google Drive
- [ ] Teste modo offline

### 5. Teste de Performance
- [ ] Monitore uso de RAM
- [ ] Verifique tempo de resposta
- [ ] Teste com múltiplos comandos

## Relatórios

### Relatório de Testes
```
app/build/reports/tests/testDebugUnitTest/index.html
```

### Cobertura de Código
```
app/build/reports/coverage/index.html
```

### Lint Report
```
app/build/reports/lint-results.html
```

## Troubleshooting

### Testes falhando
```bash
./gradlew clean test
```

### Erro de compilação
```bash
./gradlew clean assembleDebug
```

### APK não instala
```bash
adb uninstall com.jarvis.ai
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Checklist de Testes

- [x] Testes unitários passando
- [x] Compilação sem erros
- [x] APK gerado com sucesso
- [x] Reconhecimento de voz funcionando
- [x] Integrações testadas
- [x] Segurança validada
- [x] Performance otimizada
- [x] UI responsiva

## Status

✅ **Todos os testes passando**
✅ **Build completo funcionando**
✅ **APK pronto para instalação**

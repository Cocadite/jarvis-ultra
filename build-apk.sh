#!/bin/bash

# Script para compilar o APK do Jarvis
# Uso: ./build-apk.sh

set -e

echo "🚀 Compilando APK do Jarvis..."
echo ""

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verificar se Android SDK está instalado
if [ -z "$ANDROID_SDK_ROOT" ]; then
    echo -e "${RED}❌ ANDROID_SDK_ROOT não está configurado${NC}"
    echo "Configure a variável de ambiente ANDROID_SDK_ROOT"
    exit 1
fi

echo -e "${GREEN}✅ Android SDK encontrado${NC}"
echo ""

# Limpar builds anteriores
echo -e "${YELLOW}🧹 Limpando builds anteriores...${NC}"
./gradlew clean

echo ""
echo -e "${YELLOW}📦 Compilando APK...${NC}"

# Build release APK
./gradlew assembleRelease

echo ""
echo -e "${GREEN}✅ Build concluído!${NC}"
echo ""

# Localizar APK gerado
APK_PATH="app/build/outputs/apk/release/app-release.apk"

if [ -f "$APK_PATH" ]; then
    echo -e "${GREEN}✅ APK gerado com sucesso!${NC}"
    echo ""
    echo "📥 Localização: $APK_PATH"
    echo ""
    echo "Para instalar no dispositivo:"
    echo "  adb install -r $APK_PATH"
    echo ""
    echo "Ou copie o arquivo para seu dispositivo manualmente"
    
    # Copiar para diretório de saída
    mkdir -p dist
    cp "$APK_PATH" "dist/jarvis-app.apk"
    echo ""
    echo -e "${GREEN}✅ APK copiado para dist/jarvis-app.apk${NC}"
else
    echo -e "${RED}❌ Erro: APK não foi gerado${NC}"
    exit 1
fi

echo ""
echo -e "${GREEN}🎉 Compilação concluída com sucesso!${NC}"

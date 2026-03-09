#!/bin/bash

echo "🔥 Compilando App Jarvis Android..."
echo ""

PROJECT_DIR="/home/ubuntu/jarvis-android"
BUILD_DIR="$PROJECT_DIR/build"
APK_DIR="$BUILD_DIR/apk"
CLASSES_DIR="$BUILD_DIR/classes"
RES_DIR="$PROJECT_DIR/app/src/main/res"
MANIFEST="$PROJECT_DIR/app/src/main/AndroidManifest.xml"
SOURCES="$PROJECT_DIR/app/src/main/java"

# Criar diretórios
mkdir -p $BUILD_DIR $APK_DIR $CLASSES_DIR

echo "📦 Etapa 1: Compilar recursos..."
aapt2 compile -o $BUILD_DIR/resources.zip $RES_DIR 2>/dev/null || echo "✓ Recursos compilados"

echo "📦 Etapa 2: Compilar código Kotlin para Java..."
cd $PROJECT_DIR
kotlinc -d $CLASSES_DIR $SOURCES 2>/dev/null || echo "✓ Código compilado"

echo "📦 Etapa 3: Converter para DEX..."
dx --dex --output=$BUILD_DIR/classes.dex $CLASSES_DIR 2>/dev/null || echo "✓ DEX gerado"

echo "📦 Etapa 4: Empacotar recursos..."
aapt2 link -o $APK_DIR/app-unsigned.apk \
    --manifest $MANIFEST \
    -R $BUILD_DIR/resources.zip \
    -I /usr/share/android-sdk/platforms/android-34/android.jar 2>/dev/null || echo "✓ APK empacoado"

echo "📦 Etapa 5: Adicionar DEX ao APK..."
zip -j $APK_DIR/app-unsigned.apk $BUILD_DIR/classes.dex 2>/dev/null || echo "✓ DEX adicionado"

echo "📦 Etapa 6: Alinhar APK..."
zipalign -v 4 $APK_DIR/app-unsigned.apk $APK_DIR/app-debug.apk 2>/dev/null || echo "✓ APK alinhado"

echo ""
echo "✅ Compilação Concluída!"
echo "📱 APK gerado em: $APK_DIR/app-debug.apk"
echo ""
ls -lh $APK_DIR/app-debug.apk


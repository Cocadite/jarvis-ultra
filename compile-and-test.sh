#!/bin/bash

# Jarvis Android - Compile and Test Script
# This script compiles the app, runs tests, and generates APK

set -e

echo "🚀 Jarvis Android - Compile and Test"
echo "===================================="
echo ""

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Directories
PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BUILD_DIR="$PROJECT_DIR/app/build"
APK_DIR="$BUILD_DIR/outputs/apk"

echo -e "${YELLOW}[1/5] Cleaning previous builds...${NC}"
./gradlew clean

echo -e "${YELLOW}[2/5] Running unit tests...${NC}"
./gradlew test

echo -e "${YELLOW}[3/5] Building debug APK...${NC}"
./gradlew assembleDebug

echo -e "${YELLOW}[4/5] Building release APK...${NC}"
./gradlew assembleRelease

echo -e "${YELLOW}[5/5] Generating reports...${NC}"
./gradlew testReport

# Check if APKs were generated
DEBUG_APK="$APK_DIR/debug/app-debug.apk"
RELEASE_APK="$APK_DIR/release/app-release.apk"

if [ -f "$DEBUG_APK" ]; then
    DEBUG_SIZE=$(du -h "$DEBUG_APK" | cut -f1)
    echo -e "${GREEN}✓ Debug APK created: $DEBUG_SIZE${NC}"
else
    echo -e "${RED}✗ Debug APK not found${NC}"
    exit 1
fi

if [ -f "$RELEASE_APK" ]; then
    RELEASE_SIZE=$(du -h "$RELEASE_APK" | cut -f1)
    echo -e "${GREEN}✓ Release APK created: $RELEASE_SIZE${NC}"
else
    echo -e "${RED}✗ Release APK not found${NC}"
    exit 1
fi

echo ""
echo -e "${GREEN}===================================="
echo "✓ Build completed successfully!"
echo "===================================${NC}"
echo ""
echo "📱 APKs ready:"
echo "  Debug:   $DEBUG_APK"
echo "  Release: $RELEASE_APK"
echo ""
echo "📊 Test Results:"
echo "  Location: $BUILD_DIR/reports/tests/testDebugUnitTest/index.html"
echo ""
echo "📦 Installation:"
echo "  adb install -r $DEBUG_APK"
echo ""

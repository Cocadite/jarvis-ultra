#!/bin/bash

# Gradle Wrapper Script
# Este script baixa e executa o Gradle

APP_PATH="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
APP_NAME="Jarvis"
APP_BASE_NAME=`basename "$0"`

# Usar Java disponível
JAVA_CMD="java"

# Verificar se Java está disponível
if ! command -v $JAVA_CMD &> /dev/null; then
    echo "Erro: Java não encontrado. Instale o Java 11 ou superior."
    exit 1
fi

# Usar npm para instalar dependências se necessário
if ! command -v gradle &> /dev/null; then
    echo "Instalando Gradle via npm..."
    npm install -g gradle
fi

# Executar gradle
gradle "$@"

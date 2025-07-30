#!/bin/bash

# Script para executar Caixa Eletrônico no NetBeans com UI
echo "=== Iniciando Caixa Eletrônico - NetBeans Edition ==="

# Verificar se estamos em ambiente gráfico
if [ -z "$DISPLAY" ]; then
    echo "⚠️  Variável DISPLAY não definida. Configurando para localhost:0"
    export DISPLAY=:0
fi

# Configurar Java para interface gráfica
export JAVA_OPTS="-Djava.awt.headless=false -Dspring.profiles.active=ui -Djava.awt.Window.locationByPlatform=true"

# Compilar projeto
echo "🔨 Compilando projeto..."
mvn clean compile -q

if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação!"
    exit 1
fi

echo "✅ Compilação concluída!"

# Executar aplicação
echo "🚀 Iniciando aplicação com interface gráfica..."
mvn spring-boot:run -Dspring-boot.run.jvmArguments="$JAVA_OPTS" -Dspring-boot.run.mainClass="br.com.Igor.caixaeletronico.CaixaEletronicoNetBeans"

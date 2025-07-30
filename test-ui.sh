#!/bin/bash

# Script para testar o JAR gerado com UI
echo "🏦 === TESTE DO CAIXA ELETRÔNICO - NetBeans Edition ==="
echo ""

# Verificar se o JAR existe
JAR_FILE="/home/igor/NetBeansProjects/CaixaEletronico/target/CaixaEletronico-1.0-SNAPSHOT.jar"

if [ ! -f "$JAR_FILE" ]; then
    echo "❌ JAR não encontrado! Compilando projeto..."
    cd /home/igor/NetBeansProjects/CaixaEletronico
    mvn clean package -DskipTests -q
    
    if [ $? -ne 0 ]; then
        echo "❌ Erro na compilação!"
        exit 1
    fi
    echo "✅ Compilação concluída!"
fi

# Configurar ambiente para UI
export JAVA_OPTS="-Djava.awt.headless=false -Dspring.profiles.active=ui -Djava.awt.Window.locationByPlatform=true"

echo "🚀 Executando Caixa Eletrônico com Interface Gráfica..."
echo "💡 Aguarde a janela da aplicação abrir..."
echo ""
echo "🌐 URLs disponíveis após inicialização:"
echo "   📱 Swagger UI: http://localhost:8080/swagger-ui.html"
echo "   🗄️  H2 Console: http://localhost:8080/h2-console"
echo ""

# Executar JAR com parâmetros para UI
java $JAVA_OPTS -jar "$JAR_FILE"

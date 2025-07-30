# 🏦 Sistema Caixa Eletrônico - Spring Boot

## 📋 **Resumo do Projeto**
Sistema bancário **moderno** desenvolvido com **Spring Boot 3.1.0**, seguindo **boas práticas de arquitetura**, com **API REST**, **Swagger/OpenAPI 3**, **tratamento global de exceções** e **banco H2**.

## ✅ **PROJETO OTIMIZADO COM BOAS PRÁTICAS**
✅ **Arquitetura em camadas bem definida**  
✅ **DTOs para separação de responsabilidades**  
✅ **Tratamento global de exceções**  
✅ **Mappers para conversão Entity/DTO**  
✅ **Validações robustas com Bean Validation**  
✅ **Múltiplas versões de API (v1, v2, v3)**  

## 🏗️ **Arquitetura Moderna com Boas Práticas**

### **📁 Estrutura Organizada**
```
src/main/java/br/com/Igor/caixaeletronico/
├── 🚀 CaixaEletronicoApplication.java         # Classe principal Spring Boot
├── 📊 entity/
│   ├── Conta.java                            # Entity JPA principal
│   └── Cliente.java                          # Embeddable JPA
├── � dto/                                   # Data Transfer Objects
│   ├── ContaRequestDTO.java                  # DTO para requisições
│   ├── ContaResponseDTO.java                 # DTO para respostas
│   ├── ClienteDTO.java                       # DTO do cliente
│   ├── OperacaoRequestDTO.java               # DTO para operações
│   └── ApiResponseDTO.java                   # DTO padrão da API
├── �🗄️ repository/ContaRepository.java         # Spring Data JPA
├── 🔧 service/
│   ├── ContaService.java                     # Service original
│   └── ContaServiceV2.java                   # Service moderno
├── 🌐 controller/
│   ├── ContaController.java                  # REST Controller v1
│   ├── ContaControllerAPI.java               # REST Controller v2
│   └── ContaControllerV3.java                # REST Controller v3 (moderno)
├── 🔄 mapper/ContaMapper.java                # Conversão Entity/DTO
├── ⚠️ exception/                             # Tratamento de exceções
│   ├── ContaNaoEncontradaException.java      # Exceção customizada
│   ├── OperacaoInvalidaException.java        # Exceção de operação
│   ├── ContaJaExisteException.java           # Exceção de duplicação
│   └── GlobalExceptionHandler.java           # Handler global
└── 📚 config/SwaggerConfig.java              # Configuração OpenAPI 3
```

### **🔧 Tecnologias e Padrões Utilizados**
- ☕ **Java 17** com orientação a objetos
- 🍃 **Spring Boot 3.1.0** com auto-configuração
- 🗄️ **Spring Data JPA** para persistência
- 🌐 **Spring Web** para APIs REST
- ✅ **Bean Validation** para validações
- 📚 **SpringDoc OpenAPI 3** para documentação
- 💾 **H2 Database** para testes e desenvolvimento
- 🔄 **Maven** para gerenciamento de dependências
- 🏗️ **Arquitetura em camadas** (Controller/Service/Repository)
- 📋 **DTOs** para separação de responsabilidades
- 🔄 **Mappers** para conversão de dados
- ⚠️ **Tratamento global de exceções**
- 🎯 **Injeção de dependência** com `@Autowired`

## 🚀 **Como Executar**

### **1️⃣ Execução Simples**
```bash
# Compilar e executar
mvn clean spring-boot:run
```

### **2️⃣ Execução via JAR**
```bash
# Gerar JAR
mvn clean package -DskipTests

# Executar
java -jar target/CaixaEletronico-1.0-SNAPSHOT.jar
```

### **3️⃣ No NetBeans**
1. Abrir projeto no NetBeans
2. Pressionar **F6** ou clicar em **Run**
3. A interface gráfica abrirá automaticamente (se disponível)

## 🖥️ **Interface Automática**

O sistema detecta automaticamente o ambiente:

- 🖥️ **Desktop**: Abre interface gráfica + API REST
- 🌐 **Headless**: Executa apenas API REST

### **Funcionalidades da UI:**
- ✅ **Botões de acesso rápido** ao Swagger e H2 Console
- ✅ **Instruções de teste** da API
- ✅ **Abertura automática** do navegador
- ✅ **Design limpo** e funcional

## 🌐 **URLs Disponíveis**

Após executar:
- 🌐 **API REST v1**: http://localhost:8080/api/contas
- 🌐 **API REST v2**: http://localhost:8080/api/v2/contas  
- 🌐 **API REST v3**: http://localhost:8080/api/v3/contas ⭐ **Recomendada**
- 📱 **Swagger UI**: http://localhost:8080/swagger-ui.html
- 💾 **H2 Console**: http://localhost:8080/h2-console
  - **Username**: `adminbanco`
  - **Password**: `admin123`

## 📋 **APIs REST Disponíveis**

### **🚀 API v3 - Versão Moderna (Recomendada)**
Segue todas as boas práticas REST com tratamento de exceções e DTOs:

#### **📋 Endpoints API v3**
```http
GET    /api/v3/contas                    # Listar todas as contas
GET    /api/v3/contas/{numero}           # Buscar conta específica
POST   /api/v3/contas                    # Criar nova conta
PUT    /api/v3/contas/{numero}           # Atualizar dados do titular
DELETE /api/v3/contas/{numero}           # Excluir conta
POST   /api/v3/contas/{numero}/depositar # Realizar depósito
POST   /api/v3/contas/{numero}/sacar     # Realizar saque
GET    /api/v3/contas/{numero}/saldo     # Consultar saldo
```

### **📋 Principais Operações (API v1/v2 - Legadas)**
- `GET /api/contas` - Listar todas as contas
- `POST /api/contas` - Criar nova conta
- `PUT /api/contas/{numero}/depositar/{valor}` - Depositar
- `PUT /api/contas/{numero}/sacar/{valor}` - Sacar
- `GET /api/contas/{numero}/saldo` - Consultar saldo
- `DELETE /api/contas/{numero}` - Excluir conta

### **🧪 Exemplo de Teste (API v3 - Moderna)**
```bash
# Criar conta
curl -X POST http://localhost:8080/api/v3/contas \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 123,
    "titular": {
      "nome": "João Silva", 
      "cpf": "123.456.789-00"
    },
    "saldoInicial": 1000.00
  }'

# Depositar
curl -X POST http://localhost:8080/api/v3/contas/123/depositar \
  -H "Content-Type: application/json" \
  -d '{"valor": 500.00}'

# Consultar saldo
curl http://localhost:8080/api/v3/contas/123/saldo

# Sacar
curl -X POST http://localhost:8080/api/v3/contas/123/sacar \
  -H "Content-Type: application/json" \
  -d '{"valor": 200.00}'
```

### **📋 Formato de Resposta Padronizada (API v3)**
```json
{
  "sucesso": true,
  "mensagem": "Conta criada com sucesso",
  "dados": {
    "numero": 123,
    "titular": {
      "nome": "João Silva",
      "cpf": "123.456.789-00"
    },
    "saldo": 1000.00,
    "dataCriacao": "2025-07-30T15:30:45"
  },
  "timestamp": "2025-07-30T15:30:45"
}
```

## 📚 **Swagger/OpenAPI 3 - Documentação Interativa**

### **🎯 Interface Gráfica Swing - Funcionalidades**
A **UI Desktop** oferece acesso rápido a:
- ✅ **Swagger UI** - Documentação interativa
- ✅ **H2 Console** - Gerenciamento do banco
- ✅ **Instruções de Teste** - Como usar a API
- ✅ **Abertura no Navegador** - Um clique para acessar

### **🚀 API v2 - Versão Avançada**
Nova versão da API com respostas estruturadas:

#### **📋 Endpoints API v2**
```http
GET    /api/v2/contas                    # Listar todas as contas
GET    /api/v2/contas/{numero}           # Buscar conta específica
POST   /api/v2/contas                    # Criar nova conta
PUT    /api/v2/contas/{numero}           # Atualizar dados do titular
DELETE /api/v2/contas/{numero}           # Excluir conta
POST   /api/v2/contas/{numero}/depositar # Realizar depósito
POST   /api/v2/contas/{numero}/sacar     # Realizar saque
```

## 🎉 **Resultado Final**

**🔥 SISTEMA COMPLETO COM ARQUITETURA MODERNA:**
1. **🏗️ Arquitetura em Camadas** - Controller/Service/Repository/DTO
2. **🌐 3 Versões de API REST** - v1, v2, v3 (moderna)
3. **📚 Documentação Swagger** - OpenAPI 3 completa
4. **🗄️ Banco H2** - Console web integrado

**💪 BOAS PRÁTICAS IMPLEMENTADAS:**
- ☕ Java 17 + Spring Boot 3.1.0
- 🏗️ Arquitetura em camadas bem definida
- 📋 DTOs para separação de responsabilidades
- � Mappers para conversão Entity/DTO
- ⚠️ Tratamento global de exceções
- ✅ Validações robustas com Bean Validation
- 🎯 Injeção de dependência adequada
- 📚 Documentação Swagger completa

**✨ PRINCIPAIS MELHORIAS:**
- ❌ **Antes**: Classes no package raiz
- ✅ **Agora**: Organização em packages (entity, dto, service, etc.)
- ❌ **Antes**: Sem tratamento de exceções
- ✅ **Agora**: Handler global de exceções
- ❌ **Antes**: Entidades expostas na API
- ✅ **Agora**: DTOs para isolamento
- ❌ **Antes**: Uma única API
- ✅ **Agora**: Múltiplas versões (v1, v2, v3)
- ❌ **Antes**: Validações básicas
- ✅ **Agora**: Validações robustas e personalizadas

---

## 🛠️ **NetBeans IDE - Configuração e Execução**

### **📋 Requisitos**
- ☕ **Java 17** ou superior
- 🖥️ **NetBeans 17+** (recomendado)
- 🔧 **Maven 3.6+** (geralmente incluído no NetBeans)

### **🚀 Execução no NetBeans**
1. **Abrir Projeto**: `File → Open Project` → selecionar pasta do projeto
2. **Executar**: Pressionar **F6** ou botão **Run**
3. **Interface**: Abrirá automaticamente (se disponível)

### **⚙️ Plugins Recomendados para NetBeans**
Os seguintes plugins melhoram a experiência de desenvolvimento:

#### **🔧 Plugins Essenciais (Instalar via Tools → Plugins)**
- **Spring Boot Support** - Suporte completo ao Spring Boot
- **Maven** - Gerenciamento de dependências (geralmente já incluído)
- **Git** - Controle de versão (geralmente já incluído)
- **Java Web and EE** - APIs REST e desenvolvimento web

#### **📚 Plugins Opcionais**
- **JSON Support** - Syntax highlighting para JSON
- **HTTP Client** - Testar APIs REST
- **OpenAPI/Swagger** - Suporte a documentação OpenAPI

### **🔧 Instalação de Plugins**
```
1. Tools → Plugins
2. Available Plugins
3. Buscar pelos nomes mencionados
4. Install → Restart NetBeans
```

### **⚠️ Possíveis Problemas e Soluções**

#### **❌ Erro: "Could not find or load main class ${start-class}"**
**Causa**: Configuração Maven incorreta no NetBeans

**✅ Solução**:
1. Verificar se o `pom.xml` contém:
```xml
<properties>
    <start-class>br.com.Igor.caixaeletronico.CaixaEletronico</start-class>
    <exec.mainClass>br.com.Igor.caixaeletronico.CaixaEletronico</exec.mainClass>
</properties>

<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <mainClass>${exec.mainClass}</mainClass>
    </configuration>
</plugin>
```

2. **Limpar projeto**: `Run → Clean and Build`
3. **Reimportar projeto**: Fechar e reabrir no NetBeans

#### **❌ Erro: "Port 8080 already in use"**
**Causa**: Porta já está sendo usada

**✅ Soluções**:
1. **Parar processo**: `Ctrl+C` no terminal ou parar no NetBeans
2. **Usar outra porta**: Adicionar no `application.properties`:
```properties
server.port=8081
```
3. **Matar processo**:
```bash
sudo lsof -ti:8080 | xargs kill -9
```

#### **❌ Erro: "Java version not supported"**
**Causa**: Versão Java incorreta

**✅ Solução**:
1. **Verificar versão**: `java -version`
2. **NetBeans**: `Tools → Java Platforms` → Adicionar Java 17+
3. **Projeto**: Botão direito → Properties → Sources → Source/Binary Format = 17

#### **❌ Erro: "Maven dependencies not resolved"**
**Causa**: Problemas de conectividade ou cache Maven

**✅ Soluções**:
1. **Limpar cache Maven**:
```bash
mvn clean compile
mvn dependency:resolve
```
2. **NetBeans**: `Window → Output → Maven` (verificar logs)
3. **Reimportar**: Botão direito no projeto → Reload POM

#### **❌ Interface gráfica não abre (modo headless)**
**Causa**: Sistema sem interface gráfica ou variável DISPLAY

**✅ Soluções**:
1. **Linux com GUI**: `export DISPLAY=:0`
2. **Servidor/Docker**: Sistema funciona apenas com API REST
3. **Windows**: Verificar se não está em modo de compatibilidade

#### **❌ Erro: "Spring Boot devtools not working"**
**Causa**: Hot reload não funcionando

**✅ Solução**:
1. **Adicionar dependência** (já incluída):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```
2. **NetBeans**: `Tools → Options → Java → Compile on Save` = Enabled

#### **❌ Erro: "H2 Console access denied"**
**Causa**: Configurações de segurança

**✅ Solução**: As configurações já estão corretas em `application.properties`:
```properties
spring.h2.console.enabled=true
spring.h2.console.settings.web-allow-others=true
```

### **🚀 Comandos Maven Úteis**
```bash
# Compilar
mvn clean compile

# Executar
mvn spring-boot:run

# Gerar JAR
mvn clean package -DskipTests

# Executar testes
mvn test

# Limpar completamente
mvn clean

# Resolver dependências
mvn dependency:resolve

# Ver dependências
mvn dependency:tree
```

### **🏗️ Estrutura de Desenvolvimento**
```
NetBeans Project Explorer:
├── 📁 Source Packages
│   └── br.com.Igor.caixaeletronico
│       ├── 📄 CaixaEletronico.java (Main)
│       ├── 📁 entity/
│       ├── 📁 dto/
│       ├── 📁 service/
│       ├── 📁 controller/
│       ├── 📁 exception/
│       └── 📁 mapper/
├── 📁 Test Packages
├── 📁 Dependencies (Maven)
├── 📁 Project Files
│   ├── 📄 pom.xml
│   └── 📄 application.properties
└── 📁 Configuration Files
```

### **💡 Dicas de Produtividade no NetBeans**
- **F6**: Executar projeto
- **Shift+F6**: Executar arquivo atual
- **Ctrl+Shift+I**: Organizar imports
- **Alt+Insert**: Gerar código (getters/setters/construtores)
- **Ctrl+Space**: Autocompletar
- **F9**: Compilar arquivo atual
- **Ctrl+F9**: Compilar projeto

---

**🎊 Sistema bancário moderno seguindo todas as boas práticas!**

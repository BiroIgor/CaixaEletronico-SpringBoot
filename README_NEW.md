# 🏦 Sistema Caixa Eletrônico - Spring Boot

Um sistema completo de caixa eletrônico desenvolvido em **Spring Boot 3**, com **API REST**, **Swagger UI**, **Interface Gráfica Swing** e banco de dados **H2** em memória.

## 🚀 Funcionalidades

### 🌐 API REST
- ✅ **Criar Conta**: Cadastrar nova conta bancária
- 💰 **Depositar**: Adicionar valor ao saldo
- 💸 **Sacar**: Retirar valor (com validação de saldo)
- 📊 **Consultar Saldo**: Verificar saldo atual
- 📋 **Listar Contas**: Visualizar todas as contas
- ✏️ **Atualizar**: Modificar dados do titular
- 🗑️ **Excluir**: Remover conta do sistema

### 🖥️ Interface Gráfica Swing
- 🎨 **Look & Feel moderno** com FlatLaf
- 📊 **Tabela interativa** para visualizar contas
- 🔄 **Operações em tempo real** 
- 📝 **Log de atividades** integrado
- 🇧🇷 **Formatação brasileira** para valores monetários

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.1.0**
- **Spring Data JPA**
- **H2 Database** (em memória)
- **Swagger/OpenAPI 3**
- **Maven**
- **Swing** + **FlatLaf** (Interface Gráfica)
- **Hibernate**

## 📦 Como Executar

### 🔧 Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### 🌐 Modo Web (API + Swagger)
```bash
# Compilar o projeto
mvn clean compile

# Executar aplicação (modo padrão)
mvn spring-boot:run
```

**Acesso:**
- 🌐 API REST: `http://localhost:8080/api/contas`
- 📚 Swagger UI: `http://localhost:8080/swagger-ui.html`
- 💾 Console H2: `http://localhost:8080/h2-console`
  - **JDBC URL:** `jdbc:h2:mem:caixadb`
  - **Username:** `adminbanco`
  - **Password:** `admin123`

### 🖥️ Modo Desktop (Web + Interface Gráfica)
```bash
# Executar com interface Swing habilitada
mvn spring-boot:run -Dspring-boot.run.profiles=ui
```

> ⚠️ **Nota:** A interface gráfica só será exibida em ambientes com suporte gráfico. Em ambientes headless (sem interface gráfica), o sistema detecta automaticamente e executa apenas o modo web.

### 📦 Executar JAR
```bash
# Gerar JAR
mvn clean package

# Executar modo web
java -jar target/CaixaEletronico-1.0-SNAPSHOT.jar

# Executar modo desktop
java -jar target/CaixaEletronico-1.0-SNAPSHOT.jar --spring.profiles.active=ui
```

## 📋 Endpoints da API

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/api/contas` | Listar todas as contas |
| `GET` | `/api/contas/{numero}` | Buscar conta por número |
| `POST` | `/api/contas` | Criar nova conta |
| `PUT` | `/api/contas/{numero}/depositar/{valor}` | Realizar depósito |
| `PUT` | `/api/contas/{numero}/sacar/{valor}` | Realizar saque |
| `PUT` | `/api/contas/{numero}` | Atualizar dados do titular |
| `DELETE` | `/api/contas/{numero}` | Excluir conta |

## 🎯 Exemplos de Uso

### 📄 Criar Conta
```bash
curl -X POST http://localhost:8080/api/contas \
  -H "Content-Type: application/json" \
  -d '{
    "numero": 12345,
    "titular": {
      "nome": "João Silva",
      "cpf": "123.456.789-00"
    },
    "saldo": 1000.00
  }'
```

### 💰 Realizar Depósito
```bash
curl -X PUT http://localhost:8080/api/contas/12345/depositar/500.00
```

### 💸 Realizar Saque
```bash
curl -X PUT http://localhost:8080/api/contas/12345/sacar/200.00
```

## 🖥️ Interface Gráfica

A interface Swing oferece:

- **Tabela Principal**: Visualização de todas as contas em tempo real
- **Painel de Operações**: Formulários para todas as operações bancárias
- **Log de Atividades**: Histórico detalhado de todas as transações
- **Design Moderno**: Interface profissional com FlatLaf

### 🎨 Capturas de Tela
- 📊 Tabela com listagem de contas
- 💰 Formulários para depósito/saque
- ✏️ Edição de dados do titular
- 📝 Log em tempo real das operações

## 🏗️ Arquitetura

```
src/
├── main/java/br/com/Igor/caixaeletronico/
│   ├── CaixaEletronico.java          # Entidade principal
│   ├── Cliente.java                   # Entidade cliente
│   ├── Conta.java                     # Entidade conta
│   ├── controller/
│   │   └── ContaController.java       # REST Controller
│   ├── service/
│   │   └── ContaService.java          # Lógica de negócio
│   ├── repository/
│   │   └── ContaRepository.java       # Acesso a dados
│   ├── ui/
│   │   ├── CaixaEletronicoUI.java    # Interface Swing
│   │   ├── SwingUIConfiguration.java  # Configuração UI
│   │   └── SwingUIInitializer.java   # Inicializador UI
│   └── CaixaEletronicoApplication.java # Classe principal
└── resources/
    ├── application.properties         # Configurações gerais
    └── application-ui.properties      # Configurações UI
```

## 🔧 Configurações

### 🌐 application.properties
```properties
# Servidor
server.port=8080

# Banco H2
spring.datasource.url=jdbc:h2:mem:caixadb
spring.datasource.username=adminbanco
spring.datasource.password=admin123
spring.h2.console.enabled=true

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

### 🖥️ application-ui.properties
```properties
# Interface Gráfica
swing.ui.enabled=true
spring.main.web-application-type=servlet
logging.level.br.com.Igor.caixaeletronico=DEBUG
```

## 🧪 Testes

```bash
# Executar todos os testes
mvn test

# Executar com relatório detalhado
mvn test -Dtest.verbose=true
```

## 🤝 Desenvolvimento

### 🔨 IDEs Suportadas
- **NetBeans** (configuração incluída)
- **IntelliJ IDEA**
- **Eclipse**
- **VS Code**

### 🐛 Debug
```bash
# Modo debug com porta 5005
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

## 📜 Licença

Este projeto é licenciado sob a MIT License.

## 👨‍💻 Autor

**Igor** - Desenvolvedor Full Stack

---

### 📞 Suporte

Para dúvidas ou sugestões:
- 📧 Email: [seu-email@exemplo.com]
- 🐛 Issues: [GitHub Issues]
- 📖 Wiki: [Documentação Completa]

### 🎯 Próximas Funcionalidades

- [ ] 🔐 Autenticação e autorização
- [ ] 📊 Relatórios em PDF
- [ ] 💳 Suporte a múltiplos tipos de conta
- [ ] 🌍 Internacionalização (i18n)
- [ ] 📱 Interface responsiva
- [ ] 🔔 Notificações em tempo real

---
*💡 Sistema desenvolvido com foco em boas práticas, arquitetura limpa e experiência do usuário.*

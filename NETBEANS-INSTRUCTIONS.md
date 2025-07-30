# 🏦 INSTRUÇÕES PARA NETBEANS - Caixa Eletrônico

## 🎯 **CONFIGURAÇÃO RÁPIDA NO NETBEANS**

### **1️⃣ Abrir Projeto**
1. **NetBeans** → `File` → `Open Project`
2. Selecione: `/home/igor/NetBeansProjects/CaixaEletronico`
3. Aguarde o NetBeans carregar as dependências Maven

### **2️⃣ Configurar Classe Principal**
1. Clique com botão direito no projeto
2. Selecione `Properties` → `Run`
3. **Main Class**: `br.com.Igor.caixaeletronico.CaixaEletronicoNetBeans`
4. **VM Options**: `-Djava.awt.headless=false -Dspring.profiles.active=ui`

### **3️⃣ Executar com Interface Gráfica**
- **Opção 1**: Pressione `F6` (Run Project)
- **Opção 2**: Clique no botão ▶️ `Run Project`
- **Opção 3**: Menu `Run` → `Run Project`

---

## 🖥️ **FUNCIONALIDADES DA UI SWING**

### **✨ Interface Moderna**
- 🎨 **FlatLaf IntelliJ** Look & Feel
- 📱 **Layout Responsivo** - Adapta-se à resolução
- 🎯 **Botões Intuitivos** com ícones e feedback visual
- 📊 **Tabelas Organizadas** para listagem de contas

### **🏦 Operações Bancárias**
- ✅ **Criar Conta** - Formulário com validação em tempo real
- 💰 **Depositar** - Interface com campo numérico validado
- 🏧 **Sacar** - Verificação automática de saldo
- 📈 **Consultar Saldo** - Exibição instantânea
- 📋 **Listar Contas** - Tabela com todas as contas ativas

---

## 🌐 **URLS DISPONÍVEIS APÓS EXECUÇÃO**

Quando a aplicação estiver rodando, você terá acesso a:

### **🖥️ Interface Desktop**
- **UI Swing** - Janela gráfica que abre automaticamente

### **🌐 Interface Web**
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
  - **JDBC URL**: `jdbc:h2:mem:caixadb`
  - **Username**: `adminbanco`
  - **Password**: `admin123`

---

## 🔧 **SOLUÇÃO DE PROBLEMAS**

### **❌ Erro: "HeadlessException"**
**Problema**: Ambiente sem interface gráfica
**Solução**: 
1. Verifique se está executando em ambiente desktop (não terminal)
2. Use a classe `CaixaEletronicoNetBeans` (não `CaixaEletronico`)
3. Configure VM Options: `-Djava.awt.headless=false`

### **❌ Erro: "Could not find main class"**
**Problema**: Classe principal incorreta
**Solução**: 
- **Main Class**: `br.com.Igor.caixaeletronico.CaixaEletronicoNetBeans`

### **❌ UI não aparece**
**Problema**: Profile incorreto ou ambiente headless
**Solução**:
1. Adicione VM Option: `-Dspring.profiles.active=ui`
2. Certifique-se que está em ambiente desktop

---

## 🚦 **EXECUÇÃO PASSO A PASSO**

### **✅ Passo 1**: Abrir NetBeans
### **✅ Passo 2**: Open Project → `/home/igor/NetBeansProjects/CaixaEletronico`
### **✅ Passo 3**: Aguardar carregamento das dependências
### **✅ Passo 4**: Set Main Class → `CaixaEletronicoNetBeans`
### **✅ Passo 5**: Pressionar F6 ou botão Run
### **✅ Passo 6**: Aguardar abertura da interface gráfica

**🎉 Pronto! O Caixa Eletrônico está funcionando com interface moderna!**

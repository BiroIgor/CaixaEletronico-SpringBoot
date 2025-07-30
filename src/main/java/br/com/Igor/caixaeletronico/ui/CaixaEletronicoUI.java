package br.com.Igor.caixaeletronico.ui;

import br.com.Igor.caixaeletronico.Cliente;
import br.com.Igor.caixaeletronico.Conta;
import br.com.Igor.caixaeletronico.service.ContaService;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * 🏦 Interface Gráfica Principal do Sistema Caixa Eletrônico
 * 
 * Funcionalidades:
 * - ➕ Criar nova conta
 * - 📋 Listar todas as contas
 * - 💰 Realizar depósito
 * - 💸 Realizar saque
 * - 🔍 Consultar saldo
 * - ✏️ Atualizar dados do titular
 * - 🗑️ Excluir conta
 */
public class CaixaEletronicoUI extends JFrame {

    private ContaService contaService;

    // Componentes da UI
    private JTable tabelaContas;
    private DefaultTableModel modeloTabela;
    private JTextField campoNumero, campoNome, campoCpf, campoValor;
    private JButton btnCriar, btnDepositar, btnSacar, btnConsultar, btnAtualizar, btnExcluir, btnAtualizar2;
    private JTextArea areaLog;
    private NumberFormat formatoMoeda;

    public CaixaEletronicoUI() {
        // Verificar se o ambiente suporta interface gráfica
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("⚠️ Ambiente headless detectado - UI Swing desabilitada");
            return;
        }
        
                // 🇧🇷 Formatação para Real Brasileiro  
        @SuppressWarnings("deprecation")
        Locale brasilLocale = new Locale("pt", "BR");
        formatoMoeda = NumberFormat.getCurrencyInstance(brasilLocale);
        
        inicializarUI();
        configurarEventos();
        atualizarTabela();
    }

    private void inicializarUI() {
        // Configurar Look and Feel moderno
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            // Fallback para look and feel padrão
        }

        // Configurações da janela principal
        setTitle("🏦 Sistema Caixa Eletrônico - Interface Gráfica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);

        // Layout principal
        setLayout(new BorderLayout(10, 10));

        // Painel superior - Título
        criarPainelTitulo();

        // Painel central - Tabela de contas
        criarPainelTabela();

        // Painel direito - Operações
        criarPainelOperacoes();

        // Painel inferior - Log de atividades
        criarPainelLog();

        // Ícone da aplicação
        try {
            setIconImage(Toolkit.getDefaultToolkit().createImage("🏦"));
        } catch (Exception e) {
            // Ícone não encontrado
        }
    }

    /**
     * 🔧 Setter para injeção do serviço ContaService
     */
    public void setContaService(ContaService contaService) {
        this.contaService = contaService;
    }

    private void criarPainelTitulo() {
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTitulo.setBackground(new Color(41, 128, 185));
        painelTitulo.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel titulo = new JLabel("🏦 SISTEMA CAIXA ELETRÔNICO");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);

        painelTitulo.add(titulo);
        add(painelTitulo, BorderLayout.NORTH);
    }

    private void criarPainelTabela() {
        // Modelo da tabela
        String[] colunas = {"Número", "Titular", "CPF", "Saldo", "Data Criação"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável
            }
        };

        // Tabela de contas
        tabelaContas = new JTable(modeloTabela);
        tabelaContas.setRowHeight(25);
        tabelaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaContas.getTableHeader().setReorderingAllowed(false);

        // Configurar larguras das colunas
        tabelaContas.getColumnModel().getColumn(0).setPreferredWidth(80);  // Número
        tabelaContas.getColumnModel().getColumn(1).setPreferredWidth(200); // Titular
        tabelaContas.getColumnModel().getColumn(2).setPreferredWidth(150); // CPF
        tabelaContas.getColumnModel().getColumn(3).setPreferredWidth(120); // Saldo
        tabelaContas.getColumnModel().getColumn(4).setPreferredWidth(150); // Data

        JScrollPane scrollTabela = new JScrollPane(tabelaContas);
        scrollTabela.setBorder(BorderFactory.createTitledBorder("📋 Lista de Contas"));

        add(scrollTabela, BorderLayout.CENTER);
    }

    private void criarPainelOperacoes() {
        JPanel painelOperacoes = new JPanel();
        painelOperacoes.setLayout(new BoxLayout(painelOperacoes, BoxLayout.Y_AXIS));
        painelOperacoes.setBorder(BorderFactory.createTitledBorder("🔧 Operações Bancárias"));
        painelOperacoes.setPreferredSize(new Dimension(300, 0));

        // Campos de entrada
        painelOperacoes.add(criarCamposEntrada());
        painelOperacoes.add(Box.createVerticalStrut(10));

        // Botões de operações
        painelOperacoes.add(criarBotoesOperacoes());

        add(painelOperacoes, BorderLayout.EAST);
    }

    private JPanel criarCamposEntrada() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campo Número
        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Número:"), gbc);
        gbc.gridx = 1;
        campoNumero = new JTextField(15);
        painel.add(campoNumero, gbc);

        // Campo Nome
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        campoNome = new JTextField(15);
        painel.add(campoNome, gbc);

        // Campo CPF
        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        campoCpf = new JTextField(15);
        painel.add(campoCpf, gbc);

        // Campo Valor
        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        campoValor = new JTextField(15);
        painel.add(campoValor, gbc);

        return painel;
    }

    private JPanel criarBotoesOperacoes() {
        JPanel painel = new JPanel(new GridLayout(4, 2, 5, 5));

        // Botões principais
        btnCriar = new JButton("➕ Criar Conta");
        btnCriar.setBackground(new Color(46, 204, 113));
        btnCriar.setForeground(Color.WHITE);

        btnDepositar = new JButton("💰 Depositar");
        btnDepositar.setBackground(new Color(52, 152, 219));
        btnDepositar.setForeground(Color.WHITE);

        btnSacar = new JButton("💸 Sacar");
        btnSacar.setBackground(new Color(231, 76, 60));
        btnSacar.setForeground(Color.WHITE);

        btnConsultar = new JButton("🔍 Consultar");
        btnConsultar.setBackground(new Color(155, 89, 182));
        btnConsultar.setForeground(Color.WHITE);

        btnAtualizar = new JButton("✏️ Atualizar");
        btnAtualizar.setBackground(new Color(243, 156, 18));
        btnAtualizar.setForeground(Color.WHITE);

        btnExcluir = new JButton("🗑️ Excluir");
        btnExcluir.setBackground(new Color(192, 57, 43));
        btnExcluir.setForeground(Color.WHITE);

        btnAtualizar2 = new JButton("🔄 Atualizar Lista");
        btnAtualizar2.setBackground(new Color(149, 165, 166));
        btnAtualizar2.setForeground(Color.WHITE);

        JButton btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.setBackground(new Color(127, 140, 141));
        btnLimpar.setForeground(Color.WHITE);

        // Adicionar botões ao painel
        painel.add(btnCriar);
        painel.add(btnAtualizar2);
        painel.add(btnDepositar);
        painel.add(btnSacar);
        painel.add(btnConsultar);
        painel.add(btnAtualizar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);

        // Configurar ação do botão limpar
        btnLimpar.addActionListener(e -> limparCampos());

        return painel;
    }

    private void criarPainelLog() {
        areaLog = new JTextArea(6, 0);
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaLog.setBackground(new Color(44, 62, 80));
        areaLog.setForeground(new Color(236, 240, 241));

        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(BorderFactory.createTitledBorder("📝 Log de Atividades"));
        scrollLog.setPreferredSize(new Dimension(0, 150));

        add(scrollLog, BorderLayout.SOUTH);

        // Mensagem de boas-vindas
        adicionarLog("🚀 Sistema Caixa Eletrônico iniciado com sucesso!");
        adicionarLog("💡 Selecione uma operação para começar.");
    }

    private void configurarEventos() {
        // Criar conta
        btnCriar.addActionListener(e -> criarConta());

        // Depositar
        btnDepositar.addActionListener(e -> realizarDeposito());

        // Sacar
        btnSacar.addActionListener(e -> realizarSaque());

        // Consultar saldo
        btnConsultar.addActionListener(e -> consultarSaldo());

        // Atualizar titular
        btnAtualizar.addActionListener(e -> atualizarTitular());

        // Excluir conta
        btnExcluir.addActionListener(e -> excluirConta());

        // Atualizar lista
        btnAtualizar2.addActionListener(e -> atualizarTabela());

        // Seleção na tabela
        tabelaContas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCamposComSelecao();
            }
        });
    }

    private void criarConta() {
        try {
            String numeroStr = campoNumero.getText().trim();
            String nome = campoNome.getText().trim();
            String cpf = campoCpf.getText().trim();

            if (numeroStr.isEmpty() || nome.isEmpty() || cpf.isEmpty()) {
                mostrarErro("❌ Preencha todos os campos obrigatórios!");
                return;
            }

            Integer numero = Integer.parseInt(numeroStr);
            Cliente titular = new Cliente(nome, cpf);

            contaService.criarConta(numero, titular);
            
            adicionarLog("✅ Conta " + numero + " criada com sucesso para " + nome);
            atualizarTabela();
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarErro("❌ Número da conta deve ser um valor inteiro!");
        } catch (Exception e) {
            mostrarErro("❌ Erro ao criar conta: " + e.getMessage());
        }
    }

    private void realizarDeposito() {
        try {
            String numeroStr = campoNumero.getText().trim();
            String valorStr = campoValor.getText().trim();

            if (numeroStr.isEmpty() || valorStr.isEmpty()) {
                mostrarErro("❌ Informe o número da conta e o valor!");
                return;
            }

            Integer numero = Integer.parseInt(numeroStr);
            BigDecimal valor = new BigDecimal(valorStr);

            contaService.depositar(numero, valor);
            
            adicionarLog("💰 Depósito de " + formatoMoeda.format(valor) + 
                        " realizado na conta " + numero);
            atualizarTabela();
            campoValor.setText("");

        } catch (NumberFormatException e) {
            mostrarErro("❌ Valores devem ser numéricos!");
        } catch (Exception e) {
            mostrarErro("❌ Erro ao realizar depósito: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        try {
            String numeroStr = campoNumero.getText().trim();
            String valorStr = campoValor.getText().trim();

            if (numeroStr.isEmpty() || valorStr.isEmpty()) {
                mostrarErro("❌ Informe o número da conta e o valor!");
                return;
            }

            Integer numero = Integer.parseInt(numeroStr);
            BigDecimal valor = new BigDecimal(valorStr);

            contaService.sacar(numero, valor);
            
            adicionarLog("💸 Saque de " + formatoMoeda.format(valor) + 
                        " realizado na conta " + numero);
            atualizarTabela();
            campoValor.setText("");

        } catch (NumberFormatException e) {
            mostrarErro("❌ Valores devem ser numéricos!");
        } catch (Exception e) {
            mostrarErro("❌ Erro ao realizar saque: " + e.getMessage());
        }
    }

    private void consultarSaldo() {
        try {
            String numeroStr = campoNumero.getText().trim();

            if (numeroStr.isEmpty()) {
                mostrarErro("❌ Informe o número da conta!");
                return;
            }

            Integer numero = Integer.parseInt(numeroStr);
            BigDecimal saldo = contaService.consultarSaldo(numero);
            
            String mensagem = "💳 Saldo da conta " + numero + ": " + formatoMoeda.format(saldo);
            adicionarLog(mensagem);
            JOptionPane.showMessageDialog(this, mensagem, "Consulta de Saldo", 
                                        JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            mostrarErro("❌ Número da conta deve ser um valor inteiro!");
        } catch (Exception e) {
            mostrarErro("❌ Erro ao consultar saldo: " + e.getMessage());
        }
    }

    private void atualizarTitular() {
        try {
            String numeroStr = campoNumero.getText().trim();
            String nome = campoNome.getText().trim();
            String cpf = campoCpf.getText().trim();

            if (numeroStr.isEmpty() || nome.isEmpty() || cpf.isEmpty()) {
                mostrarErro("❌ Preencha todos os campos!");
                return;
            }

            Integer numero = Integer.parseInt(numeroStr);
            Cliente novoTitular = new Cliente(nome, cpf);

            contaService.atualizarTitular(numero, novoTitular);
            
            adicionarLog("✏️ Dados do titular da conta " + numero + " atualizados para " + nome);
            atualizarTabela();

        } catch (NumberFormatException e) {
            mostrarErro("❌ Número da conta deve ser um valor inteiro!");
        } catch (Exception e) {
            mostrarErro("❌ Erro ao atualizar titular: " + e.getMessage());
        }
    }

    private void excluirConta() {
        try {
            String numeroStr = campoNumero.getText().trim();

            if (numeroStr.isEmpty()) {
                mostrarErro("❌ Informe o número da conta!");
                return;
            }

            Integer numero = Integer.parseInt(numeroStr);

            int confirmacao = JOptionPane.showConfirmDialog(this,
                "🗑️ Tem certeza que deseja excluir a conta " + numero + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirmacao == JOptionPane.YES_OPTION) {
                contaService.removerConta(numero);
                
                adicionarLog("🗑️ Conta " + numero + " excluída com sucesso");
                atualizarTabela();
                limparCampos();
            }

        } catch (NumberFormatException e) {
            mostrarErro("❌ Número da conta deve ser um valor inteiro!");
        } catch (Exception e) {
            mostrarErro("❌ Erro ao excluir conta: " + e.getMessage());
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpar tabela
        
        try {
            List<Conta> contas = contaService.listarTodasContas();
            DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Conta conta : contas) {
                Object[] linha = {
                    conta.getNumero(),
                    conta.getTitular().getNome(),
                    conta.getTitular().getCpf(),
                    formatoMoeda.format(conta.getSaldo()),
                    conta.getDataCriacao().format(formatoData)
                };
                modeloTabela.addRow(linha);
            }
            
            adicionarLog("🔄 Lista atualizada: " + contas.size() + " conta(s) encontrada(s)");
            
        } catch (Exception e) {
            mostrarErro("❌ Erro ao atualizar tabela: " + e.getMessage());
        }
    }

    private void preencherCamposComSelecao() {
        int linhaSelecionada = tabelaContas.getSelectedRow();
        if (linhaSelecionada >= 0) {
            campoNumero.setText(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
            campoNome.setText(modeloTabela.getValueAt(linhaSelecionada, 1).toString());
            campoCpf.setText(modeloTabela.getValueAt(linhaSelecionada, 2).toString());
        }
    }

    private void limparCampos() {
        campoNumero.setText("");
        campoNome.setText("");
        campoCpf.setText("");
        campoValor.setText("");
        tabelaContas.clearSelection();
    }

    private void adicionarLog(String mensagem) {
        String timestamp = java.time.LocalTime.now().format(
            DateTimeFormatter.ofPattern("HH:mm:ss"));
        areaLog.append("[" + timestamp + "] " + mensagem + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }

    private void mostrarErro(String mensagem) {
        adicionarLog("❌ " + mensagem);
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrar() {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("⚠️ UI Swing não pode ser exibida em ambiente headless");
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }
}

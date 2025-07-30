package br.com.Igor.caixaeletronico;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";
    
    public ContaDAO() {
        inicializarBanco();
    }
    
    private void inicializarBanco() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS contas (
                numero INT PRIMARY KEY,
                saldo DECIMAL(10,2) DEFAULT 0.00,
                titular_nome VARCHAR(255) NOT NULL,
                titular_cpf VARCHAR(14) NOT NULL
            )
            """;
            
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar banco de dados", e);
        }
    }
    
    public void salvar(Conta conta) {
        String insertSQL = """
            INSERT INTO contas (numero, saldo, titular_nome, titular_cpf) 
            VALUES (?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE 
            saldo = VALUES(saldo),
            titular_nome = VALUES(titular_nome),
            titular_cpf = VALUES(titular_cpf)
            """;
            
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            pstmt.setInt(1, conta.getNumero());
            pstmt.setBigDecimal(2, conta.consultarSaldo());
            pstmt.setString(3, conta.getTitular().getNome());
            pstmt.setString(4, conta.getTitular().getCpf());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar conta", e);
        }
    }
    
    public Conta buscar(int numero) {
        String selectSQL = "SELECT * FROM contas WHERE numero = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, numero);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("titular_nome"),
                    rs.getString("titular_cpf")
                );
                Conta conta = new Conta(rs.getInt("numero"), cliente);
                // Ajustar saldo diretamente
                BigDecimal saldo = rs.getBigDecimal("saldo");
                if (saldo.compareTo(BigDecimal.ZERO) > 0) {
                    conta.depositar(saldo);
                }
                return conta;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta", e);
        }
    }
    
    public List<Conta> listarTodas() {
        List<Conta> contas = new ArrayList<>();
        String selectSQL = "SELECT * FROM contas";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getString("titular_nome"),
                    rs.getString("titular_cpf")
                );
                Conta conta = new Conta(rs.getInt("numero"), cliente);
                BigDecimal saldo = rs.getBigDecimal("saldo");
                if (saldo.compareTo(BigDecimal.ZERO) > 0) {
                    conta.depositar(saldo);
                }
                contas.add(conta);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas", e);
        }
        
        return contas;
    }
    
    public boolean excluir(int numero) {
        String deleteSQL = "DELETE FROM contas WHERE numero = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setInt(1, numero);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir conta", e);
        }
    }
}

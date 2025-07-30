package br.com.Igor.caixaeletronico.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity // Marca como entidade JPA
@Table(name = "contas") // Nome da tabela no banco
public class Conta {
    
    @Id // Chave primária
    @Column(name = "numero")
    private Integer numero;
    
    @NotNull(message = "Saldo não pode ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "Saldo não pode ser negativo")
    @Column(name = "saldo", precision = 10, scale = 2)
    private BigDecimal saldo;
    
    @Valid // Valida o objeto Cliente
    @Embedded // Incorpora Cliente nesta entidade
    private Cliente titular;
    
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    // Construtor padrão necessário para JPA
    public Conta() {
        this.saldo = BigDecimal.ZERO;
        this.dataCriacao = LocalDateTime.now();
    }

    public Conta(Integer numero, Cliente titular) {
        this();
        this.numero = numero;
        this.titular = titular;
    }

    // Operações bancárias
    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            this.saldo = this.saldo.add(valor);
        }
    }
    
    public void depositar(double valor) {
        depositar(BigDecimal.valueOf(valor));
    }

    public boolean sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(this.saldo) <= 0) {
            this.saldo = this.saldo.subtract(valor);
            return true;
        }
        return false;
    }
    
    public boolean sacar(double valor) {
        return sacar(BigDecimal.valueOf(valor));
    }

    public BigDecimal consultarSaldo() {
        return saldo;
    }
    
    // Método compatível com versão anterior
    public double consultarSaldo_OLD() {
        return saldo.doubleValue();
    }

    // Getters e Setters
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Cliente getTitular() {
        return titular;
    }
    
    public void setTitular(Cliente titular) {
        this.titular = titular;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", saldo=" + saldo +
                ", titular=" + titular.getNome() +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}


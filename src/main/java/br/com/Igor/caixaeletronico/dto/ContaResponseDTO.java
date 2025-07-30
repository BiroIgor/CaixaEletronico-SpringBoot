package br.com.Igor.caixaeletronico.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para resposta com dados da conta
 */
public class ContaResponseDTO {
    
    private Integer numero;
    private ClienteDTO titular;
    private BigDecimal saldo;
    private LocalDateTime dataCriacao;
    
    // Construtores
    public ContaResponseDTO() {}
    
    public ContaResponseDTO(Integer numero, ClienteDTO titular, BigDecimal saldo, LocalDateTime dataCriacao) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.dataCriacao = dataCriacao;
    }
    
    // Getters e Setters
    public Integer getNumero() {
        return numero;
    }
    
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public ClienteDTO getTitular() {
        return titular;
    }
    
    public void setTitular(ClienteDTO titular) {
        this.titular = titular;
    }
    
    public BigDecimal getSaldo() {
        return saldo;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}

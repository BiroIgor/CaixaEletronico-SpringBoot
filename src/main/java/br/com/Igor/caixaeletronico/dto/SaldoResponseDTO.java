package br.com.Igor.caixaeletronico.dto;

import java.math.BigDecimal;

/**
 * DTO para resposta de consulta de saldo
 */
public class SaldoResponseDTO {
    
    private Integer numero;
    private BigDecimal saldo;
    
    // Construtores
    public SaldoResponseDTO() {}
    
    public SaldoResponseDTO(Integer numero, BigDecimal saldo) {
        this.numero = numero;
        this.saldo = saldo;
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
}

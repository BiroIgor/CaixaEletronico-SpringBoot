package br.com.Igor.caixaeletronico.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * DTO para requisições de criação de conta
 */
public class ContaRequestDTO {
    
    @NotNull(message = "Número da conta é obrigatório")
    @Positive(message = "Número da conta deve ser positivo")
    private Integer numero;
    
    @NotNull(message = "Dados do titular são obrigatórios")
    @Valid
    private ClienteDTO titular;
    
    @DecimalMin(value = "0.0", inclusive = true, message = "Saldo inicial não pode ser negativo")
    private BigDecimal saldoInicial = BigDecimal.ZERO;
    
    // Construtores
    public ContaRequestDTO() {}
    
    public ContaRequestDTO(Integer numero, ClienteDTO titular, BigDecimal saldoInicial) {
        this.numero = numero;
        this.titular = titular;
        this.saldoInicial = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
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
    
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }
    
    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
}

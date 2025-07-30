package br.com.Igor.caixaeletronico.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO para operações bancárias (depósito/saque)
 */
public class OperacaoRequestDTO {
    
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal valor;
    
    // Construtores
    public OperacaoRequestDTO() {}
    
    public OperacaoRequestDTO(BigDecimal valor) {
        this.valor = valor;
    }
    
    // Getters e Setters
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}

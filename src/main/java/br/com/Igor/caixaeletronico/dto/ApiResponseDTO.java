package br.com.Igor.caixaeletronico.dto;

import java.time.LocalDateTime;

/**
 * DTO para resposta padronizada da API
 */
public class ApiResponseDTO<T> {
    
    private boolean sucesso;
    private String mensagem;
    private T dados;
    private LocalDateTime timestamp;
    
    // Construtores
    public ApiResponseDTO() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiResponseDTO(boolean sucesso, String mensagem, T dados) {
        this();
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dados = dados;
    }
    
    // Métodos estáticos para facilitar criação
    public static <T> ApiResponseDTO<T> sucesso(String mensagem, T dados) {
        return new ApiResponseDTO<>(true, mensagem, dados);
    }
    
    public static <T> ApiResponseDTO<T> erro(String mensagem) {
        return new ApiResponseDTO<>(false, mensagem, null);
    }
    
    public static <T> ApiResponseDTO<T> erro(String mensagem, T dados) {
        return new ApiResponseDTO<>(false, mensagem, dados);
    }
    
    // Getters e Setters
    public boolean isSucesso() {
        return sucesso;
    }
    
    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }
    
    public String getMensagem() {
        return mensagem;
    }
    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    
    public T getDados() {
        return dados;
    }
    
    public void setDados(T dados) {
        this.dados = dados;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

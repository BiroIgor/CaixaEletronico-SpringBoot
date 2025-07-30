package br.com.Igor.caixaeletronico.exception;

/**
 * Exceção lançada quando uma operação bancária é inválida
 */
public class OperacaoInvalidaException extends RuntimeException {
    
    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}

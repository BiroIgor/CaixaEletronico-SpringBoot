package br.com.Igor.caixaeletronico.exception;

/**
 * Exceção lançada quando uma conta não é encontrada
 */
public class ContaNaoEncontradaException extends RuntimeException {
    
    public ContaNaoEncontradaException(Integer numero) {
        super("Conta não encontrada: " + numero);
    }
    
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

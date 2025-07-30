package br.com.Igor.caixaeletronico.exception;

/**
 * Exceção lançada quando uma conta já existe
 */
public class ContaJaExisteException extends RuntimeException {
    
    public ContaJaExisteException(Integer numero) {
        super("Conta já existe: " + numero);
    }
    
    public ContaJaExisteException(String mensagem) {
        super(mensagem);
    }
}

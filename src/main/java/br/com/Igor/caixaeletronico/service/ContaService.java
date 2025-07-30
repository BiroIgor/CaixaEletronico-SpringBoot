package br.com.Igor.caixaeletronico.service;

import br.com.Igor.caixaeletronico.Conta;
import br.com.Igor.caixaeletronico.Cliente;
import br.com.Igor.caixaeletronico.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service contendo a lógica de negócio para operações bancárias
 * @Transactional garante que operações sejam executadas em transação
 */
@Service
@Transactional
public class ContaService {
    
    @Autowired
    private ContaRepository contaRepository;
    
    /**
     * Cria uma nova conta
     */
    public Conta criarConta(Integer numero, Cliente titular) {
        // Verifica se já existe conta com este número
        if (contaRepository.existsByNumero(numero)) {
            throw new IllegalArgumentException("Já existe uma conta com o número: " + numero);
        }
        
        // Verifica se já existe conta com este CPF
        if (contaRepository.existsByTitularCpf(titular.getCpf())) {
            throw new IllegalArgumentException("Já existe uma conta para o CPF: " + titular.getCpf());
        }
        
        Conta novaConta = new Conta(numero, titular);
        return contaRepository.save(novaConta);
    }
    
    /**
     * Busca conta por número
     */
    @Transactional(readOnly = true)
    public Optional<Conta> buscarConta(Integer numero) {
        return contaRepository.findById(numero);
    }
    
    /**
     * Busca conta por CPF do titular
     */
    @Transactional(readOnly = true)
    public Optional<Conta> buscarContaPorCpf(String cpf) {
        return contaRepository.findByTitularCpf(cpf);
    }
    
    /**
     * Lista todas as contas
     */
    @Transactional(readOnly = true)
    public List<Conta> listarTodasContas() {
        return contaRepository.findAll();
    }
    
    /**
     * Busca contas por nome do titular
     */
    @Transactional(readOnly = true)
    public List<Conta> buscarContasPorNome(String nome) {
        return contaRepository.findByTitularNomeContainingIgnoreCase(nome);
    }
    
    /**
     * Realiza depósito em uma conta
     */
    public Conta depositar(Integer numeroConta, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo");
        }
        
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + numeroConta));
        
        conta.depositar(valor);
        return contaRepository.save(conta);
    }
    
    /**
     * Realiza saque de uma conta
     */
    public Conta sacar(Integer numeroConta, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo");
        }
        
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + numeroConta));
        
        if (!conta.sacar(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente para o saque");
        }
        
        return contaRepository.save(conta);
    }
    
    /**
     * Atualiza dados do titular
     */
    public Conta atualizarTitular(Integer numeroConta, Cliente novoTitular) {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + numeroConta));
        
        conta.setTitular(novoTitular);
        return contaRepository.save(conta);
    }
    
    /**
     * Remove uma conta
     */
    public void removerConta(Integer numeroConta) {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + numeroConta));
        
        // Regra de negócio: só pode remover conta com saldo zero
        if (conta.getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Não é possível remover conta com saldo positivo");
        }
        
        contaRepository.delete(conta);
    }
    
    /**
     * Consulta saldo de uma conta
     */
    @Transactional(readOnly = true)
    public BigDecimal consultarSaldo(Integer numeroConta) {
        Conta conta = contaRepository.findById(numeroConta)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + numeroConta));
        
        return conta.getSaldo();
    }
    
    /**
     * Busca contas por faixa de saldo
     */
    @Transactional(readOnly = true)
    public List<Conta> buscarContasPorFaixaSaldo(BigDecimal saldoMin, BigDecimal saldoMax) {
        return contaRepository.findContasPorFaixaSaldo(saldoMin, saldoMax);
    }
    
    /**
     * Retorna estatísticas das contas
     */
    @Transactional(readOnly = true)
    public Long contarContasComSaldo() {
        return contaRepository.countContasComSaldo();
    }
}

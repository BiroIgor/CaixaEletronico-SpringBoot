package br.com.Igor.caixaeletronico.service;

import br.com.Igor.caixaeletronico.dto.ContaRequestDTO;
import br.com.Igor.caixaeletronico.dto.ContaResponseDTO;
import br.com.Igor.caixaeletronico.entity.Conta;
import br.com.Igor.caixaeletronico.exception.ContaJaExisteException;
import br.com.Igor.caixaeletronico.exception.ContaNaoEncontradaException;
import br.com.Igor.caixaeletronico.exception.OperacaoInvalidaException;
import br.com.Igor.caixaeletronico.mapper.ContaMapper;
import br.com.Igor.caixaeletronico.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service moderno para operações bancárias seguindo boas práticas
 */
@Service
@Transactional
public class ContaServiceV2 {
    
    private final ContaRepository contaRepository;
    private final ContaMapper contaMapper;
    
    @Autowired
    public ContaServiceV2(ContaRepository contaRepository, ContaMapper contaMapper) {
        this.contaRepository = contaRepository;
        this.contaMapper = contaMapper;
    }
    
    /**
     * Lista todas as contas
     */
    @Transactional(readOnly = true)
    public List<ContaResponseDTO> listarContas() {
        return contaRepository.findAll().stream()
                .map(contaMapper::toContaResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca conta por número
     */
    @Transactional(readOnly = true)
    public ContaResponseDTO buscarConta(Integer numero) {
        Conta conta = buscarContaEntity(numero);
        return contaMapper.toContaResponseDTO(conta);
    }
    
    /**
     * Cria nova conta
     */
    public ContaResponseDTO criarConta(ContaRequestDTO requestDTO) {
        // Verifica se conta já existe
        if (contaRepository.existsById(requestDTO.getNumero())) {
            throw new ContaJaExisteException(requestDTO.getNumero());
        }
        
        // Converte DTO para Entity e salva
        Conta conta = contaMapper.toConta(requestDTO);
        Conta contaSalva = contaRepository.save(conta);
        
        return contaMapper.toContaResponseDTO(contaSalva);
    }
    
    /**
     * Atualiza dados do titular
     */
    public ContaResponseDTO atualizarTitular(Integer numero, ContaRequestDTO requestDTO) {
        Conta conta = buscarContaEntity(numero);
        
        // Atualiza apenas os dados do titular
        conta.getTitular().setNome(requestDTO.getTitular().getNome());
        conta.getTitular().setCpf(requestDTO.getTitular().getCpf());
        
        Conta contaAtualizada = contaRepository.save(conta);
        return contaMapper.toContaResponseDTO(contaAtualizada);
    }
    
    /**
     * Realiza depósito
     */
    public ContaResponseDTO depositar(Integer numero, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoInvalidaException("Valor do depósito deve ser positivo");
        }
        
        Conta conta = buscarContaEntity(numero);
        conta.depositar(valor);
        
        Conta contaAtualizada = contaRepository.save(conta);
        return contaMapper.toContaResponseDTO(contaAtualizada);
    }
    
    /**
     * Realiza saque
     */
    public ContaResponseDTO sacar(Integer numero, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new OperacaoInvalidaException("Valor do saque deve ser positivo");
        }
        
        Conta conta = buscarContaEntity(numero);
        
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new OperacaoInvalidaException("Saldo insuficiente");
        }
        
        conta.sacar(valor);
        
        Conta contaAtualizada = contaRepository.save(conta);
        return contaMapper.toContaResponseDTO(contaAtualizada);
    }
    
    /**
     * Consulta saldo
     */
    @Transactional(readOnly = true)
    public BigDecimal consultarSaldo(Integer numero) {
        Conta conta = buscarContaEntity(numero);
        return conta.getSaldo();
    }
    
    /**
     * Exclui conta
     */
    public void excluirConta(Integer numero) {
        if (!contaRepository.existsById(numero)) {
            throw new ContaNaoEncontradaException(numero);
        }
        
        contaRepository.deleteById(numero);
    }
    
    /**
     * Método auxiliar para buscar conta entity
     */
    private Conta buscarContaEntity(Integer numero) {
        return contaRepository.findById(numero)
                .orElseThrow(() -> new ContaNaoEncontradaException(numero));
    }
}

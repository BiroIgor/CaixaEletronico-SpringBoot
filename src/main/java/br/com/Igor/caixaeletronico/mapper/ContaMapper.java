package br.com.Igor.caixaeletronico.mapper;

import br.com.Igor.caixaeletronico.dto.ClienteDTO;
import br.com.Igor.caixaeletronico.dto.ContaRequestDTO;
import br.com.Igor.caixaeletronico.dto.ContaResponseDTO;
import br.com.Igor.caixaeletronico.entity.Cliente;
import br.com.Igor.caixaeletronico.entity.Conta;
import org.springframework.stereotype.Component;

/**
 * Mapper para conversão entre Entities e DTOs
 */
@Component
public class ContaMapper {
    
    /**
     * Converte Cliente entity para ClienteDTO
     */
    public ClienteDTO toClienteDTO(Cliente cliente) {
        if (cliente == null) return null;
        
        return new ClienteDTO(cliente.getNome(), cliente.getCpf());
    }
    
    /**
     * Converte ClienteDTO para Cliente entity
     */
    public Cliente toCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) return null;
        
        return new Cliente(clienteDTO.getNome(), clienteDTO.getCpf());
    }
    
    /**
     * Converte Conta entity para ContaResponseDTO
     */
    public ContaResponseDTO toContaResponseDTO(Conta conta) {
        if (conta == null) return null;
        
        return new ContaResponseDTO(
            conta.getNumero(),
            toClienteDTO(conta.getTitular()),
            conta.getSaldo(),
            conta.getDataCriacao()
        );
    }
    
    /**
     * Converte ContaRequestDTO para Conta entity
     */
    public Conta toConta(ContaRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        
        Conta conta = new Conta(
            requestDTO.getNumero(),
            toCliente(requestDTO.getTitular())
        );
        
        if (requestDTO.getSaldoInicial() != null) {
            conta.depositar(requestDTO.getSaldoInicial());
        }
        
        return conta;
    }
}

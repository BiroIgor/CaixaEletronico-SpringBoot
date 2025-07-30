package br.com.Igor.caixaeletronico.repository;

import br.com.Igor.caixaeletronico.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository para operações de banco de dados da entidade Conta
 * O Spring Data JPA cria automaticamente a implementação desta interface
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
    
    /**
     * Busca conta pelo CPF do titular
     * Método automático do Spring Data JPA baseado no nome
     */
    Optional<Conta> findByTitularCpf(String cpf);
    
    /**
     * Busca contas por nome do titular (busca parcial, ignorando case)
     */
    List<Conta> findByTitularNomeContainingIgnoreCase(String nome);
    
    /**
     * Busca contas com saldo maior que um valor específico
     */
    List<Conta> findBySaldoGreaterThan(BigDecimal saldo);
    
    /**
     * Busca contas com saldo menor que um valor específico
     */
    List<Conta> findBySaldoLessThan(BigDecimal saldo);
    
    /**
     * Query customizada usando JPQL para buscar contas por faixa de saldo
     */
    @Query("SELECT c FROM Conta c WHERE c.saldo BETWEEN :saldoMin AND :saldoMax")
    List<Conta> findContasPorFaixaSaldo(@Param("saldoMin") BigDecimal saldoMin, 
                                       @Param("saldoMax") BigDecimal saldoMax);
    
    /**
     * Query nativa SQL para estatísticas
     */
    @Query(value = "SELECT COUNT(*) FROM contas WHERE saldo > 0", nativeQuery = true)
    Long countContasComSaldo();
    
    /**
     * Verifica se existe conta com determinado número
     */
    boolean existsByNumero(Integer numero);
    
    /**
     * Verifica se existe conta com determinado CPF
     */
    boolean existsByTitularCpf(String cpf);
}

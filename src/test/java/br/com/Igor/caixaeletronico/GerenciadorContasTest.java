package br.com.Igor.caixaeletronico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorContasTest {
    
    private GerenciadorContas gerenciador;
    private Cliente cliente1;
    private Cliente cliente2;
    private Conta conta1;
    private Conta conta2;
    
    @BeforeEach
    void setUp() {
        gerenciador = new GerenciadorContas();
        cliente1 = new Cliente("João Silva", "123.456.789-00");
        cliente2 = new Cliente("Maria Santos", "987.654.321-00");
        conta1 = new Conta(1001, cliente1);
        conta2 = new Conta(1002, cliente2);
    }
    
    @Test
    void testCriarConta() {
        // CREATE - Adicionar conta
        gerenciador.adicionarConta(conta1);
        
        // Verificar se a conta foi adicionada
        assertEquals(1, gerenciador.listarContas().size());
        assertEquals(conta1, gerenciador.buscarConta(1001));
    }
    
    @Test
    void testBuscarConta() {
        // READ - Buscar conta
        gerenciador.adicionarConta(conta1);
        gerenciador.adicionarConta(conta2);
        
        Conta contaEncontrada = gerenciador.buscarConta(1001);
        assertNotNull(contaEncontrada);
        assertEquals("João Silva", contaEncontrada.getTitular().getNome());
        
        // Testar busca de conta inexistente
        Conta contaInexistente = gerenciador.buscarConta(9999);
        assertNull(contaInexistente);
    }
    
    @Test
    void testAtualizarTitular() {
        // UPDATE - Atualizar titular
        gerenciador.adicionarConta(conta1);
        
        Cliente novoTitular = new Cliente("João Silva Santos", "123.456.789-00");
        boolean resultado = gerenciador.atualizarTitular(1001, novoTitular);
        
        assertTrue(resultado);
        assertEquals("João Silva Santos", gerenciador.buscarConta(1001).getTitular().getNome());
    }
    
    @Test
    void testRemoverConta() {
        // DELETE - Remover conta
        gerenciador.adicionarConta(conta1);
        gerenciador.adicionarConta(conta2);
        
        assertEquals(2, gerenciador.listarContas().size());
        
        boolean resultado = gerenciador.removerConta(1001);
        assertTrue(resultado);
        assertEquals(1, gerenciador.listarContas().size());
        assertNull(gerenciador.buscarConta(1001));
        
        // Testar remoção de conta inexistente
        boolean resultadoInexistente = gerenciador.removerConta(9999);
        assertFalse(resultadoInexistente);
    }
    
    @Test
    void testListarContas() {
        // LIST - Listar todas as contas
        assertTrue(gerenciador.listarContas().isEmpty());
        
        gerenciador.adicionarConta(conta1);
        gerenciador.adicionarConta(conta2);
        
        assertEquals(2, gerenciador.listarContas().size());
        assertTrue(gerenciador.listarContas().contains(conta1));
        assertTrue(gerenciador.listarContas().contains(conta2));
    }
}

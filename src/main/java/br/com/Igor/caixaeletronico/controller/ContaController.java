package br.com.Igor.caixaeletronico.controller;

import br.com.Igor.caixaeletronico.Conta;
import br.com.Igor.caixaeletronico.Cliente;
import br.com.Igor.caixaeletronico.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 🏦 API REST v1 - OPERAÇÕES BANCÁRIAS BÁSICAS
 * Controller REST para operações bancárias básicas
 */
@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*")
@Tag(name = "Contas Bancárias (v1)", description = "API básica para operações CRUD de contas bancárias")
public class ContaController {

    @Autowired
    private ContaService contaService;

    /**
     * GET /api/contas - Lista todas as contas
     */
    @Operation(
        summary = "📋 Listar todas as contas",
        description = "Retorna uma lista com todas as contas bancárias cadastradas no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de contas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = Conta.class)))
    })
    @GetMapping
    public ResponseEntity<List<Conta>> listarTodas() {
        List<Conta> contas = contaService.listarTodasContas();
        return ResponseEntity.ok(contas);
    }

    /**
     * GET /api/contas/{numero} - Busca conta por número
     */
    @Operation(
        summary = "🔍 Buscar conta por número",
        description = "Busca uma conta específica pelo número identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Conta encontrada"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{numero}")
    public ResponseEntity<Conta> buscarPorNumero(
            @Parameter(description = "Número da conta", example = "12345") 
            @PathVariable Integer numero) {
        Optional<Conta> conta = contaService.buscarConta(numero);
        return conta.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/contas - Cria nova conta
     */
    @Operation(
        summary = "➕ Criar nova conta",
        description = "Cria uma nova conta bancária com os dados do titular"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou conta já existente")
    })
    @PostMapping
    public ResponseEntity<Conta> criarConta(@Valid @RequestBody ContaRequest request) {
        try {
            Conta contaCriada = contaService.criarConta(request.getNumero(), request.getTitular());
            return ResponseEntity.status(HttpStatus.CREATED).body(contaCriada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/contas/{numero}/depositar - Realizar depósito
     * Body: { "valor": 100.50 }
     */
    @PutMapping("/{numero}/depositar")
    public ResponseEntity<String> depositar(@PathVariable Integer numero, 
                                          @RequestBody OperacaoRequest request) {
        try {
            contaService.depositar(numero, request.getValor());
            return ResponseEntity.ok("Depósito realizado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * PUT /api/contas/{numero}/sacar - Realizar saque
     * Body: { "valor": 50.00 }
     */
    @PutMapping("/{numero}/sacar")
    public ResponseEntity<String> sacar(@PathVariable Integer numero, 
                                      @RequestBody OperacaoRequest request) {
        try {
            contaService.sacar(numero, request.getValor());
            return ResponseEntity.ok("Saque realizado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * GET /api/contas/{numero}/saldo - Consultar saldo
     */
    @GetMapping("/{numero}/saldo")
    public ResponseEntity<SaldoResponse> consultarSaldo(@PathVariable Integer numero) {
        try {
            Optional<Conta> conta = contaService.buscarConta(numero);
            if (conta.isPresent()) {
                SaldoResponse response = new SaldoResponse(
                    conta.get().getNumero(),
                    conta.get().getSaldo()
                );
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/contas/{numero} - Excluir conta
     */
    @DeleteMapping("/{numero}")
    public ResponseEntity<String> excluirConta(@PathVariable Integer numero) {
        try {
            contaService.removerConta(numero);
            return ResponseEntity.ok("Conta excluída com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Classes para requests/responses
    public static class ContaRequest {
        private Integer numero;
        private Cliente titular;
        
        public Integer getNumero() { return numero; }
        public void setNumero(Integer numero) { this.numero = numero; }
        public Cliente getTitular() { return titular; }
        public void setTitular(Cliente titular) { this.titular = titular; }
    }

    public static class OperacaoRequest {
        private BigDecimal valor;
        
        public BigDecimal getValor() { return valor; }
        public void setValor(BigDecimal valor) { this.valor = valor; }
    }

    public static class SaldoResponse {
        private Integer numero;
        private BigDecimal saldo;
        
        public SaldoResponse(Integer numero, BigDecimal saldo) {
            this.numero = numero;
            this.saldo = saldo;
        }
        
        public Integer getNumero() { return numero; }
        public BigDecimal getSaldo() { return saldo; }
    }
}

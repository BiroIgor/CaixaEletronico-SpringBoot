package br.com.Igor.caixaeletronico.controller;

import br.com.Igor.caixaeletronico.entity.Conta;
import br.com.Igor.caixaeletronico.entity.Cliente;
import br.com.Igor.caixaeletronico.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 🏦 API REST v2 - OPERAÇÕES BANCÁRIAS AVANÇADAS
 * 
 * Este controller implementa um CRUD completo com:
 * - Operações bancárias (depósito, saque)
 * - Validações automáticas
 * - Tratamento de erros
 * - Respostas padronizadas
 * - Documentação Swagger completa
 */
@RestController
@RequestMapping("/api/v2/contas")
@CrossOrigin(origins = "*")
@Tag(name = "Contas Bancárias (v2)", description = "🚀 API avançada com respostas estruturadas e tratamento completo de erros")
public class ContaControllerAPI {

    @Autowired
    private ContaService contaService;

    // ==================== OPERAÇÕES CRUD ====================

    /**
     * 📋 GET /api/v2/contas - LISTAR TODAS AS CONTAS
     */
    @Operation(
        summary = "📋 Listar todas as contas (v2)",
        description = "Retorna uma lista estruturada com todas as contas bancárias, incluindo tratamento de erros"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Contas listadas com sucesso", 
                          "dados": [
                            {
                              "numero": 12345,
                              "saldo": 1000.50,
                              "titular": {
                                "nome": "João Silva",
                                "cpf": "12345678901"
                              }
                            }
                          ]
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<Conta>>> listarTodas() {
        try {
            List<Conta> contas = contaService.listarTodasContas();
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Contas listadas com sucesso", contas)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao listar contas: " + e.getMessage(), null));
        }
    }

    /**
     * 🔍 GET /api/v2/contas/{numero} - BUSCAR CONTA POR NÚMERO
     */
    @Operation(
        summary = "🔍 Buscar conta por número (v2)",
        description = "Busca uma conta específica pelo seu número único"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Conta encontrada com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Conta encontrada",
                          "dados": {
                            "numero": 12345,
                            "saldo": 1000.50,
                            "titular": {
                              "nome": "João Silva",
                              "cpf": "12345678901"
                            }
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Conta não encontrada",
                          "dados": null
                        }
                        """)))
    })
    @GetMapping("/{numero}")
    public ResponseEntity<ApiResponse<Conta>> buscarPorNumero(
            @Parameter(description = "Número único da conta", example = "12345")
            @PathVariable Integer numero) {
        try {
            Optional<Conta> conta = contaService.buscarConta(numero);
            if (conta.isPresent()) {
                return ResponseEntity.ok(
                    new ApiResponse<>(true, "Conta encontrada", conta.get())
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, "Conta não encontrada com número: " + numero, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao buscar conta: " + e.getMessage(), null));
        }
    }

    /**
     * ➕ POST /api/v2/contas - CRIAR NOVA CONTA
     */
    @Operation(
        summary = "➕ Criar nova conta (v2)",
        description = "Cria uma nova conta bancária com dados do titular e número único"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Conta criada com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Conta criada com sucesso",
                          "dados": {
                            "numero": 12345,
                            "saldo": 0.0,
                            "titular": {
                              "nome": "João Silva",
                              "cpf": "12345678901"
                            }
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Conta com este número já existe",
                          "dados": null
                        }
                        """)))
    })
    @PostMapping
    public ResponseEntity<ApiResponse<Conta>> criarConta(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Dados para criação da conta",
                content = @Content(examples = @ExampleObject(value = """
                    {
                      "numero": 12345,
                      "titular": {
                        "nome": "João Silva",
                        "cpf": "12345678901"
                      }
                    }
                    """))
            )
            @Valid @RequestBody ContaRequest request) {
        try {
            Conta novaConta = contaService.criarConta(request.getNumero(), request.getTitular());
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Conta criada com sucesso", novaConta));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro interno ao criar conta: " + e.getMessage(), null));
        }
    }

    /**
     * ✏️ PUT /api/v2/contas/{numero} - ATUALIZAR DADOS DO TITULAR
     */
    @Operation(
        summary = "✏️ Atualizar dados do titular (v2)",
        description = "Atualiza os dados do titular de uma conta existente"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Titular atualizado com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Dados do titular atualizados com sucesso",
                          "dados": {
                            "numero": 12345,
                            "saldo": 1000.50,
                            "titular": {
                              "nome": "João Silva Santos",
                              "cpf": "12345678901"
                            }
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Conta não encontrada",
                          "dados": null
                        }
                        """)))
    })
    @PutMapping("/{numero}")
    public ResponseEntity<ApiResponse<Conta>> atualizarTitular(
            @Parameter(description = "Número único da conta", example = "12345")
            @PathVariable Integer numero, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Novos dados do titular",
                content = @Content(examples = @ExampleObject(value = """
                    {
                      "nome": "João Silva Santos",
                      "cpf": "12345678901"
                    }
                    """))
            )
            @Valid @RequestBody Cliente novoTitular) {
        try {
            Conta contaAtualizada = contaService.atualizarTitular(numero, novoTitular);
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Dados do titular atualizados com sucesso", contaAtualizada)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao atualizar titular: " + e.getMessage(), null));
        }
    }

    /**
     * 🗑️ DELETE /api/v2/contas/{numero} - EXCLUIR CONTA
     */
    @Operation(
        summary = "🗑️ Excluir conta (v2)",
        description = "Remove uma conta do sistema permanentemente"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Conta excluída com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Conta excluída com sucesso",
                          "dados": "Conta 12345 removida"
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Conta não encontrada",
                          "dados": null
                        }
                        """)))
    })
    @DeleteMapping("/{numero}")
    public ResponseEntity<ApiResponse<String>> excluirConta(
            @Parameter(description = "Número único da conta a ser excluída", example = "12345")
            @PathVariable Integer numero) {
        try {
            contaService.removerConta(numero);
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Conta excluída com sucesso", "Conta " + numero + " removida")
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao excluir conta: " + e.getMessage(), null));
        }
    }

    // ==================== OPERAÇÕES BANCÁRIAS ====================

    /**
     * 💰 POST /api/v2/contas/{numero}/depositar - REALIZAR DEPÓSITO
     */
    @Operation(
        summary = "💰 Realizar depósito (v2)",
        description = "Adiciona valor ao saldo de uma conta específica"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Depósito de R$ 500.00 realizado com sucesso",
                          "dados": {
                            "numero": 12345,
                            "saldo": 1500.50,
                            "titular": {
                              "nome": "João Silva",
                              "cpf": "12345678901"
                            }
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Valor inválido",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Valor deve ser positivo",
                          "dados": null
                        }
                        """)))
    })
    @PostMapping("/{numero}/depositar")
    public ResponseEntity<ApiResponse<Conta>> depositar(
            @Parameter(description = "Número único da conta", example = "12345")
            @PathVariable Integer numero, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Valor do depósito",
                content = @Content(examples = @ExampleObject(value = """
                    {
                      "valor": 500.00
                    }
                    """))
            )
            @Valid @RequestBody OperacaoRequest request) {
        try {
            Conta conta = contaService.depositar(numero, request.getValor());
            return ResponseEntity.ok(
                new ApiResponse<>(true, 
                    String.format("Depósito de R$ %.2f realizado com sucesso", request.getValor()), 
                    conta)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao realizar depósito: " + e.getMessage(), null));
        }
    }

    /**
     * 💸 POST /api/v2/contas/{numero}/sacar - REALIZAR SAQUE
     */
    @Operation(
        summary = "💸 Realizar saque (v2)",
        description = "Remove valor do saldo de uma conta específica"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Saque realizado com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Saque de R$ 200.00 realizado com sucesso",
                          "dados": {
                            "numero": 12345,
                            "saldo": 800.50,
                            "titular": {
                              "nome": "João Silva",
                              "cpf": "12345678901"
                            }
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Saldo insuficiente ou valor inválido",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Saldo insuficiente",
                          "dados": null
                        }
                        """)))
    })
    @PostMapping("/{numero}/sacar")
    public ResponseEntity<ApiResponse<Conta>> sacar(
            @Parameter(description = "Número único da conta", example = "12345")
            @PathVariable Integer numero, 
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Valor do saque",
                content = @Content(examples = @ExampleObject(value = """
                    {
                      "valor": 200.00
                    }
                    """))
            )
            @Valid @RequestBody OperacaoRequest request) {
        try {
            Conta conta = contaService.sacar(numero, request.getValor());
            return ResponseEntity.ok(
                new ApiResponse<>(true, 
                    String.format("Saque de R$ %.2f realizado com sucesso", request.getValor()), 
                    conta)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao realizar saque: " + e.getMessage(), null));
        }
    }

    /**
     * 💳 GET /api/v2/contas/{numero}/saldo - CONSULTAR SALDO
     */
    @Operation(
        summary = "💳 Consultar saldo (v2)",
        description = "Retorna o saldo atual de uma conta específica"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Saldo consultado com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Saldo consultado com sucesso",
                          "dados": {
                            "numero": 12345,
                            "saldo": 1000.50
                          }
                        }
                        """))),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Conta não encontrada",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": false,
                          "mensagem": "Conta não encontrada",
                          "dados": null
                        }
                        """)))
    })
    @GetMapping("/{numero}/saldo")
    public ResponseEntity<ApiResponse<SaldoResponse>> consultarSaldo(
            @Parameter(description = "Número único da conta", example = "12345")
            @PathVariable Integer numero) {
        try {
            BigDecimal saldo = contaService.consultarSaldo(numero);
            SaldoResponse response = new SaldoResponse(numero, saldo);
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Saldo consultado com sucesso", response)
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao consultar saldo: " + e.getMessage(), null));
        }
    }

    // ==================== ENDPOINTS ESPECIAIS ====================

    /**
     * 📊 GET /api/v2/contas/estatisticas - ESTATÍSTICAS DO SISTEMA
     */
    @Operation(
        summary = "📊 Estatísticas do sistema (v2)",
        description = "Retorna estatísticas gerais das contas do sistema bancário"
    )
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso",
                    content = @Content(examples = @ExampleObject(value = """
                        {
                          "sucesso": true,
                          "mensagem": "Estatísticas obtidas com sucesso",
                          "dados": {
                            "totalContas": 5,
                            "contasComSaldo": 4,
                            "saldoTotal": 10500.75
                          }
                        }
                        """)))
    })
    @GetMapping("/estatisticas")
    public ResponseEntity<ApiResponse<EstatisticasResponse>> obterEstatisticas() {
        try {
            List<Conta> todasContas = contaService.listarTodasContas();
            Long totalContas = (long) todasContas.size();
            Long contasComSaldo = contaService.contarContasComSaldo();
            
            BigDecimal saldoTotal = todasContas.stream()
                .map(Conta::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            EstatisticasResponse stats = new EstatisticasResponse(
                totalContas, contasComSaldo, saldoTotal
            );
            
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Estatísticas obtidas com sucesso", stats)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Erro ao obter estatísticas: " + e.getMessage(), null));
        }
    }

    // ==================== CLASSES DE RESPOSTA ====================

    /**
     * Classe padrão para respostas da API
     */
    public static class ApiResponse<T> {
        private boolean sucesso;
        private String mensagem;
        private T dados;

        public ApiResponse(boolean sucesso, String mensagem, T dados) {
            this.sucesso = sucesso;
            this.mensagem = mensagem;
            this.dados = dados;
        }

        // Getters
        public boolean isSucesso() { return sucesso; }
        public String getMensagem() { return mensagem; }
        public T getDados() { return dados; }
    }

    /**
     * Request para criar conta
     */
    public static class ContaRequest {
        private Integer numero;
        private Cliente titular;

        public Integer getNumero() { return numero; }
        public void setNumero(Integer numero) { this.numero = numero; }
        public Cliente getTitular() { return titular; }
        public void setTitular(Cliente titular) { this.titular = titular; }
    }

    /**
     * Request para operações financeiras
     */
    public static class OperacaoRequest {
        private BigDecimal valor;

        public BigDecimal getValor() { return valor; }
        public void setValor(BigDecimal valor) { this.valor = valor; }
    }

    /**
     * Response para consulta de saldo
     */
    public static class SaldoResponse {
        private Integer numeroConta;
        private BigDecimal saldo;

        public SaldoResponse(Integer numeroConta, BigDecimal saldo) {
            this.numeroConta = numeroConta;
            this.saldo = saldo;
        }

        public Integer getNumeroConta() { return numeroConta; }
        public BigDecimal getSaldo() { return saldo; }
    }

    /**
     * Response para estatísticas
     */
    public static class EstatisticasResponse {
        private Long totalContas;
        private Long contasComSaldo;
        private BigDecimal saldoTotal;

        public EstatisticasResponse(Long totalContas, Long contasComSaldo, BigDecimal saldoTotal) {
            this.totalContas = totalContas;
            this.contasComSaldo = contasComSaldo;
            this.saldoTotal = saldoTotal;
        }

        public Long getTotalContas() { return totalContas; }
        public Long getContasComSaldo() { return contasComSaldo; }
        public BigDecimal getSaldoTotal() { return saldoTotal; }
    }
}

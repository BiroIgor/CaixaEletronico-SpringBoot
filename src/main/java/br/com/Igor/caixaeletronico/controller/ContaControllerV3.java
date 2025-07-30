package br.com.Igor.caixaeletronico.controller;

import br.com.Igor.caixaeletronico.dto.*;
import br.com.Igor.caixaeletronico.service.ContaServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller moderno seguindo boas práticas REST
 */
@RestController
@RequestMapping("/api/v3/contas")
@CrossOrigin(origins = "*")
@Tag(name = "Contas V3", description = "API moderna para gerenciamento de contas bancárias")
public class ContaControllerV3 {
    
    private final ContaServiceV2 contaService;
    
    @Autowired
    public ContaControllerV3(ContaServiceV2 contaService) {
        this.contaService = contaService;
    }
    
    @Operation(summary = "Listar todas as contas", description = "Retorna lista de todas as contas cadastradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de contas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<ContaResponseDTO>>> listarContas() {
        List<ContaResponseDTO> contas = contaService.listarContas();
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Contas listadas com sucesso", contas)
        );
    }
    
    @Operation(summary = "Buscar conta por número", description = "Retorna dados de uma conta específica")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conta encontrada"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{numero}")
    public ResponseEntity<ApiResponseDTO<ContaResponseDTO>> buscarConta(
            @Parameter(description = "Número da conta", required = true)
            @PathVariable Integer numero) {
        
        ContaResponseDTO conta = contaService.buscarConta(numero);
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Conta encontrada", conta)
        );
    }
    
    @Operation(summary = "Criar nova conta", description = "Cria uma nova conta bancária")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Conta criada com sucesso"),
        @ApiResponse(responseCode = "409", description = "Conta já existe"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDTO<ContaResponseDTO>> criarConta(
            @Valid @RequestBody ContaRequestDTO requestDTO) {
        
        ContaResponseDTO conta = contaService.criarConta(requestDTO);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponseDTO.sucesso("Conta criada com sucesso", conta));
    }
    
    @Operation(summary = "Atualizar titular", description = "Atualiza dados do titular da conta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Titular atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{numero}")
    public ResponseEntity<ApiResponseDTO<ContaResponseDTO>> atualizarTitular(
            @Parameter(description = "Número da conta", required = true)
            @PathVariable Integer numero,
            @Valid @RequestBody ContaRequestDTO requestDTO) {
        
        ContaResponseDTO conta = contaService.atualizarTitular(numero, requestDTO);
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Titular atualizado com sucesso", conta)
        );
    }
    
    @Operation(summary = "Realizar depósito", description = "Deposita valor na conta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
        @ApiResponse(responseCode = "400", description = "Valor inválido")
    })
    @PostMapping("/{numero}/depositar")
    public ResponseEntity<ApiResponseDTO<ContaResponseDTO>> depositar(
            @Parameter(description = "Número da conta", required = true)
            @PathVariable Integer numero,
            @Valid @RequestBody OperacaoRequestDTO operacaoDTO) {
        
        ContaResponseDTO conta = contaService.depositar(numero, operacaoDTO.getValor());
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Depósito realizado com sucesso", conta)
        );
    }
    
    @Operation(summary = "Realizar saque", description = "Saca valor da conta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saque realizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada"),
        @ApiResponse(responseCode = "400", description = "Valor inválido ou saldo insuficiente")
    })
    @PostMapping("/{numero}/sacar")
    public ResponseEntity<ApiResponseDTO<ContaResponseDTO>> sacar(
            @Parameter(description = "Número da conta", required = true)
            @PathVariable Integer numero,
            @Valid @RequestBody OperacaoRequestDTO operacaoDTO) {
        
        ContaResponseDTO conta = contaService.sacar(numero, operacaoDTO.getValor());
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Saque realizado com sucesso", conta)
        );
    }
    
    @Operation(summary = "Consultar saldo", description = "Consulta saldo atual da conta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Saldo consultado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @GetMapping("/{numero}/saldo")
    public ResponseEntity<ApiResponseDTO<BigDecimal>> consultarSaldo(
            @Parameter(description = "Número da conta", required = true)
            @PathVariable Integer numero) {
        
        BigDecimal saldo = contaService.consultarSaldo(numero);
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Saldo consultado com sucesso", saldo)
        );
    }
    
    @Operation(summary = "Excluir conta", description = "Exclui uma conta do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conta excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    @DeleteMapping("/{numero}")
    public ResponseEntity<ApiResponseDTO<Object>> excluirConta(
            @Parameter(description = "Número da conta", required = true)
            @PathVariable Integer numero) {
        
        contaService.excluirConta(numero);
        return ResponseEntity.ok(
            ApiResponseDTO.sucesso("Conta excluída com sucesso", null)
        );
    }
}

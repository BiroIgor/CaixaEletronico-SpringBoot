package br.com.Igor.caixaeletronico.exception;

import br.com.Igor.caixaeletronico.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler global para tratamento de exceções da API
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Trata exceção de conta não encontrada
     */
    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleContaNaoEncontrada(ContaNaoEncontradaException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDTO.erro(ex.getMessage()));
    }
    
    /**
     * Trata exceção de operação inválida
     */
    @ExceptionHandler(OperacaoInvalidaException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleOperacaoInvalida(OperacaoInvalidaException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.erro(ex.getMessage()));
    }
    
    /**
     * Trata exceção de conta já existente
     */
    @ExceptionHandler(ContaJaExisteException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleContaJaExiste(ContaJaExisteException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponseDTO.erro(ex.getMessage()));
    }
    
    /**
     * Trata erros de validação
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDTO.erro("Dados inválidos", errors));
    }
    
    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDTO.erro("Erro interno do servidor: " + ex.getMessage()));
    }
}

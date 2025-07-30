package br.com.Igor.caixaeletronico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Classe principal da aplicação Spring Boot
 * 
 * @SpringBootApplication combina as anotações:
 * - @Configuration: Esta classe contém configurações de beans
 * - @EnableAutoConfiguration: Habilita a configuração automática do Spring Boot
 * - @ComponentScan: Escaneia componentes no pacote atual e subpacotes
 * 
 * @EnableTransactionManagement: Habilita o gerenciamento de transações
 */
@SpringBootApplication
@EnableTransactionManagement
public class CaixaEletronicoApplication {

    public static void main(String[] args) {
        System.out.println("🏦 Iniciando Caixa Eletrônico Spring Boot...");
        
        // Inicia a aplicação Spring Boot
        SpringApplication.run(CaixaEletronicoApplication.class, args);
        
        System.out.println("✅ Aplicação iniciada com sucesso!");
        System.out.println("🌐 Acesse: http://localhost:8080");
        System.out.println("📋 API Docs: http://localhost:8080/api/contas");
        System.out.println("💾 Console H2: http://localhost:8080/h2-console");
        System.out.println("   - JDBC URL: jdbc:h2:mem:caixadb");
        System.out.println("   - Username: sa");
        System.out.println("   - Password: (deixar vazio)");
    }
}

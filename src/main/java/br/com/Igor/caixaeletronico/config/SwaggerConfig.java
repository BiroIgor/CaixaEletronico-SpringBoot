package br.com.Igor.caixaeletronico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 📚 CONFIGURAÇÃO DO SWAGGER/OPENAPI 3
 * 
 * Esta classe configura a documentação automática das APIs
 * Acesse: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🏦 Sistema Caixa Eletrônico - API REST")
                        .version("2.0.0")
                        .description("""
                                ## 📋 Sistema Bancário Completo
                                
                                Esta API oferece operações bancárias completas com:
                                
                                ### ✅ Funcionalidades Principais:
                                - **CRUD Completo** de contas bancárias
                                - **Operações Financeiras**: Depósito, Saque, Consulta de saldo
                                - **Validações Automáticas**: CPF, valores, saldo
                                - **Persistência JPA**: Banco H2 em memória
                                - **Arquitetura em Camadas**: Controller → Service → Repository
                                
                                ### 🔧 Tecnologias:
                                - Spring Boot 3.1.0
                                - Spring Data JPA
                                - H2 Database
                                - Bean Validation
                                - BigDecimal (precisão monetária)
                                
                                ### 📊 Endpoints Disponíveis:
                                - **API v1**: `/api/contas` - Versão básica
                                - **API v2**: `/api/v2/contas` - Versão avançada com respostas estruturadas
                                
                                ### 💾 Console H2:
                                - URL: http://localhost:8080/h2-console
                                - JDBC: `jdbc:h2:mem:caixadb`
                                - User: `sa` | Password: *(vazio)*
                                """)
                        .contact(new Contact()
                                .name("Igor - Desenvolvedor")
                                .email("igor@exemplo.com")
                                .url("https://github.com/igor"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local de Desenvolvimento"),
                        new Server()
                                .url("https://api.caixaeletronico.com")
                                .description("Servidor de Produção (Exemplo)")));
    }
}

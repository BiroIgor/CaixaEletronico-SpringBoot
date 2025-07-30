package br.com.Igor.caixaeletronico.ui;

import br.com.Igor.caixaeletronico.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.GraphicsEnvironment;

/**
 * 🏭 Factory de Configuração para Interface Gráfica Swing
 * 
 * Esta configuração cria condicionalmente os componentes de UI 
 * apenas quando o ambiente gráfico está disponível.
 */
@Configuration
@ConditionalOnProperty(name = "swing.ui.enabled", havingValue = "true", matchIfMissing = false)
public class SwingUIConfiguration {

    @Autowired
    private ContaService contaService;

    @Bean
    public CaixaEletronicoUI caixaEletronicoUI() {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("⚠️ Ambiente headless detectado - UI Swing não será criada");
            return null; // Não criar o bean em ambiente headless
        }
        
        System.out.println("🖥️ Criando Interface Gráfica Swing...");
        CaixaEletronicoUI ui = new CaixaEletronicoUI();
        ui.setContaService(contaService); // Injeção manual do serviço
        return ui;
    }
}

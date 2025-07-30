package br.com.Igor.caixaeletronico.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;

/**
 * 🚀 Inicializador da Interface Gráfica Swing
 * 
 * Esta classe integra a UI Swing com o Spring Boot,
 * permitindo que a interface gráfica seja iniciada
 * junto com a aplicação web.
 */
@Component
@ConditionalOnProperty(name = "swing.ui.enabled", havingValue = "true", matchIfMissing = false)
public class SwingUIInitializer implements CommandLineRunner {

    @Autowired(required = false)
    private CaixaEletronicoUI caixaEletronicoUI;

    @Override
    public void run(String... args) throws Exception {
        // Só executa se estivermos em um ambiente gráfico
        if (!GraphicsEnvironment.isHeadless() && caixaEletronicoUI != null) {
            System.out.println("🖥️ Iniciando Interface Gráfica Swing...");
            
            SwingUtilities.invokeLater(() -> {
                try {
                    caixaEletronicoUI.mostrar();
                    System.out.println("✅ Interface Gráfica iniciada com sucesso!");
                } catch (Exception e) {
                    System.err.println("❌ Erro ao iniciar UI Swing: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else if (GraphicsEnvironment.isHeadless()) {
            System.out.println("⚠️ Ambiente headless detectado - Interface Gráfica não será exibida");
        }
    }
}

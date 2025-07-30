package br.com.Igor.caixaeletronico;

import br.com.Igor.caixaeletronico.ui.CaixaEletronicoUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principal preparada especialmente para rodar no NetBeans
 * com interface gráfica Swing
 */
@SpringBootApplication
public class CaixaEletronicoNetBeans {

    public static void main(String[] args) {
        // Configurar para interface gráfica
        System.setProperty("java.awt.headless", "false");
        System.setProperty("spring.profiles.active", "ui");
        System.setProperty("java.awt.Window.locationByPlatform", "true");
        
        // Detectar se estamos em ambiente gráfico
        boolean isGraphicalEnvironment = !GraphicsEnvironment.isHeadless();
        
        System.out.println("=== CAIXA ELETRÔNICO - NetBeans Edition ===");
        System.out.println("Ambiente gráfico disponível: " + isGraphicalEnvironment);
        
        if (isGraphicalEnvironment) {
            // Configurar Look and Feel moderno
            configurarLookAndFeel();
            
            // Inicializar Spring Boot em thread separada
            SwingUtilities.invokeLater(() -> {
                try {
                    // Inicializar Spring Boot
                    ConfigurableApplicationContext context = SpringApplication.run(CaixaEletronicoNetBeans.class, args);
                    
                    // Criar e exibir UI
                    CaixaEletronicoUI ui = context.getBean(CaixaEletronicoUI.class);
                    ui.setVisible(true);
                    
                    System.out.println("✅ Interface gráfica iniciada com sucesso!");
                    System.out.println("💡 Swagger UI: http://localhost:8080/swagger-ui.html");
                    
                } catch (Exception e) {
                    System.err.println("❌ Erro ao inicializar interface gráfica: " + e.getMessage());
                    e.printStackTrace();
                    
                    // Fallback para modo console
                    iniciarModoConsole(args);
                }
            });
        } else {
            System.out.println("⚠️  Ambiente sem interface gráfica detectado. Iniciando modo console...");
            iniciarModoConsole(args);
        }
    }
    
    private static void configurarLookAndFeel() {
        try {
            // Tentar FlatLaf primeiro
            try {
                Class.forName("com.formdev.flatlaf.FlatIntelliJLaf");
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
                System.out.println("✅ FlatLaf IntelliJ Look and Feel aplicado");
            } catch (ClassNotFoundException e) {
                // Fallback para Look and Feel padrão
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Metal".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
                System.out.println("✅ Look and Feel padrão aplicado");
            }
            
            // Configurações adicionais do Swing
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            
        } catch (Exception e) {
            System.err.println("⚠️  Erro ao configurar Look and Feel: " + e.getMessage());
        }
    }
    
    private static void iniciarModoConsole(String[] args) {
        // Desabilitar UI e iniciar apenas Spring Boot
        System.setProperty("java.awt.headless", "true");
        System.setProperty("spring.profiles.active", "console");
        
        try {
            SpringApplication.run(CaixaEletronicoNetBeans.class, args);
            System.out.println("✅ Aplicação iniciada em modo console");
            System.out.println("🌐 Acesse: http://localhost:8080/swagger-ui.html");
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

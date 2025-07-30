package br.com.Igor.caixaeletronico;

import java.util.Scanner;

public class MenuCaixa {
    private Scanner scanner = new Scanner(System.in);
    private GerenciadorContas gerenciador = new GerenciadorContas();

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Criar nova conta");
            System.out.println("2 - Listar contas");
            System.out.println("3 - Consultar saldo");
            System.out.println("4 - Depositar");
            System.out.println("5 - Sacar");
            System.out.println("6 - Atualizar titular");
            System.out.println("7 - Excluir conta");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    listarContas();
                    break;
                case 3:
                    consultarSaldo();
                    break;
                case 4:
                    depositar();
                    break;
                case 5:
                    sacar();
                    break;
                case 6:
                    atualizarTitular();
                    break;
                case 7:
                    excluirConta();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    // IMPLEMENTAÇÕES DOS MÉTODOS
    private void criarConta() {
        System.out.print("Nome do cliente: ");
        scanner.nextLine(); // limpa buffer
        String nome = scanner.nextLine();
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();

        Cliente cliente = new Cliente(nome, cpf);
        Conta conta = new Conta(numero, cliente);
        gerenciador.adicionarConta(conta);
        System.out.println("Conta criada com sucesso!");
    }

    private void listarContas() {
        for (Conta c : gerenciador.listarContas()) {
            System.out.println("Conta: " + c.getNumero() + ", Titular: " + c.getTitular().getNome());
        }
    }

    private void consultarSaldo() {
        Conta conta = obterContaPorNumero();
        if (conta != null) {
            System.out.println("Saldo: R$" + conta.consultarSaldo());
        }
    }

    private void depositar() {
        Conta conta = obterContaPorNumero();
        if (conta != null) {
            System.out.print("Valor do depósito: ");
            double valor = scanner.nextDouble();
            conta.depositar(valor);
            System.out.println("Depósito realizado!");
        }
    }

    private void sacar() {
        Conta conta = obterContaPorNumero();
        if (conta != null) {
            System.out.print("Valor do saque: ");
            double valor = scanner.nextDouble();
            if (conta.sacar(valor)) {
                System.out.println("Saque realizado!");
            } else {
                System.out.println("Saldo insuficiente!");
            }
        }
    }

    private void atualizarTitular() {
        Conta conta = obterContaPorNumero();
        if (conta != null) {
            scanner.nextLine(); // limpar buffer
            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();
            System.out.print("Novo CPF: ");
            String cpf = scanner.nextLine();
            Cliente novoCliente = new Cliente(nome, cpf);
            if (gerenciador.atualizarTitular(conta.getNumero(), novoCliente)) {
                System.out.println("Titular atualizado!");
            }
        }
    }

    private void excluirConta() {
        Conta conta = obterContaPorNumero();
        if (conta != null && gerenciador.removerConta(conta.getNumero())) {
            System.out.println("Conta removida!");
        }
    }

    private Conta obterContaPorNumero() {
        System.out.print("Informe o número da conta: ");
        int numero = scanner.nextInt();
        Conta conta = gerenciador.buscarConta(numero);
        if (conta == null) {
            System.out.println("Conta não encontrada!");
        }
        return conta;
    }
}

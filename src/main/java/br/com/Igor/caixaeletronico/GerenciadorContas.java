package br.com.Igor.caixaeletronico;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorContas {
    private List<Conta> contas = new ArrayList<>();

    // CREATE
    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    // READ
    public Conta buscarConta(int numero) {
        for (Conta c : contas) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    // UPDATE
    public boolean atualizarTitular(int numeroConta, Cliente novoTitular) {
        Conta conta = buscarConta(numeroConta);
        if (conta != null) {
            conta.setTitular(novoTitular);
            return true;
        }
        return false;
    }

    // DELETE
    public boolean removerConta(int numeroConta) {
        Conta conta = buscarConta(numeroConta);
        if (conta != null) {
            contas.remove(conta);
            return true;
        }
        return false;
    }

    // LISTAR
    public List<Conta> listarContas() {
        return contas;
    }
}

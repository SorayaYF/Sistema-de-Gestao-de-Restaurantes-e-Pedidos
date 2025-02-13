package br.com.ifsc.poo.restaurante;

import br.com.ifsc.poo.restaurante.exceptions.MesaSobrecarregadaException;

import javax.swing.*;

public class Mesa {

    private int numero;
    private int capacidade;
    private int ocupacaoAtual;

    public Mesa(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.ocupacaoAtual = 0;
    }

    public void adicionarClientes(int quantidade) throws MesaSobrecarregadaException {
        if (ocupacaoAtual + quantidade > capacidade) {
            throw new MesaSobrecarregadaException(
                    "A mesa " + numero + " não comporta " + quantidade + " clientes adicionais."
            );
        }
        ocupacaoAtual += quantidade;
        JOptionPane.showMessageDialog(null, "Agora a mesa " + numero + " tem " + ocupacaoAtual + " clientes.");
    }

    public void liberarClientes(int quantidade) {
        if (quantidade > ocupacaoAtual) {
            ocupacaoAtual = 0;
        } else {
            ocupacaoAtual -= quantidade;
        }
        JOptionPane.showMessageDialog(null, "Agora a mesa " + numero + " tem " + ocupacaoAtual + " clientes.");
    }

    public int getNumero() {
        return numero;
    }

    public int getOcupacaoAtual() {
        return ocupacaoAtual;
    }

    @Override
    public String toString() {
        return "Mesa #" + numero + " - Capacidade: " + capacidade + " - Ocupação Atual: " + ocupacaoAtual;
    }
}

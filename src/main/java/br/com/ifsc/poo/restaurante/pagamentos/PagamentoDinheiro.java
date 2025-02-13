package br.com.ifsc.poo.restaurante.pagamentos;

import br.com.ifsc.poo.restaurante.exceptions.PagamentoRecusadoException;

import javax.swing.*;

public class PagamentoDinheiro implements Pagamento {

    private double valorRecebido;

    public PagamentoDinheiro(double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    @Override
    public void processarPagamento(double valor) throws PagamentoRecusadoException {
        if (valorRecebido < valor) {
            throw new PagamentoRecusadoException("Valor insuficiente em dinheiro.");
        }
        double troco = valorRecebido - valor;
        JOptionPane.showMessageDialog(null, "Pagamento de R$ " + String.format("%.2f", valor) + " aprovado em dinheiro.");
        if (troco > 0) {
            JOptionPane.showMessageDialog(null, "Troco: R$ " + String.format("%.2f", troco));
        }
    }
}

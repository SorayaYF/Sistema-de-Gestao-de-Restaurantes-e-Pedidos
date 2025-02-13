package br.com.ifsc.poo.restaurante.pagamentos;

import br.com.ifsc.poo.restaurante.exceptions.PagamentoRecusadoException;

import javax.swing.*;

public class PagamentoCartao implements Pagamento {

    private String numeroCartao;
    private String nomeTitular;

    public PagamentoCartao(String numeroCartao, String nomeTitular) {
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
    }

    @Override
    public void processarPagamento(double valor) throws PagamentoRecusadoException {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }

        if (valor > 10000) {
            throw new PagamentoRecusadoException("Limite de cartão excedido.");
        }
        JOptionPane.showMessageDialog(null, "Pagamento de R$ " + String.format("%.2f", valor) + " aprovado no cartão de " + nomeTitular);
    }
}

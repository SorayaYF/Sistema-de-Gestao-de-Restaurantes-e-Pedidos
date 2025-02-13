package br.com.ifsc.poo.restaurante.pedido;

import br.com.ifsc.poo.restaurante.Mesa;

public class PedidoNoLocal extends Pedido {

    private static final double TAXA_SERVICO = 0.2;
    private Mesa mesa;

    public PedidoNoLocal(double valorBase, Mesa mesa) {
        super(valorBase);
        this.mesa = mesa;
    }

    public Mesa getMesa() {
        return mesa;
    }

    @Override
    public double calcularTotal() {
        return getValorBase() * (1 + TAXA_SERVICO);
    }
}

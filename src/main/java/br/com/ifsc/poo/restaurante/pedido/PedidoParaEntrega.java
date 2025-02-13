package br.com.ifsc.poo.restaurante.pedido;

public class PedidoParaEntrega extends Pedido {

    private static final double TAXA_ENTREGA = 5.0;

    public PedidoParaEntrega(double valorBase) {
        super(valorBase);
    }

    @Override
    public double calcularTotal() {
        return getValorBase() + TAXA_ENTREGA;
    }
}
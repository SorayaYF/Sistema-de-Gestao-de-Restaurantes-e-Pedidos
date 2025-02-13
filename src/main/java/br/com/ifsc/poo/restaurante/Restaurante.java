package br.com.ifsc.poo.restaurante;

import br.com.ifsc.poo.restaurante.exceptions.MesaJaExistenteException;
import br.com.ifsc.poo.restaurante.exceptions.PagamentoRecusadoException;
import br.com.ifsc.poo.restaurante.exceptions.PedidoInvalidoException;
import br.com.ifsc.poo.restaurante.pagamentos.Pagamento;
import br.com.ifsc.poo.restaurante.pedido.Pedido;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Restaurante {

    private List<Pedido> pedidosAbertos;
    private List<Pedido> historicoPedidos;
    private List<Mesa> mesas;

    public Restaurante() {
        this.pedidosAbertos = new ArrayList<>();
        this.historicoPedidos = new ArrayList<>();
        this.mesas = new ArrayList<>();
    }

    public void adicionarMesa(Mesa mesa) throws MesaJaExistenteException {
        if (getMesa(mesa.getNumero()) != null) {
            throw new MesaJaExistenteException("Já existe uma mesa com este número ("
                    + mesa.getNumero() + ").");
        }
        mesas.add(mesa);
    }

    public Mesa getMesa(int numero) {
        return mesas.stream()
                .filter(m -> m.getNumero() == numero)
                .findFirst()
                .orElse(null);
    }

    public void abrirPedido(Pedido pedido) throws PedidoInvalidoException {
        if (pedido.getValorBase() <= 0) {
            throw new PedidoInvalidoException("O valor base do pedido deve ser maior que zero.");
        }
        pedidosAbertos.add(pedido);
    }

    public void fecharPedido(Pedido pedido, Pagamento pagamento)
            throws PagamentoRecusadoException {
        double total = pedido.calcularTotal();
        pagamento.processarPagamento(total);

        pedidosAbertos.remove(pedido);
        historicoPedidos.add(pedido);
    }

    public List<Pedido> getPedidosAbertos() {
        return pedidosAbertos;
    }

    public List<Pedido> getHistoricoPedidos() {
        return historicoPedidos;
    }

    public List<Mesa> getMesas() {
        return mesas;
    }
}

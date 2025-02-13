package br.com.ifsc.poo.restaurante.pagamentos;

import br.com.ifsc.poo.restaurante.exceptions.PagamentoRecusadoException;

public interface Pagamento {

    /**
     * Tenta processar o pagamento de um valor.
     * @param valor O valor total a ser pago
     * @throws PagamentoRecusadoException se o pagamento não for aprovado
     */
    void processarPagamento(double valor) throws PagamentoRecusadoException;
}

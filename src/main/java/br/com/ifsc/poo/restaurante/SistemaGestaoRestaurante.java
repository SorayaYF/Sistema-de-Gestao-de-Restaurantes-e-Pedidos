package br.com.ifsc.poo.restaurante;

import br.com.ifsc.poo.restaurante.exceptions.MesaJaExistenteException;
import br.com.ifsc.poo.restaurante.exceptions.MesaSobrecarregadaException;
import br.com.ifsc.poo.restaurante.exceptions.PagamentoRecusadoException;
import br.com.ifsc.poo.restaurante.exceptions.PedidoInvalidoException;
import br.com.ifsc.poo.restaurante.pagamentos.Pagamento;
import br.com.ifsc.poo.restaurante.pagamentos.PagamentoCartao;
import br.com.ifsc.poo.restaurante.pagamentos.PagamentoDinheiro;
import br.com.ifsc.poo.restaurante.pedido.Pedido;
import br.com.ifsc.poo.restaurante.pedido.PedidoNoLocal;
import br.com.ifsc.poo.restaurante.pedido.PedidoParaEntrega;

import javax.swing.*;
import java.util.List;

public class SistemaGestaoRestaurante {

    private static final String MENU_PRINCIPAL = """
        ----- Sistema de Gestão de Restaurante -----
        1. Adicionar Mesa
        2. Abrir Pedido
        3. Fechar Pedido
        4. Listar Pedidos Abertos
        5. Listar Histórico de Pedidos
        6. Sair

        Escolha uma opção:
        """;

    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();
        boolean executando = true;

        while (executando) {
            String opcaoStr = JOptionPane.showInputDialog(MENU_PRINCIPAL);
            if (opcaoStr == null) {
                executando = false;
                continue;
            }

            int opcao;
            try {
                opcao = Integer.parseInt(opcaoStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Opção inválida! Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1 -> adicionarMesa(restaurante);
                case 2 -> abrirPedido(restaurante);
                case 3 -> fecharPedido(restaurante);
                case 4 -> listarPedidosAbertos(restaurante);
                case 5 -> listarHistoricoPedidos(restaurante);
                case 6 -> executando = false;
                default -> JOptionPane.showMessageDialog(null,
                        "Opção inválida! Tente novamente.");
            }
        }
    }

    private static void adicionarMesa(Restaurante restaurante) {
        Integer numeroMesa = lerInteiro("Digite o número da mesa:");
        if (numeroMesa == null) return;

        Integer capacidade = lerInteiro("Digite a capacidade da mesa:");
        if (capacidade == null) return;

        try {
            Mesa novaMesa = new Mesa(numeroMesa, capacidade);
            restaurante.adicionarMesa(novaMesa);

            JOptionPane.showMessageDialog(null,
                    String.format(
                            "Mesa #%d adicionada com sucesso (Capacidade: %d).",
                            numeroMesa, capacidade
                    ),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (MesaJaExistenteException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao adicionar a mesa: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void abrirPedido(Restaurante restaurante) {
        String[] opcoesTipo = {"Pedido no Local", "Pedido para Entrega"};
        int tipoSelecionado = JOptionPane.showOptionDialog(
                null,
                "Selecione o tipo de pedido:",
                "Abrir Pedido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesTipo,
                opcoesTipo[0]
        );

        if (tipoSelecionado < 0) {
            return;
        }

        Mesa mesaEscolhida = null;
        if (tipoSelecionado == 0) {
            mesaEscolhida = escolherMesa(restaurante);
            if (mesaEscolhida == null) {
                return;
            }

            Integer qtdClientes = lerInteiro(String.format(
                    """
                    Quantos clientes sentarão na mesa %d?
                    (ou 0 caso ela já esteja ocupada e não deseje adicionar novos clientes)
                    """,
                    mesaEscolhida.getNumero()
            ));
            if (qtdClientes == null) return;

            try {
                mesaEscolhida.adicionarClientes(qtdClientes);
            } catch (MesaSobrecarregadaException e) {
                JOptionPane.showMessageDialog(null,
                        "Erro ao adicionar clientes: " + e.getMessage());
                return;
            }

            if (mesaEscolhida.getOcupacaoAtual() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Não é possível abrir um pedido para uma mesa sem clientes!");
                return;
            }
        }

        Double valorBase = lerDouble("Digite o valor base do pedido:");
        if (valorBase == null) return;

        Pedido pedido;
        if (tipoSelecionado == 0) {
            pedido = new PedidoNoLocal(valorBase, mesaEscolhida);
        } else {
            pedido = new PedidoParaEntrega(valorBase);
        }

        while (true) {
            String item = JOptionPane.showInputDialog(
                    "Digite o nome do item (ou deixe vazio para encerrar):"
            );
            if (item == null || item.isBlank()) {
                break;
            }
            pedido.adicionarItem(item.trim());
        }

        if (pedido.getItens().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Nenhum item foi adicionado! O pedido não será aberto.");
            return;
        }

        try {
            restaurante.abrirPedido(pedido);

            JOptionPane.showMessageDialog(null,
                    String.format(
                            "Pedido #%d aberto com sucesso!%nValor base: R$ %.2f",
                            pedido.getId(),
                            pedido.getValorBase()
                    ),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (PedidoInvalidoException e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao abrir pedido: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void fecharPedido(Restaurante restaurante) {
        List<Pedido> pedidosAbertos = restaurante.getPedidosAbertos();
        if (pedidosAbertos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Não há pedidos abertos.",
                    "Fechar Pedido",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder("Pedidos Abertos:\n");
        for (Pedido p : pedidosAbertos) {
            sb.append("ID: ").append(p.getId())
                    .append(" - ").append(p)
                    .append("\n");
        }
        sb.append("\nDigite o ID do pedido que deseja fechar:");

        String idStr = JOptionPane.showInputDialog(sb.toString());
        if (idStr == null) return;

        int idPedido;
        try {
            idPedido = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "ID inválido.");
            return;
        }

        Pedido pedidoParaFechar = null;
        for (Pedido pedido : pedidosAbertos) {
            if (pedido.getId() == idPedido) {
                pedidoParaFechar = pedido;
                break;
            }
        }

        if (pedidoParaFechar == null) {
            JOptionPane.showMessageDialog(null,
                    "Pedido não encontrado com ID: " + idPedido);
            return;
        }

        Pagamento pagamento = escolherFormaPagamento();
        if (pagamento == null) {
            return;
        }

        try {
            restaurante.fecharPedido(pedidoParaFechar, pagamento);

            JOptionPane.showMessageDialog(null,
                    String.format(
                            "Pedido #%d fechado com sucesso!%nTotal: R$ %.2f",
                            pedidoParaFechar.getId(),
                            pedidoParaFechar.calcularTotal()
                    ));

            if (pedidoParaFechar instanceof PedidoNoLocal pedidoLocal) {
                liberarClientesSeNecessario(pedidoLocal);
            }

        } catch (PagamentoRecusadoException e) {
            JOptionPane.showMessageDialog(null,
                    "Falha no pagamento: " + e.getMessage());
        }
    }

    private static void liberarClientesSeNecessario(PedidoNoLocal pedidoLocal) {
        Mesa mesa = pedidoLocal.getMesa();
        if (mesa == null) return;

        int ocupacao = mesa.getOcupacaoAtual();
        if (ocupacao <= 0) return;

        String qtdStr = JOptionPane.showInputDialog(String.format(
                """
                Mesa %d tem %d clientes.
                Quantos clientes deseja liberar agora?
                """,
                mesa.getNumero(),
                ocupacao
        ));
        if (qtdStr == null) {
            return;
        }

        try {
            int qtdLiberar = Integer.parseInt(qtdStr);
            mesa.liberarClientes(qtdLiberar);

            JOptionPane.showMessageDialog(null,
                    String.format(
                            "Clientes liberados da mesa %d. Ocupação atual: %d",
                            mesa.getNumero(),
                            mesa.getOcupacaoAtual()
                    ));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Quantidade inválida. Nenhum cliente liberado.");
        }
    }

    private static void listarPedidosAbertos(Restaurante restaurante) {
        List<Pedido> pedidosAbertos = restaurante.getPedidosAbertos();
        if (pedidosAbertos.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Não há pedidos abertos.");
            return;
        }

        StringBuilder sb = new StringBuilder("----- Pedidos Abertos -----\n");
        for (Pedido pedido : pedidosAbertos) {
            sb.append(pedido).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void listarHistoricoPedidos(Restaurante restaurante) {
        List<Pedido> historico = restaurante.getHistoricoPedidos();
        if (historico.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Não há pedidos no histórico.");
            return;
        }

        StringBuilder sb = new StringBuilder("----- Histórico de Pedidos -----\n");
        for (Pedido p : historico) {
            sb.append(p)
                    .append(" | Total: R$ ")
                    .append(String.format("%.2f", p.calcularTotal()))
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static Integer lerInteiro(String mensagem) {
        String input = JOptionPane.showInputDialog(mensagem);
        if (input == null) {
            return null;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Valor inválido.");
            return null;
        }
    }

    private static Double lerDouble(String mensagem) {
        String input = JOptionPane.showInputDialog(mensagem);
        if (input == null) {
            return null;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Valor inválido.");
            return null;
        }
    }

    private static Mesa escolherMesa(Restaurante restaurante) {
        List<Mesa> mesas = restaurante.getMesas();
        if (mesas.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Não há mesas cadastradas.");
            return null;
        }

        StringBuilder sb = new StringBuilder("----- Mesas Disponíveis -----\n");
        for (Mesa m : mesas) {
            sb.append(m).append("\n");
        }
        sb.append("\nDigite o número da mesa para abrir o pedido:");

        String numeroStr = JOptionPane.showInputDialog(sb.toString());
        if (numeroStr == null) {
            return null;
        }

        int numeroMesa;
        try {
            numeroMesa = Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Número de mesa inválido!");
            return null;
        }

        Mesa mesa = restaurante.getMesa(numeroMesa);
        if (mesa == null) {
            JOptionPane.showMessageDialog(null,
                    "A mesa de número " + numeroMesa + " não existe!");
            return null;
        }
        return mesa;
    }

    private static Pagamento escolherFormaPagamento() {
        String[] opcoesPagamento = {"Cartão", "Dinheiro"};
        int formaEscolhida = JOptionPane.showOptionDialog(null,
                "Selecione a forma de pagamento:",
                "Fechar Pedido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesPagamento,
                opcoesPagamento[0]);

        if (formaEscolhida < 0) {
            return null;
        }

        return switch (formaEscolhida) {
            case 0 -> {
                String numeroCartao = JOptionPane.showInputDialog("Digite o número do cartão:");
                if (numeroCartao == null) {
                    yield null;
                }
                String nomeTitular = JOptionPane.showInputDialog("Digite o nome do titular:");
                if (nomeTitular == null) {
                    yield null;
                }
                yield new PagamentoCartao(numeroCartao, nomeTitular);
            }
            case 1 -> {
                Double valorRecebido = lerDouble("Digite o valor recebido em dinheiro:");
                if (valorRecebido == null) {
                    yield null;
                }
                yield new PagamentoDinheiro(valorRecebido);
            }
            default -> null;
        };
    }
}

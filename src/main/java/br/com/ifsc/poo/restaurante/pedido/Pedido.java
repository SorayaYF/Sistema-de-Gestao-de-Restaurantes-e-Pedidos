package br.com.ifsc.poo.restaurante.pedido;

import java.util.ArrayList;
import java.util.List;

public abstract class Pedido {

    private static int contadorId = 1;

    private int id;
    private List<String> itens;
    private double valorBase;

    public Pedido(double valorBase) {
        this.id = contadorId++;
        this.itens = new ArrayList<>();
        this.valorBase = valorBase;
    }

    public int getId() {
        return id;
    }

    public List<String> getItens() {
        return itens;
    }

    public void adicionarItem(String item) {
        itens.add(item);
    }

    public double getValorBase() {
        return valorBase;
    }

    public abstract double calcularTotal();

    @Override
    public String toString() {
        return "Pedido #" + id + " - Itens: " + itens.toString() + " - Valor base: " + valorBase;
    }
}

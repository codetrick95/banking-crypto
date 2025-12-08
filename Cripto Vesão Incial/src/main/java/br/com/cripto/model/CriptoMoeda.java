package br.com.cripto.model;

public class CriptoMoeda {
    private String nome;
    private String simbolo;
    private double precoAtual;
    // NOVIDADE: Campo para guardar quanto custava antes
    private double precoAnterior;

    public CriptoMoeda(String nome, String simbolo, double precoAtual) {
        this.nome = nome;
        this.simbolo = simbolo;
        this.precoAtual = precoAtual;
        // Ao criar, o preço anterior é igual ao atual
        this.precoAnterior = precoAtual;
    }

    // Getters
    public String getNome() { return nome; }
    public String getSimbolo() { return simbolo; }
    public double getPrecoAtual() { return precoAtual; }
    // NOVO GETTER
    public double getPrecoAnterior() { return precoAnterior; }

    // Setters
    public void setPrecoAtual(double novoPreco) {
        this.precoAtual = novoPreco;
    }
    
    // NOVO SETTER
    public void setPrecoAnterior(double precoAnterior) {
        this.precoAnterior = precoAnterior;
    }
}
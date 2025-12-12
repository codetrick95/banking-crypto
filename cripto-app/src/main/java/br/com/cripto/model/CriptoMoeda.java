package br.com.cripto.model;

/**
 * Representa uma Criptomoeda disponível para negociação no sistema.
 * Contém informações básicas de identificação e valores de cotação.
 */
public class CriptoMoeda {
    
    private String nome;
    private String simbolo;       // Ex: BTC, ETH
    private double precoAtual;
    
    // Armazena a cotação prévia para permitir cálculos de variação de preço
    private double precoAnterior;

    public CriptoMoeda(String nome, String simbolo, double precoAtual) {
        this.nome = nome;
        this.simbolo = simbolo;
        this.precoAtual = precoAtual;
        // Na criação do objeto, o preço anterior é inicializado igual ao atual para evitar inconsistências
        this.precoAnterior = precoAtual;
    }

    // --- Métodos de Acesso (Getters) ---

    public String getNome() { 
        return nome; 
    }
    
    public String getSimbolo() { 
        return simbolo; 
    }
    
    public double getPrecoAtual() { 
        return precoAtual; 
    }
    
    public double getPrecoAnterior() { 
        return precoAnterior; 
    }

    // --- Métodos de Atualização (Setters) ---

    public void setPrecoAtual(double novoPreco) {
        this.precoAtual = novoPreco;
    }
    
    public void setPrecoAnterior(double precoAnterior) {
        this.precoAnterior = precoAnterior;
    }
}

package br.com.cripto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carteira {
    private Usuario usuario;
    private double saldoReais;
    private Map<String, Double> criptoMoedas; // Guarda quanto tem de cada moeda (Ex: "BTC" -> 0.5)
    
    // Método novo que faltava!
    public Usuario getUsuario() {
        return usuario;
    }
    // NOVIDADE: A lista para guardar o histórico!
    private List<Transacao> historicoTransacoes;

    public Carteira(Usuario usuario, double saldoInicial) {
        this.usuario = usuario;
        this.saldoReais = saldoInicial;
        this.criptoMoedas = new HashMap<>();
        this.historicoTransacoes = new ArrayList<>(); // Começa com a lista vazia
    }

    public double getSaldo() {
        return saldoReais;
    }

    public Map<String, Double> getMoedas() {
        return criptoMoedas;
    }
    
    // Novo método para quem quiser ver o extrato
    public List<Transacao> getHistorico() {
        return historicoTransacoes;
    }

    public double getQuantidadeMoeda(String simbolo) {
        return criptoMoedas.getOrDefault(simbolo, 0.0);
    }

    // Método para COMPRAR
    public boolean comprar(CriptoMoeda moeda, double quantidade) {
        double custoTotal = quantidade * moeda.getPrecoAtual();
        
        if (saldoReais >= custoTotal) {
            saldoReais -= custoTotal;
            
            // Atualiza a quantidade da moeda
            double novaQuantidade = getQuantidadeMoeda(moeda.getSimbolo()) + quantidade;
            criptoMoedas.put(moeda.getSimbolo(), novaQuantidade);
            
            // NOVIDADE: Grava na lista que comprou!
            Transacao t = new Transacao(moeda.getSimbolo(), quantidade, moeda.getPrecoAtual(), "COMPRA");
            historicoTransacoes.add(t);
            
            return true;
        }
        return false;
    }

    // Método para VENDER
    public boolean vender(CriptoMoeda moeda, double quantidade) {
        double quantidadeAtual = getQuantidadeMoeda(moeda.getSimbolo());
        
        if (quantidadeAtual >= quantidade) {
            double valorVenda = quantidade * moeda.getPrecoAtual();
            saldoReais += valorVenda;
            
            double novaQuantidade = quantidadeAtual - quantidade;
            if (novaQuantidade == 0) {
                criptoMoedas.remove(moeda.getSimbolo());
            } else {
                criptoMoedas.put(moeda.getSimbolo(), novaQuantidade);
            }
            
            // NOVIDADE: Grava na lista que vendeu!
            Transacao t = new Transacao(moeda.getSimbolo(), quantidade, moeda.getPrecoAtual(), "VENDA");
            historicoTransacoes.add(t);
            
            return true;
        }
        return false;
    }
    
    // Método auxiliar para mostrar saldo na tela (Console) se precisar
    public void mostrarCarteira() {
        System.out.println("Saldo: R$ " + String.format("%.2f", saldoReais));
    }
    // Método para ADICIONAR dinheiro (Depósito)
    public void depositar(double valor) {
        if (valor > 0) {
            this.saldoReais += valor;
        }
    }
    // Método para definir o saldo (usado para zerar)
    public void setSaldo(double novoSaldo) {
        this.saldoReais = novoSaldo;
    }
}
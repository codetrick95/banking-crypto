package br.com.cripto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representa a carteira digital do usuário.
 * Responsável por gerenciar o saldo em Reais, a posse de criptomoedas e o histórico de transações.
 */
public class Carteira {

    private Usuario usuario;
    private double saldoReais;
    
    // Armazena a quantidade que o usuário possui de cada criptomoeda (Ex: "BTC" -> 0.5)
    private Map<String, Double> criptoMoedas; 
    
    // Lista que armazena o histórico de todas as operações (compras e vendas)
    private List<Transacao> historicoTransacoes;

    public Carteira(Usuario usuario, double saldoInicial) {
        this.usuario = usuario;
        this.saldoReais = saldoInicial;
        this.criptoMoedas = new HashMap<>();
        this.historicoTransacoes = new ArrayList<>(); // Inicia o histórico vazio
    }

    // --- Métodos de Consulta ---

    public Usuario getUsuario() {
        return usuario;
    }

    public double getSaldo() {
        return saldoReais;
    }

    // Retorna todas as criptomoedas que o usuário possui
    public Map<String, Double> getMoedas() {
        return criptoMoedas;
    }
    
    // Retorna o extrato de movimentações
    public List<Transacao> getHistorico() {
        return historicoTransacoes;
    }

    // Verifica a quantidade disponível de uma moeda específica pelo símbolo
    public double getQuantidadeMoeda(String simbolo) {
        return criptoMoedas.getOrDefault(simbolo, 0.0);
    }

    // --- Operações Financeiras ---

    /**
     * Realiza a compra de uma criptomoeda.
     * Verifica o saldo, desconta o valor, adiciona a moeda à carteira e registra no histórico.
     */
    public boolean comprar(CriptoMoeda moeda, double quantidade) {
        double custoTotal = quantidade * moeda.getPrecoAtual();
        
        // Verifica se o usuário tem saldo suficiente em Reais
        if (saldoReais >= custoTotal) {
            saldoReais -= custoTotal;
            
            // Atualiza a quantidade da moeda na carteira (soma o que já tinha com a nova compra)
            double novaQuantidade = getQuantidadeMoeda(moeda.getSimbolo()) + quantidade;
            criptoMoedas.put(moeda.getSimbolo(), novaQuantidade);
            
            // Registra a operação no histórico
            Transacao t = new Transacao(moeda.getSimbolo(), quantidade, moeda.getPrecoAtual(), "COMPRA");
            historicoTransacoes.add(t);
            
            return true; // Compra realizada com sucesso
        }
        return false; // Saldo insuficiente
    }

    /**
     * Realiza a venda de uma criptomoeda.
     * Verifica se o usuário possui a moeda, remove a quantidade, adiciona o valor em Reais e registra no histórico.
     */
    public boolean vender(CriptoMoeda moeda, double quantidade) {
        double quantidadeAtual = getQuantidadeMoeda(moeda.getSimbolo());
        
        // Verifica se o usuário tem a quantidade de moeda necessária para vender
        if (quantidadeAtual >= quantidade) {
            double valorVenda = quantidade * moeda.getPrecoAtual();
            saldoReais += valorVenda;
            
            // Atualiza a carteira: remove a moeda se zerar ou apenas diminui a quantidade
            double novaQuantidade = quantidadeAtual - quantidade;
            if (novaQuantidade == 0) {
                criptoMoedas.remove(moeda.getSimbolo());
            } else {
                criptoMoedas.put(moeda.getSimbolo(), novaQuantidade);
            }
            
            // Registra a operação no histórico
            Transacao t = new Transacao(moeda.getSimbolo(), quantidade, moeda.getPrecoAtual(), "VENDA");
            historicoTransacoes.add(t);
            
            return true; // Venda realizada com sucesso
        }
        return false; // Quantidade de moeda insuficiente
    }
    
    // --- Métodos Auxiliares ---

    // Exibe o saldo atual formatado no console
    public void mostrarCarteira() {
        System.out.println("Saldo: R$ " + String.format("%.2f", saldoReais));
    }

    // Adiciona saldo em Reais à carteira (Depósito)
    public void depositar(double valor) {
        if (valor > 0) {
            this.saldoReais += valor;
        }
    }

    // Define o saldo manualmente (pode ser usado para resetar ou corrigir valores)
    public void setSaldo(double novoSaldo) {
        this.saldoReais = novoSaldo;
    }
}

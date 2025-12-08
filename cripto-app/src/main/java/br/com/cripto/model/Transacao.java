package br.com.cripto.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    // Atributos (As informações da nota fiscal)
    private LocalDateTime dataHora;
    private String simboloMoeda;  // Ex: "BTC"
    private double quantidade;    // Ex: 0.5
    private double precoNaEpoca;  // Ex: 250000.00 (Quanto custava 1 unidade na hora)
    private String tipo;          // "COMPRA" ou "VENDA"

    // Construtor (Para criar uma nova transação facilmente)
    public Transacao(String simboloMoeda, double quantidade, double precoNaEpoca, String tipo) {
        this.dataHora = LocalDateTime.now(); // Pega a hora atual do computador
        this.simboloMoeda = simboloMoeda;
        this.quantidade = quantidade;
        this.precoNaEpoca = precoNaEpoca;
        this.tipo = tipo;
    }

    // Getters (Para o sistema conseguir ler essas informações depois)
    public String getSimboloMoeda() { return simboloMoeda; }
    public double getQuantidade() { return quantidade; }
    public double getPrecoNaEpoca() { return precoNaEpoca; }
    public String getTipo() { return tipo; }

    // Método extra para mostrar a data bonitinha na tela (Ex: 07/12/2025 10:30)
    public String getDataFormatada() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatador);
    }
}
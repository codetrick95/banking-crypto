package br.com.cripto.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa o registro histórico de uma operação (Compra ou Venda).
 * Armazena os detalhes financeiros e o momento em que a transação ocorreu.
 */
public class Transacao {
    
    private LocalDateTime dataHora;
    private String simboloMoeda;      // Ex: "BTC"
    private double quantidade;        // Ex: 0.5
    private double precoNaEpoca;      // Valor unitário da moeda no momento da operação
    private String tipo;              // "COMPRA" ou "VENDA"

    /**
     * Construtor da Transação.
     * A data e hora são registradas automaticamente no momento da criação do objeto.
     */
    public Transacao(String simboloMoeda, double quantidade, double precoNaEpoca, String tipo) {
        this.dataHora = LocalDateTime.now(); 
        this.simboloMoeda = simboloMoeda;
        this.quantidade = quantidade;
        this.precoNaEpoca = precoNaEpoca;
        this.tipo = tipo;
    }

    // --- Métodos de Acesso (Getters) ---

    public String getSimboloMoeda() { 
        return simboloMoeda; 
    }
    
    public double getQuantidade() { 
        return quantidade; 
    }
    
    public double getPrecoNaEpoca() { 
        return precoNaEpoca; 
    }
    
    public String getTipo() { 
        return tipo; 
    }

    // --- Métodos Auxiliares ---

    // Retorna a data e hora da transação formatada como String (dd/MM/yyyy HH:mm)
    public String getDataFormatada() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatador);
    }
}

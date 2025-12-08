package br.com.cripto.factory;

import java.util.ArrayList;
import java.util.List;
import br.com.cripto.model.CriptoMoeda;

public class Cripto {
    private static List<CriptoMoeda> criptoMoedas;
    
    static {
        criptoMoedas = new ArrayList<>();
        criptoMoedas.add(new CriptoMoeda("Bitcoin", "BTC", 250000.00));
        criptoMoedas.add(new CriptoMoeda("Ethereum", "ETH", 15000.00));
        criptoMoedas.add(new CriptoMoeda("Binance Coin", "BNB", 2000.00));
        criptoMoedas.add(new CriptoMoeda("Cardano", "ADA", 3.50));
        criptoMoedas.add(new CriptoMoeda("Solana", "SOL", 500.00));
    }
    
    public static List<CriptoMoeda> getCriptoMoedas() {
        return new ArrayList<>(criptoMoedas);
    }
    
    public static CriptoMoeda getCriptoMoedaPorSimbolo(String simbolo) {
        for (CriptoMoeda criptoMoeda : criptoMoedas) {
            if (criptoMoeda.getSimbolo().equalsIgnoreCase(simbolo)) {
                return criptoMoeda;
            }
        }
        return null;
    }
    
    public static void listarCriptoMoedas() {
        System.out.println("\n=== CRIPTOMOEDAS DISPONÍVEIS ===");
        for (int i = 0; i < criptoMoedas.size(); i++) {
            System.out.println((i + 1) + ". " + criptoMoedas.get(i));
        }
    }
}


package br.com.cripto.util;

public class UtilsCalculos {
    
    public static double calcularValorTotal(double quantidade, double precoUnitario) {
        return quantidade * precoUnitario;
    }
    
    public static String formatarMoeda(double valor) {
        return String.format("R$ %.2f", valor);
    }
    
    public static String formatarQuantidade(double quantidade) {
        return String.format("%.6f", quantidade);
    }
    
    public static boolean validarValorPositivo(double valor) {
        return valor > 0;
    }
    
    public static boolean validarSaldoSuficiente(double saldoDisponivel, double valorNecessario) {
        return saldoDisponivel >= valorNecessario;
    }
}


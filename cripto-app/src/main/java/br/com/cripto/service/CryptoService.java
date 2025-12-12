package br.com.cripto.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import br.com.cripto.factory.Cripto;
import br.com.cripto.model.Carteira;
import br.com.cripto.model.CriptoMoeda;
import br.com.cripto.model.Usuario;
import br.com.cripto.util.UtilsCalculos;

@Service
public class CryptoService {
    private Carteira carteira;
    
    // --- CONSTRUTOR: Prepara a "casa" antes de usarmos ---
    public CryptoService() {
        // 1. Cria um usuário fictício para testes
        Usuario usuario = new Usuario(
            "Matheus Ledur", 
            "123.456.789-00", 
            "matheus@email.com", 
            "11999999999", 
            "123456", 
            LocalDate.of(2006, 3, 27)
        );

        // Inicialização da carteira com saldo zerado (o saldo será definido ou depositado posteriormente)
        this.carteira = new Carteira(usuario, 0.00);
    }
    
    // Retorna a instância atual da carteira para consulta
    public Carteira getCarteira() {
        return carteira;
    }

    // --- NOVOS MÉTODOS PARA O PERFIL ---
    
    // 1. Método para pegar os dados do usuário (para mostrar na tela)
    public Usuario getUsuario() {
        return carteira.getUsuario(); // Pega o usuário de dentro da carteira
    }

    // Atualiza as informações de contato do usuário
    public String atualizarUsuario(String novoNome, String novoEmail) {
        Usuario user = carteira.getUsuario();
        
        // Aqui usamos os "Setters" para mudar os valores
        user.setNome(novoNome);
        user.setEmail(novoEmail);
        
        return "Perfil atualizado com sucesso!";
    }
    
   /**
     * Processa a compra de uma criptomoeda.
     * Valida a existência da moeda e o saldo disponível antes de efetivar a transação.
     */
    public String comprarCriptoMoeda(String simbolo, double quantidade) {
        // Busca a moeda pelo símbolo (ex: "BTC")
        CriptoMoeda criptoMoeda = Cripto.getCriptoMoedaPorSimbolo(simbolo);
        
        if (criptoMoeda == null) {
            return "Erro: Moeda não encontrada!";
        }
        
        // Tenta realizar a compra na carteira
        if (carteira.comprar(criptoMoeda, quantidade)) {
            return "Sucesso! Compra realizada. Novo saldo: " + UtilsCalculos.formatarMoeda(carteira.getSaldo());
        } else {
            // Se falhou, calculamos o porquê para avisar o usuário
            double valorTotal = UtilsCalculos.calcularValorTotal(quantidade, criptoMoeda.getPrecoAtual());
            if (!UtilsCalculos.validarSaldoSuficiente(carteira.getSaldo(), valorTotal)) {
                return "Saldo insuficiente! Você precisa de " + UtilsCalculos.formatarMoeda(valorTotal);
            } else if (!UtilsCalculos.validarValorPositivo(quantidade)) {
                return "A quantidade deve ser maior que zero!";
            }
            return "Erro desconhecido ao comprar.";
        }
    }
    
    public String venderCriptoMoeda(String simbolo, double quantidade) {
        CriptoMoeda criptoMoeda = Cripto.getCriptoMoedaPorSimbolo(simbolo);
        
        if (criptoMoeda == null) {
            return "Erro: Moeda não encontrada!";
        }
        
        if (carteira.vender(criptoMoeda, quantidade)) {
            return "Sucesso! Venda realizada. Novo saldo: " + UtilsCalculos.formatarMoeda(carteira.getSaldo());
        } else {
            if (!UtilsCalculos.validarValorPositivo(quantidade)) {
                return "A quantidade deve ser maior que zero!";
            } else {
                return "Quantidade insuficiente na carteira para vender!";
            }
        }
    }

    public String realizarDeposito(double valor) {
        if (valor <= 0) {
            return "O valor do depósito deve ser positivo!";
        }
        carteira.depositar(valor);
        return "Depósito realizado com sucesso! Novo saldo: " + UtilsCalculos.formatarMoeda(carteira.getSaldo());
    }

    // --- SIMULADOR DE MERCADO ATUALIZADO ---
    public void simularVariacaoDePrecos() {
        java.util.List<CriptoMoeda> moedas = Cripto.getCriptoMoedas();
        java.util.Random random = new java.util.Random();

        for (CriptoMoeda moeda : moedas) {
            // 1. Pega o preço que está agora
            double precoAntigo = moeda.getPrecoAtual();

            // 2. IMPORTANTE: Salva ele como "Preço Anterior" antes de mudar!
            moeda.setPrecoAnterior(precoAntigo);

            // 3. Calcula a variação (+- 3%)
            double fator = (random.nextDouble() * 0.06) - 0.03;
            double novoPreco = precoAntigo + (precoAntigo * fator);
            
            // 4. Define o novo preço atual
            moeda.setPrecoAtual(novoPreco);
        
    
        }
    }

    public String zerarCarteira() {
        carteira.setSaldo(0.0);
        return "Carteira zerada com sucesso!";
    }
}

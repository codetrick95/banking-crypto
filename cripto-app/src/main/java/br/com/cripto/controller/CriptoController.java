package br.com.cripto.controller;

import br.com.cripto.factory.Cripto;
import br.com.cripto.model.Carteira;
import br.com.cripto.model.CriptoMoeda;
import br.com.cripto.model.Usuario;
import br.com.cripto.service.AIService;
import br.com.cripto.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/moedas")
public class CriptoController {

    @Autowired
    private AIService aiService;

    @Autowired
    private CryptoService cryptoService;

    @GetMapping
    public List<CriptoMoeda> listarMoedas() {
        cryptoService.simularVariacaoDePrecos();
        return Cripto.getCriptoMoedas();
    }
    
    @GetMapping("/carteira")
    public Carteira verCarteira() {
        return cryptoService.getCarteira();
    }

    @GetMapping("/analise")
    public String pedirAnaliseIA(@RequestParam String nome) {
        return aiService.analisarTendencia(nome);
    }

    @PostMapping("/comprar")
    public String comprarMoeda(@RequestParam String simbolo, @RequestParam double quantidade) {
        return cryptoService.comprarCriptoMoeda(simbolo, quantidade);
    }
    
    @PostMapping("/vender")
    public String venderMoeda(@RequestParam String simbolo, @RequestParam double quantidade) {
        return cryptoService.venderCriptoMoeda(simbolo, quantidade);
    }

    // --- DEPÓSITO E ZERAR ---
    @PostMapping("/depositar")
    public String depositarDinheiro(@RequestParam double valor) {
        return cryptoService.realizarDeposito(valor);
    }

    @PostMapping("/zerar")
    public String zerarSaldo() {
        return cryptoService.zerarCarteira();
    }

    // --- PERFIL ---
    @GetMapping("/usuario")
    public Usuario verUsuario() {
        return cryptoService.getUsuario();
    }

    @PostMapping("/usuario")
    public String atualizarPerfil(@RequestParam String nome, @RequestParam String email) {
        return cryptoService.atualizarUsuario(nome, email);
    }
}
package br.com.cripto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot está funcionando! Servidor rodando na porta 8080.";
    }

    @GetMapping("/test")
    public String test() {
        return "Endpoint de teste funcionando corretamente!";
    }
}

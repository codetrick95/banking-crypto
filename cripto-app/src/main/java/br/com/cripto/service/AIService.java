package br.com.cripto.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.Map;
import java.util.List;

@Service
public class AIService {

    // ⚠️ COLE SUA CHAVE AQUI DENTRO (Sem espaços extras!)
    private static final String API_KEY = "AIzaSyAgvB6IwAhAgH-3HIFeW3mWAjMOtLnvm7g";
    
    // ✅ USANDO O MODELO QUE APARECEU NA SUA LISTA: GEMINI 2.5 FLASH
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public String analisarTendencia(String moeda) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            
            // O Prompt (A pergunta para a IA)
            String prompt = "Atue como um consultor financeiro. Faça uma análise curta (máximo 2 linhas) sobre " + moeda + ". Termine com: COMPRA, VENDA ou ESPERA.";

            // Monta o JSON
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of("parts", List.of(
                        Map.of("text", prompt)
                    ))
                )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Envia para o Google
            Map<String, Object> response = restTemplate.postForObject(API_URL, entity, Map.class);

            // Lê a resposta
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return "🤖 IA (v2.5): " + parts.get(0).get("text");

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erro ao consultar IA. Verifique o terminal.";
        }
    }
}
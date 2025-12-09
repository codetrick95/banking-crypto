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

    private String getApiKey() {
        String key = System.getenv("GEMINI_API_KEY");
        if (key == null || key.isEmpty()) {
            return "CHAVE_LOCAL"; 
        }
        return key;
    }

    // Mantendo o modelo flash que é rápido e barato
    private static final String MODEL_NAME = "gemini-1.5-flash-001
"; 

    public String analisarTendencia(String moeda) {
        try {
            String apiKey = getApiKey();
            // AQUI ESTÁ A CORREÇÃO: Mudamos de v1beta para v1
            String apiUrl = "https://generativelanguage.googleapis.com/v1/models/" + MODEL_NAME + ":generateContent?key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            
            String prompt = "Atue como um consultor financeiro. Faça uma análise curta (máximo 2 linhas) sobre " + moeda + ". Termine com: COMPRA, VENDA ou ESPERA.";

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

            Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);

            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return "🤖 IA: " + parts.get(0).get("text");

        } catch (Exception e) {
            e.printStackTrace();
            // Isso ajuda a ver o erro real no log se acontecer de novo
            return "❌ Erro Google: " + e.getMessage();
        }
    }
}

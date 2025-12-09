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

    // 1. Pega a chave das Variáveis de Ambiente (Seguro para Render)
    private String getApiKey() {
        String key = System.getenv("GEMINI_API_KEY");
        if (key == null || key.isEmpty()) {
            // Fallback: Se não achar no ambiente, tenta usar uma fixa (apenas para teste local)
            // CUIDADO: Não commite com sua chave real aqui se for código público
            return "SUA_CHAVE_NOVA_AQUI_SE_FOR_RODAR_LOCAL"; 
        }
        return key;
    }

    // 2. Corrigido para a versão 1.5 (que é a estável)
    private static final String MODEL_NAME = "gemini-1.5-flash"; 

    public String analisarTendencia(String moeda) {
        try {
            String apiKey = getApiKey();
            String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_NAME + ":generateContent?key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            
            // Prompt
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
            Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);

            // Lê a resposta
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return "🤖 IA (v1.5): " + parts.get(0).get("text");

        } catch (Exception e) {
            e.printStackTrace(); // Isso vai mostrar o erro real no LOG do Render
            return "❌ Erro ao consultar IA. (Verifique Logs)";
        }
    }
}
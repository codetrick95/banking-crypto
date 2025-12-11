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

    // Configuração do Modelo de IA: Utilizamos a versão 'Flash' (2.5) por ser a mais atual, rápida e econômica.
    private static final String MODEL_NAME = "gemini-2.5-flash"; 

    /**
     * Método responsável por obter a Chave de Segurança (API Key) do sistema.
     * Busca nas configurações do servidor (Variáveis de Ambiente) para manter a segurança dos dados.
     */
    private String getApiKey() {
        String key = System.getenv("GEMINI_API_KEY");
        
        // Caso não encontre a chave (ex: ambiente de teste local), retorna um valor de segurança.
        if (key == null || key.isEmpty()) {
            return "CHAVE_LOCAL_PARA_TESTES"; 
        }
        return key;
    }

    /**
     * Função principal: Recebe o nome de uma criptomoeda, consulta a IA e retorna a recomendação financeira.
     * Fluxo: 1. Autentica -> 2. Monta a pergunta -> 3. Envia ao Google -> 4. Processa a resposta.
     */
    public String analisarTendencia(String moeda) {
        try {
            String apiKey = getApiKey();
            
            // Endereço oficial da API do Google (Versão Beta necessária para os modelos mais recentes)
            String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_NAME + ":generateContent?key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            
            // O "Prompt": A instrução exata que enviamos para a Inteligência Artificial.
            String prompt = "Atue como um consultor financeiro. Faça uma análise curta (máximo 2 linhas) sobre " + moeda + ". Termine com: COMPRA, VENDA ou ESPERA.";

            // Preparação dos dados para envio (Formato JSON estrito exigido pelo Google)
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of("parts", List.of(
                        Map.of("text", prompt)
                    ))
                )
            );

            // Definição dos cabeçalhos HTTP (Informando que estamos trocando dados estruturados)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Disparo da solicitação para a nuvem do Google
            Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);

            // Processamento da resposta: Navega pelo pacote recebido para extrair apenas o texto da análise.
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return "🤖 IA: " + parts.get(0).get("text");

        } catch (Exception e) {
            // Em caso de falha técnica (ex: instabilidade na rede ou limite de uso), registra o erro no sistema.
            e.printStackTrace();
            return "❌ Indisponível no momento. (Erro técnico: " + e.getMessage() + ")";
        }
    }
}

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

    // Definição do Modelo: 'gemini-1.5-flash' é a versão otimizada para velocidade e baixo custo.
    // Evita erros de quota (429) comuns em modelos experimentais.
    private static final String MODEL_NAME = "gemini-1.5-flash"; 

    /**
     * Recupera a chave de API de forma segura.
     * Busca nas Variáveis de Ambiente do sistema (Render) para não expor a chave no código.
     */
    private String getApiKey() {
        String key = System.getenv("GEMINI_API_KEY");
        
        // Verificação de segurança: Se não achar a chave (ex: rodando local sem config), usa um fallback.
        if (key == null || key.isEmpty()) {
            return "CHAVE_LOCAL_PARA_TESTES"; 
        }
        return key;
    }

    /**
     * Método principal: Recebe o nome da moeda e retorna a análise da IA.
     */
    public String analisarTendencia(String moeda) {
        try {
            String apiKey = getApiKey();
            
            // Montagem da URL da API do Google.
            // Utilizamos a versão 'v1beta' que garante compatibilidade total com o modelo Flash.
            String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/" + MODEL_NAME + ":generateContent?key=" + apiKey;

            RestTemplate restTemplate = new RestTemplate();
            
            // O Prompt: A instrução exata que enviamos para a Inteligência Artificial.
            String prompt = "Atue como um consultor financeiro. Faça uma análise curta (máximo 2 linhas) sobre " + moeda + ". Termine com: COMPRA, VENDA ou ESPERA.";

            // Construção do Corpo da Requisição (JSON Body):
            // Estrutura hierárquica exigida pela documentação do Google Gemini (Contents -> Parts -> Text).
            Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                    Map.of("parts", List.of(
                        Map.of("text", prompt)
                    ))
                )
            );

            // Cabeçalhos HTTP: Informa ao servidor que estamos enviando dados em formato JSON.
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Empacota o cabeçalho e o corpo para envio.
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Disparo da Requisição: Envia os dados para o Google e aguarda a resposta.
            Map<String, Object> response = restTemplate.postForObject(apiUrl, entity, Map.class);

            // Processamento da Resposta:
            // Navega pelo JSON de retorno para extrair apenas a mensagem de texto da IA.
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            
            return "🤖 IA: " + parts.get(0).get("text");

        } catch (Exception e) {
            // Em caso de erro (sem internet, chave inválida, erro no Google), imprime no log do servidor.
            e.printStackTrace();
            return "❌ Indisponível no momento. (Erro: " + e.getMessage() + ")";
        }
    }
}
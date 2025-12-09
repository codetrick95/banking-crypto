# 🪙 Banking Crypto - Simulador de Exchange

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-green)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue)
![Status](https://img.shields.io/badge/Status-Concluído-success)

## 📖 Sobre o Projeto
O **Banking Crypto** é um sistema web desenvolvido para simular uma corretora de criptomoedas (Exchange). O projeto foi criado como parte da disciplina de Programação Orientada a Objetos, integrando conceitos fundamentais de lógica de programação com uma arquitetura moderna baseada em microsserviços.

O sistema permite gerenciar uma carteira digital, realizar transações de compra e venda com cotações dinâmicas e consultar tendências de mercado utilizando **Inteligência Artificial (Google Gemini)**.

---

## 🚀 Funcionalidades Principais

* **📊 Mercado em Tempo Real:** Simulação de variação de preços (oscilação de +/- 3%) a cada atualização, utilizando a classe `Random` do Java.
* **💼 Gestão de Carteira:** Depósito de fundos, compra e venda de ativos (Bitcoin, Ethereum, Solana, etc.) com validação de saldo.
* **🤖 Consultoria via IA:** Integração com a API do Google Gemini 1.5 Flash para análises financeiras automatizadas sobre as moedas.
* **📝 Histórico de Transações:** Registro detalhado de todas as operações (Data, Tipo, Valor e Cotação na época).
* **🔐 Segurança de Dados:** Proteção de dados sensíveis e encapsulamento de saldo.

---

## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 21, Spring Boot (Web, DevTools).
* **Frontend:** HTML5, CSS3, JavaScript (Fetch API).
* **Build & Deploy:** Maven, Docker, Render.com.
* **Integração:** Google Generative AI (Gemini).

---

## 📚 Aplicação de Conceitos Acadêmicos

Este projeto demonstra a aplicação prática dos conteúdos estudados:

### 1. Orientação a Objetos (OO)
* **Classe vs. Objeto:** Definição de modelos (`Usuario`, `CriptoMoeda`) e sua instanciação dinâmica.
* **Encapsulamento:** Uso de modificadores `private` em atributos críticos (como `saldoReais` na classe `Carteira`) com acesso restrito via Getters/Setters.
* **Polimorfismo:** Utilização de Interfaces (`List`, `Map`) para manipulação genérica de coleções de dados.
* **Pacotes:** Organização do código em camadas lógicas (`model`, `service`, `controller`, `util`).

### 2. Lógica de Programação
* **Estruturas de Controle:** Uso de `if/else` para validação de regras de negócio (ex: verificar saldo suficiente antes da compra).
* **Laços de Repetição:** Uso de `for-each` para percorrer listas de ativos e aplicar variações de preço.
* **Coleções Dinâmicas:** Uso de `ArrayList` e `HashMap` para superar as limitações de vetores estáticos.

### 3. Segurança e Boas Práticas
* **Variáveis de Ambiente:** A chave da API de Inteligência Artificial não fica exposta no código-fonte (`Hardcoded`). Ela é injetada via `System.getenv("GEMINI_API_KEY")`, seguindo as melhores práticas de segurança para projetos em nuvem (12-Factor App).

---

## 🔑 Configuração da API (Importante!)

Para que a funcionalidade de Inteligência Artificial funcione, você precisa de uma chave de API do Google Gemini.

### 1. Obtenha a Chave
Gere sua chave gratuitamente no [Google AI Studio](https://aistudio.google.com/app/apikey).

### 2. Configurando a Variável de Ambiente
O sistema busca a chave na variável de ambiente `GEMINI_API_KEY`.

#### Rodando Localmente (Terminal):
**Windows (CMD):**
```cmd
set GEMINI_API_KEY=sua_chave_aqui
./mvnw spring-boot:run
Linux/Mac:

Bash

export GEMINI_API_KEY=sua_chave_aqui
./mvnw spring-boot:run
Rodando com Docker:
Ao rodar o container, passe a chave com a flag -e:

Bash

docker run -p 8080:8080 -e GEMINI_API_KEY=sua_chave_aqui banking-crypto
⚙️ Como Executar o Projeto
Pré-requisitos
Java JDK 21 instalado.

Maven instalado.

Passo a Passo Local
Clone o repositório:

Bash

git clone [https://github.com/codetrick95/banking-crypto.git](https://github.com/codetrick95/banking-crypto.git)
Entre na pasta do projeto e execute (lembrando de configurar a API Key antes):

Bash

./mvnw spring-boot:run
Acesse no navegador: http://localhost:8080

🐳 Construindo a Imagem Docker
Se você tiver o Docker instalado, basta rodar:

Bash

docker build -t banking-crypto .
📂 Estrutura do Projeto
br.com.cripto
├── controller  # Recebe as requisições HTTP do site (O "Garçom")
├── model       # Classes que definem os objetos (Usuario, Moeda, Transacao)
├── service     # Regras de negócio, cálculos e conexão com IA (A "Cozinha")
├── factory     # Criação inicial das moedas
└── util        # Utilitários de cálculo e formatação

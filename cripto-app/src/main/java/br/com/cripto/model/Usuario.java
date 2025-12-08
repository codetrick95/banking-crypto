package br.com.cripto.model;

import java.time.LocalDate;

public class Usuario {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private LocalDate dataNascimento;

    public Usuario(String nome, String cpf, String email, String telefone, String senha, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
    }

    // --- GETTERS (Para o site ler os dados) ---
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getSenha() { return senha; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    // --- SETTERS (Para o site alterar os dados) ---
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
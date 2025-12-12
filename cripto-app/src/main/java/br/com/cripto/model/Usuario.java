package br.com.cripto.model;

import java.time.LocalDate;

/**
 * Representa um usuário cadastrado na plataforma.
 * Contém dados pessoais, informações de contato e credenciais de acesso.
 */
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

   // --- Métodos de Acesso (Getters) ---
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getSenha() { return senha; }
    public LocalDate getDataNascimento() { return dataNascimento; }

    // --- Métodos de Atualização (Setters) ---
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}

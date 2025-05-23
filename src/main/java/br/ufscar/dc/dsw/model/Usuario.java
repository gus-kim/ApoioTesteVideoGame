package br.ufscar.dc.dsw.model;

// Modelo que representa um usuário do sistema, podendo ter diferentes papéis (ex: ADMIN, etc.)
public class Usuario {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String papel;

    public Usuario() {}

    public Usuario(String nome, String email, String senha, String papel) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPapel() { return papel; }
    public void setPapel(String papel) { this.papel = papel; }
}

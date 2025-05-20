package br.ufscar.dc.dsw.model;

public class Estrategia {
    private Long id;
    private String nome;
    private String descricao;
    private String exemplos;
    private String dicas;
    private String imagemUrl;

    public Estrategia() {}

    public Estrategia(Long id, String nome, String descricao, String exemplos, String dicas, String imagemUrl) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.exemplos = exemplos;
        this.dicas = dicas;
        this.imagemUrl = imagemUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getExemplos() { return exemplos; }
    public void setExemplos(String exemplos) { this.exemplos = exemplos; }

    public String getDicas() { return dicas; }
    public void setDicas(String dicas) { this.dicas = dicas; }

    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
}

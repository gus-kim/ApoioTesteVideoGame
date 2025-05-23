package br.ufscar.dc.dsw.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Projeto {
    private Long id;
    private String nome;
    private String descricao;
    private Timestamp dataCriacao;
    private List<Long> membros = new ArrayList<>();  // Novo campo

    public Projeto() {
    }

    public Projeto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Projeto(Long id, String nome, String descricao, Timestamp dataCriacao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
    }

    public List<Long> getMembros() {
        return membros;
    }

    public void setMembros(List<Long> membros) {
        this.membros = membros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    // Atualização do toString
    @Override
    public String toString() {
        return "Projeto [id=" + id + ", nome=" + nome + ", descricao=" + descricao
                + ", dataCriacao=" + dataCriacao + ", membros=" + membros + "]";
    }
}
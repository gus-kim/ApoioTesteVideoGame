package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.model.Projeto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MockedProjectDAO {

    public MockedProjectDAO() {
    }

    public List<Projeto> listarTodos() {
        List<Projeto> projetos = new ArrayList<>();

        // Projeto 1
        Projeto p1 = new Projeto();
        p1.setId(1L);
        p1.setNome("Projeto: Jogo de Corrida");
        p1.setDescricao("Testes no game de corrida urbana.");
        p1.setDataCriacao(Timestamp.valueOf("2025-05-23 02:52:21"));

        // Projeto 2
        Projeto p2 = new Projeto();
        p2.setId(2L);
        p2.setNome("Projeto: Aventura 3D");
        p2.setDescricao("Game estilo RPG com mapas abertos.");
        p2.setDataCriacao(Timestamp.valueOf("2025-05-23 02:52:21"));

        projetos.add(p1);
        projetos.add(p2);

        return projetos;
    }

    public Projeto getById(Long id) {
        // Primeiro obtemos todos os projetos
        List<Projeto> projetos = listarTodos();

        // Procuramos o projeto com o ID especificado
        for (Projeto projeto : projetos) {
            if (projeto.getId().equals(id)) {
                return projeto;
            }
        }

        // Se não encontrou, retorna null
        return null;
    }

}

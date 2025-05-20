package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.ConexaoBD;
import br.ufscar.dc.dsw.model.Estrategia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstrategiaDAO {

    public void inserir(Estrategia estrategia) throws SQLException {
        String sql = "INSERT INTO Estrategia (nome, descricao, exemplos, dicas, imagem_url) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estrategia.getNome());
            stmt.setString(2, estrategia.getDescricao());
            stmt.setString(3, estrategia.getExemplos());
            stmt.setString(4, estrategia.getDicas());
            stmt.setString(5, estrategia.getImagemUrl());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Estrategia estrategia) throws SQLException {
        String sql = "UPDATE Estrategia SET nome = ?, descricao = ?, exemplos = ?, dicas = ?, imagem_url = ? WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, estrategia.getNome());
            stmt.setString(2, estrategia.getDescricao());
            stmt.setString(3, estrategia.getExemplos());
            stmt.setString(4, estrategia.getDicas());
            stmt.setString(5, estrategia.getImagemUrl());
            stmt.setLong(6, estrategia.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM Estrategia WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Estrategia buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM Estrategia WHERE id = ?";
        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Estrategia estrategia = new Estrategia();
                estrategia.setId(rs.getLong("id"));
                estrategia.setNome(rs.getString("nome"));
                estrategia.setDescricao(rs.getString("descricao"));
                estrategia.setExemplos(rs.getString("exemplos"));
                estrategia.setDicas(rs.getString("dicas"));
                estrategia.setImagemUrl(rs.getString("imagem_url"));
                return estrategia;
            }
        }
        return null;
    }

    public List<Estrategia> listarTodos() throws SQLException {
        List<Estrategia> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estrategia ORDER BY nome";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Estrategia estrategia = new Estrategia();
                estrategia.setId(rs.getLong("id"));
                estrategia.setNome(rs.getString("nome"));
                estrategia.setDescricao(rs.getString("descricao"));
                estrategia.setExemplos(rs.getString("exemplos"));
                estrategia.setDicas(rs.getString("dicas"));
                estrategia.setImagemUrl(rs.getString("imagem_url"));
                lista.add(estrategia);
            }
        }
        return lista;
    }
}

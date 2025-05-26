package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.ConexaoBD;
import br.ufscar.dc.dsw.model.Projeto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public void inserir(Projeto projeto) throws SQLException {
        String sqlProjeto = "INSERT INTO Projeto (nome, descricao, data_criacao) VALUES (?, ?, ?)";
        String sqlMembro = "INSERT INTO MembroProjeto (projeto_id, usuario_id) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtProjeto = conn.prepareStatement(sqlProjeto, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmtMembro = conn.prepareStatement(sqlMembro)) {

                // Insere Projeto
                stmtProjeto.setString(1, projeto.getNome());
                stmtProjeto.setString(2, projeto.getDescricao());
                stmtProjeto.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                stmtProjeto.executeUpdate();

                // Obtém ID gerado
                ResultSet rs = stmtProjeto.getGeneratedKeys();
                if (!rs.next()) {
                    throw new SQLException("Falha ao gerar ID do projeto.");
                }
                long projetoId = rs.getLong(1);

                // Insere Membros
                for (Long usuarioId : projeto.getMembros()) {
                    stmtMembro.setLong(1, projetoId);
                    stmtMembro.setLong(2, usuarioId);
                    stmtMembro.addBatch();
                }
                stmtMembro.executeBatch();

                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void atualizar(Projeto projeto) throws SQLException {
        String sqlProjeto = "UPDATE Projeto SET nome = ?, descricao = ? WHERE id = ?";
        String sqlDeleteMembros = "DELETE FROM MembroProjeto WHERE projeto_id = ?";
        String sqlInsertMembro = "INSERT INTO MembroProjeto (projeto_id, usuario_id) VALUES (?, ?)";

        try (Connection conn = ConexaoBD.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtProjeto = conn.prepareStatement(sqlProjeto);
                 PreparedStatement stmtDelete = conn.prepareStatement(sqlDeleteMembros);
                 PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertMembro)) {

                // Atualiza Projeto
                stmtProjeto.setString(1, projeto.getNome());
                stmtProjeto.setString(2, projeto.getDescricao());
                stmtProjeto.setLong(3, projeto.getId());
                stmtProjeto.executeUpdate();

                // Remove membros antigos
                stmtDelete.setLong(1, projeto.getId());
                stmtDelete.executeUpdate();

                // Insere novos membros
                for (Long usuarioId : projeto.getMembros()) {
                    stmtInsert.setLong(1, projeto.getId());
                    stmtInsert.setLong(2, usuarioId);
                    stmtInsert.addBatch();
                }
                stmtInsert.executeBatch();

                conn.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void excluir(Long id) throws SQLException {
        String sql = "DELETE FROM Projeto WHERE id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Projeto buscarPorId(Long id) throws SQLException {
        String sqlProjeto = "SELECT * FROM Projeto WHERE id = ?";
        String sqlMembros = "SELECT usuario_id FROM MembroProjeto WHERE projeto_id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             PreparedStatement stmtProjeto = conn.prepareStatement(sqlProjeto)) {
            stmtProjeto.setLong(1, id);
            ResultSet rs = stmtProjeto.executeQuery();

            if (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getLong("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setDataCriacao(rs.getTimestamp("data_criacao"));

                // Carrega membros
                try (PreparedStatement stmtMembros = conn.prepareStatement(sqlMembros)) {
                    stmtMembros.setLong(1, id);
                    ResultSet rsMembros = stmtMembros.executeQuery();
                    List<Long> membros = new ArrayList<>();
                    while (rsMembros.next()) {
                        membros.add(rsMembros.getLong("usuario_id"));
                    }
                    projeto.setMembros(membros);
                }
                return projeto;
            }
            return null;
        }
    }

    public List<Projeto> listarTodos() throws SQLException {
        List<Projeto> projetos = new ArrayList<>();
        String sqlProjeto = "SELECT * FROM Projeto";
        String sqlMembros = "SELECT usuario_id FROM MembroProjeto WHERE projeto_id = ?";

        try (Connection conn = ConexaoBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlProjeto)) {

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getLong("id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setDescricao(rs.getString("descricao"));
                projeto.setDataCriacao(rs.getTimestamp("data_criacao"));

                // Carrega membros para cada projeto
                try (PreparedStatement stmtMembros = conn.prepareStatement(sqlMembros)) {
                    stmtMembros.setLong(1, projeto.getId());
                    ResultSet rsMembros = stmtMembros.executeQuery();
                    List<Long> membros = new ArrayList<>();
                    while (rsMembros.next()) {
                        membros.add(rsMembros.getLong("usuario_id"));
                    }
                    projeto.setMembros(membros);
                }
                projetos.add(projeto);
            }
        }
        return projetos;
    }
}
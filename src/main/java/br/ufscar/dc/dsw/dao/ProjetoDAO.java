package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.model.Projeto;
import br.ufscar.dc.dsw.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {
    private static final String INSERT_PROJETO =
            "INSERT INTO Projeto (nome, descricao) VALUES (?, ?)";

    private static final String INSERT_MEMBRO =
            "INSERT INTO MembroProjeto (projeto_id, usuario_id) VALUES (?, ?)";

    private static final String SELECT_BY_ID =
            "SELECT * FROM Projeto WHERE id = ?";

    private static final String SELECT_ALL =
            "SELECT * FROM Projeto ORDER BY ";

    private static final String SELECT_MEMBROS =
            "SELECT usuario_id FROM MembroProjeto WHERE projeto_id = ?";

    // Cria um novo projeto com membros
    public boolean createProjeto(Projeto projeto, List<Long> membrosIds) {
        Connection conn = null;
        PreparedStatement stmtProjeto = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // Insere o projeto
            stmtProjeto = conn.prepareStatement(INSERT_PROJETO, Statement.RETURN_GENERATED_KEYS);
            stmtProjeto.setString(1, projeto.getNome());
            stmtProjeto.setString(2, projeto.getDescricao());
            stmtProjeto.executeUpdate();

            // Obtém ID gerado
            ResultSet rs = stmtProjeto.getGeneratedKeys();
            if (!rs.next()) {
                conn.rollback();
                return false;
            }

            long projetoId = rs.getLong(1);

            // Insere membros
            try (PreparedStatement stmtMembros = conn.prepareStatement(INSERT_MEMBRO)) {
                for (Long usuarioId : membrosIds) {
                    stmtMembros.setLong(1, projetoId);
                    stmtMembros.setLong(2, usuarioId);
                    stmtMembros.addBatch();
                }
                stmtMembros.executeBatch();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); }
            catch (SQLException ex) { /* Log error */ }
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.closeConnection(conn, stmtProjeto);
        }
    }

    // Recupera projeto por ID com membros
    public Projeto getProjetoById(long id) {
        Projeto projeto = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                projeto = new Projeto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_criacao")
                );

                // Carrega membros
                projeto.setMembros(getMembrosProjeto(conn, id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projeto;
    }

    // Lista todos projetos com ordenação
    public List<Projeto> listAllProjetos(String ordenacao) {
        List<Projeto> projetos = new ArrayList<>();
        String sql = SELECT_ALL + validarOrdenacao(ordenacao);

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Projeto p = new Projeto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_criacao")
                );
                projetos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projetos;
    }

    // Método auxiliar para carregar membros
    private List<Long> getMembrosProjeto(Connection conn, long projetoId) throws SQLException {
        List<Long> membros = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_MEMBROS)) {
            stmt.setLong(1, projetoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                membros.add(rs.getLong("usuario_id"));
            }
        }
        return membros;
    }

    // Valida parâmetro de ordenação
    private String validarOrdenacao(String ordenacao) {
        return switch (ordenacao) {
            case "data_criacao" -> "data_criacao DESC";
            case "nome" -> "nome ASC";
            default -> "id ASC";
        };
    }
}

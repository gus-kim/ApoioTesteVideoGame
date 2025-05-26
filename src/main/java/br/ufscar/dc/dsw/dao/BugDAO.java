package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.ConexaoBD;
import br.ufscar.dc.dsw.model.Bug;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BugDAO {

    public void insert(Bug bug) {
        String sql = "INSERT INTO Bug (sessao_id, descricao) VALUES (?, ?)";

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setLong(1, bug.getSessaoId());
            statement.setString(2, bug.getDescricao());

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bug.setId(generatedKeys.getLong(1));
            }

            generatedKeys.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Bug bug) {
        String sql = "DELETE FROM Bug WHERE id = ?";

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, bug.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Bug getById(Long id) {
        String sql = "SELECT * FROM Bug WHERE id = ?";
        Bug bug = null;

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                bug = mapResultSetToBug(rs);
            }

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bug;
    }

    public List<Bug> getAll() {
        String sql = "SELECT * FROM Bug";
        List<Bug> bugs = new ArrayList<>();

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Bug bug = mapResultSetToBug(rs);
                bugs.add(bug);
            }

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bugs;
    }

    public List<Bug> getBySessaoId(Long sessaoId) {
        String sql = "SELECT * FROM Bug WHERE sessao_id = ?";
        List<Bug> bugs = new ArrayList<>();

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, sessaoId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Bug bug = mapResultSetToBug(rs);
                bugs.add(bug);
            }

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bugs;
    }

    private Bug mapResultSetToBug(ResultSet rs) throws SQLException {
        Bug bug = new Bug();

        bug.setId(rs.getLong("id"));
        bug.setSessaoId(rs.getLong("sessao_id"));
        bug.setDescricao(rs.getString("descricao"));

        Timestamp ts = rs.getTimestamp("data_registro");
        bug.setDataRegistro(ts != null ? ts.toLocalDateTime() : null);

        return bug;
    }
}


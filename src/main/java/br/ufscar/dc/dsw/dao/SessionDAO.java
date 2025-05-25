package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.ConexaoBD;
import br.ufscar.dc.dsw.model.Session;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {

    public void insert(Session session) {
        String sql = "INSERT INTO SessaoTeste (projeto_id, testador_id, estrategia_id, tempo_minutos, descricao, status, data_criacao, data_inicio, data_fim) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            statement.setLong(1, session.getProjectId());
            statement.setLong(2, session.getTesterId());
            statement.setLong(3, session.getStrategyId());
            statement.setInt(4, session.getMinutesDuration());
            statement.setString(5, session.getDescription());
            statement.setString(6, session.getStatus().toString());
            statement.setTimestamp(7, Timestamp.valueOf(session.getCreationDate()));
            statement.setTimestamp(8, session.getStartDate() != null ? Timestamp.valueOf(session.getStartDate()) : null);
            statement.setTimestamp(9, session.getEndDate() != null ? Timestamp.valueOf(session.getEndDate()) : null);

            statement.executeUpdate();

            // Get generated ID
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                session.setId(generatedKeys.getLong(1));
            }

            generatedKeys.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Session session) {
        String sql = "UPDATE SessaoTeste SET projeto_id = ?, testador_id = ?, estrategia_id = ?, tempo_minutos = ?, descricao = ?, status = ?, data_criacao = ?, data_inicio = ?, data_fim = ? WHERE id = ?";

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);


            statement.setLong(1, session.getProjectId());
            statement.setLong(2, session.getTesterId());
            statement.setLong(3, session.getStrategyId());
            statement.setInt(4, session.getMinutesDuration());
            statement.setString(5, session.getDescription());
            statement.setString(6, session.getStatus().toString());
            statement.setTimestamp(7, Timestamp.valueOf(session.getCreationDate()));
            statement.setTimestamp(8, session.getStartDate() != null ? Timestamp.valueOf(session.getStartDate()) : null);
            statement.setTimestamp(9, session.getEndDate() != null ? Timestamp.valueOf(session.getEndDate()) : null);
            statement.setLong(10, session.getId());

            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Session session) {
        String sql = "DELETE FROM SessaoTeste WHERE id = ?";

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, session.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Session getById(Long id) {
        String sql = "SELECT * FROM SessaoTeste WHERE id = ?";
        Session session = null;

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                session = mapResultSetToSession(resultSet);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return session;
    }

    public List<Session> getAll() {
        String sql = "SELECT * FROM SessaoTeste";
        List<Session> sessions = new ArrayList<>();

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Session session = mapResultSetToSession(resultSet);
                sessions.add(session);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessions;
    }


    public List<Session> getByStatus(Session.SessionStatus status) {
        String sql = "SELECT * FROM SessaoTeste WHERE status = ?";
        List<Session> sessions = new ArrayList<>();

        try {
            Connection conn = ConexaoBD.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, status.toString());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Session session = mapResultSetToSession(resultSet);
                sessions.add(session);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessions;
    }

    private Session mapResultSetToSession(ResultSet resultSet) throws SQLException {
        Session session = new Session();
        session.setId(resultSet.getLong("id"));
        session.setProjectId(resultSet.getLong("projeto_id"));
        session.setTesterId(resultSet.getLong("testador_id"));
        session.setStrategyId(resultSet.getLong("estrategia_id"));
        session.setMinutesDuration(resultSet.getInt("tempo_minutos"));
        session.setDescription(resultSet.getString("descricao"));
        session.setStatus(Session.SessionStatus.valueOf(resultSet.getString("status")));
        session.setCreationDate(resultSet.getTimestamp("data_criacao").toLocalDateTime());

        Timestamp startDate = resultSet.getTimestamp("data_inicio");
        if (startDate != null) {
            session.setStartDate(startDate.toLocalDateTime());
        }

        Timestamp endDate = resultSet.getTimestamp("data_fim");
        if (endDate != null) {
            session.setEndDate(endDate.toLocalDateTime());
        }

        return session;
    }
}
package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carrega driver MySQL
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver não encontrado", e);
        }
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/sistema_testes",
                "root",
                "root"
        );
    }

    //ADMIN -----------------------------------------------

    public void criarAdmin(Usuario admin) throws SQLException {
        if (existeEmail(admin.getEmail())) {
            throw new SQLException("E-mail já cadastrado!");
        }

        String sql = "INSERT INTO Usuario (nome, email, senha, papel) VALUES (?, ?, ?, 'ADMIN')";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNome());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getSenha());
            stmt.executeUpdate();
        }
    }

    public List<Usuario> listarAdmins() throws SQLException {
        List<Usuario> admins = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM Usuario WHERE papel = 'ADMIN'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                admins.add(u);
            }
        }
        return admins;
    }

    //TESTADOR ------------------------------------

    public void criarTester(Usuario tester) throws SQLException {
        if (existeEmail(tester.getEmail())) {
            throw new SQLException("E-mail já cadastrado!");
        }

        String sql = "INSERT INTO Usuario (nome, email, senha, papel) VALUES (?, ?, ?, 'TESTADOR')";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tester.getNome());
            stmt.setString(2, tester.getEmail());
            stmt.setString(3, tester.getSenha());
            stmt.executeUpdate();
        }
    }

    public List<Usuario> listarTesters() throws SQLException {
        List<Usuario> testers = new ArrayList<>();
        String sql = "SELECT id, nome, email FROM Usuario WHERE papel = 'TESTADOR'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                testers.add(u);
            }
        }
        return testers;
    }

    //ALL USERS ---------------------------------

    public void atualizarUsuario(Usuario user) throws SQLException {
        if (existeEmail(user.getEmail(), user.getId())) {
            throw new SQLException("E-mail já cadastrado!");
        }

        String sql = "UPDATE Usuario SET nome = ?, email = ?, senha = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setLong(4, user.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirUsuario(Long id) throws SQLException {
        String sql = "DELETE FROM Usuario WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorId(Long id) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getLong("id"));
                    u.setNome(rs.getString("nome"));
                    u.setEmail(rs.getString("email"));
                    u.setSenha(rs.getString("senha"));
                    u.setPapel(rs.getString("papel"));
                    return u;
                }
            }
        }
        return null;
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Usuario WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPapel(rs.getString("papel"));
                    return usuario;
                }
            }
        }
        return null;
    }

    // Verifica se já existe usuário com o e-mail informado (para criação)
    private boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Verifica se já existe outro usuário com mesmo e-mail (para edição)
    private boolean existeEmail(String email, Long id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Usuario WHERE email = ? AND id <> ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setLong(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}

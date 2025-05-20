package br.ufscar.dc.dsw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_testes?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";  // ajuste conforme seu usuário do BD
    private static final String PASSWORD = "root";  // ajuste a senha

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver MySQL 8+
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

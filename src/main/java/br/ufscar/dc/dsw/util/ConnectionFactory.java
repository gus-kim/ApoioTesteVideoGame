package br.ufscar.dc.dsw.util;

import java.sql.*;
import java.util.Properties;
import java.io.InputStream;

public class ConnectionFactory {
    private static final String PROPERTIES_FILE = "database.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
        registerDriver();
    }

    private static void loadProperties() {
        try (InputStream input = ConnectionFactory.class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE)) {

            if (input == null) {
                throw new RuntimeException("Arquivo " + PROPERTIES_FILE + " não encontrado no classpath");
            }

            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco", e);
        }
    }

    private static void registerDriver() {
        try {
            Class.forName(PROPERTIES.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = PROPERTIES.getProperty("jdbc.url");
        String user = PROPERTIES.getProperty("jdbc.user");
        String password = PROPERTIES.getProperty("jdbc.password");

        return DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection(Connection conn) {
        close(conn);
    }

    public static void closeConnection(Connection conn, Statement stmt) {
        close(stmt);
        close(conn);
    }

    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(conn);
    }

    private static void close(AutoCloseable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (Exception e) {
                System.err.println("Erro ao fechar recurso: " + e.getMessage());
            }
        }
    }
}
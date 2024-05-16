package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteService implements DatabaseConnectionService {
    private final String dbPath;
    public Connection connect() throws SQLException {
        var conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        conn.setAutoCommit(false);
        return conn;
    }
    public SQLiteService(String dbPath) {
        this.dbPath = dbPath;
    }
}

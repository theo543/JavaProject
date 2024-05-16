package service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionService {
    public Connection connect() throws SQLException;
}

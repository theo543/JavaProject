package service;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionService {
    Connection connect() throws SQLException;
}

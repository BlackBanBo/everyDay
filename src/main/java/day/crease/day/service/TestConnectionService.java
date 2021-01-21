package day.crease.day.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface TestConnectionService {


    Connection getConnection(Connection conn, String url, String userName, String password, String driver) throws SQLException;
}

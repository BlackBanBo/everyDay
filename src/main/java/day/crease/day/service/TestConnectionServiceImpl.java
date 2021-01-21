package day.crease.day.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class TestConnectionServiceImpl implements TestConnectionService{

    @Override
    public Connection getConnection(Connection conn, String url, String userName, String password, String driver) throws SQLException {
        if (conn == null) {
            try {
                if (url == null || userName == null || password == null) {
                    throw new NullPointerException();
                }
                Class.forName(driver);
                conn = DriverManager.getConnection(url, userName, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}

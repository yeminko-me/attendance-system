package attendance.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private Connection conn;
    private static DAO singleton;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private DAO() {
        try {
            this.conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/attendance_db",
                "root", "C@s38vxuaj");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static DAO getSingleton() {
        if (singleton == null) {
            singleton = new DAO();
        }
        return singleton;
    }

    public Connection getConnection() {
        return conn;
    }
}

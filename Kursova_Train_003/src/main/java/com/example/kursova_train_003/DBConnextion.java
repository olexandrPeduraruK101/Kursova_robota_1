package com.example.kursova_train_003;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnextion {
    static String user ="root";
    static String password ="12345";
    static String url ="jdbc:mysql://localhost/train_database_001";
    static String driver ="com.mysql.cj.jdbc.Driver";

    public static Connection getCon() {
        Connection con = null;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url,user,password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return con;
    }
}

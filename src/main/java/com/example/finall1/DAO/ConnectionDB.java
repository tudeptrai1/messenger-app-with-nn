
package com.example.finall1.DAO;


import java.sql.*;
import javax.swing.*;


public class ConnectionDB {

    private static String URL = "jdbc:mysql://localhost:3306/chat11?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=utf-8";
    private static String USER = "root";
    private static String PASS = "";

    private Connection conn = null;

    
    
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    

    public ConnectionDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến server");
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println("Không thể đóng kết nối : " + e);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbconnecttest;

import app.dbconnect.DBConnector;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class DbconnectTest {
    public static void main(String[] args) {
        Connection conn = new DBConnector().getConnection();
        System.out.println(conn);
    }
}

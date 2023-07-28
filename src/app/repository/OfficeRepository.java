/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Office;
import java.util.UUID;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class OfficeRepository {

    public static OfficeRepository getInstance() {
        return new OfficeRepository();
    }

    public Office selectById(UUID id) {
        Office office = new Office();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select * from ChucVu\n"
                    + "where id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                office.setId(rs.getObject(1, UUID.class));
                office.setCode(rs.getString(2));
                office.setName(rs.getString(3));
            }
            conn.close();
            return office;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public Office selectByRole(boolean role){
        Office office = new Office();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "";
            if (role) {
                sql = "Select * from ChucVu\n"
                        + "where Ma = 'ST2'";
            }else{
                sql = "Select * from ChucVu\n"
                        + "where Ma = 'ST1'";
            }
            
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {                
                office.setId(rs.getObject(1, UUID.class));
                office.setCode(rs.getString(2));
                office.setName(rs.getString(3));
            }
            conn.close();
            return office;
        } catch (SQLException e) {
            return null;
        }
    }
    
}

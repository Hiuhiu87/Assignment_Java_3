/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.ColorProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class ColorRepository implements ModelRepository<ColorProduct> {

    public static ColorRepository getInstance() {
        return new ColorRepository();
    }

    public ColorProduct selectByName(String name) {
        ColorProduct color = new ColorProduct();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from MauSac WHERE Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                color.setId(rs.getObject(1, UUID.class));
                color.setCode(rs.getString(2));
                color.setName(rs.getString(3));
            }
            conn.close();
            return color;
        } catch (SQLException e) {
            return null;
        }
    }

    public ColorProduct selectByUUID(UUID id) {
        ColorProduct colorProduct = new ColorProduct();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from MauSac WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                colorProduct.setId(rs.getObject(1, UUID.class));
                colorProduct.setCode(rs.getString(2));
                colorProduct.setName(rs.getString(3));
            }
            conn.close();
            return colorProduct;
        } catch (SQLException e) {
            return null;
        }
    }

    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(Ma) FROM MauSac";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "MS1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "MS" + nextNumber;
                return nextCode;
            } else {
                return "MS1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stm != null) {
                    stm.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ArrayList<String> getNameColor() {
        ArrayList<String> listColor = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select Ten FROM MauSac";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listColor.add(rs.getString(1));
            }
            conn.close();
            return listColor;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int insert(ColorProduct t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO MauSac\n"
                    + "VALUES(?, ?, ?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, t.getId());
            stm.setString(2, t.getCode());
            stm.setString(3, t.getName());
            int res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int update(ColorProduct t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public ArrayList<ColorProduct> getAll() {
        ArrayList<ColorProduct> listColor = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select * from MauSac";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ColorProduct colorProduct = new ColorProduct();
                colorProduct.setId(rs.getObject(1, UUID.class));
                colorProduct.setCode(rs.getString(2));
                colorProduct.setName(rs.getString(3));
                listColor.add(colorProduct);
            }
            conn.close();
            return listColor;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public ColorProduct selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

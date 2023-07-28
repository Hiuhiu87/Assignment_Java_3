/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.dbconnect.DBConnector;
import app.model.ProductType;
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
public class ProductTypeDAO implements ModelDAO<ProductType>{
    
    public static ProductTypeDAO getInstance() {
        return new ProductTypeDAO();
    }
    
    public ProductType selectByName(String name) {
        ProductType prdType = new ProductType();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from DongSP WHERE Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                prdType.setId(rs.getObject(1, UUID.class));
                prdType.setCode(rs.getString(2));
                prdType.setName(rs.getString(3));
            }
            conn.close();
            return prdType;
        } catch (SQLException e) {
            return null;
        }
    }

    public ProductType selectByUUID(UUID id) {
        ProductType productType = new ProductType();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from DongSP WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                productType.setId(rs.getObject(1, UUID.class));
                productType.setCode(rs.getString(2));
                productType.setName(rs.getString(3));
            }
            conn.close();
            return productType;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ArrayList<String> getNameType() {
        ArrayList<String> listNameType = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select Ten FROM DongSP";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listNameType.add(rs.getString(1));
            }
            conn.close();
            return listNameType;
        } catch (SQLException e) {
            return null;
        }
    }
    
    @Override
    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(Ma) FROM DongSP";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "TSP1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(3));
                int nextNumber = lastNumber + 1;
                String nextCode = "TSP" + nextNumber;
                return nextCode;
            } else {
                return "TSP1";
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

    @Override
    public int insert(ProductType t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO DongSP\n"
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
    public int update(ProductType t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public ArrayList<ProductType> getAll() {
        ArrayList<ProductType> listProductType = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM DongSP";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ProductType prdType = new ProductType();
                prdType.setId(rs.getObject(1, UUID.class));
                prdType.setCode(rs.getString(2));
                prdType.setName(rs.getString(3));
                listProductType.add(prdType);
            }
            conn.close();
            return listProductType;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ProductType selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

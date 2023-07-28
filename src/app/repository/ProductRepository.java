/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Product;
import java.util.ArrayList;
import java.sql.*;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class ProductRepository implements ModelRepository<Product> {

    public static ProductRepository getInstance() {
        return new ProductRepository();
    }

    @Override
    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(Ma) FROM SanPham";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "SP1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "SP" + nextNumber;
                return nextCode;
            } else {
                return "SP1";
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
    public int insert(Product t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO SanPham\n"
                    + "VALUES (?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            UUID id = UUID.randomUUID();
            stm.setObject(1, id);
            stm.setString(2, t.getCode());
            stm.setString(3, t.getName());
            stm.setInt(4, t.getQuantity());
            int result = stm.executeUpdate();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Product t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "UPDATE SanPham\n"
                    + "SET Ten = ? \n"
                    + "WHERE Ma = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, t.getName());
            stm.setString(2, t.getCode());
            int result = stm.executeUpdate();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> listProducts = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM SanPham";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product prd = new Product();
                prd.setCode(rs.getString(2));
                prd.setName(rs.getString(3));
                prd.setQuantity(rs.getInt(4));
                listProducts.add(prd);
            }
            conn.close();
            return listProducts;
        } catch (SQLException e) {
            return null;
        }
    }

    public Product selectByName(String name) {
        Product prd = new Product();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from SanPham WHERE Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                prd.setId(rs.getObject(1, UUID.class));
                prd.setCode(rs.getString(2));
                prd.setName(rs.getString(3));
                prd.setQuantity(rs.getInt(4));
            }
            conn.close();
            return prd;
        } catch (SQLException e) {
            return null;
        }
    }

    public Product selectByUUID(UUID id) {
        Product prd = new Product();
        try {
            Connection conn = DBConnector.getConnection();
            System.out.println(conn);
            String sql = "SELECT * from SanPham WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                prd.setId(rs.getObject(1, UUID.class));
                prd.setCode(rs.getString(2));
                prd.setName(rs.getString(3));
                prd.setQuantity(rs.getInt(4));
            }
            conn.close();
            return prd;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean updateQuantityProduct(int quantity, String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE SanPham 
                         SET SoLuongTon = ?
                         WHERE Ma = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, quantity);
            preparedStatement.setObject(2, code);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Product selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

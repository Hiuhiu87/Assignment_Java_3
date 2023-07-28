/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CartRepository implements ModelRepository<Cart> {

    public static CartRepository getInstance() {
        return new CartRepository();
    }

    public Cart selectByUUID(UUID id) {
        Cart cart = new Cart();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = """
                         SELECT [Id]
                         ,[IdKH]
                         ,[IdNV]
                         ,[Ma]
                         ,[NgayTao]
                         ,[TinhTrang]
                         FROM [dbo].[GioHang]
                         WHERE [Id] = ?
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                cart.setId(resultSet.getObject(1, UUID.class));
                cart.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                cart.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                cart.setCode(resultSet.getString(4));
                cart.setCreateDate(resultSet.getObject(5, LocalDate.class));
                cart.setPayStatus(resultSet.getInt(6));
            }
            conn.close();
            return cart;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int insert(Cart t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO [dbo].[GioHang]
                                    ([Id]
                                    ,[IdKH]
                                    ,[IdNV]
                                    ,[Ma]
                                    ,[NgayTao]
                                    ,[TinhTrang])
                              VALUES
                                    (?,?,?,?,?,?)
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getId());
            preparedStatement.setObject(2, t.getCustomer().getId());
            preparedStatement.setObject(3, t.getUser().getId());
            preparedStatement.setObject(4, t.getCode());
            preparedStatement.setObject(5, t.getCreateDate());
            preparedStatement.setObject(6, t.getPayStatus());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Cart t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE [dbo].[GioHang]
                                       SET [TinhTrang] = ?
                                     WHERE [Ma] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getPayStatus());
            preparedStatement.setObject(2, t.getCode());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public ArrayList<Cart> getAll() {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [Id]
                               ,[IdKH]
                               ,[IdNV]
                               ,[Ma]
                               ,[NgayTao]
                               ,[TinhTrang]
                           FROM [dbo].[GioHang]
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Cart> listCarts = new ArrayList<>();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setId(resultSet.getObject(1, UUID.class));
                cart.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                cart.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                cart.setCode(resultSet.getString(4));
                cart.setCreateDate(resultSet.getObject(5, LocalDate.class));
                cart.setPayStatus(resultSet.getInt(6));
                listCarts.add(cart);
            }
            return listCarts;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Cart selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Cart selectByCode(String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [Id]
                               ,[IdKH]
                               ,[IdNV]
                               ,[Ma]
                               ,[NgayTao]
                               ,[TinhTrang]
                           FROM [dbo].[GioHang]
                         WHERE [Ma] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            Cart cart = new Cart();
            while (resultSet.next()) {
                cart.setId(resultSet.getObject(1, UUID.class));
                cart.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                cart.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                cart.setCode(resultSet.getString(4));
                cart.setCreateDate(resultSet.getObject(5, LocalDate.class));
                cart.setPayStatus(resultSet.getInt(6));
            }
            return cart;
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
            String sql = "SELECT MAX(Ma) FROM GioHang";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "GH1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "GH" + nextNumber;
                return nextCode;
            } else {
                return "GH1";
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

//    public Cart selectByUUID(UUID id) {
//        try {
//            Connection conn = DBConnector.getConnection();
//            String sql = """
//                         SELECT [Id]
//                                ,[IdKH]
//                                ,[IdNV]
//                                ,[Ma]
//                                ,[NgayTao]
//                                ,[TinhTrang]
//                        FROM [dbo].[GioHang]
//                         WHERE [Id] = ?
//                         """;
//            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setObject(1, id);
//            Cart cart = new Cart();
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                cart.setId(resultSet.getObject(1, UUID.class));
//                cart.setCustomer(CustomerDAO.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
//                cart.setUser(UserDAO.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
//                cart.setCode(resultSet.getString(4));
//                cart.setCreateDate(resultSet.getObject(5, LocalDate.class));
//                cart.setPayStatus(resultSet.getInt(6));
//            }
//            return cart;
//        } catch (SQLException e) {
//            return null;
//        }
//    }
}

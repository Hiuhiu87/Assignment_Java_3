/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class OrderRepository implements ModelRepository<Order> {

    public static OrderRepository getInstance() {
        return new OrderRepository();
    }

    @Override
    public int insert(Order t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO [dbo].[HoaDon]
                                    ([Id]
                                    ,[IdKH]
                                    ,[IdNV]
                                    ,[Ma]
                                    ,[NgayTao]
                                    ,[TinhTrang]
                                    ,[HinhThucThanhToan]
                                    ,[IdGioHang])
                              VALUES
                                    (?,?,?,?,?,?,?,?)
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getId());
            preparedStatement.setObject(2, t.getCustomer().getId());
            preparedStatement.setObject(3, t.getUser().getId());
            preparedStatement.setObject(4, t.getCode());
            preparedStatement.setObject(5, t.getCreateDate());
            preparedStatement.setObject(6, t.getPayStatus());
            preparedStatement.setObject(7, t.getTypePayment());
            preparedStatement.setObject(8, t.getCart().getId());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int update(Order t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE [dbo].[HoaDon]
                                       SET [TinhTrang] = ?
                                          ,[HinhThucThanhToan] = ?
                                     WHERE [Ma] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getPayStatus());
            preparedStatement.setObject(2, t.getTypePayment());
            preparedStatement.setObject(3, t.getCode());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public ArrayList<Order> getAll() {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                        SELECT [Id]
                                                        ,[IdKH]
                                                        ,[IdNV]
                                                        ,[Ma]
                                                        ,[NgayTao]
                                                        ,[TinhTrang]
                                                        ,[HinhThucThanhToan]
                                                        ,[IdGioHang]
                                                        ,[GiaTriHoaDon]
                                                        ,[TienKhachThanhToan]
                                                    FROM [dbo].[HoaDon]
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Order> listOrders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getObject(1, UUID.class));
                order.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                order.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                order.setCode(resultSet.getString(4));
                order.setCreateDate(resultSet.getObject(5, LocalDate.class));
                order.setPayStatus(resultSet.getInt(6));
                order.setTypePayment(resultSet.getString(7));
                order.setCart(CartRepository.getInstance().selectByUUID(resultSet.getObject(8, UUID.class)));
                order.setTotalMoney(resultSet.getBigDecimal(9));
                order.setCustomerMoney(resultSet.getBigDecimal(10));
                listOrders.add(order);
            }
            return listOrders;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Order selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(Ma) FROM HoaDon";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "HD1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "HD" + nextNumber;
                return nextCode;
            } else {
                return "HD1";
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

    public Order selectByCode(String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [Id]
                                                        ,[IdKH]
                                                        ,[IdNV]
                                                        ,[Ma]
                                                        ,[NgayTao]
                                                        ,[TinhTrang]
                                                        ,[HinhThucThanhToan]
                                                        ,[IdGioHang]
                                                        ,[GiaTriHoaDon]
                                                        ,[TienKhachThanhToan]
                                                    FROM [dbo].[HoaDon]
                         WHERE [Ma] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            Order order = new Order();
            while (resultSet.next()) {
                order.setId(resultSet.getObject(1, UUID.class));
                order.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                order.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                order.setCode(resultSet.getString(4));
                order.setCreateDate(resultSet.getObject(5, LocalDate.class));
                order.setPayStatus(resultSet.getInt(6));
                order.setTypePayment(resultSet.getString(7));
                order.setCart(CartRepository.getInstance().selectByUUID(resultSet.getObject(8, UUID.class)));
                order.setTotalMoney(resultSet.getBigDecimal(9));
                order.setCustomerMoney(resultSet.getBigDecimal(10));
            }
            return order;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Order> getOrderByPayStatus(Integer payStatus) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [Id]
                                                        ,[IdKH]
                                                        ,[IdNV]
                                                        ,[Ma]
                                                        ,[NgayTao]
                                                        ,[TinhTrang]
                                                        ,[HinhThucThanhToan]
                                                        ,[IdGioHang]
                                                        ,[GiaTriHoaDon]
                                                        ,[TienKhachThanhToan]
                                                    FROM [dbo].[HoaDon]
                         WHERE [TinhTrang] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, payStatus);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Order> listOrders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getObject(1, UUID.class));
                order.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                order.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                order.setCode(resultSet.getString(4));
                order.setCreateDate(resultSet.getObject(5, LocalDate.class));
                order.setPayStatus(resultSet.getInt(6));
                order.setTypePayment(resultSet.getString(7));
                order.setCart(CartRepository.getInstance().selectByUUID(resultSet.getObject(8, UUID.class)));
                listOrders.add(order);
            }
            return listOrders;
        } catch (SQLException e) {
            return null;
        }
    }

    public Order selectByUUID(UUID id) {
        Order order = new Order();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = """
                         SELECT [Id]
                               ,[IdKH]
                               ,[IdNV]
                               ,[Ma]
                               ,[NgayTao]
                               ,[TinhTrang]
                               ,[HinhThucThanhToan]
                               ,[IdGioHang]
                               ,[GiaTriHoaDon]
                               ,[TienKhachThanhToan]
                           FROM [dbo].[HoaDon]
                         WHERE [Id] = ?
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                order.setId(resultSet.getObject(1, UUID.class));
                order.setCustomer(CustomerRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                order.setUser(UserRepository.getInstance().selectByUUID(resultSet.getObject(3, UUID.class)));
                order.setCode(resultSet.getString(4));
                order.setCreateDate(resultSet.getObject(5, LocalDate.class));
                order.setPayStatus(resultSet.getInt(6));
                order.setTypePayment(resultSet.getString(7));
                order.setCart(CartRepository.getInstance().selectByUUID(resultSet.getObject(8, UUID.class)));
                order.setTotalMoney(resultSet.getBigDecimal(9));
                order.setCustomerMoney(resultSet.getBigDecimal(10));
            }
            conn.close();
            return order;
        } catch (SQLException e) {
            return null;
        }
    }

    public int changeStatusPayment(String code, Integer status) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE [dbo].[HoaDon]
                            SET [TinhTrang] = ?
                          WHERE [Ma] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, status);
            preparedStatement.setObject(2, code);
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    public int updatePriceAfterPay(BigDecimal priceOrder, BigDecimal priceCustomer, String typePay, String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE [dbo].[HoaDon]
                            SET [TinhTrang] = ?,
                                [GiaTriHoaDon] = ?,
                                [TienKhachThanhToan] = ?,
                                [HinhThucThanhToan] = ?
                          WHERE [Ma] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int statusPaied = 1;
            preparedStatement.setObject(1, statusPaied);
            preparedStatement.setObject(2, priceOrder);
            preparedStatement.setObject(3, priceCustomer);
            preparedStatement.setObject(4, typePay);
            preparedStatement.setObject(5, code);
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.Customer;
import app.model.OrderDetail;
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
public class CustomerRepository implements ModelRepository<Customer> {

    public static CustomerRepository getInstance() {
        return new CustomerRepository();
    }

    @Override
    public int insert(Customer t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO [dbo].[KhachHang]
                                    ([Id]
                                    ,[Ma]
                                    ,[Ten]
                                    ,[TenDem]
                                    ,[Ho]
                                    ,[NgaySinh]
                                    ,[Sdt]
                                    ,[DiaChi])
                              VALUES
                                    (?, ?, ?, ?, ?, ?, ?, ?)
                         """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, t.getId());
            ps.setObject(2, t.getCode());
            ps.setObject(3, t.getName());
            ps.setObject(4, t.getMiddleName());
            ps.setObject(5, t.getLastName());
            ps.setObject(6, t.getDateOfBirth());
            ps.setObject(7, t.getPhoneNumber());
            ps.setObject(8, t.getAddress());
            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int update(Customer t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE [dbo].[KhachHang]
                                       SET [Ten] = ?
                                          ,[TenDem] = ?
                                          ,[Ho] = ?
                                          ,[NgaySinh] = ?
                                          ,[Sdt] = ?
                                          ,[DiaChi] = ?
                                     WHERE [Ma] = ?
                         """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, t.getName());
            ps.setObject(2, t.getMiddleName());
            ps.setObject(3, t.getLastName());
            ps.setObject(4, t.getDateOfBirth());
            ps.setObject(5, t.getPhoneNumber());
            ps.setObject(6, t.getAddress());
            ps.setObject(7, t.getCode());
            int result = ps.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public ArrayList<Customer> getAll() {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [Id]
                               ,[Ma]
                               ,[Ten]
                               ,[TenDem]
                               ,[Ho]
                               ,[NgaySinh]
                               ,[Sdt]
                               ,[DiaChi]
                               ,[email]
                           FROM [dbo].[KhachHang]
                         """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ArrayList<Customer> listCustomers = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getObject(1, UUID.class));
                customer.setCode(rs.getString(2));
                customer.setName(rs.getString(3));
                customer.setMiddleName(rs.getString(4));
                customer.setLastName(rs.getString(5));
                customer.setDateOfBirth(rs.getObject(6, LocalDate.class));
                customer.setPhoneNumber(rs.getString(7));
                customer.setAddress(rs.getString(8));
                customer.setEmail(rs.getString(9));
                listCustomers.add(customer);
            }
            return listCustomers;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Customer selectById(String code) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [Id]
                               ,[Ma]
                               ,[Ten]
                               ,[TenDem]
                               ,[Ho]
                               ,[NgaySinh]
                               ,[Sdt]
                               ,[DiaChi]
                               ,[email]
                           FROM [dbo].[KhachHang]
                         WHERE [Ma] = ?
                         """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, code);
            Customer customer = new Customer();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                customer.setId(rs.getObject(1, UUID.class));
                customer.setCode(rs.getString(2));
                customer.setName(rs.getString(3));
                customer.setMiddleName(rs.getString(4));
                customer.setLastName(rs.getString(5));
                customer.setDateOfBirth(rs.getObject(6, LocalDate.class));
                customer.setPhoneNumber(rs.getString(7));
                customer.setAddress(rs.getString(8));
                customer.setEmail(rs.getString(9));
            }
            return customer;
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
            String sql = "SELECT MAX(Ma) FROM KhachHang";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "KH1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "KH" + nextNumber;
                return nextCode;
            } else {
                return "KH1";
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

    public Customer selectByUUID(UUID id) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = """
                         SELECT [Id]
                               ,[Ma]
                               ,[Ten]
                               ,[TenDem]
                               ,[Ho]
                               ,[NgaySinh]
                               ,[Sdt]
                               ,[DiaChi]
                           FROM [dbo].[KhachHang]
                         WHERE [Id] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, id);
            Customer customer = new Customer();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                customer.setId(rs.getObject(1, UUID.class));
                customer.setCode(rs.getString(2));
                customer.setName(rs.getString(3));
                customer.setMiddleName(rs.getString(4));
                customer.setLastName(rs.getString(5));
                customer.setDateOfBirth(rs.getObject(6, LocalDate.class));
                customer.setPhoneNumber(rs.getString(7));
                customer.setAddress(rs.getString(8));
            }
            return customer;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<OrderDetail> getListBought(String customerCode) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [IdHoaDon]
                               ,[IdChiTietSP]
                               ,[SoLuong]
                               ,[DonGia]
                           FROM [dbo].[HoaDonChiTiet]
                           JOIN HoaDon ON HoaDonChiTiet.IdHoaDon = HoaDon.Id
                           JOIN KhachHang ON HoaDon.IdKH = KhachHang.Id
                           WHERE KhachHang.Ma = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, customerCode);
            ArrayList<OrderDetail> listOrderDetails = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(OrderRepository.getInstance().selectByUUID(resultSet.getObject(1, UUID.class)));
                orderDetail.setProduct(ProductDetailRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                orderDetail.setQuantity(resultSet.getInt(3));
                orderDetail.setUnitPrice(resultSet.getBigDecimal(4));
                listOrderDetails.add(orderDetail);
            }
            return listOrderDetails;
        } catch (Exception e) {
            return null;
        }
    }

}

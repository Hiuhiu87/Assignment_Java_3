/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.OrderDetail;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class OrderDetailRepository implements ModelRepository<OrderDetail> {

    public static OrderDetailRepository getInstance() {
        return new OrderDetailRepository();
    }

    @Override
    public int insert(OrderDetail t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO [dbo].[HoaDonChiTiet]
                                    ([IdHoaDon]
                                    ,[IdChiTietSP]
                                    ,[SoLuong]
                                    ,[DonGia])
                              VALUES
                                    (?,?,?,?)
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getOrder().getId());
            preparedStatement.setObject(2, t.getProduct().getId());
            preparedStatement.setObject(3, t.getQuantity());
            preparedStatement.setObject(4, t.getUnitPrice());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int update(OrderDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<OrderDetail> getAll() {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [IdHoaDon]
                               ,[IdChiTietSP]
                               ,[SoLuong]
                               ,[DonGia]
                           FROM [dbo].[HoaDonChiTiet]
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
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
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public OrderDetail selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String generateNextModelCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

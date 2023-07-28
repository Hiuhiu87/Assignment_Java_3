/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.CartDetail;
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
public class CartDetailDAO implements ModelRepository<CartDetail> {

    public static CartDetailDAO getInstance() {
        return new CartDetailDAO();
    }

    @Override
    public int insert(CartDetail t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         INSERT INTO [dbo].[GioHangChiTiet]
                                    ([IdGioHang]
                                    ,[IdChiTietSP]
                                    ,[SoLuong]
                                    ,[DonGia])
                              VALUES
                                    (?,?,?,?)
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getCart().getId());
            preparedStatement.setObject(2, t.getProduct().getId());
            preparedStatement.setObject(3, t.getQuantity());
            preparedStatement.setObject(4, t.getUnitPrice());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public int update(CartDetail t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         UPDATE [dbo].[GioHangChiTiet]
                            SET [IdGioHang] = ?
                               ,[IdChiTietSP] = ?
                               ,[SoLuong] = ?
                               ,[DonGia] = ?
                          WHERE [IdGioHang] = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getCart().getId());
            preparedStatement.setObject(2, t.getProduct().getId());
            preparedStatement.setObject(3, t.getQuantity());
            preparedStatement.setObject(4, t.getUnitPrice());
            preparedStatement.setObject(5, t.getCart().getId());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    @Override
    public ArrayList<CartDetail> getAll() {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [IdGioHang]
                               ,[IdChiTietSP]
                               ,[SoLuong]
                               ,[DonGia]
                               ,[DonGiaKhiGiam]
                           FROM [dbo].[GioHangChiTiet]
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<CartDetail> listCarts = new ArrayList<>();
            while (resultSet.next()) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(CartRepository.getInstance().selectByUUID(resultSet.getObject(1, UUID.class)));
                cartDetail.setProduct(ProductDetailRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                cartDetail.setQuantity(resultSet.getInt(3));
                cartDetail.setUnitPrice(resultSet.getBigDecimal(4));
                listCarts.add(cartDetail);
            }
            return listCarts;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<CartDetail> getCartDetailByCartCode(String cartCode) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         SELECT [IdGioHang]
                               ,[IdChiTietSP]
                               ,[SoLuong]
                               ,[DonGia]
                           FROM [dbo].[GioHangChiTiet]
                           JOIN GioHang ON GioHangChiTiet.IdGioHang = GioHang.Id
                           WHERE Ma = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, cartCode);
            ArrayList<CartDetail> list = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(CartRepository.getInstance().selectByUUID(resultSet.getObject(1, UUID.class)));
                cartDetail.setProduct(ProductDetailRepository.getInstance().selectByUUID(resultSet.getObject(2, UUID.class)));
                cartDetail.setQuantity(resultSet.getInt(3));
                cartDetail.setUnitPrice(resultSet.getBigDecimal(4));
                list.add(cartDetail);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CartDetail selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String generateNextModelCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int delete(CartDetail t) {
        try (Connection conn = DBConnector.getConnection()) {
            String sql = """
                         DELETE FROM [dbo].[GioHangChiTiet]
                               WHERE IdGioHang = ? AND IdChiTietSP = ?
                         """;
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, t.getCart().getId());
            preparedStatement.setObject(2, t.getProduct().getId());
            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

}

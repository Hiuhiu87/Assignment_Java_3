/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.User;
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
public class UserRepository implements ModelRepository<User> {

    public static UserRepository getInstance() {
        return new UserRepository();
    }

    @Override
    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(Ma) FROM NhanVien";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "NV1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(2));
                int nextNumber = lastNumber + 1;
                String nextCode = "NV" + nextNumber;
                return nextCode;
            } else {
                return "NV1";
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

    public User login(String email, String password) {
        User user = new User();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select * from NhanVien\n"
                    + "where Email = ? and MatKhau = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    user.setId(rs.getObject(1, UUID.class));
                    user.setCode(rs.getString(2));
                    user.setName(rs.getString(3));
                    user.setMiddleName(rs.getString(4));
                    user.setLastName(rs.getString(5));
                    user.setGender(rs.getBoolean(6));
                    user.setDateOfBirth(rs.getObject(7, LocalDate.class));
                    user.setAddress(rs.getString(8));
                    user.setPhoneNumber(rs.getString(9));
                    user.setPassword(rs.getString(10));
                    user.setOffice(OfficeRepository.getInstance().selectById(rs.getObject(11, UUID.class)));
                    user.setStatus(rs.getBoolean(12));
                    user.setEmail(rs.getString(13));
                }
            }
            conn.close();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public int changePassword(String staffCode, String password) {
        int res = 0;
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "UPDATE NhanVien\n"
                    + "SET MatKhau = ?\n"
                    + "WHERE Ma = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, password);
            stm.setString(2, staffCode);
            res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return res;
        }
    }

    public int changeStatus(String staffCode) {
        int res = 0;
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "UPDATE NhanVien "
                    + "SET TrangThai = ? "
                    + "WHERE Ma = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            User user = selectByCode(staffCode);
            if (user.isStatus()) {
                stm.setBoolean(1, false);
            } else {
                stm.setBoolean(1, true);
            }
            stm.setString(2, staffCode);
            res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return res;
        }
    }

    public User selectByCode(String code) {
        User user = new User();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT Ma, Ten, TenDem, Ho, GioiTinh, NgaySinh, DiaChi, Sdt, idCv, TrangThai, Email FROM NhanVien\n"
                    + "WHERE Ma = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, code);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                user.setCode(rs.getString(1));
                user.setName(rs.getString(2));
                user.setMiddleName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setDateOfBirth(rs.getObject(6, LocalDate.class));
                user.setAddress(rs.getString(7));
                user.setPhoneNumber(rs.getString(8));
                user.setOffice(OfficeRepository.getInstance().selectById(rs.getObject(9, UUID.class)));
                user.setStatus(rs.getBoolean(10));
                user.setEmail(rs.getString(11));
            }
            conn.close();
            return user;
        } catch (SQLException e) {
            return user;
        }
    }

    public ArrayList<User> selectByName(String name) {
        ArrayList<User> listUser = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = """
                            SELECT [Id]
                                  ,[Ma]
                                  ,[Ten]
                                  ,[TenDem]
                                  ,[Ho]
                                  ,[GioiTinh]
                                  ,[NgaySinh]
                                  ,[DiaChi]
                                  ,[Sdt]
                                  ,[MatKhau]
                                  ,[IdCV]
                                  ,[TrangThai]
                                  ,[Email]
                              FROM [dbo].[NhanVien]
                              WHERE Ten LIKE ?
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, "%" + name + "%");
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getObject(1, UUID.class));
                user.setCode(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setMiddleName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setGender(resultSet.getBoolean(6));
                user.setDateOfBirth(resultSet.getObject(7, LocalDate.class));
                user.setAddress(resultSet.getString(8));
                user.setPhoneNumber(resultSet.getString(9));
                user.setOffice(OfficeRepository.getInstance().selectById(resultSet.getObject(11, UUID.class)));
                user.setStatus(resultSet.getBoolean(12));
                user.setEmail(resultSet.getString(13));
                listUser.add(user);
            }
            conn.close();
            return listUser;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int insert(User t) {
        int res = 0;
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO NhanVien\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, t.getId());
            stm.setString(2, t.getCode());
            stm.setString(3, t.getName());
            stm.setString(4, t.getMiddleName());
            stm.setString(5, t.getLastName());
            stm.setBoolean(6, t.isGender());
            stm.setObject(7, t.getDateOfBirth());
            stm.setString(8, t.getAddress());
            stm.setString(9, t.getPhoneNumber());
            stm.setString(10, t.getPassword());
            stm.setObject(11, t.getOffice().getId());
            stm.setBoolean(12, t.isStatus());
            stm.setString(13, t.getEmail());
            res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return res;
        }
    }

    @Override
    public int update(User t) {
        int res = 0;
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "UPDATE NhanVien "
                    + "SET Ten = ?, TenDem = ?, Ho = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, Sdt = ?, IdCv = ?, TrangThai = ?, Email = ? "
                    + " WHERE Ma = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1, t.getName());
            stm.setString(2, t.getMiddleName());
            stm.setString(3, t.getLastName());
            stm.setBoolean(4, t.isGender());
            stm.setObject(5, t.getDateOfBirth());
            stm.setString(6, t.getAddress());
            stm.setString(7, t.getPhoneNumber());
            stm.setObject(8, t.getOffice().getId());
            stm.setBoolean(9, t.isStatus());
            stm.setString(10, t.getEmail());
            stm.setString(11, t.getCode());
            res = stm.executeUpdate();
            conn.close();
            return res;
        } catch (SQLException e) {
            return res;
        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> listUser = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM NhanVien";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getObject(1, UUID.class));
                user.setCode(resultSet.getString(2));
                user.setName(resultSet.getString(3));
                user.setMiddleName(resultSet.getString(4));
                user.setLastName(resultSet.getString(5));
                user.setGender(resultSet.getBoolean(6));
                user.setDateOfBirth(resultSet.getObject(7, LocalDate.class));
                user.setAddress(resultSet.getString(8));
                user.setPhoneNumber(resultSet.getString(9));
                user.setOffice(OfficeRepository.getInstance().selectById(resultSet.getObject(11, UUID.class)));
                user.setStatus(resultSet.getBoolean(12));
                user.setEmail(resultSet.getString(13));
                listUser.add(user);
            }
            conn.close();
            return listUser;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public User selectByUUID(UUID id) {
        User user = new User();
        try (Connection conn = DBConnector.getConnection();) {
            String sql = """
                         SELECT [Ma]
                               ,[Ten]
                               ,[TenDem]
                               ,[Ho]
                               ,[GioiTinh]
                               ,[NgaySinh]
                               ,[DiaChi]
                               ,[Sdt]
                               ,[IdCV]
                               ,[TrangThai]
                               ,[Email]
                           FROM [dbo].[NhanVien]
                         WHERE [Id] = ?
                         """;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                user.setCode(rs.getString(1));
                user.setName(rs.getString(2));
                user.setMiddleName(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setGender(rs.getBoolean(5));
                user.setDateOfBirth(rs.getObject(6, LocalDate.class));
                user.setAddress(rs.getString(7));
                user.setPhoneNumber(rs.getString(8));
                user.setOffice(OfficeRepository.getInstance().selectById(rs.getObject(9, UUID.class)));
                user.setStatus(rs.getBoolean(10));
                user.setEmail(rs.getString(11));
            }
            conn.close();
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
    
}

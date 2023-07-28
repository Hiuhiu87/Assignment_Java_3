/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dao;

import app.dbconnect.DBConnector;
import app.model.Company;
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
public class CompanyDAO implements ModelDAO<Company> {

    public static CompanyDAO getInstance() {
        return new CompanyDAO();
    }

    @Override
    public String generateNextModelCode() {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection();
            String sql = "SELECT MAX(Ma) FROM NSX";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "NSX1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(3));
                int nextNumber = lastNumber + 1;
                String nextCode = "NSX" + nextNumber;
                return nextCode;
            } else {
                return "NSX1";
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

    public ArrayList<String> getNameCompany() {
        ArrayList<String> listNameCompany = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select Ten FROM NSX";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listNameCompany.add(rs.getString(1));
            }
            conn.close();
            return listNameCompany;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public int insert(Company t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO NSX\n"
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
    public int update(Company t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Company> getAll() {
        ArrayList<Company> listCompany = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM NSX";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Company company = new Company();
                company.setId(rs.getObject(1, UUID.class));
                company.setCode(rs.getString(2));
                company.setName(rs.getString(3));
                listCompany.add(company);
            }
            conn.close();
            return listCompany;
        } catch (Exception e) {
            return null;
        }
    }

    public Company selectByName(String name) {
        Company cpn = new Company();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from NSX WHERE Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cpn.setId(rs.getObject(1, UUID.class));
                cpn.setCode(rs.getString(2));
                cpn.setName(rs.getString(3));
            }
            conn.close();
            return cpn;
        } catch (SQLException e) {
            return null;
        }
    }

    public Company selectByUUID(UUID id) {
        Company cpn = new Company();
        try {
            Connection conn = DBConnector.getConnection();
            System.out.println(conn);
            String sql = "SELECT * from NSX WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cpn.setId(rs.getObject(1, UUID.class));
                cpn.setCode(rs.getString(2));
                cpn.setName(rs.getString(3));
            }
            conn.close();
            return cpn;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Company selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

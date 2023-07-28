/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.CPUInfo;
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
public class CPUInforRepository implements ModelRepository<CPUInfo>{
    
    public static CPUInforRepository getInstance() {
        return new CPUInforRepository();
    }
    
    public CPUInfo selectByName(String name) {
        CPUInfo cpu = new CPUInfo();
        try {
            Connection conn = new DBConnector().getConnection();
            String sql = "SELECT * from ThongTinCPU WHERE Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cpu.setId(rs.getObject(1, UUID.class));
                cpu.setCode(rs.getString(2));
                cpu.setName(rs.getString(3));
            }
            conn.close();
            return cpu;
        } catch (SQLException e) {
            return null;
        }
    }

    public CPUInfo selectByUUID(UUID id) {
        CPUInfo cpu = new CPUInfo();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from ThongTinCPU WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                cpu.setId(rs.getObject(1, UUID.class));
                cpu.setCode(rs.getString(2));
                cpu.setName(rs.getString(3));
            }
            conn.close();
            return cpu;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ArrayList<String> getNameCpu() {
        ArrayList<String> listCpu = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select Ten FROM ThongTinCPU";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listCpu.add(rs.getString(1));
            }
            conn.close();
            return listCpu;
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
            String sql = "SELECT MAX(Ma) FROM ThongTinCPU";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "CPU1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(3));
                int nextNumber = lastNumber + 1;
                String nextCode = "CPU" + nextNumber;
                return nextCode;
            } else {
                return "CPU1";
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
    public int insert(CPUInfo t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO ThongTinCPU\n"
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
    public int update(CPUInfo t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<CPUInfo> getAll() {
        ArrayList<CPUInfo> listCpu = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM ThongTinCPU";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                CPUInfo cpu = new CPUInfo();
                cpu.setId(rs.getObject(1, UUID.class));
                cpu.setCode(rs.getString(2));
                cpu.setName(rs.getString(3));
                listCpu.add(cpu);
            }
            conn.close();
            return listCpu;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CPUInfo selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

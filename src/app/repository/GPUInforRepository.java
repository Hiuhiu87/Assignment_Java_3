/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.repository;

import app.dbconnect.DBConnector;
import app.model.GPUInfo;
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
public class GPUInforRepository implements ModelRepository<GPUInfo>{
    
    public static GPUInforRepository getInstance() {
        return new GPUInforRepository();
    }
    
    public GPUInfo selectByName(String name) {
        GPUInfo gpu = new GPUInfo();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from ThongTinGPU WHERE Ten = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, name);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                gpu.setId(rs.getObject(1, UUID.class));
                gpu.setCode(rs.getString(2));
                gpu.setName(rs.getString(3));
            }
            conn.close();
            return gpu;
        } catch (SQLException e) {
            return null;
        }
    }

    public GPUInfo selectByUUID(UUID id) {
        GPUInfo gpu = new GPUInfo();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * from ThongTinGPU WHERE id = ?";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                gpu.setId(rs.getObject(1, UUID.class));
                gpu.setCode(rs.getString(2));
                gpu.setName(rs.getString(3));
            }
            conn.close();
            return gpu;
        } catch (SQLException e) {
            return null;
        }
    }
    
     public ArrayList<String> getNameGpu() {
        ArrayList<String> listGpu = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "Select Ten FROM ThongTinGPU";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listGpu.add(rs.getString(1));
            }
            conn.close();
            return listGpu;
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
            String sql = "SELECT MAX(Ma) FROM ThongTinGPU";
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String lastCode = rs.getString(1);
                if (lastCode == null) {
                    return "GPU1";
                }
                int lastNumber = Integer.parseInt(lastCode.substring(3));
                int nextNumber = lastNumber + 1;
                String nextCode = "GPU" + nextNumber;
                return nextCode;
            } else {
                return "GPU1";
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
    public int insert(GPUInfo t) {
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "INSERT INTO ThongTinGPU\n"
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
    public int update(GPUInfo t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<GPUInfo> getAll() {
        ArrayList<GPUInfo> listGpu = new ArrayList<>();
        try {
            Connection conn = DBConnector.getConnection();
            String sql = "SELECT * FROM ThongTinGPU";
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                GPUInfo gpu = new GPUInfo();
                gpu.setId(rs.getObject(1, UUID.class));
                gpu.setCode(rs.getString(2));
                gpu.setName(rs.getString(3));
                listGpu.add(gpu);
            }
            conn.close();
            return listGpu;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public GPUInfo selectById(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

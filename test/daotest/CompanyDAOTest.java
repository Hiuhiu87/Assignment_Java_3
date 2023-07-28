/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import app.repository.CompanyRepository;
import app.dbconnect.DBConnector;
import app.model.Company;
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
public class CompanyDAOTest {

//    public static void main(String[] args) throws SQLException {
////        ArrayList<Company> listCom = new ArrayList<>();
////        listCom = CompanyDAO.getInstance().getAll();
////        for (Company company : listCom) {
////            System.out.println(company.getId());
////            System.out.println(company.getCode());
////            System.out.println(company.getName());
////        }
//
//        Connection conn = new DBConnector().getConnection();
//        System.out.println(conn);
//        String sql = "SELECT * from NSX WHERE Ten = ?";
//        PreparedStatement stm = conn.prepareStatement(sql);
//        stm.setString(1, "Dell");
//        ResultSet rs = stm.executeQuery();
//        while (rs.next()) {            
//            UUID idCompany = rs.getObject(1, UUID.class);
//            System.out.println(idCompany);
//        }
//        conn.close();
//        
//    }
//    public static void main(String[] args) {
//        UUID id = CompanyDAO.getInstance().selectByName("Asus");
//        System.out.println(id);
//    }
    
//    public static void main(String[] args) {
//        String name = CompanyDAO.getInstance().selectByUUID(UUID.fromString("331B12BC-EE39-4F0B-B0AE-51B9D5E08BDA"));
//        System.out.println(name);
//    }

}

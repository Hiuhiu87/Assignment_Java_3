/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import app.dao.ProductDetailDAO;
import app.model.ProductDetail;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductDetailDAOTest {
    public static void main(String[] args) {
        ArrayList<ProductDetail> serialArrayList = ProductDetailDAO.getInstance().getListByName("Dell");
        System.out.println(serialArrayList.size());
        for (ProductDetail productDetail : serialArrayList) {
            System.out.println(productDetail.getSerial());
        }
    }
}

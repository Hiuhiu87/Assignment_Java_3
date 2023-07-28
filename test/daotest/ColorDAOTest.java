/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daotest;

import app.dao.ColorDAO;
import app.model.ColorProduct;

/**
 *
 * @author Admin
 */
public class ColorDAOTest {
    public static void main(String[] args) {
        ColorProduct colorProduct = ColorDAO.getInstance().selectByName("Trắng");
        System.out.println(colorProduct.getName());
        System.out.println(colorProduct.getCode());
        System.out.println(colorProduct.getId());
    }
}

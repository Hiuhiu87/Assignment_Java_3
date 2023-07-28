/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.ProductDAO;
import app.dao.ProductDetailDAO;
import app.model.Product;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductService {

    public ArrayList<Product> getAll() {
        return ProductDAO.getInstance().getAll();
    }

    public void add(Product prd) {
        ProductDAO.getInstance().insert(prd);
    }

    public void update(Product prd) {
        ProductDAO.getInstance().update(prd);
    }
    
    public boolean updateQuantityProduct(String code){
        int quantityProduct = ProductDetailDAO.getInstance().getQuantityProductById(code);
        return ProductDAO.getInstance().updateQuantityProduct(quantityProduct, code);
    }
    
}

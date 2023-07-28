/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.ProductRepository;
import app.repository.ProductDetailRepository;
import app.model.Product;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductService {

    public ArrayList<Product> getAll() {
        return ProductRepository.getInstance().getAll();
    }

    public void add(Product prd) {
        ProductRepository.getInstance().insert(prd);
    }

    public void update(Product prd) {
        ProductRepository.getInstance().update(prd);
    }
    
    public boolean updateQuantityProduct(String code){
        int quantityProduct = ProductDetailRepository.getInstance().getQuantityProductById(code);
        return ProductRepository.getInstance().updateQuantityProduct(quantityProduct, code);
    }
    
}

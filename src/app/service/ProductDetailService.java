/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.ProductDetailDAO;
import app.model.ProductDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class ProductDetailService {

    public ArrayList<String> getAllNameProduct() {
        return ProductDetailDAO.getInstance().getNameProduct();
    }

    public ArrayList<ProductDetail> getAll() {
        return ProductDetailDAO.getInstance().getAll();
    }

    public int add(ProductDetail prdDetail) {
        return ProductDetailDAO.getInstance().insert(prdDetail);
    }

    public int update(ProductDetail prdDetail) {
        return ProductDetailDAO.getInstance().update(prdDetail);
    }

    public ArrayList<String> getProductSerial(String name) {
        return ProductDetailDAO.getInstance().getListSerial(name);
    }

    public ProductDetail getProductBySerial(String serial) {
        return ProductDetailDAO.getInstance().getProductDetailBySerial(serial);
    }

    public boolean changeProductStatus(String serial) {
        return ProductDetailDAO.getInstance().updateStatusProduct(serial);
    }

    public ArrayList<ProductDetail> searchByPrice(BigDecimal priceMin, BigDecimal pricMax) {
        return ProductDetailDAO.getInstance().selectByPrice(priceMin, pricMax);
    }

    public ArrayList<ProductDetail> searchByName(String name) {
        return ProductDetailDAO.getInstance().getListByName(name);
    }

//    public int getProductQuantity(UUID id){
//        return ProductDetailDAO.getInstance().getQuantityProductById(id);
//    }
    
    public ArrayList<ProductDetail> getListByCode(String code){
        return ProductDetailDAO.getInstance().getListByCode(code);
    }

}

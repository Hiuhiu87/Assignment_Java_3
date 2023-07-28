/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.ProductDetailRepository;
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
        return ProductDetailRepository.getInstance().getNameProduct();
    }

    public ArrayList<ProductDetail> getAll() {
        return ProductDetailRepository.getInstance().getAll();
    }

    public int add(ProductDetail prdDetail) {
        return ProductDetailRepository.getInstance().insert(prdDetail);
    }

    public int update(ProductDetail prdDetail) {
        return ProductDetailRepository.getInstance().update(prdDetail);
    }

    public ArrayList<String> getProductSerial(String name) {
        return ProductDetailRepository.getInstance().getListSerial(name);
    }

    public ProductDetail getProductBySerial(String serial) {
        return ProductDetailRepository.getInstance().getProductDetailBySerial(serial);
    }

    public boolean changeProductStatus(String serial) {
        return ProductDetailRepository.getInstance().updateStatusProduct(serial);
    }

    public ArrayList<ProductDetail> searchByPrice(BigDecimal priceMin, BigDecimal pricMax) {
        return ProductDetailRepository.getInstance().selectByPrice(priceMin, pricMax);
    }

    public ArrayList<ProductDetail> searchByName(String name) {
        return ProductDetailRepository.getInstance().getListByName(name);
    }

//    public int getProductQuantity(UUID id){
//        return ProductDetailDAO.getInstance().getQuantityProductById(id);
//    }
    
    public ArrayList<ProductDetail> getListByCode(String code){
        return ProductDetailRepository.getInstance().getListByCode(code);
    }

}

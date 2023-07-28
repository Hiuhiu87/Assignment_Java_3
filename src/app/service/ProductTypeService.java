/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.ProductTypeRepository;
import app.model.ProductType;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductTypeService {
    public ArrayList<ProductType> getAll() {
        return ProductTypeRepository.getInstance().getAll();
    }

    public void add(ProductType productType) {
        ProductTypeRepository.getInstance().insert(productType);
    }

    public void update(ProductType productType) {
        ProductTypeRepository.getInstance().update(productType);
    }

    public ArrayList<String> getNameType() {
        return ProductTypeRepository.getInstance().getNameType();
    }
}

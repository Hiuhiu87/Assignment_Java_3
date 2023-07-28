/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.ProductTypeDAO;
import app.model.ProductType;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ProductTypeService {
    public ArrayList<ProductType> getAll() {
        return ProductTypeDAO.getInstance().getAll();
    }

    public void add(ProductType productType) {
        ProductTypeDAO.getInstance().insert(productType);
    }

    public void update(ProductType productType) {
        ProductTypeDAO.getInstance().update(productType);
    }

    public ArrayList<String> getNameType() {
        return ProductTypeDAO.getInstance().getNameType();
    }
}

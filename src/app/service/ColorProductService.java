/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.ColorDAO;
import app.model.ColorProduct;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ColorProductService {

    public ArrayList<ColorProduct> getAll() {
        return ColorDAO.getInstance().getAll();
    }

    public void add(ColorProduct color) {
        ColorDAO.getInstance().insert(color);
    }

    public void update(ColorProduct color) {
        ColorDAO.getInstance().update(color);
    }

    public ArrayList<String> getNameColor() {
        return ColorDAO.getInstance().getNameColor();
    }
    
}

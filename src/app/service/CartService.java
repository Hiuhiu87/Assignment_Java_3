/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.CartDAO;
import app.model.Cart;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CartService {

    public boolean add(Cart order) {
        if (order != null) {
            int addSuccess = CartDAO.getInstance().insert(order);
            return addSuccess > 0;
        }
        return false;
    }

    public ArrayList<Cart> getAll() {
        return CartDAO.getInstance().getAll();
    }
    
    public Cart selectByCode(String cartCode){
        return CartDAO.getInstance().selectByCode(cartCode);
    }
}

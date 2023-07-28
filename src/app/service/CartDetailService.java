/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.CartDetailDAO;
import app.model.CartDetail;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CartDetailService {

    public boolean add(CartDetail cartDetail) {
        if (cartDetail != null) {
            int addSuccess = CartDetailDAO.getInstance().insert(cartDetail);
            return addSuccess > 0;
        }
        return false;
    }

    public ArrayList<CartDetail> getAll() {
        return CartDetailDAO.getInstance().getAll();
    }

    public ArrayList<CartDetail> getByCode(String cartCode) {
        return CartDetailDAO.getInstance().getCartDetailByCartCode(cartCode);
    }

    public boolean delete(CartDetail cartDetail) {
        if (cartDetail != null) {
            int deleteSuccess = CartDetailDAO.getInstance().delete(cartDetail);
            return deleteSuccess > 0;
        }
        return false;
    }
}

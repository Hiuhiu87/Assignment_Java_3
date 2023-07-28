/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.CartDetailRepository;
import app.model.CartDetail;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CartDetailService {

    public boolean add(CartDetail cartDetail) {
        if (cartDetail != null) {
            int addSuccess = CartDetailRepository.getInstance().insert(cartDetail);
            return addSuccess > 0;
        }
        return false;
    }

    public ArrayList<CartDetail> getAll() {
        return CartDetailRepository.getInstance().getAll();
    }

    public ArrayList<CartDetail> getByCode(String cartCode) {
        return CartDetailRepository.getInstance().getCartDetailByCartCode(cartCode);
    }

    public boolean delete(CartDetail cartDetail) {
        if (cartDetail != null) {
            int deleteSuccess = CartDetailRepository.getInstance().delete(cartDetail);
            return deleteSuccess > 0;
        }
        return false;
    }
}

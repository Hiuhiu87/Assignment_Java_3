/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.OrderDetailDAO;
import app.model.OrderDetail;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class OrderDetailService {

    public boolean add(OrderDetail orderDetail) {
        if (orderDetail != null) {
            int result = OrderDetailDAO.getInstance().insert(orderDetail);
            return result > 0;
        }
        return false;
    }

    public ArrayList<OrderDetail> getAll() {
        return OrderDetailDAO.getInstance().getAll();
    }
    
}

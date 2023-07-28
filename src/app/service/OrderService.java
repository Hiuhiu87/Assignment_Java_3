/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.OrderRepository;
import app.model.Order;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class OrderService {
    
    public boolean add(Order order){
        if (order != null) {
            int addSuccess = OrderRepository.getInstance().insert(order);
            return addSuccess > 0;
        }
        return false;
    }
    
    public ArrayList<Order> getAll(){
        return OrderRepository.getInstance().getAll();
    }
    
    public Order getByCode(String codeOrder){
        return OrderRepository.getInstance().selectByCode(codeOrder);
    }
    
    public ArrayList<Order> getOrderByStatus(Integer payStatus){
        return OrderRepository.getInstance().getOrderByPayStatus(payStatus);
    }
    
}

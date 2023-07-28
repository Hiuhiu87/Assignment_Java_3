/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.CustomerDAO;
import app.model.Customer;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CustomerService {
    
    public ArrayList<Customer> getAll(){
        return CustomerDAO.getInstance().getAll();
    }
    
    public boolean add(Customer customer){
        if (customer != null) {
            int addSuccess = CustomerDAO.getInstance().insert(customer);
            return addSuccess > 0;
        }
        return false;
    }
    
    public boolean update(Customer customer){
        if (customer != null) {
            int updateSuccess = CustomerDAO.getInstance().insert(customer);
            return updateSuccess > 0;
        }
        return false;
    }
    
    public Customer getCustomer(String code){
        return CustomerDAO.getInstance().selectById(code);
    }
    
}

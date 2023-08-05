/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.CustomerRepository;
import app.model.Customer;
import app.model.OrderDetail;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CustomerService {
    
    public ArrayList<Customer> getAll(){
        return CustomerRepository.getInstance().getAll();
    }
    
    public boolean add(Customer customer){
        if (customer != null) {
            int addSuccess = CustomerRepository.getInstance().insert(customer);
            return addSuccess > 0;
        }
        return false;
    }
    
    public boolean update(Customer customer){
        if (customer != null) {
            int updateSuccess = CustomerRepository.getInstance().insert(customer);
            return updateSuccess > 0;
        }
        return false;
    }
    
    public Customer getCustomer(String code){
        return CustomerRepository.getInstance().selectById(code);
    }
    
    public ArrayList<OrderDetail> getListBought(String code){
        return CustomerRepository.getInstance().getListBought(code);
    }
    
}

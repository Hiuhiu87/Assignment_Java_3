/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import app.dao.CartDAO;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Cart {

    private UUID id;
    private Customer customer;
    private User user;
    private String code;
    private LocalDate createDate;
    private Integer payStatus;

    public Cart() {
    }

    public Cart(Customer customer, User user) {
        this.id = UUID.randomUUID();
        this.customer = customer;
        this.user = user;
        this.code = CartDAO.getInstance().generateNextModelCode();
        this.createDate = LocalDate.now();
        this.payStatus = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusString() {
        if (null == this.payStatus) {
            return "Hủy";
        } else {
            return switch (this.payStatus) {
                case 0 ->
                    "Chờ Thanh Toán";
                case 1 ->
                    "Đã Thanh Toán";
                default ->
                    "Hủy";
            };
        }
    }

}

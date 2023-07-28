/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import app.dao.ProductDAO;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class Product {

    private UUID Id;
    private String code;
    private String name;
    private int quantity;

    public Product() {
    }

    public Product(String code, String name) {
        this.Id = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.quantity = 0;
    }
    
    public Product(String code, String name, int quantity) {
        this.Id = UUID.randomUUID();
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID Id) {
        this.Id = Id;
    }

    public UUID getId() {
        return Id;
    }

}

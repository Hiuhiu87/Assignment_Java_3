/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import app.dao.ColorDAO;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class ColorProduct {

    private UUID id;
    private String code;
    private String name;

    public ColorProduct(String name) {
        this.id = UUID.randomUUID();
        this.code = ColorDAO.getInstance().generateNextModelCode();
        this.name = name;
    }

    public ColorProduct() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
    
    
}

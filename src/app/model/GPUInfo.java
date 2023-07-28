/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import app.dao.GPUInforDAO;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class GPUInfo {
    
    private UUID id;
    private String code;
    private String name;

    public GPUInfo(String name) {
        this.id = UUID.randomUUID();
        this.code = GPUInforDAO.getInstance().generateNextModelCode();
        this.name = name;
    }

    public GPUInfo() {
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import app.dao.CPUInforDAO;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CPUInfo {

    private UUID id;
    private String code;
    private String name;

    public CPUInfo(String name) {
        this.id = UUID.randomUUID();
        this.code = CPUInforDAO.getInstance().generateNextModelCode();
        this.name = name;
    }

    public CPUInfo() {
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

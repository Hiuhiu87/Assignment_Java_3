/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.CPUInforDAO;
import app.model.CPUInfo;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CPUInforService {

    public ArrayList<CPUInfo> getAll() {
        return CPUInforDAO.getInstance().getAll();
    }

    public void add(CPUInfo cpu) {
        CPUInforDAO.getInstance().insert(cpu);
    }

    public void update(CPUInfo cpu) {
        CPUInforDAO.getInstance().update(cpu);
    }

    public ArrayList<String> getNameCpu() {
        return CPUInforDAO.getInstance().getNameCpu();
    }
}

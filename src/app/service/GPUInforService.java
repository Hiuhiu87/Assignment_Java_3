/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.GPUInforDAO;
import app.model.GPUInfo;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class GPUInforService {

    public ArrayList<GPUInfo> getAll() {
        return GPUInforDAO.getInstance().getAll();
    }

    public void add(GPUInfo gpu) {
        GPUInforDAO.getInstance().insert(gpu);
    }

    public void update(GPUInfo gpu) {
        GPUInforDAO.getInstance().update(gpu);
    }

    public ArrayList<String> getNameCpu() {
        return GPUInforDAO.getInstance().getNameGpu();
    }
}

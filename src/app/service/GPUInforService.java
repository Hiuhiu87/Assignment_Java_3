/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.GPUInforRepository;
import app.model.GPUInfo;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class GPUInforService {

    public ArrayList<GPUInfo> getAll() {
        return GPUInforRepository.getInstance().getAll();
    }

    public void add(GPUInfo gpu) {
        GPUInforRepository.getInstance().insert(gpu);
    }

    public void update(GPUInfo gpu) {
        GPUInforRepository.getInstance().update(gpu);
    }

    public ArrayList<String> getNameCpu() {
        return GPUInforRepository.getInstance().getNameGpu();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.CPUInforRepository;
import app.model.CPUInfo;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CPUInforService {

    public ArrayList<CPUInfo> getAll() {
        return CPUInforRepository.getInstance().getAll();
    }

    public void add(CPUInfo cpu) {
        CPUInforRepository.getInstance().insert(cpu);
    }

    public void update(CPUInfo cpu) {
        CPUInforRepository.getInstance().update(cpu);
    }

    public ArrayList<String> getNameCpu() {
        return CPUInforRepository.getInstance().getNameCpu();
    }
}

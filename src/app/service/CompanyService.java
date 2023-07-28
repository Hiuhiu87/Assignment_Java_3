/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.repository.CompanyRepository;
import app.model.Company;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CompanyService {

    public ArrayList<Company> getAll() {
        return CompanyRepository.getInstance().getAll();
    }

    public void add(Company cpn) {
        CompanyRepository.getInstance().insert(cpn);
    }

    public void update(Company cpn) {
        CompanyRepository.getInstance().update(cpn);
    }

    public ArrayList<String> getNameCompany() {
        return CompanyRepository.getInstance().getNameCompany();
    }

}

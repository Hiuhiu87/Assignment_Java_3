/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.CompanyDAO;
import app.model.Company;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CompanyService {

    public ArrayList<Company> getAll() {
        return CompanyDAO.getInstance().getAll();
    }

    public void add(Company cpn) {
        CompanyDAO.getInstance().insert(cpn);
    }

    public void update(Company cpn) {
        CompanyDAO.getInstance().update(cpn);
    }

    public ArrayList<String> getNameCompany() {
        return CompanyDAO.getInstance().getNameCompany();
    }

}

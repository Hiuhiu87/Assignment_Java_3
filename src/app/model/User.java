/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.model;

import app.repository.UserRepository;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class User {

    private UUID id;
    private String code;
    private String name;
    private String middleName;
    private String lastName;
    private boolean gender;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String password;
    private Office office;
    private boolean status;
    private String email;

    public User(String name, String middleName, String lastName, boolean gender, LocalDate dateOfBirth, String address, String phoneNumber, Office office, boolean status, String email) {
        this.id = UUID.randomUUID();
        this.code = UserRepository.getInstance().generateNextModelCode();
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = PasswordGenerator.generatePassword();
        this.office = office;
        this.status = status;
        this.email = email;
    }

    public User(String code, String name, String middleName, String lastName, boolean gender, LocalDate dateOfBirth, String address, String phoneNumber, Office office, boolean status, String email) {
        this.code = code;
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = PasswordGenerator.generatePassword();
        this.office = office;
        this.status = status;
        this.email = email;
    }

    public User() {
    }

    private class PasswordGenerator {

        private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        public static String generatePassword() {
            Random random = new Random();
            String password = "";
            for (int i = 0; i < 6; i++) {
                password += CHARS.charAt(random.nextInt(CHARS.length()));
            }
            return password;
        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFullname() {
        return lastName + " " + middleName + " " + lastName;
    }

}

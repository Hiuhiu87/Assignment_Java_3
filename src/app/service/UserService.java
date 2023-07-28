/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.service;

import app.dao.UserDAO;
import app.model.User;
import app.util.SplitString;
import app.util.XEmail;
import java.util.ArrayList;
import javax.mail.MessagingException;

/**
 *
 * @author Admin
 */
public class UserService {

    public User login(String username, String password) {
        User user = UserDAO.getInstance().login(username, password);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public ArrayList<User> getAll() {
        ArrayList<User> listUser = UserDAO.getInstance().getAll();
        return listUser;
    }

    public boolean add(User user) {
        int addSuccess = UserDAO.getInstance().insert(user);
        if (addSuccess > 0) {
            String toEmail = user.getEmail();
            String message = "Vui Lòng Đổi Mật Khẩu Mới Sau Khi Đăng Nhập Vào Ứng Dụng \n" + user.getPassword();
            String subject = "Mật Khẩu Cho Tài Khoản Nhân Viên Mới";

            try {
                XEmail.sendEmailInfor(subject, toEmail, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean update(User user) {
        int updateSuccess = UserDAO.getInstance().update(user);
        if (updateSuccess > 0) {
            String toEmail = user.getEmail();
            String subject = "Cập Nhật Thông Tin Nhân Viên " + user.getName();
            String message = "Thông Tin Nhân Viên Của Bạn Vừa Được Cập Nhật";
            try {
                XEmail.sendEmailInfor(subject, toEmail, message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public boolean changePassword(String staffCode, String password) {
        int success = UserDAO.getInstance().changePassword(staffCode, password);
        return success > 0;
    }

    public User getUserByCode(String code) {
        return UserDAO.getInstance().selectByCode(code);
    }

    public boolean changeStatus(String staffCode) {
        int changeSuccess = UserDAO.getInstance().changeStatus(staffCode);
        return changeSuccess > 0;
    }

    public String generateNextStaffCode() {
        return UserDAO.getInstance().generateNextModelCode();
    }

    public ArrayList<User> getListByName(String name) {
        return UserDAO.getInstance().selectByName(name);
    }

}

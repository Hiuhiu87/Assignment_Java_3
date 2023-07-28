/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.dao.OfficeDAO;
import app.model.Office;
import app.model.User;
import app.service.UserService;
import app.util.CheckValidate;
import app.util.SplitString;
import app.util.XFileExcel;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Admin
 */
public class StaffPanel extends javax.swing.JPanel {

    private DefaultTableModel tableModel = new DefaultTableModel();
    private UserService service = new UserService();
    private ArrayList<User> listUser = new ArrayList<>();

    public StaffPanel() {
        initComponents();
        listUser = service.getAll();
        btnChangeStatus.setVisible(false);
        tblDisplay.setModel(tableModel);
        addcolumn();
        txtCodeStaff.setVisible(false);
        fillTable(listUser);
        onChange();
    }

    private void fillTable(ArrayList<User> list) {
        tableModel.setRowCount(0);
        for (User user : list) {
            Object[] row = {
                user.getCode(),
                user.getLastName() + " " + user.getMiddleName() + " " + user.getName(),
                user.isGender() ? "Nam" : "Nữ",
                user.getDateOfBirth(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.isStatus() ? "Đang Làm Việc" : "Nghỉ Việc",
                user.getOffice().getName()
            };
            tableModel.addRow(row);
        }
    }

    private void refreshTable() {
        listUser = service.getAll();
        fillTable(listUser);
    }

    private void addcolumn() {
        tableModel.addColumn("Mã Nhân Viên");
        tableModel.addColumn("Tên Nhân Viên");
        tableModel.addColumn("Giới Tính");
        tableModel.addColumn("Ngày Sinh");
        tableModel.addColumn("Địa Chỉ");
        tableModel.addColumn("SĐT");
        tableModel.addColumn("Email");
        tableModel.addColumn("Trạng Thái");
        tableModel.addColumn("Chức vụ");
    }

    private void showDetailStaff(User user) {
        txtCodeStaff.setText(user.getCode());
        txtFullName.setText(user.getLastName() + " " + user.getMiddleName() + " " + user.getName());
        rdMale.setSelected(user.isGender());
        rdFemale.setSelected(!user.isGender());
        txtDate.setText(String.valueOf(user.getDateOfBirth()));
        txtPhoneNumber.setText(user.getPhoneNumber());
        txtAddress.setText(user.getAddress());
        txtEmail.setText(user.getEmail());
        if (user.getOffice().getCode().equals("ST1")) {
            rdStaff.setSelected(true);
        } else {
            rdManager.setSelected(true);
        }
        btnChangeStatus.setVisible(true);
        txtCodeStaff.setVisible(true);
    }

    private User inputUser() {
        String fullName = "";
        String name = "";
        String middleName = "";
        String lastName = "";
        boolean gender = rdMale.isSelected();
        LocalDate dateOfBirth = null;
        String phoneNumber = "";
        String address = "";
        String email = "";
        Office office = OfficeDAO.getInstance().selectByRole(rdManager.isSelected());
        int countError = 0;

        if (txtFullName.getText().trim().isEmpty()) {
            countError++;
            labelNameError.setText("Tên Không Được Để Trống");
        } else {
            if (txtFullName.getText().length() < 6) {
                countError++;
                labelNameError.setText("Tên Không Hợp Lệ");
            } else {
                fullName = txtFullName.getText();
                String [] splitName = SplitString.splitName(fullName);
                name = splitName[0];
                middleName = splitName[1];
                lastName = splitName[2];
                labelNameError.setText("");
            }
        }

        if (txtDate.getText().trim().isEmpty()) {
            countError++;
            labelDateError.setText("Ngày Sinh Không Được Để Trống");
        } else {
            dateOfBirth = LocalDate.parse(txtDate.getText(), DateTimeFormatter.ISO_DATE);
            labelDateError.setText("");
        }

        if (txtPhoneNumber.getText().trim().isEmpty()) {
            countError++;
            labelPhoneError.setText("Số Điện Thoại Không Được Để Trống");
        } else {
            if (!CheckValidate.validateString(txtPhoneNumber.getText(), CheckValidate.PHONENUMBER_CHECK)) {
                countError++;
                labelPhoneError.setText("Số Điện Thoại Phải Là Số Của Việt Nam");
            } else {
                phoneNumber = txtPhoneNumber.getText();
                labelPhoneError.setText("");
            }
        }

        if (txtAddress.getText().trim().isEmpty()) {
            countError++;
            labelAddressError.setText("Địa Chỉ Không Được Để Trống");
        } else {
            address = txtAddress.getText();
            labelAddressError.setText("");
        }

        if (txtEmail.getText().trim().isEmpty()) {
            countError++;
            labelEmailError.setText("Email Không Được Để Trống");
        } else {
            if (!CheckValidate.validateString(txtEmail.getText(), CheckValidate.EMAIL_CHECK)) {
                countError++;
                labelEmailError.setText("Email Không Đúng Định Dạng");
            } else {
                email = txtEmail.getText();
                labelEmailError.setText("");
            }
        }

        if (countError == 0) {
            User user;
            if (txtCodeStaff.isVisible()) {
                String code = txtCodeStaff.getText();
                user = new User(code, name, middleName, lastName, gender, dateOfBirth, address, phoneNumber, office, true, email);
            } else {
                user = new User(name, middleName, lastName, gender, dateOfBirth, address, phoneNumber, office, true, email);
            }
            return user;
        } else {
            return null;
        }
    }

    private void resetForm() {
        String nextStaffCode = service.generateNextStaffCode();
        txtCodeStaff.setText(nextStaffCode);
        txtAddress.setText("");
        txtDate.setText("");
        txtEmail.setText("");
        txtFullName.setText("");
        txtPhoneNumber.setText("");
        rdMale.setSelected(true);
        rdStaff.setSelected(true);
        btnChangeStatus.setVisible(false);
        txtCodeStaff.setVisible(false);
    }

    private void onChange() {
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        refreshTable();
                    } else {
                        String nameStaffSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        ArrayList<User> listSearch = service.getListByName(nameStaffSearch);
                        if (listSearch != null) {
                            fillTable(listSearch);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTable(listUser);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        refreshTable();
                    } else {
                        String nameStaffSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        ArrayList<User> listSearch = service.getListByName(nameStaffSearch);
                        if (listSearch != null) {
                            fillTable(listSearch);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGender = new javax.swing.ButtonGroup();
        btnGroupOffice = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtFullName = new app.view.swing.TextField();
        labelFullName = new javax.swing.JLabel();
        labelDate = new javax.swing.JLabel();
        txtDate = new app.view.swing.TextField();
        labelPhoneNumber = new javax.swing.JLabel();
        txtPhoneNumber = new app.view.swing.TextField();
        rdFemale = new radio_button.RadioButtonCustom();
        rdMale = new radio_button.RadioButtonCustom();
        labelGender = new javax.swing.JLabel();
        labelAddress = new javax.swing.JLabel();
        txtAddress = new app.view.swing.TextField();
        labelEmail = new javax.swing.JLabel();
        txtEmail = new app.view.swing.TextField();
        rdStaff = new radio_button.RadioButtonCustom();
        labelOffice = new javax.swing.JLabel();
        rdManager = new radio_button.RadioButtonCustom();
        btnAdd = new app.view.swing.Button();
        btnNew = new app.view.swing.Button();
        btnChangeStatus = new app.view.swing.Button();
        btnUpdate = new app.view.swing.Button();
        labelAddressError = new javax.swing.JLabel();
        labelDateError = new javax.swing.JLabel();
        labelPhoneError = new javax.swing.JLabel();
        labelNameError = new javax.swing.JLabel();
        labelEmailError = new javax.swing.JLabel();
        txtCodeStaff = new app.view.swing.TextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplay = new javax.swing.JTable();
        txtSearch = new app.view.swing.TextField();
        labelFullName1 = new javax.swing.JLabel();
        btnExport = new app.view.swing.Button();
        button2 = new app.view.swing.Button();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1418, 731));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Quản Lý Nhân Viên");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Nhân Viên"));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        txtFullName.setLabelText("");

        labelFullName.setText("Họ và Tên");

        labelDate.setText("Ngày Sinh");

        txtDate.setLabelText("");

        labelPhoneNumber.setText("Số Điện Thoại");

        txtPhoneNumber.setLabelText("");

        rdFemale.setBackground(new java.awt.Color(51, 153, 255));
        btnGroupGender.add(rdFemale);
        rdFemale.setText("Nữ");

        rdMale.setBackground(new java.awt.Color(51, 153, 255));
        btnGroupGender.add(rdMale);
        rdMale.setSelected(true);
        rdMale.setText("Nam");

        labelGender.setText("Giới Tính");

        labelAddress.setText("Địa Chỉ");

        txtAddress.setLabelText("");

        labelEmail.setText("Email");

        txtEmail.setLabelText("");

        rdStaff.setBackground(new java.awt.Color(51, 153, 255));
        btnGroupOffice.add(rdStaff);
        rdStaff.setSelected(true);
        rdStaff.setText("Nhân Viên");

        labelOffice.setText("Chức Vụ");

        rdManager.setBackground(new java.awt.Color(51, 153, 255));
        btnGroupOffice.add(rdManager);
        rdManager.setText("Quản Lý");

        btnAdd.setBackground(new java.awt.Color(0, 0, 0));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(0, 0, 0));
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnChangeStatus.setBackground(new java.awt.Color(0, 0, 0));
        btnChangeStatus.setForeground(new java.awt.Color(255, 255, 255));
        btnChangeStatus.setText("Đổi Trạng Thái");
        btnChangeStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeStatusActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(0, 0, 0));
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        labelAddressError.setForeground(new java.awt.Color(255, 51, 51));
        labelAddressError.setPreferredSize(new java.awt.Dimension(0, 15));

        labelDateError.setForeground(new java.awt.Color(255, 51, 51));
        labelDateError.setPreferredSize(new java.awt.Dimension(0, 15));

        labelPhoneError.setForeground(new java.awt.Color(255, 51, 51));
        labelPhoneError.setPreferredSize(new java.awt.Dimension(0, 15));

        labelNameError.setForeground(new java.awt.Color(255, 51, 51));
        labelNameError.setPreferredSize(new java.awt.Dimension(0, 15));

        labelEmailError.setForeground(new java.awt.Color(255, 51, 51));
        labelEmailError.setPreferredSize(new java.awt.Dimension(0, 15));

        txtCodeStaff.setEditable(false);
        txtCodeStaff.setLabelText("Mã Nhân Viên");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelGender, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdMale, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(rdFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelDateError, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelNameError, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodeStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(166, 166, 166)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelOffice, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(rdStaff, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdManager, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelAddressError, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(labelPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelPhoneError, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(155, 155, 155)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(238, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCodeStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelAddressError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)
                                .addComponent(labelEmailError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelOffice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelPhoneNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPhoneError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelFullName, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtFullName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelNameError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rdMale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(rdFemale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelDate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelDateError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnChangeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Nhân Viên"));

        tblDisplay.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblDisplay.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDisplay);

        txtSearch.setLabelText("");

        labelFullName1.setText("Họ và Tên");

        btnExport.setBackground(new java.awt.Color(0, 0, 0));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        button2.setBackground(new java.awt.Color(0, 0, 0));
        button2.setForeground(new java.awt.Color(255, 255, 255));
        button2.setText("Import");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(labelFullName1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFullName1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(565, 565, 565)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        User user = inputUser();
        if (user != null) {
            boolean addSuccess = service.add(user);
            if (addSuccess) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "Thêm Thành Công");
            } else {
                JOptionPane.showMessageDialog(this, "Thêm Không Thành Công");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayMouseClicked
        int row = tblDisplay.getSelectedRow();
        int columnCodeStaff = 0;
        String code = (String) tblDisplay.getValueAt(row, columnCodeStaff);
        User user = service.getUserByCode(code);
        if (user != null) {
            showDetailStaff(user);
        }
    }//GEN-LAST:event_tblDisplayMouseClicked

    private void btnChangeStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeStatusActionPerformed
        int row = tblDisplay.getSelectedRow();
        int columnCodeStaff = 0;
        String code = (String) tblDisplay.getValueAt(row, columnCodeStaff);
        boolean changeSuccess = service.changeStatus(code);
        if (changeSuccess) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "Đổi Trạng Thái Thành Công !");
        } else {
            JOptionPane.showMessageDialog(this, "Đổi Trạng Thái Không Thành Công !");
        }
    }//GEN-LAST:event_btnChangeStatusActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        User user = inputUser();
        if (user != null) {
            boolean updateSuccess = service.update(user);
            if (updateSuccess) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "Sửa Thành Công");
            } else {
                JOptionPane.showMessageDialog(this, "Sửa Không Thành Công - Đã Xảy Ra Lỗi");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        resetForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        try {
            boolean exportSuccess = XFileExcel.exportToFile(tableModel);
            if (exportSuccess) {
                JOptionPane.showMessageDialog(this, "Export Thành công");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Export Không Thành công");
        }

    }//GEN-LAST:event_btnExportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.Button btnAdd;
    private app.view.swing.Button btnChangeStatus;
    private app.view.swing.Button btnExport;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupOffice;
    private app.view.swing.Button btnNew;
    private app.view.swing.Button btnUpdate;
    private app.view.swing.Button button2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelAddress;
    private javax.swing.JLabel labelAddressError;
    private javax.swing.JLabel labelDate;
    private javax.swing.JLabel labelDateError;
    private javax.swing.JLabel labelEmail;
    private javax.swing.JLabel labelEmailError;
    private javax.swing.JLabel labelFullName;
    private javax.swing.JLabel labelFullName1;
    private javax.swing.JLabel labelGender;
    private javax.swing.JLabel labelNameError;
    private javax.swing.JLabel labelOffice;
    private javax.swing.JLabel labelPhoneError;
    private javax.swing.JLabel labelPhoneNumber;
    private radio_button.RadioButtonCustom rdFemale;
    private radio_button.RadioButtonCustom rdMale;
    private radio_button.RadioButtonCustom rdManager;
    private radio_button.RadioButtonCustom rdStaff;
    private javax.swing.JTable tblDisplay;
    private app.view.swing.TextField txtAddress;
    private app.view.swing.TextField txtCodeStaff;
    private app.view.swing.TextField txtDate;
    private app.view.swing.TextField txtEmail;
    private app.view.swing.TextField txtFullName;
    private app.view.swing.TextField txtPhoneNumber;
    private app.view.swing.TextField txtSearch;
    // End of variables declaration//GEN-END:variables
}

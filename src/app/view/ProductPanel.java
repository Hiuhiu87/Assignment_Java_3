/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.repository.CPUInforRepository;
import app.repository.ColorRepository;
import app.repository.CompanyRepository;
import app.repository.GPUInforRepository;
import app.repository.ProductRepository;
import app.repository.ProductDetailRepository;
import app.repository.ProductTypeRepository;
import app.model.CPUInfo;
import app.model.ColorProduct;
import app.model.Company;
import app.model.GPUInfo;
import app.model.Product;
import app.model.ProductDetail;
import app.model.ProductType;
import app.service.CPUInforService;
import app.service.ColorProductService;
import app.service.CompanyService;
import app.service.GPUInforService;
import app.service.ProductDetailService;
import app.service.ProductService;
import app.service.ProductTypeService;
import app.util.XFileExcel;
import app.util.XGenerateQRCode;
import app.view.swing.Combobox;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Admin
 */
public class ProductPanel extends javax.swing.JPanel {

    ArrayList<Product> listProducts = new ArrayList<>();
    ArrayList<ProductDetail> listProductDetail = new ArrayList<>();
    ProductService serviceProduct = new ProductService();
    ProductDetailService serviceProductDetail = new ProductDetailService();
    CompanyService serviceCompany = new CompanyService();
    ProductTypeService serviceProductType = new ProductTypeService();
    CPUInforService serviceCpu = new CPUInforService();
    GPUInforService serviceGpu = new GPUInforService();
    ColorProductService serviceColor = new ColorProductService();
    DefaultTableModel tableModelProduct = new DefaultTableModel();
    DefaultTableModel tableModelProductDetail = new DefaultTableModel();
    DefaultComboBoxModel comboBoxModelNameProduct = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelNameCompany = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelNameCpu = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelNameGpu = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelTypeProduct = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelRam = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelRom = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelColor = new DefaultComboBoxModel();
    DefaultComboBoxModel comboBoxModelSerial = new DefaultComboBoxModel();

    public ProductPanel() {
        initComponents();
        listProducts = serviceProduct.getAll();
        listProductDetail = serviceProductDetail.getAll();
        tblDisplay.setModel(tableModelProduct);
        tblDisplayPrdDetail.setModel(tableModelProductDetail);
        cbbNameProductDetail.setModel(comboBoxModelNameProduct);
        cbbCompany.setModel(comboBoxModelNameCompany);
        cbbTypeLaptop.setModel(comboBoxModelTypeProduct);
        cbbCPUProduct.setModel(comboBoxModelNameCpu);
        cbbCardProduct.setModel(comboBoxModelNameGpu);
        cbbRamProduct.setModel(comboBoxModelRam);
        cbbRomProduct.setModel(comboBoxModelRom);
        cbbColorProduct.setModel(comboBoxModelColor);
        cbbDetailProduct.setModel(comboBoxModelSerial);
        addColumnTablePrd();
        addColumnTablePrdDetail();
        addValueToCbbNameProduct();
        addValueToCbbNameCompany();
        addValueToCbbTypeProduct();
        addValueToCbbNameCpu();
        addValueToCbbNameGpu();
        addValueToCbbNameColor();
        addValueToCbbRam();
        addValueToCbbRom();
        fillTableProduct(listProducts);
        fillTableProductDetail(listProductDetail);
        cbbDetailProduct.setVisible(false);
        btnStatusDetailProd.setVisible(false);
        labelSerialSel.setVisible(false);
        onChange();
    }

    private void fillTableProduct(ArrayList<Product> list) {
        tableModelProduct.setRowCount(0);
        int stt = 0;
        for (Product product : list) {
            stt++;
            Object[] row = {stt, product.getCode(), product.getName(), product.getQuantity()};
            tableModelProduct.addRow(row);
        }
    }

    private void addColumnTablePrd() {
        tableModelProduct.addColumn("STT");
        tableModelProduct.addColumn("Mã Sản Phẩm");
        tableModelProduct.addColumn("Tên Sản Phẩm");
        tableModelProduct.addColumn("Số Lượng");
    }

    private void addColumnTablePrdDetail() {
        tableModelProductDetail.addColumn("STT");
        tableModelProductDetail.addColumn("Tên Sản Phẩm");
        tableModelProductDetail.addColumn("Hãng");
        tableModelProductDetail.addColumn("Màu Sắc");
        tableModelProductDetail.addColumn("Loại Máy");
        tableModelProductDetail.addColumn("CPU");
        tableModelProductDetail.addColumn("GPU");
        tableModelProductDetail.addColumn("ROM");
        tableModelProductDetail.addColumn("RAM");
        tableModelProductDetail.addColumn("Serial");
        tableModelProductDetail.addColumn("Năm BH");
        tableModelProductDetail.addColumn("Mô tả");
        tableModelProductDetail.addColumn("Giá Nhập");
        tableModelProductDetail.addColumn("Giá Bán");
        tableModelProductDetail.addColumn("Tình Trạng");
    }

    private void addValueToCbbNameProduct() {
        ArrayList<String> listNameProduct = serviceProductDetail.getAllNameProduct();
        comboBoxModelNameProduct.removeAllElements();
        comboBoxModelNameProduct.addAll(listNameProduct);
    }

    public void addValueToCbbNameCompany() {
        ArrayList<String> listNameCompany = serviceCompany.getNameCompany();
        comboBoxModelNameCompany.removeAllElements();
        comboBoxModelNameCompany.addAll(listNameCompany);
    }

    public void addValueToCbbTypeProduct() {
        ArrayList<String> listTypeProduct = serviceProductType.getNameType();
        comboBoxModelTypeProduct.removeAllElements();
        comboBoxModelTypeProduct.addAll(listTypeProduct);
    }

    private void addValueToCbbNameCpu() {
        ArrayList<String> listNamePCpu = serviceCpu.getNameCpu();
        comboBoxModelNameCpu.removeAllElements();
        comboBoxModelNameCpu.addAll(listNamePCpu);
    }

    private void addValueToCbbNameGpu() {
        ArrayList<String> listNameGpu = serviceGpu.getNameCpu();
        comboBoxModelNameGpu.removeAllElements();
        comboBoxModelNameGpu.addAll(listNameGpu);
    }

    private void addValueToCbbNameColor() {
        ArrayList<String> listNameColor = serviceColor.getNameColor();
        comboBoxModelColor.removeAllElements();
        comboBoxModelColor.addAll(listNameColor);
    }

    private void addValueToCbbRam() {
        ArrayList<Integer> listRam = new ArrayList<>();
        for (int i = 4; i <= 32; i += 4) {
            listRam.add(i);
        }
        comboBoxModelRam.removeAllElements();
        comboBoxModelRam.addAll(listRam);
    }

    private void addValueToCbbRom() {
        ArrayList<Integer> listRom = new ArrayList<>();
        listRom.add(128);
        listRom.add(256);
        listRom.add(512);
        listRom.add(1024);
        listRom.add(2048);
        comboBoxModelRom.removeAllElements();
        comboBoxModelRom.addAll(listRom);
    }

    private Product productInput() {
        String nameProduct = "";
        String codeProduct = txtCodeProduct.getText();
        int countError = 0;
        if (txtNameProduct.getText().isEmpty()) {
            countError++;
            labelNamePrdError.setText("Trường Này Không Được Để Trống");
        } else {
            if (txtNameProduct.getText().length() < 4) {
                countError++;
                labelNamePrdError.setText("Trường Này Không Được Để Trống");
            } else {
                nameProduct = txtNameProduct.getText();
                labelNamePrdError.setText("");
            }
        }
        if (countError == 0) {
            Product prd = new Product(codeProduct, nameProduct);
            return prd;
        }
        return null;
    }

    private ProductDetail productDetailInput() {
        int countError = 0;

        Product product = null;
        if (!checkCbbEmpty(cbbNameProductDetail)) {
            countError++;
        } else {
            String nameProduct = (String) cbbNameProductDetail.getSelectedItem();
            product = ProductRepository.getInstance().selectByName(nameProduct);
        }

        Company company = null;
        if (!checkCbbEmpty(cbbCompany)) {
            countError++;
        } else {
            String nameCompany = (String) cbbCompany.getSelectedItem();
            company = CompanyRepository.getInstance().selectByName(nameCompany);
        }

        ColorProduct color = null;
        if (!checkCbbEmpty(cbbColorProduct)) {
            countError++;
        } else {
            String nameColor = (String) cbbColorProduct.getSelectedItem();
            color = ColorRepository.getInstance().selectByName(nameColor);
        }

        ProductType type = null;
        if (!checkCbbEmpty(cbbTypeLaptop)) {
            countError++;
        } else {
            String nameTypePro = (String) cbbTypeLaptop.getSelectedItem();
            type = ProductTypeRepository.getInstance().selectByName(nameTypePro);
        }

        CPUInfo idCPU = null;
        if (!checkCbbEmpty(cbbCPUProduct)) {
            countError++;
        } else {
            String cpuName = (String) cbbCPUProduct.getSelectedItem();
            idCPU = CPUInforRepository.getInstance().selectByName(cpuName);
        }

        GPUInfo idGPU = null;
        if (!checkCbbEmpty(cbbCardProduct)) {
            countError++;
        } else {
            String gpuName = (String) cbbCardProduct.getSelectedItem();
            idGPU = GPUInforRepository.getInstance().selectByName(gpuName);
        }

        String moTa = txtAreaDescription.getText();

        int rom = 0;
        if (!checkCbbEmpty(cbbRomProduct)) {
            countError++;
        } else {
            rom = (int) cbbRomProduct.getSelectedItem();
        }

        int ram = 0;
        if (!checkCbbEmpty(cbbRamProduct)) {
            countError++;
        } else {
            ram = (int) cbbRamProduct.getSelectedItem();
        }

        String serial = "";
        if (txtSerial.getText().isEmpty()) {
            countError++;
            labelSerialError.setText("Số Serial Không Được Để Trống");
        } else {
            if (txtSerial.getText().length() < 6) {
                countError++;
                labelSerialError.setText("Số Serial Phải Lớn Hơn 6 Ký Tự");
            } else {
                serial = txtSerial.getText();
                labelSerialError.setText("");
            }
        }

        int namBH = 0;
        if (txtYearWarranty.getText().isEmpty()) {
            countError++;
            labelYearWarrantyError.setText("Năm Bảo Hành Không Được Để Trống");
        } else {
            try {
                namBH = Integer.parseInt(txtYearWarranty.getText());
                labelYearWarrantyError.setText("");
            } catch (NumberFormatException e) {
                countError++;
                labelYearWarrantyError.setText("Năm Bảo Hành Phải Là Số");
            }
        }

        int soLuongTon = 1;

        BigDecimal giaNhap = null;
        if (txtPriceIn.getText().isEmpty()) {
            countError++;
            labelPriceInError.setText("Giá Nhập Không Được Để Trống");
        } else {
            try {
                double giaNhapGet = Double.parseDouble(txtPriceIn.getText());
                giaNhap = BigDecimal.valueOf(giaNhapGet);
                labelPriceInError.setText("");
            } catch (NumberFormatException e) {
                countError++;
                labelPriceInError.setText("Giá Nhập Phải Là Số");
            }
        }

        BigDecimal giaBan = null;
        if (txtPriceOut.getText().isEmpty()) {
            countError++;
            labelPriceOutError.setText("Giá Nhập Không Được Để Trống");
        } else {
            try {
                double giaBanGet = Double.parseDouble(txtPriceOut.getText());
                giaBan = BigDecimal.valueOf(giaBanGet);
                labelPriceOutError.setText("");
            } catch (NumberFormatException e) {
                countError++;
                labelPriceOutError.setText("Giá Nhập Phải Là Số");
            }
        }

        boolean tinhTrang = true;

        if (countError == 0) {
            ProductDetail prdDetail = new ProductDetail(product, company, color, type, idCPU, idGPU, rom, ram, serial, namBH, moTa, giaNhap, giaBan, tinhTrang);
            ProductDetail productDetailCheck = ProductDetailRepository.getInstance().getProductDetailBySerial(serial);
            if (productDetailCheck.getId() != null) {
                prdDetail.setId(productDetailCheck.getId());
            }
            return prdDetail;
        } else {
            JOptionPane.showMessageDialog(this, "Chưa Nhập Đủ Thông Số Kỹ Thuật");
            return null;
        }
    }

    private boolean checkCbbEmpty(Combobox cbb) {
        return cbb.getSelectedItem() != null;
    }

    private void refreshTable() {
        listProducts = serviceProduct.getAll();
        fillTableProduct(listProducts);
    }

    private void refreshTablePrdDetail() {
        listProductDetail = serviceProductDetail.getAll();
        fillTableProductDetail(listProductDetail);
    }

    private void showDetailProduct(Product prd) {
        txtNameProduct.setText(prd.getName());
        txtCodeProduct.setText(prd.getCode());
    }

    private void fillTableProductDetail(ArrayList<ProductDetail> list) {
        int stt = 0;
        tableModelProductDetail.setRowCount(0);
        for (ProductDetail productDetail : list) {
            stt++;
            Object[] row = {
                stt,
                productDetail.getIdProduct().getName(),
                productDetail.getIdCompany().getName(),
                productDetail.getIdColor().getName(),
                productDetail.getIdType().getName(),
                productDetail.getIdCPU().getName(),
                productDetail.getIdGPU().getName(),
                productDetail.getROM(),
                productDetail.getRAM(),
                productDetail.getSerial(),
                productDetail.getNamBH(),
                productDetail.getMoTa(),
                productDetail.getGiaNhap(),
                productDetail.getGiaBan(),
                productDetail.isTinhTrang() ? "Còn Bán" : "Ngừng Bán"
            };
            tableModelProductDetail.addRow(row);
        }
    }

    private void resetForm() {
        txtSerial.setText("");
        txtPriceIn.setText("");
        txtPriceOut.setText("");
        cbbTypeLaptop.setSelectedIndex(-1);
        cbbDetailProduct.setSelectedIndex(-1);
        cbbDetailProduct.setVisible(false);
        cbbCompany.setSelectedIndex(-1);
        cbbCPUProduct.setSelectedIndex(-1);
        cbbCardProduct.setSelectedIndex(-1);
        cbbRamProduct.setSelectedIndex(-1);
        cbbRomProduct.setSelectedIndex(-1);
        cbbColorProduct.setSelectedIndex(-1);
        txtAreaDescription.setText("");
        labelNamePrdError.setText("");
        labelPriceInError.setText("");
        labelPriceOutError.setText("");
        labelYearWarrantyError.setText("");
        labelQuantityError.setText("");
        labelSerialError.setText("");
        txtYearWarranty.setText("");
        txtQuantiy.setText("");
    }

    private void showDetail(ProductDetail productDetail) {
        txtSerial.setText(productDetail.getSerial());
        txtPriceIn.setText(String.valueOf(productDetail.getGiaNhap()));
        txtPriceOut.setText(String.valueOf(productDetail.getGiaBan()));
        cbbTypeLaptop.setSelectedItem(productDetail.getIdType().getName());
        txtAreaDescription.setText(productDetail.getMoTa());
        txtYearWarranty.setText(String.valueOf(productDetail.getNamBH()));
        cbbCompany.setSelectedItem(productDetail.getIdCompany().getName());
        cbbCPUProduct.setSelectedItem(productDetail.getIdCPU().getName());
        cbbCardProduct.setSelectedItem(productDetail.getIdGPU().getName());
        cbbRamProduct.setSelectedItem(productDetail.getRAM());
        cbbRomProduct.setSelectedItem(productDetail.getROM());
        cbbColorProduct.setSelectedItem(productDetail.getIdColor().getName());
        txtQuantiy.setText(String.valueOf(productDetail.getIdProduct().getQuantity()));
        boolean statusProd = productDetail.isTinhTrang();
        if (statusProd) {
            btnStatusDetailProd.setText("Ngừng Bán");
            btnStatusDetailProd.setVisible(true);
        } else {
            btnStatusDetailProd.setText("Tiếp Tục Bán");
            btnStatusDetailProd.setVisible(true);
        }
    }

    private void onChange() {
        txtSearchProduct.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        refreshTablePrdDetail();
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        ArrayList<ProductDetail> listSearch = serviceProductDetail.searchByName(namePrdSearch);
                        if (listSearch != null) {
                            fillTableProductDetail(listSearch);
                        }
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ProductPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fillTableProductDetail(listProductDetail);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    if (e.getDocument().getText(0, e.getDocument().getLength()).length() <= 0) {
                        refreshTablePrdDetail();
                    } else {
                        String namePrdSearch = e.getDocument().getText(0, e.getDocument().getLength());
                        ArrayList<ProductDetail> listSearch = serviceProductDetail.searchByName(namePrdSearch);
                        if (listSearch != null) {
                            fillTableProductDetail(listSearch);
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

        panelDisplayList = new app.view.swing.TabbedPaneCustom();
        panelProduct = new javax.swing.JPanel();
        labelNameProduct = new javax.swing.JLabel();
        txtNameProduct = new app.view.swing.TextField();
        btnAdd = new app.view.swing.MyButton();
        btnUpdate = new app.view.swing.MyButton();
        labelNameProductSearch = new javax.swing.JLabel();
        txtSearchNameProduct = new app.view.swing.TextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplay = new javax.swing.JTable();
        btnRefesh = new app.view.swing.MyButton();
        labelNamePrdError = new javax.swing.JLabel();
        labelCodeProduct = new javax.swing.JLabel();
        txtCodeProduct = new app.view.swing.TextField();
        btnNew = new app.view.swing.MyButton();
        panelDetailProduct = new javax.swing.JPanel();
        panelFunction = new javax.swing.JPanel();
        labelNameProductTab2 = new javax.swing.JLabel();
        cbbNameProductDetail = new app.view.swing.Combobox();
        labelSerial = new javax.swing.JLabel();
        txtSerial = new app.view.swing.TextField();
        labelPriceIn = new javax.swing.JLabel();
        txtPriceIn = new app.view.swing.TextField();
        labelPriceOut = new javax.swing.JLabel();
        txtPriceOut = new app.view.swing.TextField();
        btnAddDetailProd = new app.view.swing.Button();
        btnRefresh = new app.view.swing.Button();
        btnUpdateDetailProd = new app.view.swing.Button();
        cbbTypeLaptop = new app.view.swing.Combobox();
        btnAddTypeLaptop = new app.view.swing.Button();
        labelTypeProduct = new javax.swing.JLabel();
        labelDescription = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaDescription = new javax.swing.JTextArea();
        txtYearWarranty = new app.view.swing.TextField();
        labelYearWarranty = new javax.swing.JLabel();
        txtQuantiy = new app.view.swing.TextField();
        labelQuantity = new javax.swing.JLabel();
        labelSerialError = new javax.swing.JLabel();
        labelPriceInError = new javax.swing.JLabel();
        labelPriceOutError = new javax.swing.JLabel();
        labelYearWarrantyError = new javax.swing.JLabel();
        labelQuantityError = new javax.swing.JLabel();
        btnStatusDetailProd = new app.view.swing.Button();
        cbbDetailProduct = new app.view.swing.ComboBoxSuggestion();
        labelSerialSel = new javax.swing.JLabel();
        panelAtribute = new javax.swing.JPanel();
        labelCompany = new javax.swing.JLabel();
        cbbCompany = new app.view.swing.Combobox();
        cbbCPUProduct = new app.view.swing.Combobox();
        labelCPU = new javax.swing.JLabel();
        btnAddCompany = new app.view.swing.Button();
        btnAddCpu = new app.view.swing.Button();
        cbbRamProduct = new app.view.swing.Combobox();
        button8 = new app.view.swing.Button();
        labelRam = new javax.swing.JLabel();
        cbbRomProduct = new app.view.swing.Combobox();
        button9 = new app.view.swing.Button();
        labelROM = new javax.swing.JLabel();
        cbbCardProduct = new app.view.swing.Combobox();
        button10 = new app.view.swing.Button();
        labelCard = new javax.swing.JLabel();
        cbbColorProduct = new app.view.swing.Combobox();
        button11 = new app.view.swing.Button();
        labelColor = new javax.swing.JLabel();
        panelDisplayProduct = new javax.swing.JPanel();
        panelListProduct = new javax.swing.JPanel();
        labelSearch = new javax.swing.JLabel();
        txtSearchProduct = new app.view.swing.TextField();
        labelSearchByPrice = new javax.swing.JLabel();
        txtPriceMin = new app.view.swing.TextField();
        txtPriceMax = new app.view.swing.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisplayPrdDetail = new javax.swing.JTable();
        btnImport = new app.view.swing.Button();
        btnExport = new app.view.swing.Button();
        btnFilterPrice = new app.view.swing.Button();
        btnResetTable = new app.view.swing.Button();

        panelDisplayList.setForeground(new java.awt.Color(255, 255, 255));
        panelDisplayList.setFont(new java.awt.Font("Segoe UI Variable", 1, 12)); // NOI18N
        panelDisplayList.setSelectedColor(new java.awt.Color(23, 35, 51));
        panelDisplayList.setUnselectedColor(new java.awt.Color(23, 44, 76));

        panelProduct.setBackground(new java.awt.Color(255, 255, 255));

        labelNameProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelNameProduct.setText("Tên Sản Phẩm");

        txtNameProduct.setLabelText("Tên Sản Phẩm");

        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Thêm");
        btnAdd.setBorderColor(new java.awt.Color(23, 35, 51));
        btnAdd.setColor(new java.awt.Color(23, 35, 51));
        btnAdd.setColorClick(new java.awt.Color(23, 16, 71));
        btnAdd.setColorOver(new java.awt.Color(23, 11, 84));
        btnAdd.setRadius(10);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.setBorderColor(new java.awt.Color(23, 35, 51));
        btnUpdate.setColor(new java.awt.Color(23, 35, 51));
        btnUpdate.setColorClick(new java.awt.Color(23, 16, 71));
        btnUpdate.setColorOver(new java.awt.Color(23, 11, 84));
        btnUpdate.setRadius(10);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        labelNameProductSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelNameProductSearch.setText("Tìm Kiếm");

        txtSearchNameProduct.setLabelText("Tên Sản Phẩm");

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

        btnRefesh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefesh.setText("Làm Mới");
        btnRefesh.setBorderColor(new java.awt.Color(23, 35, 51));
        btnRefesh.setColor(new java.awt.Color(23, 35, 51));
        btnRefesh.setColorClick(new java.awt.Color(23, 16, 71));
        btnRefesh.setColorOver(new java.awt.Color(23, 11, 84));
        btnRefesh.setRadius(10);
        btnRefesh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefeshActionPerformed(evt);
            }
        });

        labelNamePrdError.setForeground(new java.awt.Color(255, 51, 51));

        labelCodeProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelCodeProduct.setText("Mã Sản Phẩm");

        txtCodeProduct.setEditable(false);
        txtCodeProduct.setLabelText("Mã Sản Phẩm");

        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Tạo Mới");
        btnNew.setBorderColor(new java.awt.Color(23, 35, 51));
        btnNew.setColor(new java.awt.Color(23, 35, 51));
        btnNew.setColorClick(new java.awt.Color(23, 16, 71));
        btnNew.setColorOver(new java.awt.Color(23, 11, 84));
        btnNew.setRadius(10);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelProductLayout = new javax.swing.GroupLayout(panelProduct);
        panelProduct.setLayout(panelProductLayout);
        panelProductLayout.setHorizontalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1401, Short.MAX_VALUE))
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(labelNameProductSearch)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(labelCodeProduct)
                        .addGap(18, 18, 18)
                        .addComponent(txtCodeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(labelNameProduct)
                        .addGap(18, 18, 18)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelNamePrdError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
        );
        panelProductLayout.setVerticalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(labelNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addComponent(labelNamePrdError, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCodeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
                        .addComponent(btnRefesh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSearchNameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelNameProductSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelDisplayList.addTab("Sản Phẩm", panelProduct);

        panelDetailProduct.setBackground(new java.awt.Color(255, 255, 255));

        panelFunction.setBackground(new java.awt.Color(255, 255, 255));
        panelFunction.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức Năng"));
        panelFunction.setPreferredSize(new java.awt.Dimension(1230, 430));

        labelNameProductTab2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelNameProductTab2.setText("Tên Sản Phẩm");

        cbbNameProductDetail.setLabeText("Tên Sản Phẩm");
        cbbNameProductDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbNameProductDetailActionPerformed(evt);
            }
        });

        labelSerial.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelSerial.setText("Serial");

        txtSerial.setLabelText("Serial");

        labelPriceIn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceIn.setText("Giá Nhập");

        txtPriceIn.setLabelText("Giá Nhập");

        labelPriceOut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelPriceOut.setText("GIá Bán");

        txtPriceOut.setLabelText("Giá Bán");

        btnAddDetailProd.setBackground(new java.awt.Color(23, 35, 51));
        btnAddDetailProd.setForeground(new java.awt.Color(255, 255, 255));
        btnAddDetailProd.setText("Thêm");
        btnAddDetailProd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnAddDetailProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDetailProdActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(23, 35, 51));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm Mới");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        btnUpdateDetailProd.setBackground(new java.awt.Color(23, 35, 51));
        btnUpdateDetailProd.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateDetailProd.setText("Sửa");
        btnUpdateDetailProd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnUpdateDetailProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailProdActionPerformed(evt);
            }
        });

        cbbTypeLaptop.setLabeText("");

        btnAddTypeLaptop.setBackground(new java.awt.Color(23, 35, 51));
        btnAddTypeLaptop.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTypeLaptop.setText("+");
        btnAddTypeLaptop.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddTypeLaptop.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddTypeLaptop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTypeLaptopActionPerformed(evt);
            }
        });

        labelTypeProduct.setText("Loại Laptop");

        labelDescription.setText("Mô Tả");

        txtAreaDescription.setColumns(20);
        txtAreaDescription.setRows(5);
        jScrollPane3.setViewportView(txtAreaDescription);

        txtYearWarranty.setLabelText("Năm Bảo Hành");

        labelYearWarranty.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelYearWarranty.setText("Năm Bảo Hành");

        txtQuantiy.setEditable(false);
        txtQuantiy.setLabelText("Số Lượng");

        labelQuantity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelQuantity.setText("Số Lượng");

        labelSerialError.setForeground(new java.awt.Color(255, 51, 0));

        labelPriceInError.setForeground(new java.awt.Color(255, 51, 0));

        labelPriceOutError.setForeground(new java.awt.Color(255, 51, 0));

        labelYearWarrantyError.setForeground(new java.awt.Color(255, 51, 0));

        labelQuantityError.setForeground(new java.awt.Color(255, 51, 0));

        btnStatusDetailProd.setBackground(new java.awt.Color(23, 35, 51));
        btnStatusDetailProd.setForeground(new java.awt.Color(255, 255, 255));
        btnStatusDetailProd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnStatusDetailProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatusDetailProdActionPerformed(evt);
            }
        });

        cbbDetailProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDetailProductActionPerformed(evt);
            }
        });

        labelSerialSel.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        labelSerialSel.setText("Serial");

        javax.swing.GroupLayout panelFunctionLayout = new javax.swing.GroupLayout(panelFunction);
        panelFunction.setLayout(panelFunctionLayout);
        panelFunctionLayout.setHorizontalGroup(
            panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelFunctionLayout.createSequentialGroup()
                            .addComponent(labelPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(labelSerialError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(panelFunctionLayout.createSequentialGroup()
                            .addComponent(labelPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelFunctionLayout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(labelPriceOutError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(panelFunctionLayout.createSequentialGroup()
                            .addComponent(labelSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtSerial, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addComponent(labelNameProductTab2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbbNameProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelPriceInError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(labelQuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(labelQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelYearWarranty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtYearWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtQuantiy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(labelYearWarrantyError, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelTypeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSerialSel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbbDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addComponent(cbbTypeLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddTypeLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                        .addComponent(labelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAddDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnStatusDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(164, 164, 164))))
        );
        panelFunctionLayout.setVerticalGroup(
            panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbNameProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbDetailProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelSerialSel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(labelNameProductTab2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelFunctionLayout.createSequentialGroup()
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addComponent(labelSerial, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelSerialError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addComponent(labelPriceIn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelPriceInError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addComponent(labelPriceOut, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbbTypeLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnAddTypeLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(labelTypeProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtYearWarranty, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelFunctionLayout.createSequentialGroup()
                                        .addComponent(labelYearWarranty, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelYearWarrantyError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtQuantiy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panelFunctionLayout.createSequentialGroup()
                                        .addComponent(labelQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)))
                                .addGap(14, 14, 14)))
                        .addGroup(panelFunctionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPriceOutError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelQuantityError, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelFunctionLayout.createSequentialGroup()
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStatusDetailProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52))
        );

        panelAtribute.setBackground(new java.awt.Color(255, 255, 255));
        panelAtribute.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Số Kỹ Thuật"));

        labelCompany.setText("Hãng");

        cbbCompany.setLabeText("");

        cbbCPUProduct.setLabeText("");

        labelCPU.setText("CPU");

        btnAddCompany.setBackground(new java.awt.Color(23, 35, 51));
        btnAddCompany.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCompany.setText("+");
        btnAddCompany.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddCompany.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddCompany.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCompanyActionPerformed(evt);
            }
        });

        btnAddCpu.setBackground(new java.awt.Color(23, 35, 51));
        btnAddCpu.setForeground(new java.awt.Color(255, 255, 255));
        btnAddCpu.setText("+");
        btnAddCpu.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        btnAddCpu.setPreferredSize(new java.awt.Dimension(23, 26));
        btnAddCpu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCpuActionPerformed(evt);
            }
        });

        cbbRamProduct.setLabeText("");

        button8.setBackground(new java.awt.Color(23, 35, 51));
        button8.setForeground(new java.awt.Color(255, 255, 255));
        button8.setText("+");
        button8.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        button8.setPreferredSize(new java.awt.Dimension(23, 26));

        labelRam.setText("Ram");

        cbbRomProduct.setLabeText("");

        button9.setBackground(new java.awt.Color(23, 35, 51));
        button9.setForeground(new java.awt.Color(255, 255, 255));
        button9.setText("+");
        button9.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        button9.setPreferredSize(new java.awt.Dimension(23, 26));

        labelROM.setText("Rom");

        cbbCardProduct.setLabeText("");

        button10.setBackground(new java.awt.Color(23, 35, 51));
        button10.setForeground(new java.awt.Color(255, 255, 255));
        button10.setText("+");
        button10.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        button10.setPreferredSize(new java.awt.Dimension(23, 26));
        button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button10ActionPerformed(evt);
            }
        });

        labelCard.setText("Card");

        cbbColorProduct.setLabeText("");

        button11.setBackground(new java.awt.Color(23, 35, 51));
        button11.setForeground(new java.awt.Color(255, 255, 255));
        button11.setText("+");
        button11.setFont(new java.awt.Font("ROG Fonts", 0, 13)); // NOI18N
        button11.setPreferredSize(new java.awt.Dimension(23, 26));
        button11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button11ActionPerformed(evt);
            }
        });

        labelColor.setText("Color");

        javax.swing.GroupLayout panelAtributeLayout = new javax.swing.GroupLayout(panelAtribute);
        panelAtribute.setLayout(panelAtributeLayout);
        panelAtributeLayout.setHorizontalGroup(
            panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbCPUProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddCpu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(38, 38, 38)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelROM, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbRomProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelRam, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbRamProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelColor, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbColorProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addComponent(labelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbCardProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        panelAtributeLayout.setVerticalGroup(
            panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAtributeLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAddCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                                    .addComponent(labelRam, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(3, 3, 3))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbRamProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbCPUProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnAddCpu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbRomProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(button9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                                    .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(labelCPU, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(labelROM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(3, 3, 3)))
                            .addGroup(panelAtributeLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cbbColorProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(button11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                                        .addComponent(labelColor, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)))))
                        .addGap(30, 30, 30))
                    .addGroup(panelAtributeLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createSequentialGroup()
                                .addComponent(labelCard, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAtributeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbCardProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(9, 143, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout panelDetailProductLayout = new javax.swing.GroupLayout(panelDetailProduct);
        panelDetailProduct.setLayout(panelDetailProductLayout);
        panelDetailProductLayout.setHorizontalGroup(
            panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailProductLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAtribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFunction, javax.swing.GroupLayout.PREFERRED_SIZE, 1245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        panelDetailProductLayout.setVerticalGroup(
            panelDetailProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetailProductLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFunction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAtribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        panelDisplayList.addTab("Chi Tiết Sản Phẩm", panelDetailProduct);

        panelListProduct.setBackground(new java.awt.Color(255, 255, 255));
        panelListProduct.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        labelSearch.setText("Tìm Kiếm");

        txtSearchProduct.setLabelText("Tên Sản Phẩm");

        labelSearchByPrice.setText("Lọc Giá");

        txtPriceMin.setLabelText("Giá Thấp Nhất");

        txtPriceMax.setLabelText("Giá Cao Nhất");

        tblDisplayPrdDetail.setBackground(new java.awt.Color(255, 255, 255));
        tblDisplayPrdDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDisplayPrdDetail);

        btnImport.setBackground(new java.awt.Color(23, 35, 51));
        btnImport.setForeground(new java.awt.Color(255, 255, 255));
        btnImport.setText("Import");

        btnExport.setBackground(new java.awt.Color(23, 35, 51));
        btnExport.setForeground(new java.awt.Color(255, 255, 255));
        btnExport.setText("Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnFilterPrice.setBackground(new java.awt.Color(0, 0, 102));
        btnFilterPrice.setForeground(new java.awt.Color(255, 255, 255));
        btnFilterPrice.setText("Lọc");
        btnFilterPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterPriceActionPerformed(evt);
            }
        });

        btnResetTable.setBackground(new java.awt.Color(0, 0, 102));
        btnResetTable.setForeground(new java.awt.Color(255, 255, 255));
        btnResetTable.setText("Làm Mới");
        btnResetTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelListProductLayout = new javax.swing.GroupLayout(panelListProduct);
        panelListProduct.setLayout(panelListProductLayout);
        panelListProductLayout.setHorizontalGroup(
            panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelListProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1397, Short.MAX_VALUE)
            .addGroup(panelListProductLayout.createSequentialGroup()
                .addGap(259, 259, 259)
                .addComponent(labelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(labelSearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnResetTable, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelListProductLayout.setVerticalGroup(
            panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelListProductLayout.createSequentialGroup()
                            .addComponent(labelSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(5, 5, 5)))
                    .addComponent(labelSearchByPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPriceMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPriceMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFilterPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnResetTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelListProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelDisplayProductLayout = new javax.swing.GroupLayout(panelDisplayProduct);
        panelDisplayProduct.setLayout(panelDisplayProductLayout);
        panelDisplayProductLayout.setHorizontalGroup(
            panelDisplayProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1413, Short.MAX_VALUE)
            .addGroup(panelDisplayProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelDisplayProductLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelListProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelDisplayProductLayout.setVerticalGroup(
            panelDisplayProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 698, Short.MAX_VALUE)
            .addGroup(panelDisplayProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelDisplayProductLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelListProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        panelDisplayList.addTab("Danh Sách Sản Phẩm", panelDisplayProduct);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelDisplayList, javax.swing.GroupLayout.PREFERRED_SIZE, 1418, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelDisplayList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Product prd = productInput();
        if (prd != null) {
            serviceProduct.add(prd);
            refreshTable();
            addValueToCbbNameProduct();
            JOptionPane.showMessageDialog(this, "Thêm Thành Công");
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        int row = tblDisplay.getSelectedRow();
        if (row >= 0) {
            Product prd = productInput();
            if (prd != null) {
                serviceProduct.update(prd);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Sửa Thành Công");
            } else {
                JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm Cần Sửa");
            }
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblDisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayMouseClicked
        int row = tblDisplay.getSelectedRow();
        Product prd = listProducts.get(row);
        showDetailProduct(prd);
    }//GEN-LAST:event_tblDisplayMouseClicked

    private void btnRefeshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefeshActionPerformed
        refreshTable();
    }//GEN-LAST:event_btnRefeshActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        String codeProduct = ProductRepository.getInstance().generateNextModelCode();
        txtCodeProduct.setText(codeProduct);
        txtNameProduct.setText("");
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnAddCompanyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCompanyActionPerformed
        new CompanyView().setVisible(true);
        addValueToCbbNameCompany();
    }//GEN-LAST:event_btnAddCompanyActionPerformed

    private void btnAddTypeLaptopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTypeLaptopActionPerformed
        new TypeProductView().setVisible(true);
        addValueToCbbTypeProduct();
    }//GEN-LAST:event_btnAddTypeLaptopActionPerformed

    private void btnAddDetailProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDetailProdActionPerformed
        ProductDetail productDetail = productDetailInput();
        if (productDetail != null) {
            int res = serviceProductDetail.add(productDetail);
            boolean updateQuantity = serviceProduct.updateQuantityProduct(productDetail.getIdProduct().getCode());
            if (res > 0 && updateQuantity) {
                refreshTable();
                refreshTablePrdDetail();
                JOptionPane.showMessageDialog(this, "Thêm Sản Phẩm Thành Công");
                boolean generateQR = XGenerateQRCode.doGenerate(productDetail.getSerial(), productDetail.getIdProduct().getName());
                if (generateQR) {
                    JOptionPane.showMessageDialog(this, "Tạo mã QR thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Tạo mã QR Không thành công");
                }
            }
        }
    }//GEN-LAST:event_btnAddDetailProdActionPerformed

    private void btnAddCpuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCpuActionPerformed
        new CpuView().setVisible(true);
        addValueToCbbNameCpu();
    }//GEN-LAST:event_btnAddCpuActionPerformed

    private void button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button10ActionPerformed
        new GpuView().setVisible(true);
        addValueToCbbNameGpu();
    }//GEN-LAST:event_button10ActionPerformed

    private void button11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button11ActionPerformed
        new ColorView().setVisible(true);
        addValueToCbbNameColor();
    }//GEN-LAST:event_button11ActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        resetForm();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cbbNameProductDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbNameProductDetailActionPerformed
        resetForm();
        String nameProduct = (String) cbbNameProductDetail.getSelectedItem();
        if (nameProduct != null) {

            ArrayList<String> listSerial = serviceProductDetail.getProductSerial(nameProduct);
            if (listSerial != null && listSerial.size() >= 1) {
                cbbDetailProduct.setVisible(true);
                labelSerialSel.setVisible(true);
                comboBoxModelSerial.removeAllElements();
                comboBoxModelSerial.addAll(listSerial);
                cbbDetailProduct.setSelectedIndex(0);
            } else {
                resetForm();
                btnStatusDetailProd.setVisible(false);
                labelSerialSel.setVisible(false);
                JOptionPane.showMessageDialog(this, "Sản phẩm này chưa được nhập thông tin chi tiết");
            }
        }
    }//GEN-LAST:event_cbbNameProductDetailActionPerformed

    private void btnUpdateDetailProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailProdActionPerformed
        ProductDetail productDetail = productDetailInput();
        if (productDetail != null) {
            int checkUpdate = JOptionPane.showConfirmDialog(this, "Bạn Có Chắc Muốn Cập Nhật Thông Tin ?", "Xác Nhận", JOptionPane.YES_NO_OPTION);
            if (checkUpdate == JOptionPane.YES_OPTION) {
                serviceProductDetail.update(productDetail);
                refreshTablePrdDetail();
                JOptionPane.showMessageDialog(this, "Cập Nhật Sản Phẩm " + productDetail.getIdProduct().getName() + "-" + productDetail.getSerial() + " Thành Công");
            }
        }
    }//GEN-LAST:event_btnUpdateDetailProdActionPerformed

    private void btnStatusDetailProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatusDetailProdActionPerformed
        String serial = (String) cbbDetailProduct.getSelectedItem();
        boolean successChange = serviceProductDetail.changeProductStatus(serial);
        if (successChange) {
            if (btnStatusDetailProd.getText().equals("Ngừng Bán")) {
                btnStatusDetailProd.setText("Tiếp Tục Bán");
            } else {
                btnStatusDetailProd.setText("Ngừng Bán");
            }
            refreshTablePrdDetail();
            JOptionPane.showMessageDialog(this, "Thay Đổi Trạng Thái Thành Công");
        }
    }//GEN-LAST:event_btnStatusDetailProdActionPerformed

    private void cbbDetailProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDetailProductActionPerformed
        String serial = (String) cbbDetailProduct.getSelectedItem();
        if (serial != null) {
            ProductDetail productDetail = serviceProductDetail.getProductBySerial(serial);
            if (productDetail != null) {
                showDetail(productDetail);
            }
        }
    }//GEN-LAST:event_cbbDetailProductActionPerformed

    private void btnFilterPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterPriceActionPerformed
        if (txtPriceMin.getText().isEmpty() || txtPriceMax.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Nhập Vào Mức Giá Cần Lọc");
            return;
        } else {
            try {
                double priceMinGet = Double.parseDouble(txtPriceMin.getText());
                double priceMaxGet = Double.parseDouble(txtPriceMax.getText());
                BigDecimal priceMin = BigDecimal.valueOf(priceMinGet);
                BigDecimal priceMax = BigDecimal.valueOf(priceMaxGet);
                ArrayList<ProductDetail> listFilter = serviceProductDetail.searchByPrice(priceMin, priceMax);
                fillTableProductDetail(listFilter);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mức Giá Phải Là Số");
                return;
            }
        }
    }//GEN-LAST:event_btnFilterPriceActionPerformed

    private void btnResetTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetTableActionPerformed
        refreshTablePrdDetail();
        txtPriceMin.setText("");
        txtPriceMax.setText("");
    }//GEN-LAST:event_btnResetTableActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        try {
            boolean exportSuccess = XFileExcel.exportToFile(tableModelProductDetail);
            if (exportSuccess) {
                JOptionPane.showMessageDialog(this, "Export Thành công");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Export không Thành công");
        }
    }//GEN-LAST:event_btnExportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.MyButton btnAdd;
    private app.view.swing.Button btnAddCompany;
    private app.view.swing.Button btnAddCpu;
    private app.view.swing.Button btnAddDetailProd;
    private app.view.swing.Button btnAddTypeLaptop;
    private app.view.swing.Button btnExport;
    private app.view.swing.Button btnFilterPrice;
    private app.view.swing.Button btnImport;
    private app.view.swing.MyButton btnNew;
    private app.view.swing.MyButton btnRefesh;
    private app.view.swing.Button btnRefresh;
    private app.view.swing.Button btnResetTable;
    private app.view.swing.Button btnStatusDetailProd;
    private app.view.swing.MyButton btnUpdate;
    private app.view.swing.Button btnUpdateDetailProd;
    private app.view.swing.Button button10;
    private app.view.swing.Button button11;
    private app.view.swing.Button button8;
    private app.view.swing.Button button9;
    private app.view.swing.Combobox cbbCPUProduct;
    private app.view.swing.Combobox cbbCardProduct;
    private app.view.swing.Combobox cbbColorProduct;
    private app.view.swing.Combobox cbbCompany;
    private app.view.swing.ComboBoxSuggestion cbbDetailProduct;
    private app.view.swing.Combobox cbbNameProductDetail;
    private app.view.swing.Combobox cbbRamProduct;
    private app.view.swing.Combobox cbbRomProduct;
    private app.view.swing.Combobox cbbTypeLaptop;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelCPU;
    private javax.swing.JLabel labelCard;
    private javax.swing.JLabel labelCodeProduct;
    private javax.swing.JLabel labelColor;
    private javax.swing.JLabel labelCompany;
    private javax.swing.JLabel labelDescription;
    private javax.swing.JLabel labelNamePrdError;
    private javax.swing.JLabel labelNameProduct;
    private javax.swing.JLabel labelNameProductSearch;
    private javax.swing.JLabel labelNameProductTab2;
    private javax.swing.JLabel labelPriceIn;
    private javax.swing.JLabel labelPriceInError;
    private javax.swing.JLabel labelPriceOut;
    private javax.swing.JLabel labelPriceOutError;
    private javax.swing.JLabel labelQuantity;
    private javax.swing.JLabel labelQuantityError;
    private javax.swing.JLabel labelROM;
    private javax.swing.JLabel labelRam;
    private javax.swing.JLabel labelSearch;
    private javax.swing.JLabel labelSearchByPrice;
    private javax.swing.JLabel labelSerial;
    private javax.swing.JLabel labelSerialError;
    private javax.swing.JLabel labelSerialSel;
    private javax.swing.JLabel labelTypeProduct;
    private javax.swing.JLabel labelYearWarranty;
    private javax.swing.JLabel labelYearWarrantyError;
    private javax.swing.JPanel panelAtribute;
    private javax.swing.JPanel panelDetailProduct;
    private app.view.swing.TabbedPaneCustom panelDisplayList;
    private javax.swing.JPanel panelDisplayProduct;
    private javax.swing.JPanel panelFunction;
    private javax.swing.JPanel panelListProduct;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JTable tblDisplay;
    private javax.swing.JTable tblDisplayPrdDetail;
    private javax.swing.JTextArea txtAreaDescription;
    private app.view.swing.TextField txtCodeProduct;
    private app.view.swing.TextField txtNameProduct;
    private app.view.swing.TextField txtPriceIn;
    private app.view.swing.TextField txtPriceMax;
    private app.view.swing.TextField txtPriceMin;
    private app.view.swing.TextField txtPriceOut;
    private app.view.swing.TextField txtQuantiy;
    private app.view.swing.TextField txtSearchNameProduct;
    private app.view.swing.TextField txtSearchProduct;
    private app.view.swing.TextField txtSerial;
    private app.view.swing.TextField txtYearWarranty;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package app.view;

import app.model.Cart;
import app.model.CartDetail;
import app.model.Customer;
import app.model.Order;
import app.model.OrderDetail;
import app.model.ProductDetail;
import app.model.User;
import app.service.CartDetailService;
import app.service.CartService;
import app.service.OrderDetailService;
import app.service.OrderService;
import app.service.ProductDetailService;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class SellPanel extends javax.swing.JPanel {

    private DefaultTableModel tblModelOrder = new DefaultTableModel();
    private DefaultTableModel tblModelCart = new DefaultTableModel();
    private DefaultTableModel tblModelProduct = new DefaultTableModel();
    private DefaultComboBoxModel comboBoxModelTypePaymen = new DefaultComboBoxModel();
    private ArrayList<ProductDetail> listProductDetails = new ArrayList<>();
    private ArrayList<Order> listOrders = new ArrayList<>();
    private ArrayList<CartDetail> listCartDetails = new ArrayList<>();
    private ArrayList<ProductDetail> listProducChose = new ArrayList<>();
    private ProductDetailService serviceProductDetail = new ProductDetailService();
    private OrderService orderService = new OrderService();
    private CartDetailService cartDetailService = new CartDetailService();
    private OrderDetailService orderDetailService = new OrderDetailService();
    private CartService cartService = new CartService();
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private User user;
    private Customer customer;
    private Order order;
    private Cart cart;
    private CartDetail cartDetail;
    private ProductChooseView productChooseView;
    private CustomerView customerView;

    public SellPanel(User user) {
        initComponents();
        Thread threadCam = new Thread(new Runnable() {
            @Override
            public void run() {
                initWebcam();
            }
        });
        threadCam.start();
        this.user = user;
        tblDisplayProduct.setModel(tblModelProduct);
        tblDisplayOrder.setModel(tblModelOrder);
        tblDisplayCart.setModel(tblModelCart);
        cbbTypePayment.setModel(comboBoxModelTypePaymen);
        addColumnProduct();
        addColumnOrder();
        addColumnCart();
        addValueCbbTypePayment();
        refreshDataProduct();
        refreshTableOrder();
    }

    private void addColumnOrder() {
        tblModelOrder.addColumn("STT");
        tblModelOrder.addColumn("Mã Hóa Đơn");
        tblModelOrder.addColumn("Ngày Tạo");
        tblModelOrder.addColumn("Tên Nhân Viên");
        tblModelOrder.addColumn("Tình Trạng");
    }

    private void addColumnCart() {
        tblModelCart.addColumn("STT");
        tblModelCart.addColumn("Mã Sản Phẩm");
        tblModelCart.addColumn("Tên Sản Phẩm");
        tblModelCart.addColumn("Đơn Giá");
        tblModelCart.addColumn("Số Lượng");
        tblModelCart.addColumn("Thành Tiền");
    }

    private void addColumnProduct() {
        tblModelProduct.addColumn("STT");
        tblModelProduct.addColumn("Mã");
        tblModelProduct.addColumn("Tên");
        tblModelProduct.addColumn("CPU");
        tblModelProduct.addColumn("Ram");
        tblModelProduct.addColumn("GPU");
        tblModelProduct.addColumn("Ổ Cứng");
        tblModelProduct.addColumn("Hãng");
        tblModelProduct.addColumn("Giá");
    }

    private void addValueCbbTypePayment() {
        ArrayList<String> listTypePayment = new ArrayList<>();
        listTypePayment.add("Tiền Mặt");
        listTypePayment.add("Chuyển Khoản");
        listTypePayment.add("Tiền Mặt Và Chuyển Khoản");
        comboBoxModelTypePaymen.removeAllElements();
        comboBoxModelTypePaymen.addAll(listTypePayment);
    }

    private void showInforCustomer(Customer customer) {
        if (customer.getName().equals("Khách Lẻ")) {
            txtPhoneNumber.setText("");
            txtNameCustomer.setText(customer.getName());
        } else {
            txtNameCustomer.setText(customer.getFullname());
            txtPhoneNumber.setText(customer.getPhoneNumber());
        }
    }

    private void createNewOrder() {
        if (customer != null) {
            cart = new Cart(customer, user);
            order = new Order(customer, user, cart);
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Khách Hàng");
        }

    }

    private void fillTableProduct(ArrayList<ProductDetail> list) {
        int stt = 0;
        tblModelProduct.setRowCount(0);
        for (ProductDetail productDetail : list) {
            if (productDetail.isTinhTrang()) {
                Object[] row = {
                    ++stt,
                    productDetail.getIdProduct().getCode(),
                    productDetail.getIdProduct().getName(),
                    productDetail.getIdCPU().getName(),
                    productDetail.getRAM(),
                    productDetail.getIdGPU().getName(),
                    productDetail.getROM(),
                    productDetail.getIdCompany().getName(),
                    productDetail.getGiaBan()
                };
                tblModelProduct.addRow(row);
            }
        }
    }

    private void refreshDataProduct() {
        listProductDetails = serviceProductDetail.getAll();
        fillTableProduct(listProductDetails);
    }

    private void fillTableOrder(ArrayList<Order> list) {
        int stt = 0;
        tblModelOrder.setRowCount(0);
        for (Order o : list) {
            Object[] row = {
                ++stt,
                o.getCode(),
                o.getCreateDate(),
                o.getUser().getName(),
                o.getPayStatusString()
            };
            tblModelOrder.addRow(row);
        }
    }

    private void refreshTableOrder() {
        listOrders = orderService.getAll();
        fillTableOrder(listOrders);
    }

    private void fillTableCart(ArrayList<CartDetail> list) {
        int stt = 0;
        tblModelCart.setRowCount(0);
        for (CartDetail cd : list) {
            Object[] row = {
                ++stt,
                cd.getProduct().getIdProduct().getCode(),
                cd.getProduct().getIdProduct().getName(),
                cd.getProduct().getGiaBan(),
                1,
                cd.getProduct().getGiaBan()
            };
            tblModelCart.addRow(row);
        }
    }

    private void refreshCardDetail(String cartCode) {
        listCartDetails = cartDetailService.getByCode(cartCode);
        fillTableCart(listCartDetails);
    }

    private void showDetailOrder() {
        txtCodeOrder.setText(order.getCode());
        txtNameStaff.setText(order.getUser().getFullname());
        txtCreateDate.setText(String.valueOf(order.getCreateDate()));
        refreshTotalMoney();
    }

    private void refreshTotalMoney() {
        BigDecimal totalMoney = BigDecimal.ZERO;
        for (CartDetail cd : listCartDetails) {
            totalMoney = totalMoney.add(cd.getUnitPrice());
        }
        txtTotalMoney.setText(String.valueOf(totalMoney));
    }

    private boolean payOrder() {
        if (order == null) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn");
            return false;
        }

        if (cbbTypePayment.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Phương Thức Thanh Toán");
            return false;
        }

        if (!listCartDetails.isEmpty() && order != null) {
            for (CartDetail cd : listCartDetails) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(cd.getProduct());
                orderDetail.setQuantity(cd.getQuantity());
                orderDetail.setUnitPrice(cd.getUnitPrice());
                orderDetailService.add(orderDetail);
            }
            return true;
        } else {
            return false;
        }

    }
    
    private void initWebcam(){
        Dimension size = WebcamResolution.QVGA.getSize();
        webcam = Webcam.getWebcams().get(1);
        webcam.setViewSize(size);
        
        panel = new WebcamPanel(webcam);
        panel.setPreferredSize(size);
        
        panelBarcode.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,470,300));
        panelBarcode.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupStatusOrder = new javax.swing.ButtonGroup();
        panelOrder = new javax.swing.JPanel();
        btnAdd = new app.view.swing.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayOrder = new javax.swing.JTable();
        rdAll = new javax.swing.JRadioButton();
        rdPaied = new javax.swing.JRadioButton();
        rdWaitPay = new javax.swing.JRadioButton();
        rdCancel = new javax.swing.JRadioButton();
        panelBarcode = new javax.swing.JPanel();
        panelCart = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDisplayCart = new javax.swing.JTable();
        btnDeleteCart = new app.view.swing.Button();
        panelProduct = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDisplayProduct = new javax.swing.JTable();
        txtSearchProduct = new app.view.swing.TextField();
        panelPay = new javax.swing.JPanel();
        panelCustomer = new javax.swing.JPanel();
        txtPhoneNumber = new app.view.swing.TextField();
        txtNameCustomer = new app.view.swing.TextField();
        btnChoose = new app.view.swing.Button();
        txtNameStaff = new app.view.swing.TextField();
        txtCreateDate = new app.view.swing.TextField();
        txtTotalMoney = new app.view.swing.TextField();
        txtCash = new app.view.swing.TextField();
        txtBanking = new app.view.swing.TextField();
        txtPayBack = new app.view.swing.TextField();
        cbbTypePayment = new app.view.swing.Combobox();
        btnPay = new app.view.swing.Button();
        btnRefresh = new app.view.swing.Button();
        txtCodeOrder = new app.view.swing.TextField();
        labelCashError = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1082, 615));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        panelOrder.setBackground(new java.awt.Color(255, 255, 255));
        panelOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa Đơn"));

        btnAdd.setBackground(new java.awt.Color(23, 35, 51));
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("Tạo Hóa Đơn");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        tblDisplayOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDisplayOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDisplayOrder);

        btnGroupStatusOrder.add(rdAll);
        rdAll.setSelected(true);
        rdAll.setText("Tất Cả");
        rdAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdAllActionPerformed(evt);
            }
        });

        btnGroupStatusOrder.add(rdPaied);
        rdPaied.setText("Đã Thanh Toán");
        rdPaied.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdPaiedActionPerformed(evt);
            }
        });

        btnGroupStatusOrder.add(rdWaitPay);
        rdWaitPay.setText("Chờ Thanh Toán");
        rdWaitPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdWaitPayActionPerformed(evt);
            }
        });

        btnGroupStatusOrder.add(rdCancel);
        rdCancel.setText("Hủy");
        rdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(rdAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdPaied)
                        .addGap(12, 12, 12)
                        .addComponent(rdWaitPay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdCancel)
                        .addGap(0, 63, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdAll)
                    .addComponent(rdPaied)
                    .addComponent(rdWaitPay)
                    .addComponent(rdCancel))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelBarcode.setPreferredSize(new java.awt.Dimension(0, 10));
        panelBarcode.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCart.setBackground(new java.awt.Color(255, 255, 255));
        panelCart.setBorder(javax.swing.BorderFactory.createTitledBorder("Giỏ Hàng"));

        tblDisplayCart.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDisplayCart);

        btnDeleteCart.setBackground(new java.awt.Color(23, 35, 51));
        btnDeleteCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/dustbin.png"))); // NOI18N
        btnDeleteCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCartLayout = new javax.swing.GroupLayout(panelCart);
        panelCart.setLayout(panelCartLayout);
        panelCartLayout.setHorizontalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeleteCart, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelCartLayout.setVerticalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                .addComponent(btnDeleteCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelProduct.setBackground(new java.awt.Color(255, 255, 255));
        panelProduct.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách Sản Phẩm"));

        tblDisplayProduct.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDisplayProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDisplayProductMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblDisplayProduct);

        txtSearchProduct.setLabelText("Tên Sản Phẩm");

        javax.swing.GroupLayout panelProductLayout = new javax.swing.GroupLayout(panelProduct);
        panelProduct.setLayout(panelProductLayout);
        panelProductLayout.setHorizontalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(panelProductLayout.createSequentialGroup()
                        .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelProductLayout.setVerticalGroup(
            panelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelProductLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSearchProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelPay.setBackground(new java.awt.Color(255, 255, 255));
        panelPay.setBorder(javax.swing.BorderFactory.createTitledBorder("Đơn Hàng - Thanh Toán"));

        panelCustomer.setBackground(new java.awt.Color(255, 255, 255));
        panelCustomer.setBorder(javax.swing.BorderFactory.createTitledBorder("Thông Tin Khách Hàng"));

        txtPhoneNumber.setLabelText("Số Điện Thoại");

        txtNameCustomer.setLabelText("Tên Khách Hàng");

        btnChoose.setBackground(new java.awt.Color(23, 35, 51));
        btnChoose.setForeground(new java.awt.Color(255, 255, 255));
        btnChoose.setText("Chọn");
        btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCustomerLayout = new javax.swing.GroupLayout(panelCustomer);
        panelCustomer.setLayout(panelCustomerLayout);
        panelCustomerLayout.setHorizontalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panelCustomerLayout.setVerticalGroup(
            panelCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCustomerLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelCustomerLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtNameStaff.setEditable(false);
        txtNameStaff.setLabelText("Tên Nhân Viên");

        txtCreateDate.setEditable(false);
        txtCreateDate.setLabelText("Ngày Tạo");

        txtTotalMoney.setEditable(false);
        txtTotalMoney.setLabelText("Tổng Tiền");

        txtCash.setLabelText("Tiền Mặt");
        txtCash.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCashFocusLost(evt);
            }
        });

        txtBanking.setLabelText("Tiền Chuyển Khoản");

        txtPayBack.setEditable(false);
        txtPayBack.setLabelText("Tiền Trả Lại");

        cbbTypePayment.setLabeText("Hình Thức Thanh Toán");
        cbbTypePayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTypePaymentActionPerformed(evt);
            }
        });

        btnPay.setBackground(new java.awt.Color(23, 35, 51));
        btnPay.setForeground(new java.awt.Color(255, 255, 255));
        btnPay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/app/content/hand.png"))); // NOI18N
        btnPay.setText("Thanh Toán");
        btnPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPayActionPerformed(evt);
            }
        });

        btnRefresh.setBackground(new java.awt.Color(23, 35, 51));
        btnRefresh.setForeground(new java.awt.Color(255, 255, 255));
        btnRefresh.setText("Làm Mới");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        txtCodeOrder.setEditable(false);
        txtCodeOrder.setLabelText("Mã Hóa Đơn");

        labelCashError.setForeground(new java.awt.Color(255, 51, 0));
        labelCashError.setPreferredSize(new java.awt.Dimension(0, 12));

        javax.swing.GroupLayout panelPayLayout = new javax.swing.GroupLayout(panelPay);
        panelPay.setLayout(panelPayLayout);
        panelPayLayout.setHorizontalGroup(
            panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPayLayout.createSequentialGroup()
                .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPayBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBanking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCash, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotalMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCreateDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNameStaff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbTypePayment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodeOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                            .addComponent(labelCashError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(panelPayLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelPayLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPayLayout.setVerticalGroup(
            panelPayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtCodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtNameStaff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCreateDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTotalMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbbTypePayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(txtCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelCashError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBanking, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPayBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panelCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(panelPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelPay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelBarcode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(panelCart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        createNewOrder();
        if (order != null && cart != null) {
            boolean addCartSuccess = cartService.add(cart);
            boolean addSuccess = orderService.add(order);
            if (addSuccess && addCartSuccess) {
                refreshTableOrder();
                JOptionPane.showMessageDialog(this, "Tạo Hóa Đơn Thành Công");
            } else {
                JOptionPane.showMessageDialog(this, "Đã Xảy Ra Lỗi");
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseActionPerformed
        customerView = new CustomerView();
        customerView.setVisible(true);
        customerView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                customer = customerView.getCustomer();
                if (customer != null) {
                    showInforCustomer(customer);
                }
            }
        });
    }//GEN-LAST:event_btnChooseActionPerformed

    private void tblDisplayProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayProductMouseClicked
        int row = tblDisplayProduct.getSelectedRow();
        int column = 1;
        if (this.cart != null) {
            if (row >= 0) {
                String productCode = (String) tblDisplayProduct.getValueAt(row, column);
                if (productCode != null && !productCode.equals("")) {
                    productChooseView = new ProductChooseView(productCode);
                    productChooseView.setVisible(true);
                    productChooseView.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            listProductDetails = productChooseView.getListProductChose();
                            for (ProductDetail productDetail : listProductDetails) {
                                cartDetail = new CartDetail(cart, productDetail, 1, productDetail.getGiaBan());
                                boolean addSuccess = cartDetailService.add(cartDetail);
                                if (addSuccess) {
                                    refreshCardDetail(cart.getCode());
                                    refreshDataProduct();
                                    refreshTotalMoney();
                                }
                            }
                        }
                    });
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Hóa Đơn");
        }

    }//GEN-LAST:event_tblDisplayProductMouseClicked

    private void tblDisplayOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDisplayOrderMouseClicked
        int row = tblDisplayOrder.getSelectedRow();
        int column = 1;
        if (row >= 0) {
            String orderCode = (String) tblDisplayOrder.getValueAt(row, column);
            this.order = orderService.getByCode(orderCode);
            this.cart = cartService.selectByCode(order.getCart().getCode());
            this.customer = order.getCustomer();
            listCartDetails = cartDetailService.getByCode(cart.getCode());
            showInforCustomer(customer);
            showDetailOrder();
            if (listCartDetails.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hóa Đơn Chưa Có Sản Phẩm Nào");
                tblModelCart.setRowCount(0);
            } else {
                fillTableCart(listCartDetails);
            }
        }
    }//GEN-LAST:event_tblDisplayOrderMouseClicked

    private void btnDeleteCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCartActionPerformed
        int row = tblDisplayCart.getSelectedRow();
        System.out.println(listCartDetails.size());
        if (row >= 0 && !listCartDetails.isEmpty()) {
            CartDetail cartDetailDelete = listCartDetails.get(row);
            if (cartDetailDelete != null) {
                serviceProductDetail.changeProductStatus(cartDetailDelete.getProduct().getSerial());
                boolean deleteSuccess = cartDetailService.delete(cartDetailDelete);
                if (deleteSuccess) {
                    refreshCardDetail(cart.getCode());
                    refreshDataProduct();
                    JOptionPane.showMessageDialog(this, "Xóa Thành Công");
                } else {
                    JOptionPane.showMessageDialog(this, "Đã Xay Ra Lỗi");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bạn Chưa Chọn Sản Phẩm Cần Xóa");
        }
    }//GEN-LAST:event_btnDeleteCartActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        customer = null;
        order = null;
        cart = null;
        cartDetail = null;
        tblModelCart.setRowCount(0);
        tblDisplayOrder.getSelectionModel().clearSelection();
        tblDisplayProduct.getSelectionModel().clearSelection();
        txtCodeOrder.setText("");
        txtNameCustomer.setText("");
        txtCreateDate.setText("");
        txtNameCustomer.setText("");
        txtPhoneNumber.setText("");
        txtTotalMoney.setText("");
        cbbTypePayment.setSelectedIndex(-1);
        txtNameStaff.setText("");
        refreshDataProduct();
        refreshTableOrder();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void rdPaiedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdPaiedActionPerformed
        listOrders = orderService.getOrderByStatus(1);
        fillTableOrder(listOrders);
    }//GEN-LAST:event_rdPaiedActionPerformed

    private void rdWaitPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdWaitPayActionPerformed
        listOrders = orderService.getOrderByStatus(0);
        fillTableOrder(listOrders);
    }//GEN-LAST:event_rdWaitPayActionPerformed

    private void rdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdCancelActionPerformed
        listOrders = orderService.getOrderByStatus(2);
        fillTableOrder(listOrders);
    }//GEN-LAST:event_rdCancelActionPerformed

    private void rdAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdAllActionPerformed
        listOrders = orderService.getAll();
        fillTableOrder(listOrders);
    }//GEN-LAST:event_rdAllActionPerformed

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPayActionPerformed

    }//GEN-LAST:event_btnPayActionPerformed

    private void cbbTypePaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTypePaymentActionPerformed
        int typePamentChoose = cbbTypePayment.getSelectedIndex();
        switch (typePamentChoose) {
            case -1 -> {
                txtBanking.setEditable(false);
                txtCash.setEditable(false);
            }

            case 0 -> {
                txtBanking.setEditable(false);
                txtCash.setEditable(true);
            }

            case 1 -> {
                txtBanking.setEditable(true);
                txtCash.setEditable(false);
            }

            case 2 -> {
                txtBanking.setEditable(true);
                txtCash.setEditable(true);
            }

            default ->
                throw new AssertionError();
        }
    }//GEN-LAST:event_cbbTypePaymentActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.grabFocus();
    }//GEN-LAST:event_formMouseClicked

    private void txtCashFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCashFocusLost
        try {
            BigDecimal totalMoney = new BigDecimal(txtTotalMoney.getText());
            BigDecimal cash = new BigDecimal(txtCash.getText());
            if (cash.compareTo(totalMoney) == -1) {
                labelCashError.setText("Tiền Khách Phải Lớn Hơn Hoặc Bằng Tiền Hóa Đơn");
            } else {
                labelCashError.setText("");
                BigDecimal payBack = cash.subtract(totalMoney);
                txtPayBack.setText(String.valueOf(payBack));
            }
        } catch (NumberFormatException e) {
            labelCashError.setText("Tiền Phải Là Số");
        }

    }//GEN-LAST:event_txtCashFocusLost

//    private void payOnchange(JTextField txt) {
//        if (order != null) {
//            BigDecimal totalMoney = new BigDecimal(txtTotalMoney.getText());
//            txt.getDocument().addDocumentListener(new DocumentListener() {
//                @Override
//                public void insertUpdate(DocumentEvent e) {
//                    BigDecimal customerMoney = new BigDecimal(txt.getText());
//                    if (totalMoney.compareTo(customerMoney) == -1) {
//                        labelCashError.setText("Lỗi");
//                    } else {
//                        BigDecimal payBack = customerMoney.subtract(totalMoney);
//                        txtPayBack.setText(String.valueOf(payBack));
//                    }
//
//                }
//
//                @Override
//                public void removeUpdate(DocumentEvent e) {
//                    labelCashError.setText("");
//                }
//
//                @Override
//                public void changedUpdate(DocumentEvent e) {
//                    BigDecimal customerMoney = new BigDecimal(txt.getText());
//                    if (totalMoney.compareTo(customerMoney) == 1) {
//                        labelCashError.setText("Lỗi");
//                    } else {
//                        BigDecimal payBack = customerMoney.subtract(totalMoney);
//                        txtPayBack.setText(String.valueOf(payBack));
//                    }
//                }
//            });
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.view.swing.Button btnAdd;
    private app.view.swing.Button btnChoose;
    private app.view.swing.Button btnDeleteCart;
    private javax.swing.ButtonGroup btnGroupStatusOrder;
    private app.view.swing.Button btnPay;
    private app.view.swing.Button btnRefresh;
    private app.view.swing.Combobox cbbTypePayment;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelCashError;
    private javax.swing.JPanel panelBarcode;
    private javax.swing.JPanel panelCart;
    private javax.swing.JPanel panelCustomer;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JPanel panelPay;
    private javax.swing.JPanel panelProduct;
    private javax.swing.JRadioButton rdAll;
    private javax.swing.JRadioButton rdCancel;
    private javax.swing.JRadioButton rdPaied;
    private javax.swing.JRadioButton rdWaitPay;
    private javax.swing.JTable tblDisplayCart;
    private javax.swing.JTable tblDisplayOrder;
    private javax.swing.JTable tblDisplayProduct;
    private app.view.swing.TextField txtBanking;
    private app.view.swing.TextField txtCash;
    private app.view.swing.TextField txtCodeOrder;
    private app.view.swing.TextField txtCreateDate;
    private app.view.swing.TextField txtNameCustomer;
    private app.view.swing.TextField txtNameStaff;
    private app.view.swing.TextField txtPayBack;
    private app.view.swing.TextField txtPhoneNumber;
    private app.view.swing.TextField txtSearchProduct;
    private app.view.swing.TextField txtTotalMoney;
    // End of variables declaration//GEN-END:variables
}

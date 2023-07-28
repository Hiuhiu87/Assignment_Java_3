/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package app.view;

import app.model.ProductDetail;
import app.service.ProductDetailService;
import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Admin
 */
public class ProductChooseView extends javax.swing.JFrame implements TableModelListener {

    private static final int COLUMN_CHECKBOX = 0;
    private static final int COLUMN_SERIAL = 3;
    private String productCode;
    private DefaultTableModel tableModel;
    private ArrayList<ProductDetail> listProduct = new ArrayList<>();
    private ProductDetailService service = new ProductDetailService();
    private ArrayList<ProductDetail> listProductChose = new ArrayList<>();

    public ArrayList<ProductDetail> getListProductChose() {
        return this.listProductChose;
    }

    public ProductChooseView(String productCode) {
        initComponents();
        tableModel = (DefaultTableModel) tblDisplayProduct.getModel();
        tableModel.addTableModelListener((TableModelEvent e) -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == COLUMN_CHECKBOX) {
                tableModel = (DefaultTableModel) (TableModel) e.getSource();
                Boolean checked = (Boolean) tableModel.getValueAt(row, column);
                if (checked) {
                    String serialProduct = (String) tableModel.getValueAt(row, COLUMN_SERIAL);
                    ProductDetail productDetail = service.getProductBySerial(serialProduct);
                    listProductChose.add(productDetail);
                    service.changeProductStatus(serialProduct);
                    listProduct.remove(productDetail);
                } else {
                    String serialProduct = (String) tableModel.getValueAt(row, COLUMN_SERIAL);
                    ProductDetail productDetail = service.getProductBySerial(serialProduct);
                    listProductChose.remove(productDetail);
                }
            }
        });
        this.productCode = productCode;
        if (productCode != null && !productCode.equalsIgnoreCase("")) {
            listProduct = service.getListByCode(productCode);
            fillTable(listProduct);
        }
    }

    private void fillTable(ArrayList<ProductDetail> list) {
        tableModel.setRowCount(0);
        for (ProductDetail productDetail : list) {
            if (productDetail.isTinhTrang()) {
                Object[] row = {
                    false,
                    productDetail.getIdProduct().getCode(),
                    productDetail.getIdProduct().getName(),
                    productDetail.getSerial()
                };
                tableModel.addRow(row);
            }

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblDisplayProduct = new javax.swing.JTable();
        cbChooseAll = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblDisplayProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Boolean(false), null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "", "Tên", "Mã Sản Phẩm", "Serial"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDisplayProduct.setAutoscrolls(false);
        tblDisplayProduct.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblDisplayProduct);
        if (tblDisplayProduct.getColumnModel().getColumnCount() > 0) {
            tblDisplayProduct.getColumnModel().getColumn(0).setResizable(false);
            tblDisplayProduct.getColumnModel().getColumn(0).setPreferredWidth(2);
            tblDisplayProduct.getColumnModel().getColumn(1).setResizable(false);
            tblDisplayProduct.getColumnModel().getColumn(2).setResizable(false);
            tblDisplayProduct.getColumnModel().getColumn(3).setResizable(false);
        }

        cbChooseAll.setText("All");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(cbChooseAll, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(cbChooseAll)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(ProductChooseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ProductChooseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ProductChooseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ProductChooseView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ProductChooseView().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbChooseAll;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDisplayProduct;
    // End of variables declaration//GEN-END:variables

    @Override
    public void tableChanged(TableModelEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
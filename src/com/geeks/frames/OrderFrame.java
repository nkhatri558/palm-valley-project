/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.frames;

import com.geeks.beans.AccountBean;
import com.geeks.beans.OrderBean;
import com.geeks.beans.OrderDetailsBean;
import com.geeks.beans.ProductBean;
import com.geeks.beans.UserBean;
import com.geeks.dao.OrderDao;
import com.geeks.dao.OrderDetailsDao;
import com.geeks.dao.ProductDao;
import com.geeks.dao.UserDao;
import com.geeks.daoimpl.OrderDaoImpl;
import com.geeks.daoimpl.OrderDetailsDaoImpl;
import com.geeks.daoimpl.ProductDaoImpl;
import com.geeks.daoimpl.UserDaoImpl;
import com.geeks.util.BarcodeGenerator;
import com.geeks.util.UtilityClass;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;  
import java.util.logging.Level;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.core.exception.BirtException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author khatr
 */
public class OrderFrame extends javax.swing.JFrame {

    private DefaultTableModel model;
    private Double grandTotal;
    private Double countTotal;
    private Double gst;
    private Double amountReceived;
    private Double change;
    private Double tax;
    private JPopupMenu popupMenu = null;
    private JMenuItem menuItem = null;
    private JMenuItem editItem = null;
    private UserBean userBean;
    private OrderBean orderBean;
    

    /**
     * Creates new form OrderFrame
     *
     * @param userBean
     * @throws java.sql.SQLException
     */
    public OrderFrame(UserBean userBean) throws SQLException {
        grandTotal = 0.0;
        countTotal = 0.0;
        gst = 0.0;
        change = 0.0;
        amountReceived = 0.0;
        this.userBean = userBean;
        initComponents();
        OrderDao orderDao = new OrderDaoImpl();
        String barcode = BarcodeGenerator.generateBarcode();
        Boolean isMatch = true;
        while (!isMatch) {
            isMatch = orderDao.checkBarcode(barcode);
            if (isMatch) {
                barcode = BarcodeGenerator.generateBarcode();
            }
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        orderNoTxt.setText(barcode);
        orderNoTxt.setEnabled(false);
        populateProductTable();
        populateCustomerCombo();
        String tableHeader[] = {"Product Name", "Quantity", "Price", "Total"};
        model = new DefaultTableModel(tableHeader, 0);
        model.setRowCount(0);
        grandTotalTxt.setText(grandTotal.toString());
        countTotalTxt.setText(countTotal.toString());
        gstTxt.setText(gst.toString());
        //amountReceivedTxt.setText(amountReceived.toString());
        changeTxt.setText(change.toString());
        popupMenu = new JPopupMenu();
        popUpMenu(this);
        //updateBtn.setEnabled(false);
        //deleteBtn.setEnabled(false);
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);

    }

    public OrderFrame(UserBean userBean, OrderBean orderBean) throws SQLException, ParseException {
        grandTotal = 0.0;

        countTotal = 0.0;
        gst = 0.0;
        change = 0.0;
        amountReceived = 0.0;
        this.orderBean = orderBean;
        this.userBean = userBean;
        initComponents();
        populateOrderDetailsTable(orderBean.getOrderId());
        populateProductTable();
        populateCustomerCombo();
        orderNoTxt.setText(orderBean.getOrderNo());
        orderNoTxt.setEnabled(false);
        customerCombo.setSelectedItem(orderBean.getCustomer().getUsername());
        System.out.println(orderBean.getCustomer().getUsername());
        System.out.println(orderBean.getOrderDate().toString());
        dateTxt.setDate(orderBean.getOrderDate());
        updateBtn.setVisible(true);
        deleteBtn.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        calculateTotals();
        saveBtn.setVisible(false);
        amountReceivedTxt.setText(Double.toString(0.0));
        amountPaidLbl.setText("Amount Paid : " + orderBean.getAmountPaid().toString());
        gst = Double.parseDouble(String.format("%.3f", Double.valueOf(grandTotalTxt.getText()) * 0.18));
        countTotal = Double.parseDouble(String.format("%.3f", Double.valueOf(grandTotalTxt.getText()) + Double.valueOf(grandTotalTxt.getText()) * 0.18));
        change = Double.parseDouble(String.format("%.3f", Double.valueOf(orderBean.getAmountPaid() - countTotal)));
        changeTxt.setText(change.toString());
        popupMenu = new JPopupMenu();
        popUpMenu(this);

    }

    public void popUpMenu(JFrame jframe) {
        menuItem = new JMenuItem("Remove Product");
        menuItem.getAccessibleContext().setAccessibleDescription("Remove Product");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer quantitySelected = (Integer) orderTable.getValueAt(orderTable.getSelectedRow(), 1);
                String name = (String) orderTable.getValueAt(orderTable.getSelectedRow(), 0);
                if (orderBean != null) {
                    Integer id = (Integer) orderTable.getModel().getValueAt(orderTable.getSelectedRow(), 0);
                    if (id != 0) {
                        OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();
                        OrderDetailsBean orderDetailsBean = orderDetailsDao.getOrderDetail((Integer) orderTable.getModel().getValueAt(orderTable.getSelectedRow(), 0));
                        orderDetailsBean.setModifiedBy(userBean.getUserId());
                        java.util.Date date = new java.util.Date(System.currentTimeMillis());
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        String stringDate = sdf.format(date);
                        orderDetailsBean.setModifiedDate(Timestamp.valueOf(stringDate));
                        orderDetailsDao.delete(orderDetailsBean);
                        ProductDao productDao = new ProductDaoImpl();
                        ProductBean productBean = productDao.getProductByProductName(name);
                        productBean.setQuantity(productBean.getQuantity() + orderDetailsBean.getQuantity());
                        productDao.updateProduct(productBean);
                    }
                }

                model = (DefaultTableModel) orderTable.getModel();
                model.removeRow(orderTable.getSelectedRow());
                calculateTotals();
                for (int i = 0; i < productTable.getRowCount(); i++) {
                    if (productTable.getValueAt(i, 1).toString().equals(name)) {
                        int totalQuantity = (int) productTable.getValueAt(i, 5);
                        totalQuantity += quantitySelected;
                        productTable.setValueAt(totalQuantity, i, 5);
                        break;
                    }
                }
            }
        });
        popupMenu.add(menuItem);
        editItem = new JMenuItem("Edit");
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
        productTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderTable = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        grandTotalTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        gstTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        countTotalTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        amountReceivedTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        changeTxt = new javax.swing.JTextField();
        backBtn = new javax.swing.JButton();
        checkAmountReceivedLbl = new javax.swing.JLabel();
        amountPaidLbl = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        generateBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateTxt = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        orderNoTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        customerCombo = new javax.swing.JComboBox<>();
        checkProductLbl = new javax.swing.JLabel();
        addCustomerBtn = new javax.swing.JButton();
        checkQuantityLbl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        productNameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        quantityTxt = new javax.swing.JTextField();
        addQuantityBtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        productTable.setModel(new javax.swing.table.DefaultTableModel(
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
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(productTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(469, 119, 890, 190));

        orderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity", "Price", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        orderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                orderTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(orderTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(469, 357, 890, 200));

        jLabel6.setText("Grand Total");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 611, 100, 30));

        grandTotalTxt.setEnabled(false);
        grandTotalTxt.setFocusable(false);
        getContentPane().add(grandTotalTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(841, 616, 100, -1));

        jLabel7.setText("GST (18%)");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 652, 100, 20));

        gstTxt.setEnabled(false);
        getContentPane().add(gstTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(841, 652, 100, -1));

        jLabel8.setText("Count Total");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 686, 100, -1));

        countTotalTxt.setEnabled(false);
        getContentPane().add(countTotalTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(841, 683, 100, -1));

        jLabel9.setText("Amount Received");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(959, 615, 100, 23));

        amountReceivedTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                amountReceivedTxtFocusGained(evt);
            }
        });
        amountReceivedTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountReceivedTxtActionPerformed(evt);
            }
        });
        amountReceivedTxt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                amountReceivedTxtPropertyChange(evt);
            }
        });
        amountReceivedTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amountReceivedTxtKeyPressed(evt);
            }
        });
        getContentPane().add(amountReceivedTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(1085, 616, 100, -1));

        jLabel10.setText("Change");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(975, 655, 100, -1));

        changeTxt.setEnabled(false);
        changeTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeTxtActionPerformed(evt);
            }
        });
        getContentPane().add(changeTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(1085, 652, 100, -1));

        backBtn.setBackground(new java.awt.Color(64, 38, 28));
        backBtn.setForeground(new java.awt.Color(255, 255, 255));
        backBtn.setText("Back");
        backBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        backBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 30));
        getContentPane().add(checkAmountReceivedLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1195, 621, 100, 20));
        getContentPane().add(amountPaidLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1001, 38, 179, 30));

        jLabel11.setBackground(new java.awt.Color(64, 38, 28));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Order");
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 0, 1360, 40));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Save.png"))); // NOI18N
        saveBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Update.png"))); // NOI18N
        updateBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Delete.png"))); // NOI18N
        deleteBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        generateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Print.png"))); // NOI18N
        generateBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(saveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(updateBtn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(deleteBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(generateBtn)))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(updateBtn)
                    .addComponent(saveBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deleteBtn)
                    .addComponent(generateBtn))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 510, 470, 260));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Date");

        jLabel2.setText("Order No");

        orderNoTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderNoTxtActionPerformed(evt);
            }
        });

        jLabel3.setText("Customer");

        customerCombo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                customerComboFocusGained(evt);
            }
        });

        addCustomerBtn.setBackground(new java.awt.Color(64, 38, 28));
        addCustomerBtn.setForeground(new java.awt.Color(255, 255, 255));
        addCustomerBtn.setText("Add");
        addCustomerBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addCustomerBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addCustomerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCustomerBtnActionPerformed(evt);
            }
        });

        jLabel4.setText("Product");

        productNameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                productNameTxtFocusGained(evt);
            }
        });
        productNameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productNameTxtActionPerformed(evt);
            }
        });
        productNameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productNameTxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                productNameTxtKeyReleased(evt);
            }
        });

        jLabel5.setText("Quantity");

        quantityTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                quantityTxtFocusGained(evt);
            }
        });
        quantityTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantityTxtKeyReleased(evt);
            }
        });

        addQuantityBtn.setBackground(new java.awt.Color(64, 38, 28));
        addQuantityBtn.setForeground(new java.awt.Color(255, 255, 255));
        addQuantityBtn.setText("Add");
        addQuantityBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addQuantityBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addQuantityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addQuantityBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(checkQuantityLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(checkProductLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(productNameTxt))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(customerCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(orderNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addCustomerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                    .addComponent(addQuantityBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(orderNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(customerCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCustomerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkProductLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addQuantityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(checkQuantityLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 79, 470, 433));

        jLabel12.setBackground(new java.awt.Color(64, 38, 28));
        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Products");
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 900, 40));

        jLabel13.setBackground(new java.awt.Color(64, 38, 28));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Order");
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 304, 890, 50));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setOpaque(true);
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 34, 1360, 50));

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 554, 890, 210));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void orderNoTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderNoTxtActionPerformed

    }//GEN-LAST:event_orderNoTxtActionPerformed

    private void productTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTableMouseClicked
        String productName = (String) productTable.getValueAt(productTable.getSelectedRow(), 1);
        productNameTxt.setText(productName);
    }//GEN-LAST:event_productTableMouseClicked

    private void addQuantityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addQuantityBtnActionPerformed

        Boolean isValid = validateFields();
        if (isValid) {
            model = (DefaultTableModel) orderTable.getModel();
            try {
                Boolean isMatch = false;
                String productName = productNameTxt.getText();
                Integer quantity = Integer.parseInt(quantityTxt.getText());
                BigDecimal bdPrice = (BigDecimal) productTable.getValueAt(productTable.getSelectedRow(), 3);
                Double price = bdPrice.doubleValue();
                Integer quantityAvailable = (Integer) productTable.getValueAt(productTable.getSelectedRow(), 5);
                int remainingQuantity = quantityAvailable - quantity;
                if (remainingQuantity < 0) {
                    checkQuantityLbl.setText("*Amount Not Available");
                } else {
                    for (int i = 0; i < orderTable.getRowCount(); i++) {
                        if (orderTable.getValueAt(i, 0).equals(productName)) {
                            isMatch = true;
                            Integer addQuantity = (Integer) orderTable.getValueAt(i, 1) + quantity;
                            orderTable.setValueAt(addQuantity, i, 1);
                            Double totalPrice = price * addQuantity;
                            orderTable.setValueAt(totalPrice, i, 3);
                            productTable.setValueAt(remainingQuantity, productTable.getSelectedRow(), 5);
                            break;
                        }
                    }
                    Double totalPrice = price * quantity;
                    if (!isMatch) {
                        productTable.setValueAt(remainingQuantity, productTable.getSelectedRow(), 5);
                        Vector<Object> data = new Vector<>();
                        if(orderBean != null) {
                            data.add(0);
                        }
                        
                        data.add(productName);
                        data.add(quantity);
                        data.add(price);
                        data.add(totalPrice);

                        model.addRow(data);
                        orderTable.setModel(model);
                    }

//                    grandTotal += totalPrice;
//                    gst += Double.parseDouble(String.format("%.3f", totalPrice * 0.18));
//                    countTotal += Double.parseDouble(String.format("%.3f", totalPrice + totalPrice * 0.18));
//
//                    grandTotalTxt.setText(grandTotal.toString());
//                    countTotalTxt.setText(countTotal.toString());
//                    gstTxt.setText(gst.toString());
                    calculateGrandTotal();
                    productNameTxt.setText("");
                    quantityTxt.setText("");

                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                checkProductLbl.setText("*Product does not exist");
            }

        }


    }//GEN-LAST:event_addQuantityBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed

        addData();
        clearFields();


    }//GEN-LAST:event_saveBtnActionPerformed

    private void addCustomerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCustomerBtnActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CustomerFrame customerFrame = new CustomerFrame(userBean);
                customerFrame.setVisible(true);

            }
        });

    }//GEN-LAST:event_addCustomerBtnActionPerformed

    private void customerComboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_customerComboFocusGained
        customerCombo.removeAllItems();
        populateCustomerCombo();
    }//GEN-LAST:event_customerComboFocusGained

    private void productNameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productNameTxtFocusGained
        checkProductLbl.setText("");
    }//GEN-LAST:event_productNameTxtFocusGained

    private void quantityTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityTxtFocusGained
        checkQuantityLbl.setText("");
    }//GEN-LAST:event_quantityTxtFocusGained

    private void amountReceivedTxtPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_amountReceivedTxtPropertyChange
//        change = Double.valueOf(amountReceivedTxt.getText()) - countTotal;
//        changeTxt.setText(change.toString());
    }//GEN-LAST:event_amountReceivedTxtPropertyChange

    private void amountReceivedTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountReceivedTxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                if (orderBean != null) {
                    Double amountReceived = orderBean.getAmountPaid() + Double.valueOf(amountReceivedTxt.getText());
                    if (Double.valueOf(amountReceivedTxt.getText()) == orderBean.getAmountPaid()) {
                        change = amountReceived - Double.valueOf(countTotalTxt.getText());
                        changeTxt.setText(change.toString());
                    } else {

                        //change = Double.parseDouble(String.format("%.3f", Double.valueOf(amountReceivedTxt.getText()) - countTotal));
                        change = Double.parseDouble(String.format("%.3f", amountReceived - countTotal));
                        changeTxt.setText(change.toString());

                    }
                } else {
                    if (amountReceivedTxt.getText().trim().isEmpty()) {
                        change = Double.parseDouble(String.format("%.3f", 0.0 - countTotal));
                        changeTxt.setText(change.toString());
                    } else {
                        change = Double.parseDouble(String.format("%.3f", Double.valueOf(amountReceivedTxt.getText()) - countTotal));
                        System.out.println("this Code is running");
                        changeTxt.setText(change.toString());
                    }
                }
            } catch (NumberFormatException ex) {
                checkAmountReceivedLbl.setText("*Invalid Input");
            }

        }
    }//GEN-LAST:event_amountReceivedTxtKeyPressed

    private void quantityTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantityTxtKeyReleased
        try {
            Integer quantity = Integer.parseInt(quantityTxt.getText());
        } catch (NumberFormatException ex) {
            checkQuantityLbl.setText("*Invalid Input");
        }
        if (quantityTxt.getText().trim().isEmpty()) {
            checkQuantityLbl.setText("");
        }
    }//GEN-LAST:event_quantityTxtKeyReleased

    private void productNameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productNameTxtKeyPressed
        UtilityClass.searchFromTable(productTable, productNameTxt.getText());
    }//GEN-LAST:event_productNameTxtKeyPressed

    private void productNameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productNameTxtKeyReleased
        try {
            String productName = productNameTxt.getText();
        } catch (NumberFormatException ex) {
            checkProductLbl.setText("*Invalid Input");
        }
        if (productNameTxt.getText().trim().isEmpty()) {
            checkProductLbl.setText("");
        }
        if (productTable.getRowCount() != 0) {
            if (productTable.getRowCount() == 1) {
                productTable.setRowSelectionInterval(0, 0);
            }
        }
    }//GEN-LAST:event_productNameTxtKeyReleased

    private void orderTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orderTableMouseReleased
        if (evt.isPopupTrigger()) {
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;
            popupMenu.show(this, x, y);
        }
    }//GEN-LAST:event_orderTableMouseReleased

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateOrderDetails();
        updateOrder();
        ViewOrders viewOrders = new ViewOrders(userBean);
        viewOrders.setVisible(true);
        dispose();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void productNameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productNameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productNameTxtActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        if (orderBean != null) {
            ViewOrders viewOrders = new ViewOrders(userBean);
            viewOrders.setVisible(true);
            dispose();
        } else {
            RestaurantDashboard dashboard = new RestaurantDashboard(userBean);
            dashboard.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_backBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.deleteOrder(orderBean.getOrderId());
        ProductDao productDao = new ProductDaoImpl();
        ProductBean productBean = null;
        for (int i = 0; i < orderTable.getRowCount(); i++) {
            Integer quantity = (Integer) orderTable.getValueAt(i, 1);
            String productName = (String) orderTable.getValueAt(i, 0);
            productBean = productDao.getProductByProductName(productName);
            productBean.setQuantity(productBean.getQuantity() + quantity);
            productDao.updateProduct(productBean);

        }
        ViewOrders viewOrders = new ViewOrders(userBean);
        viewOrders.setVisible(true);
        dispose();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void amountReceivedTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amountReceivedTxtFocusGained
//        amountReceivedTxt.setText("");
        checkAmountReceivedLbl.setText("");
    }//GEN-LAST:event_amountReceivedTxtFocusGained

    private void changeTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_changeTxtActionPerformed

    private void amountReceivedTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountReceivedTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountReceivedTxtActionPerformed

    private void generateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateBtnActionPerformed
          if(orderBean == null) {
              addData();
          }
// TODO add your handling code here:
        EngineConfig config = new EngineConfig();
        String path1="C:/Users/khatr/Desktop";
        //String orderNo="7";
          // config.setBIRTHome(path1);
           config.setLogConfig("C:\\Users\\khatr\\OneDrive\\Desktop\\temp", Level.FINEST);
        try {
            Platform.startup(config);
        } catch (BirtException ex) {
            Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        IReportEngineFactory factory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        IReportEngine engine = factory.createReportEngine(config);
           String path = "E:\\birt\\workspace\\palmValleyReport.rptdesign";
        IReportRunnable design = null ;
        try {
            design = engine.openReportDesign(path);
        } catch (EngineException ex) {
            Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
           IRunAndRenderTask task = engine.createRunAndRenderTask(design);
        String  saleNumber=orderNoTxt.getText();
        System.out.println(orderNoTxt.getText());
          task.setParameterValue("Top Count", (new Integer(5)));
          //task.setParameterValue("orderParameter", orderNo);
           task.setParameterValue("param", saleNumber);
           task.validateParameters();

           HTMLRenderOption options = new HTMLRenderOption();
           options.setOutputFileName("C:\\Users\\khatr\\OneDrive\\Desktop\\temp\\parameter1.html");
           options.setOutputFormat("html");
           //options.setHtmlRtLFlag(false);
           //options.setEmbeddable(false);
           //options.setImageDirectory("C:\\test\\images");

           //PDFRenderOption options = new PDFRenderOption();
           //options.setOutputFileName("c:/temp/test.pdf");
           //options.setOutputFormat("pdf");
           task.setRenderOption(options);
           if (Desktop.isDesktopSupported()) {
               try {
                   File myFile = new File("C:\\Users\\khatr\\OneDrive\\Desktop\\temp\\parameter1.html");
                   if(myFile.exists() == false) {
                       myFile.createNewFile();
                   }
                   Desktop.getDesktop().open(myFile);
               } catch (IOException ex) {
                   JOptionPane.showMessageDialog(this, ex.getMessage());
               }
           }
        try {
            task.run();
        } catch (EngineException ex) {
            Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
           task.close();
           engine.destroy();
           if(orderBean == null) {
               clearFields();
           }
    }//GEN-LAST:event_generateBtnActionPerformed
    public void addData() {
        UserDao userDao = new UserDaoImpl();
        OrderDao orderDao = new OrderDaoImpl();
        ProductDao productDao = new ProductDaoImpl();
        OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();
        java.util.Date d = dateTxt.getDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String s = df.format(d);
        OrderBean orderBean = new OrderBean();
        orderBean.setOrderDate(Date.valueOf(s));
        orderBean.setOrderNo(orderNoTxt.getText());
        //Getting Details of customer by selected name
        UserBean userBean = userDao.getUserByName(customerCombo.getSelectedItem().toString());
        orderBean.setCustomer(userBean);
        orderBean.setOrderNo(orderNoTxt.getText());
        //Undecided
        AccountBean accountBean = new AccountBean();
        accountBean.setAccountId(5);
        orderBean.setAccount(accountBean);

        orderBean.setActive("1");
        orderBean.setCreatedBy(userBean.getUserId());
        orderBean.setModifiedBy(userBean.getUserId());
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDate = sdf.format(date);
        orderBean.setCreatedDate(Timestamp.valueOf(stringDate));
        orderBean.setModifiedDate(Timestamp.valueOf(stringDate));
        orderBean.setGrandTotal(Double.parseDouble(grandTotalTxt.getText()));
        orderBean.setAmountPaid(Double.parseDouble(amountReceivedTxt.getText()));
        if (Double.parseDouble(amountReceivedTxt.getText()) > Double.parseDouble(countTotalTxt.getText())) {
            orderBean.setStatus("paid");
        } else {
            orderBean.setStatus("unpaid");
        }
        //Adding data to order Table
        Integer row = orderDao.addOrder(orderBean);
        if (row > 0) {
            //Adding Data to order details Table
            OrderBean order = orderDao.getOrderByOrderNo(orderBean.getOrderNo());
            for (int i = 0; i < orderTable.getRowCount(); i++) {
                OrderDetailsBean orderDetailsBean = new OrderDetailsBean();
                orderDetailsBean.setOrder(order);
                String productName = (String) orderTable.getValueAt(i, 0);
                ProductBean productBean = productDao.getProductByProductName(productName);
                orderDetailsBean.setProductBean(productBean);
                Integer quantity = (Integer) orderTable.getValueAt(i, 1);
                Integer remainingQuantity = productBean.getQuantity() - quantity;
                productBean.setQuantity(remainingQuantity);
                productDao.updateProduct(productBean);
                orderDetailsBean.setQuantity(quantity);
                Double price = (Double) orderTable.getValueAt(i, 2);
                orderDetailsBean.setPrice(price);
                orderDetailsBean.setActive("1");
                orderDetailsBean.setCreatedBy(userBean.getUserId());
                orderDetailsBean.setModifiedBy(userBean.getUserId());
                orderDetailsBean.setCreatedDate(Timestamp.valueOf(stringDate));
                orderDetailsBean.setModifiedDate(Timestamp.valueOf(stringDate));
                orderDetailsDao.addOrderDetails(orderDetailsBean);
            }
        }

    }

    /**
     * @param rs
     * @return
     * @throws java.sql.SQLException
     */
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount() - 5;
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    private void populateProductTable() throws SQLException {
        ProductDao productDao = new ProductDaoImpl();
        productTable.setModel(buildTableModel(productDao.getAllProducts()));
        productTable.removeColumn(productTable.getColumnModel().getColumn(0));

    }

    private void populateCustomerCombo() {
        UserDao userDao = new UserDaoImpl();
        List<UserBean> list = userDao.getCustomers();
        customerCombo.addItem("Walk Away Customer");
        for (UserBean user : list) {
            if (!user.getUsername().equals("Walk Away Customer")) {
                customerCombo.addItem(user.getUsername());
            }
        }
    }

    private Boolean validateFields() {
        Boolean isValid = true;
        if (productNameTxt.getText().trim().isEmpty()) {
            checkProductLbl.setText("*Select Product");
            isValid = false;
        }
        if (quantityTxt.getText().trim().isEmpty()) {
            checkQuantityLbl.setText("*Enter Quantity");
            isValid = false;
        } else if (Integer.valueOf(quantityTxt.getText()) < 1) {
            checkQuantityLbl.setText("*Invalid Amount");
            isValid = false;
        }

        return isValid;
    }

    //Update Orders
    public void updateOrderDetails() {
        for (int i = 0; i < orderTable.getRowCount(); i++) {
            ProductDao productDao = new ProductDaoImpl();
            Integer id = (Integer) orderTable.getModel().getValueAt(i, 0);
            OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();
            OrderDetailsBean orderDetailsBean = orderDetailsDao.getOrderDetail(id);
            if (orderDetailsBean != null && id != 0) {
                orderDetailsBean.setOrder(orderBean);
                String productName = (String) orderTable.getValueAt(i, 0);
                ProductBean productBean = productDao.getProductByProductName(productName);
                orderDetailsBean.setProductBean(productBean);
                Integer quantity = (Integer) orderTable.getValueAt(i, 1);

                if (orderDetailsBean.getQuantity() < quantity) {
                    orderDetailsBean.setQuantity(quantity - orderDetailsBean.getQuantity());
                    Integer remaining = quantity - orderDetailsBean.getQuantity();
                    System.out.println(quantity - orderDetailsBean.getQuantity());
                    Integer remaining1 = quantity - remaining;
                    System.out.println(remaining1);
                    Integer remainingQuantity = productBean.getQuantity() - remaining1;
                    System.out.println(remainingQuantity);
                    productBean.setQuantity(remainingQuantity);
                }
                productDao.updateProduct(productBean);
//                Integer remainingQuantity = productBean.getQuantity() - quantity;
//                productBean.setQuantity(remainingQuantity);
                orderDetailsBean.setQuantity(quantity);
                BigDecimal bdPrice = (BigDecimal) orderTable.getValueAt(i, 2);
                Double price = bdPrice.doubleValue();
                orderDetailsBean.setPrice(price);
                orderDetailsBean.setActive("1");
                orderDetailsBean.setCreatedBy(userBean.getUserId());
                orderDetailsBean.setModifiedBy(userBean.getUserId());
                java.util.Date date = new java.util.Date(System.currentTimeMillis());
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String stringDate = sdf.format(date);
                orderDetailsBean.setCreatedDate(Timestamp.valueOf(stringDate));
                orderDetailsBean.setModifiedDate(Timestamp.valueOf(stringDate));
                //Update OrderDetailsDao is to be implemented
                orderDetailsDao.updateOrderDetails(orderDetailsBean);
            } else {

                orderDetailsBean.setOrder(orderBean);
                String productName = (String) orderTable.getValueAt(i, 0);
                ProductBean productBean = productDao.getProductByProductName(productName);
                orderDetailsBean.setProductBean(productBean);
                Integer quantity = (Integer) orderTable.getValueAt(i, 1);
                Integer remainingQuantity = productBean.getQuantity() - quantity;
                productBean.setQuantity(remainingQuantity);
                productDao.updateProduct(productBean);
                orderDetailsBean.setQuantity(quantity);
                Double price = (Double) orderTable.getValueAt(i, 2);
                orderDetailsBean.setPrice(price);
                orderDetailsBean.setActive("1");
                orderDetailsBean.setCreatedBy(userBean.getUserId());
                orderDetailsBean.setModifiedBy(userBean.getUserId());
                java.util.Date date = new java.util.Date(System.currentTimeMillis());
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String stringDate = sdf.format(date);
                orderDetailsBean.setCreatedDate(Timestamp.valueOf(stringDate));
                orderDetailsBean.setModifiedDate(Timestamp.valueOf(stringDate));
                orderDetailsDao.addOrderDetails(orderDetailsBean);
            }
        }
    }

    public void updateOrder() {
        UserBean customer = new UserBean();
        UserDao userDao = new UserDaoImpl();
        customer = userDao.getUserByName(customerCombo.getSelectedItem().toString());
        orderBean.setCustomer(customer);
        Double amountReceived = orderBean.getAmountPaid() + Double.parseDouble(amountReceivedTxt.getText());
        if (!amountReceivedTxt.getText().isEmpty() && amountReceived > Double.parseDouble(countTotalTxt.getText())) {
            orderBean.setAmountPaid(amountReceived);
            orderBean.setStatus("paid");
        } else if (!amountReceivedTxt.getText().isEmpty()) {
            orderBean.setAmountPaid(amountReceived);
            orderBean.setStatus("unpaid");
        } else {
            orderBean.setStatus("unpaid");
        }
        orderBean.setGrandTotal(Double.valueOf(grandTotalTxt.getText()));
        orderBean.setModifiedBy(userBean.getUserId());
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDate = sdf.format(date);
        orderBean.setModifiedDate(Timestamp.valueOf(stringDate));
        calculateGrandTotal();
        OrderDao orderDao = new OrderDaoImpl();
        orderDao.updateOrder(orderBean);

    }

    public void calculateGrandTotal() {
        grandTotal = 0.0;
        for (int i = 0; i < orderTable.getRowCount(); i++) {
            Object amount = orderTable.getValueAt(i, 3);
            System.out.println(amount.getClass());
            if (amount.getClass().toString().equals("class java.math.BigDecimal")) {
                BigDecimal bdPrice = (BigDecimal) amount;
                grandTotal += bdPrice.doubleValue();
            } else {
                grandTotal += (Double) amount;
            }
            System.out.println(grandTotal);
            //grandAmount += amount.doubleValue();
            //System.out.println(grandAmount);

        }
        gst = Double.parseDouble(String.format("%.3f", grandTotal * 0.18));
        countTotal = Double.parseDouble(String.format("%.3f", grandTotal + grandTotal * 0.18));
        gstTxt.setText(gst.toString());
        grandTotalTxt.setText(grandTotal.toString());
        countTotalTxt.setText(countTotal.toString());

    }

    private void clearFields() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        dateTxt.setDate(date);
        customerCombo.setSelectedIndex(0);
        productNameTxt.setText(null);
        quantityTxt.setText(null);
        checkProductLbl.setText("");
        checkQuantityLbl.setText("");
        grandTotalTxt.setText("");
        gstTxt.setText("");
        countTotalTxt.setText("");
        amountReceivedTxt.setText("");
        changeTxt.setText("");
        OrderDao orderDao = new OrderDaoImpl();
        String barcode = BarcodeGenerator.generateBarcode();
        Boolean isMatch = true;
        while (!isMatch) {
            isMatch = orderDao.checkBarcode(barcode);
            if (isMatch) {
                barcode = BarcodeGenerator.generateBarcode();
            }
        }
        orderNoTxt.setText(barcode);
        DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
        tableModel.setRowCount(0);
        productTable.clearSelection();
    }

    public void populateOrderDetailsTable(Integer orderId) throws SQLException {
        OrderDetailsDao orderDetailsDao = new OrderDetailsDaoImpl();
        ResultSet rst = orderDetailsDao.getOrderDetailsByOrderId(orderId);
        orderTable.setModel(buildOrderDetailsTableModel(rst));
        orderTable.removeColumn(orderTable.getColumnModel().getColumn(0));
    }

    public static DefaultTableModel buildOrderDetailsTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    public void calculateTotals() {
        for (int i = 0; i < orderTable.getRowCount(); i++) {
            BigDecimal bdPrice = (BigDecimal) orderTable.getValueAt(i, 3);
            grandTotal += bdPrice.doubleValue();

        }
        grandTotalTxt.setText(Double.toString(grandTotal));
        gst = Double.parseDouble(String.format("%.3f", grandTotal * 0.18));
        gstTxt.setText(gst.toString());
        countTotal = Double.parseDouble(String.format("%.3f", grandTotal + grandTotal * 0.18));
        countTotalTxt.setText(countTotal.toString());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCustomerBtn;
    private javax.swing.JButton addQuantityBtn;
    private javax.swing.JLabel amountPaidLbl;
    private javax.swing.JTextField amountReceivedTxt;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField changeTxt;
    private javax.swing.JLabel checkAmountReceivedLbl;
    private javax.swing.JLabel checkProductLbl;
    private javax.swing.JLabel checkQuantityLbl;
    private javax.swing.JTextField countTotalTxt;
    private javax.swing.JComboBox<String> customerCombo;
    private com.toedter.calendar.JDateChooser dateTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton generateBtn;
    private javax.swing.JTextField grandTotalTxt;
    private javax.swing.JTextField gstTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField orderNoTxt;
    private javax.swing.JTable orderTable;
    private javax.swing.JTextField productNameTxt;
    private javax.swing.JTable productTable;
    private javax.swing.JTextField quantityTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}

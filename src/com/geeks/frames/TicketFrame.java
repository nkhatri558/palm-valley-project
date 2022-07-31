/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.frames;

import com.geeks.beans.PackageBean;
import com.geeks.beans.TicketBean;
import com.geeks.beans.TicketDetailsBean;
import com.geeks.beans.UserBean;
import com.geeks.dao.PackageDao;
import com.geeks.dao.TicketDao;
import com.geeks.dao.TicketDetailsDao;
import com.geeks.daoimpl.PackageDaoImpl;
import com.geeks.daoimpl.TicketDaoImpl;
import com.geeks.daoimpl.TicketDetailsDaoImpl;
import com.geeks.util.BarcodeGenerator;
import com.geeks.util.UtilityClass;
import com.geeks.util.Validation;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;

/**
 *
 * @author khatr
 */
public class TicketFrame extends javax.swing.JFrame {

    /**
     * Creates new form TicketFrame
     */
    private DefaultTableModel model;
    private Double grandTotal = 0.0;
    private UserBean userBean;
    private TicketBean ticketBean;
    private JPopupMenu popupMenu = null;
    private JMenuItem removeItem = null;
    private JMenuItem editItem = null;

    public TicketFrame(UserBean userBean) {
        this.userBean = userBean;
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        String ticketNo = BarcodeGenerator.generateBarcode();
        TicketDao ticketDao = new TicketDaoImpl();
        Boolean isMatch = true;
        while (!isMatch) {
            isMatch = ticketDao.checkBarcode(ticketNo);
            if (isMatch) {
                ticketNo = BarcodeGenerator.generateBarcode();
            }
        }
        ticketNoTxt.setText(ticketNo);
        ticketNoTxt.setEnabled(false);
        populatePackageTable();
        String tableHeader[] = {"package_name", "quantity", "price", "total"};
        model = new DefaultTableModel(tableHeader, 0);
        model.setRowCount(0);
        ticketDetailsTable.setModel(model);
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);
        grandTotalTxt.setEnabled(false);
        grandTotalTxt.setText(grandTotal.toString());
        popupMenu = new JPopupMenu();
        popUpMenu(this);
        changeTxt.setEnabled(false);
    }

    public TicketFrame(UserBean userBean, TicketBean ticketBean) {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.userBean = userBean;
        this.ticketBean = ticketBean;
        contactTxt.setText(ticketBean.getMobileNo());
        ticketNoTxt.setText(ticketBean.getBarcode());
        populatePackageTable();
        populateTicketDetailsTable();
        saveBtn.setVisible(false);
        updateBtn.setVisible(true);
        deleteBtn.setVisible(true);
        grandTotalTxt.setEnabled(false);
        calculateTotals();
        popupMenu = new JPopupMenu();
        popUpMenu(this);
        changeTxt.setEnabled(false);
        amountPaidLbl.setText("Amount Paid :" + grandTotalTxt.getText());
    }
    
    public void popUpMenu(JFrame jframe) {
        removeItem = new JMenuItem("Remove Package");
        removeItem.getAccessibleContext().setAccessibleDescription("Remove Package");
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(ticketBean != null) {
                    Integer id = (Integer) ticketDetailsTable.getModel().getValueAt(ticketDetailsTable.getSelectedRow(), 0);
                    if(id != 0) {
                        TicketDetailsDao ticketDetailsDao = new TicketDetailsDaoImpl();
                        TicketDetailsBean ticketDetailsBean = ticketDetailsDao.getTicketDetailsById(id);
                        ticketDetailsBean.setModifiedBy(userBean.getUserId());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                        ticketDetailsBean.setModifiedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
                        ticketDetailsDao.delete(ticketDetailsBean);
                    }
                    
                }
                model = (DefaultTableModel) ticketDetailsTable.getModel();
                
                model.removeRow(ticketDetailsTable.getSelectedRow());
                calculateTotals();
            }
        });
        popupMenu.add(removeItem);
        editItem = new JMenuItem("Edit");
        editItem.getAccessibleContext().setAccessibleDescription("Edit");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                BigDecimal bdPrice = (BigDecimal) ticketDetailsTable.getValueAt(ticketDetailsTable.getSelectedRow(), 2);
//                Double price = bdPrice.doubleValue();
                Double price = (Double) ticketDetailsTable.getValueAt(ticketDetailsTable.getSelectedRow(), 2);
                Integer quantity = 0;
                Object object = ticketDetailsTable.getValueAt(ticketDetailsTable.getSelectedRow(), 1);
                if(object.getClass().toString().equals("class java.lang.Integer")) {
                    quantity = (Integer) quantity;
                }
                else {
                    quantity = Integer.parseInt((String) object);
                }
                
                Double total = price * quantity;
                ticketDetailsTable.setValueAt(total, ticketDetailsTable.getSelectedRow(), 3);
                calculateTotals();
            }
            
        });
        
        popupMenu.add(editItem);
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
        packageTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        ticketDetailsTable = new javax.swing.JTable();
        backBtn = new javax.swing.JButton();
        amountPaidLbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        packageNameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        quantityTxt = new javax.swing.JTextField();
        addQuantityBtn = new javax.swing.JButton();
        checkContactLbl = new javax.swing.JLabel();
        checkPackageNameLbl = new javax.swing.JLabel();
        checkQuantityLbl = new javax.swing.JLabel();
        dateTxt = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ticketNoTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        contactTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        printBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        changeTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        grandTotalTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        amountReceivedTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        packageTable.setModel(new javax.swing.table.DefaultTableModel(
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
        packageTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                packageTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(packageTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 900, 200));

        ticketDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ticketDetailsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ticketDetailsTableMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(ticketDetailsTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, 900, 210));

        backBtn.setBackground(new java.awt.Color(64, 38, 28));
        backBtn.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        backBtn.setForeground(new java.awt.Color(255, 255, 255));
        backBtn.setText("Back");
        backBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 100, 30));
        getContentPane().add(amountPaidLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 49, 150, 30));

        jLabel9.setBackground(new java.awt.Color(64, 38, 28));
        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Ticket Booking");
        jLabel9.setOpaque(true);
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-8, -6, 1370, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        packageNameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                packageNameTxtFocusGained(evt);
            }
        });
        packageNameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                packageNameTxtKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                packageNameTxtKeyReleased(evt);
            }
        });

        jLabel5.setText("Quantity");

        quantityTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                quantityTxtFocusGained(evt);
            }
        });
        quantityTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantityTxtActionPerformed(evt);
            }
        });
        quantityTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantityTxtKeyReleased(evt);
            }
        });

        addQuantityBtn.setBackground(new java.awt.Color(64, 38, 28));
        addQuantityBtn.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        addQuantityBtn.setForeground(new java.awt.Color(255, 255, 255));
        addQuantityBtn.setText("Add");
        addQuantityBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addQuantityBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addQuantityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addQuantityBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Date");

        jLabel2.setText("Ticket No");

        jLabel3.setText("Contact");

        contactTxt.setToolTipText("03xx-xxxxxxx");
        contactTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contactTxtFocusGained(evt);
            }
        });

        jLabel4.setText("Package Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ticketNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkContactLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(116, 116, 116))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkPackageNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(packageNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(checkQuantityLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(quantityTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addComponent(addQuantityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ticketNoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(contactTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(checkContactLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(packageNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkPackageNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(quantityTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addQuantityBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkQuantityLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 460, 420));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Print.png"))); // NOI18N
        printBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
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

        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Save.png"))); // NOI18N
        saveBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(saveBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateBtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(deleteBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveBtn)
                    .addComponent(updateBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(deleteBtn)
                    .addComponent(printBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 507, 460, 260));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Grand Total");

        jLabel7.setText("Amount Received");

        amountReceivedTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountReceivedTxtActionPerformed(evt);
            }
        });
        amountReceivedTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                amountReceivedTxtKeyPressed(evt);
            }
        });

        jLabel8.setText("Change");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(amountReceivedTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(changeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(grandTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(270, 270, 270))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(grandTotalTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amountReceivedTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 571, 900, 200));

        jLabel10.setBackground(new java.awt.Color(64, 38, 28));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("TIckets");
        jLabel10.setOpaque(true);
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 323, 900, 40));

        jLabel11.setBackground(new java.awt.Color(64, 38, 28));
        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Packages");
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 90, 900, 30));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 44, 1360, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void packageNameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_packageNameTxtKeyPressed
        UtilityClass.searchFromTable(packageTable, packageNameTxt.getText());
    }//GEN-LAST:event_packageNameTxtKeyPressed

    private void packageNameTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_packageNameTxtKeyReleased
        try {
            String productName = packageNameTxt.getText();
        } catch (NumberFormatException ex) {
            checkPackageNameLbl.setText("*Invalid Input");
        }
        if (packageNameTxt.getText().trim().isEmpty()) {
            checkPackageNameLbl.setText("");
        }
        if (packageTable.getRowCount() != 0) {
            if (packageTable.getRowCount() == 1) {
                packageTable.setRowSelectionInterval(0, 0);
            }
        }


    }//GEN-LAST:event_packageNameTxtKeyReleased

    private void packageTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_packageTableMouseClicked
        String packageName = (String) packageTable.getValueAt(packageTable.getSelectedRow(), 0);
        packageNameTxt.setText(packageName);
    }//GEN-LAST:event_packageTableMouseClicked

    private void addQuantityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addQuantityBtnActionPerformed
        if (validateFields()) {
            model = (DefaultTableModel) ticketDetailsTable.getModel();
            try {
                //if same package exists then add count to it
                String packageName = (String) packageTable.getValueAt(packageTable.getSelectedRow(), 0);
                Integer quantity = Integer.parseInt(quantityTxt.getText());
                Object object = packageTable.getValueAt(packageTable.getSelectedRow(), 1);
                Double price = 0.0;
                if (object.getClass().toString().equals("class java.math.BigDecimal")) {
                    BigDecimal bdPrice = (BigDecimal) object;
                    price = bdPrice.doubleValue();
                } else {
                    price = (Double) object;
                }
                Double total = price * quantity;
                Boolean isMatch = false;
                for (int i = 0; i < ticketDetailsTable.getRowCount(); i++) {
                    String packageName1 = (String) ticketDetailsTable.getValueAt(i, 0);
                    if (packageName1.equals(packageName)) {
                        quantity += (Integer) ticketDetailsTable.getValueAt(i, 1);
                        ticketDetailsTable.setValueAt(quantity, i, 1);
                        total = price * quantity;
                        ticketDetailsTable.setValueAt(total, i, 3);
                        isMatch = true;
                        break;
                    }
                }
                //grandTotal += total;
                if (!isMatch) {
                    Vector<Object> data = new Vector<>();
                    data.add(packageName);
                    data.add(quantity);
                    data.add(price);
                    data.add(total);
                    model.addRow(data);
                    ticketDetailsTable.setModel(model);
                }

                packageNameTxt.setText("");
                quantityTxt.setText("");
                calculateTotals();
                populatePackageTable();
            } catch (ArrayIndexOutOfBoundsException ex) {
                checkPackageNameLbl.setText("*Package Does not Exist");
            }

        }

    }//GEN-LAST:event_addQuantityBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if (addTicketData()) {
            addTicketDetails();
            clearFields();
        }
    }//GEN-LAST:event_saveBtnActionPerformed

    private void quantityTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantityTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantityTxtActionPerformed

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

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        if(ticketBean != null) {
            new ViewTicketBookings(userBean).setVisible(true);
            dispose();
        }
        else {
            new ParkDashboard(userBean).setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_backBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        deleteBooking();
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        updateTicket();
        updateTicketDetails();
        new ViewTicketBookings(userBean).setVisible(true);
        dispose();
    }//GEN-LAST:event_updateBtnActionPerformed

    private void ticketDetailsTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ticketDetailsTableMouseReleased
        if (evt.isPopupTrigger()) {
            int x = MouseInfo.getPointerInfo().getLocation().x;
            int y = MouseInfo.getPointerInfo().getLocation().y;
            popupMenu.show(this, x, y);
        }
    }//GEN-LAST:event_ticketDetailsTableMouseReleased

    private void amountReceivedTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountReceivedTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountReceivedTxtActionPerformed

    private void amountReceivedTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountReceivedTxtKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Double total = Double.parseDouble(grandTotalTxt.getText());
            Double amountReceived = Double.parseDouble(amountReceivedTxt.getText());
            Double change = amountReceived - total;
            changeTxt.setText(change.toString());
        }
    }//GEN-LAST:event_amountReceivedTxtKeyPressed

    private void contactTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactTxtFocusGained
        checkContactLbl.setText("");
    }//GEN-LAST:event_contactTxtFocusGained

    private void packageNameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_packageNameTxtFocusGained
        checkPackageNameLbl.setText("");
    }//GEN-LAST:event_packageNameTxtFocusGained

    private void quantityTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantityTxtFocusGained
        checkQuantityLbl.setText("");
    }//GEN-LAST:event_quantityTxtFocusGained

    private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
         // TODO add your handling code here:
        if(ticketBean == null) {
            if (addTicketData()) {
                addTicketDetails();
            }
        }
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
           String path = "C:\\Users\\khatr\\OneDrive\\Desktop\\waterparkTicket.rptdesign";
        IReportRunnable design = null ;
        try {
            design = engine.openReportDesign(path);
        } catch (EngineException ex) {
            Logger.getLogger(OrderFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
           IRunAndRenderTask task = engine.createRunAndRenderTask(design);
        String  saleNumber=ticketNoTxt.getText();
        
          task.setParameterValue("Top Count", (new Integer(5)));
          //task.setParameterValue("orderParameter", orderNo);
           task.setParameterValue("Parameter", saleNumber);
           task.validateParameters();

           HTMLRenderOption options = new HTMLRenderOption();
           options.setOutputFileName("C:\\Users\\khatr\\OneDrive\\Desktop\\temp\\parameter2.html");
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
                   File myFile = new File("C:\\Users\\khatr\\OneDrive\\Desktop\\temp\\parameter2.html");
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
        if(ticketBean == null) {
            clearFields();
        }
    }//GEN-LAST:event_printBtnActionPerformed

    private Boolean validateFields() {
        Boolean isValid = true;
        if (contactTxt.getText().trim().isEmpty()) {
            checkContactLbl.setText("*Required");
            isValid = false;
        } else if (!Validation.validateContact(contactTxt.getText())) {
            checkContactLbl.setText("*Invalid Format");
            isValid = false;
        }

        if (packageNameTxt.getText().trim().isEmpty()) {
            checkPackageNameLbl.setText("*Required");
            isValid = false;
        }
        if (quantityTxt.getText().trim().isEmpty()) {
            checkQuantityLbl.setText("*Required");
            isValid = false;
        }
        return isValid;
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        System.out.println(columnCount);
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    private void populatePackageTable() {
        PackageDao packageDao = new PackageDaoImpl();
        try {
            packageTable.setModel(buildTableModel(packageDao.getAllPackages()));
            packageTable.removeColumn(packageTable.getColumnModel().getColumn(0));
            packageTable.removeColumn(packageTable.getColumnModel().getColumn(3));
            packageTable.removeColumn(packageTable.getColumnModel().getColumn(3));
            packageTable.removeColumn(packageTable.getColumnModel().getColumn(3));
            packageTable.removeColumn(packageTable.getColumnModel().getColumn(3));

        } catch (SQLException ex) {
            Logger.getLogger(TicketFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Boolean addTicketData() {
        TicketBean ticketBean = new TicketBean();
        ticketBean.setBarcode(ticketNoTxt.getText());
        ticketBean.setMobileNo(contactTxt.getText());
        ticketBean.setActive("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDate = sdf.format(dateTxt.getDate());
        ticketBean.setDate(Timestamp.valueOf(stringDate));
        ticketBean.setCreatedBy(userBean.getUserId());
        ticketBean.setCreatedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
        ticketBean.setModifiedBy(userBean.getUserId());
        ticketBean.setModifiedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
        TicketDao ticketDao = new TicketDaoImpl();
        Integer row = ticketDao.add(ticketBean);
        return row > 0;
    }

    private void addTicketDetails() {
        TicketDetailsBean ticketDetailsBean = new TicketDetailsBean();
        TicketDetailsDao ticketDetailsDao = new TicketDetailsDaoImpl();
        TicketDao ticketDao = new TicketDaoImpl();
        TicketBean ticketBean = ticketDao.getTicketByTicketNo(ticketNoTxt.getText());
        ticketDetailsBean.setTicketBean(ticketBean);
        PackageDao packageDao = new PackageDaoImpl();
        for (int i = 0; i < ticketDetailsTable.getRowCount(); i++) {
            String packageName = (String) ticketDetailsTable.getValueAt(i, 0);;
            PackageBean packageBean = packageDao.getPackageByName(packageName);
            ticketDetailsBean.setPackageBean(packageBean);
            Integer quantity = (Integer) ticketDetailsTable.getValueAt(i, 1);
            ticketDetailsBean.setQuantity(quantity);
            ticketDetailsBean.setActive("1");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            ticketDetailsBean.setCreatedBy(userBean.getUserId());
            ticketDetailsBean.setCreatedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
            ticketDetailsBean.setModifiedBy(userBean.getUserId());
            ticketDetailsBean.setModifiedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
            ticketDetailsDao.add(ticketDetailsBean);
        }

    }

    public void clearFields() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        dateTxt.setDate(date);
        TicketDao ticketDao = new TicketDaoImpl();
        String ticketNo = BarcodeGenerator.generateBarcode();
        Boolean isMatch = true;
        while (!isMatch) {
            isMatch = ticketDao.checkBarcode(ticketNo);
            if (isMatch) {
                ticketNo = BarcodeGenerator.generateBarcode();
            }
        }
        ticketNoTxt.setText(ticketNo);
        contactTxt.setText("");
        quantityTxt.setText("");
        DefaultTableModel tableModel = (DefaultTableModel) ticketDetailsTable.getModel();
        tableModel.setRowCount(0);
        packageTable.clearSelection();
        amountReceivedTxt.setText("");
        changeTxt.setText("");
    }

    public void populateTicketDetailsTable() {
        TicketDetailsDao ticketDetailsDao = new TicketDetailsDaoImpl();
        try {
            ticketDetailsTable.setModel(buildTableModel(ticketDetailsDao.getTicketDetailsByTicketId(ticketBean.getTicketId())));
            ticketDetailsTable.removeColumn(ticketDetailsTable.getColumnModel().getColumn(0));
        } catch (SQLException ex) {
            Logger.getLogger(TicketFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void calculateTotals() {
        Double grandTotal = 0.0;
        for (int i = 0; i < ticketDetailsTable.getRowCount(); i++) {
            Object object = ticketDetailsTable.getValueAt(i, 3);
            if (object.getClass().toString().equals("class java.math.BigDecimal")) {
                BigDecimal bdPrice = (BigDecimal) object;
                grandTotal += bdPrice.doubleValue();
            } else {
                grandTotal += (Double) object;
            }
        }
        grandTotalTxt.setText(grandTotal.toString());
    }

    public void deleteBooking() {
        TicketDao ticketDao = new TicketDaoImpl();
        ticketBean.setModifiedBy(userBean.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ticketBean.setModifiedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
        ticketDao.delete(ticketBean);
        new ViewTicketBookings(userBean).setVisible(true);
        dispose();
    }

    public void updateTicketDetails() {
        PackageDao packageDao = new PackageDaoImpl();
        for (int i = 0; i < ticketDetailsTable.getRowCount(); i++) {
            Integer id = (Integer) ticketDetailsTable.getModel().getValueAt(i, 0);
            TicketDetailsDao ticketDetailsDao = new TicketDetailsDaoImpl();
            TicketDetailsBean ticketDetailsBean = ticketDetailsDao.getTicketDetailsById(id);
            if (ticketDetailsBean != null && id != 0) {
                ticketDetailsBean.setTicketBean(ticketBean);
                Object object = ticketDetailsTable.getValueAt(i, 1);
                System.out.println(object.getClass());
                Integer quantity = 0;
                if(object.getClass().toString().equals("class java.lang.Integer")) {
                    quantity = (Integer) object;
                }
                else {
                    quantity = Integer.parseInt((String) object);
                }
                
                Integer remainingQuantity = quantity - ticketDetailsBean.getQuantity();
                if (remainingQuantity != 0) {
                    ticketDetailsBean.setQuantity(quantity);
                }
                ticketDetailsDao.update(ticketDetailsBean);

            } else {
                String packageName = (String) ticketDetailsTable.getValueAt(i, 0);;
                PackageBean packageBean = packageDao.getPackageByName(packageName);
                ticketDetailsBean.setPackageBean(packageBean);
                Integer quantity = (Integer) ticketDetailsTable.getValueAt(i, 1);
                ticketDetailsBean.setQuantity(quantity);
                ticketDetailsBean.setActive("1");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                ticketDetailsBean.setCreatedBy(userBean.getUserId());
                ticketDetailsBean.setCreatedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
                ticketDetailsBean.setModifiedBy(userBean.getUserId());
                ticketDetailsBean.setModifiedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
                ticketDetailsDao.add(ticketDetailsBean);
            }
        }
    }
    public void updateTicket() {
        ticketBean.setBarcode(ticketNoTxt.getText());
        ticketBean.setMobileNo(contactTxt.getText());
        ticketBean.setActive("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDate = sdf.format(dateTxt.getDate());
        ticketBean.setDate(Timestamp.valueOf(stringDate));
        ticketBean.setCreatedBy(userBean.getUserId());
        ticketBean.setCreatedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
        ticketBean.setModifiedBy(userBean.getUserId());
        ticketBean.setModifiedDate(Timestamp.valueOf(sdf.format(new java.util.Date(System.currentTimeMillis()))));
        TicketDao ticketDao = new TicketDaoImpl();
        ticketDao.update(ticketBean);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addQuantityBtn;
    private javax.swing.JLabel amountPaidLbl;
    private javax.swing.JTextField amountReceivedTxt;
    private javax.swing.JButton backBtn;
    private javax.swing.JTextField changeTxt;
    private javax.swing.JLabel checkContactLbl;
    private javax.swing.JLabel checkPackageNameLbl;
    private javax.swing.JLabel checkQuantityLbl;
    private javax.swing.JTextField contactTxt;
    private com.toedter.calendar.JDateChooser dateTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JTextField grandTotalTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField packageNameTxt;
    private javax.swing.JTable packageTable;
    private javax.swing.JButton printBtn;
    private javax.swing.JTextField quantityTxt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JTable ticketDetailsTable;
    private javax.swing.JTextField ticketNoTxt;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}

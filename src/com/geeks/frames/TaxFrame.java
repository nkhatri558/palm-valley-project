/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.frames;

import com.geeks.beans.TaxBean;
import com.geeks.beans.UserBean;
import com.geeks.dao.TaxDao;
import com.geeks.daoimpl.TaxDaoImpl;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khatr
 */
public class TaxFrame extends javax.swing.JFrame {
    private UserBean userBean;
    private Integer taxId;
    /**
     * Creates new form TaxFrame
     */
    public TaxFrame(UserBean userBean) {
        initComponents();
        this.userBean = userBean;
        populateTaxTable();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taxTable = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        checkPercentageLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        percentageTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dateFromTxt = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        dateToTxt = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backBtn.setBackground(new java.awt.Color(64, 38, 28));
        backBtn.setForeground(new java.awt.Color(255, 255, 255));
        backBtn.setText("Back");
        backBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 31));

        clearBtn.setBackground(new java.awt.Color(64, 38, 28));
        clearBtn.setForeground(new java.awt.Color(255, 255, 255));
        clearBtn.setText("Clear");
        clearBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });
        getContentPane().add(clearBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 60, 100, 31));

        taxTable.setModel(new javax.swing.table.DefaultTableModel(
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
        taxTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                taxTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(taxTable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 110, 900, 730));

        jLabel4.setBackground(new java.awt.Color(64, 38, 28));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tax");
        jLabel4.setOpaque(true);
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Percentage");

        percentageTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                percentageTxtFocusGained(evt);
            }
        });
        percentageTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                percentageTxtKeyReleased(evt);
            }
        });

        jLabel2.setText("Date From");

        jLabel3.setText("Date To");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(percentageTxt)
                    .addComponent(checkPercentageLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateFromTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateToTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(percentageTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(checkPercentageLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateFromTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dateToTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(204, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 109, 460, 480));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/ADD.png"))); // NOI18N
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Update.png"))); // NOI18N
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Delete.png"))); // NOI18N
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateBtn))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(deleteBtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn)
                    .addComponent(updateBtn))
                .addGap(18, 18, 18)
                .addComponent(deleteBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, -1, -1));

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 44, 1350, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void percentageTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_percentageTxtKeyReleased
        try {
            Integer percentage = Integer.parseInt(percentageTxt.getText());
        } catch (NumberFormatException ex) {
            checkPercentageLbl.setText("*Invalid Input");
        }
        if (percentageTxt.getText().trim().isEmpty()) {
            checkPercentageLbl.setText("");
        }
    }//GEN-LAST:event_percentageTxtKeyReleased

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        addData();
        clearFields();
        populateTaxTable();
        
    }//GEN-LAST:event_addBtnActionPerformed

    private void percentageTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_percentageTxtFocusGained
        checkPercentageLbl.setText("");        
    }//GEN-LAST:event_percentageTxtFocusGained

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        clearFields();
    }//GEN-LAST:event_clearBtnActionPerformed

    private void taxTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_taxTableMouseClicked
        taxId = (Integer) taxTable.getModel().getValueAt(taxTable.getSelectedRow(), 0);
        TaxDao taxDao = new TaxDaoImpl();
        TaxBean taxBean = taxDao.getTaxById(taxId);
        percentageTxt.setText(taxBean.getPercentage().toString());
        Timestamp date = taxBean.getDateFrom();
        dateFromTxt.setDate(new java.util.Date(date.getTime()));
        date = taxBean.getDateTo();
        dateToTxt.setDate(new java.util.Date(date.getTime()));
        updateBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        addBtn.setEnabled(false);
    }//GEN-LAST:event_taxTableMouseClicked

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        TaxDao taxDao = new TaxDaoImpl();
        TaxBean taxBean = taxDao.getTaxById(taxId);
        if(validateFields()) {
            Integer percentage = Integer.parseInt(percentageTxt.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String dateFromString = sdf.format(dateFromTxt.getDate());
            String dateToString = sdf.format(dateToTxt.getDate());
            taxBean.setPercentage(percentage);
            taxBean.setDateFrom(Timestamp.valueOf(dateFromString));
            taxBean.setDateTo(Timestamp.valueOf(dateToString));
            taxBean.setActive("1");
            taxBean.setCreatedBy(userBean.getUserId());
            java.util.Date date = new java.util.Date(System.currentTimeMillis());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String stringDate = df.format(date);
            taxBean.setCreatedDate(Timestamp.valueOf(stringDate));
            taxBean.setModifiedBy(userBean.getUserId());
            taxBean.setModifiedDate(Timestamp.valueOf(stringDate));
            taxDao.update(taxBean);
            populateTaxTable();
            clearFields();
        }
        
    }//GEN-LAST:event_updateBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        TaxDao taxDao = new TaxDaoImpl();
        TaxBean taxBean = taxDao.getTaxById(taxId);
        taxBean.setModifiedBy(userBean.getUserId());
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String stringDate = df.format(date);
        taxBean.setModifiedDate(Timestamp.valueOf(stringDate));
        taxDao.delete(taxBean);
        populateTaxTable();
        clearFields();
        
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        new RestaurantDashboard(userBean).setVisible(true);
        dispose();
    }//GEN-LAST:event_backBtnActionPerformed
    public void addData() {
        if(validateFields()) {
            Integer percentage = Integer.parseInt(percentageTxt.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String dateFromString = sdf.format(dateFromTxt.getDate());
            String dateToString = sdf.format(dateToTxt.getDate());
            TaxBean taxBean = new TaxBean();
            taxBean.setPercentage(percentage);
            taxBean.setDateFrom(Timestamp.valueOf(dateFromString));
            taxBean.setDateTo(Timestamp.valueOf(dateToString));
            taxBean.setActive("1");
            taxBean.setCreatedBy(userBean.getUserId());
            java.util.Date date = new java.util.Date(System.currentTimeMillis());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String stringDate = df.format(date);
            taxBean.setCreatedDate(Timestamp.valueOf(stringDate));
            taxBean.setModifiedBy(userBean.getUserId());
            taxBean.setModifiedDate(Timestamp.valueOf(stringDate));
            TaxDao taxDao = new TaxDaoImpl();
            taxDao.add(taxBean);
        }
    }
    
    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
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
    
    public void populateTaxTable() {
        try {
            TaxDao taxDao = new TaxDaoImpl();
            taxTable.setModel(buildTableModel(taxDao.getAllTaxes()));
            taxTable.removeColumn(taxTable.getColumnModel().getColumn(0));
        } catch (SQLException ex) {
            Logger.getLogger(TaxFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Boolean validateFields() {
        Boolean isValid = true;
        if(percentageTxt.getText().trim().isEmpty()) {
            checkPercentageLbl.setText("*Empty Field");
            isValid = false;
        }
        return isValid;
    }
    public void clearFields() {
        percentageTxt.setText("");
        dateFromTxt.setDate(Date.valueOf(LocalDate.now()));
        dateToTxt.setDate(Date.valueOf(LocalDate.now()));
        checkPercentageLbl.setText("");
        taxTable.getSelectionModel().clearSelection();
        addBtn.setEnabled(true);
        updateBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        taxId = null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel checkPercentageLbl;
    private javax.swing.JButton clearBtn;
    private com.toedter.calendar.JDateChooser dateFromTxt;
    private com.toedter.calendar.JDateChooser dateToTxt;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField percentageTxt;
    private javax.swing.JTable taxTable;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}

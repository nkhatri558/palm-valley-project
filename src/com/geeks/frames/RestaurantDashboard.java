/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.frames;

import com.geeks.beans.UserBean;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author khatr
 */
public class RestaurantDashboard extends javax.swing.JFrame {
    private UserBean userBean;
    /**
     * Creates new form Dashboard
     */
    public RestaurantDashboard(UserBean userBean) {
        initComponents();
        this.userBean = userBean;
        userLbl.setText(userBean.getUsername());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userBtn = new javax.swing.JButton();
        AccountFrame = new javax.swing.JButton();
        userLbl = new javax.swing.JLabel();
        categoryBtn = new javax.swing.JButton();
        productBtn = new javax.swing.JButton();
        stockBtn = new javax.swing.JButton();
        orderBtn = new javax.swing.JButton();
        orderViewBtn = new javax.swing.JButton();
        backBtn = new javax.swing.JButton();
        taxBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userBtn.setBorder(null);
        userBtn.setBorderPainted(false);
        userBtn.setContentAreaFilled(false);
        userBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userBtnActionPerformed(evt);
            }
        });
        getContentPane().add(userBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 280, 180));

        AccountFrame.setBorder(null);
        AccountFrame.setBorderPainted(false);
        AccountFrame.setContentAreaFilled(false);
        AccountFrame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        AccountFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccountFrameActionPerformed(evt);
            }
        });
        getContentPane().add(AccountFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 430, 280, 190));

        userLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userLbl.setForeground(new java.awt.Color(64, 38, 28));
        getContentPane().add(userLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 150, 30));

        categoryBtn.setBorder(null);
        categoryBtn.setBorderPainted(false);
        categoryBtn.setContentAreaFilled(false);
        categoryBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        categoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryBtnActionPerformed(evt);
            }
        });
        getContentPane().add(categoryBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 270, 180));

        productBtn.setBorder(null);
        productBtn.setBorderPainted(false);
        productBtn.setContentAreaFilled(false);
        productBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productBtnActionPerformed(evt);
            }
        });
        getContentPane().add(productBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(363, 204, 280, 180));

        stockBtn.setBorder(null);
        stockBtn.setBorderPainted(false);
        stockBtn.setContentAreaFilled(false);
        stockBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockBtnActionPerformed(evt);
            }
        });
        getContentPane().add(stockBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 200, 270, 180));

        orderBtn.setBorder(null);
        orderBtn.setBorderPainted(false);
        orderBtn.setContentAreaFilled(false);
        orderBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        orderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderBtnActionPerformed(evt);
            }
        });
        getContentPane().add(orderBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, 280, 180));

        orderViewBtn.setBorder(null);
        orderViewBtn.setBorderPainted(false);
        orderViewBtn.setContentAreaFilled(false);
        orderViewBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        orderViewBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderViewBtnActionPerformed(evt);
            }
        });
        getContentPane().add(orderViewBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 440, 280, 180));

        backBtn.setBorder(null);
        backBtn.setBorderPainted(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        getContentPane().add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1029, 28, 290, 80));

        taxBtn.setBorder(null);
        taxBtn.setBorderPainted(false);
        taxBtn.setContentAreaFilled(false);
        taxBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        taxBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                taxBtnActionPerformed(evt);
            }
        });
        getContentPane().add(taxBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 430, 280, 190));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Restaurant Dashboard – 1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1360, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userBtnActionPerformed
        try {
            UserFrame userFrame = new UserFrame(userBean);
            userFrame.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_userBtnActionPerformed

    private void AccountFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccountFrameActionPerformed
        try {
            AccountFrame accountFrame = new AccountFrame(userBean);
            accountFrame.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_AccountFrameActionPerformed

    private void categoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryBtnActionPerformed
        try {
            CategoryFrame categoryFrame = new CategoryFrame(userBean);
            categoryFrame.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_categoryBtnActionPerformed

    private void productBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productBtnActionPerformed
        try {
            ProductFrame productFrame = new ProductFrame(userBean);
            productFrame.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_productBtnActionPerformed

    private void stockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockBtnActionPerformed
        try {
            StockFrame stockFrame = new StockFrame(userBean);
            stockFrame.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_stockBtnActionPerformed

    private void orderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderBtnActionPerformed
        try {
            OrderFrame orderFrame = new OrderFrame(userBean);
            orderFrame.setVisible(true);
            dispose();
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_orderBtnActionPerformed

    private void orderViewBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderViewBtnActionPerformed
        ViewOrders viewOrders = new ViewOrders(userBean);
        viewOrders.setVisible(true);
        dispose();
    }//GEN-LAST:event_orderViewBtnActionPerformed

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        new MainDashboard(userBean).setVisible(true);
        dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void taxBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxBtnActionPerformed
        new TaxFrame(userBean).setVisible(true);
        dispose();
    }//GEN-LAST:event_taxBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AccountFrame;
    private javax.swing.JButton backBtn;
    private javax.swing.JButton categoryBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton orderBtn;
    private javax.swing.JButton orderViewBtn;
    private javax.swing.JButton productBtn;
    private javax.swing.JButton stockBtn;
    private javax.swing.JButton taxBtn;
    private javax.swing.JButton userBtn;
    private javax.swing.JLabel userLbl;
    // End of variables declaration//GEN-END:variables
}
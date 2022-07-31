/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.frames;

import com.geeks.beans.UserBean;
import javax.swing.JFrame;

/**
 *
 * @author khatr
 */
public class MainDashboard extends javax.swing.JFrame {
    private UserBean userBean;
    /**
     * Creates new form MainDashboard
     */
    
    public MainDashboard(UserBean userBean) {
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

        userLbl = new javax.swing.JLabel();
        restaurantBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        parkBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1360, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        userLbl.setForeground(new java.awt.Color(64, 38, 28));
        getContentPane().add(userLbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 120, 30));

        restaurantBtn.setBorder(null);
        restaurantBtn.setBorderPainted(false);
        restaurantBtn.setContentAreaFilled(false);
        restaurantBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        restaurantBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restaurantBtnActionPerformed(evt);
            }
        });
        getContentPane().add(restaurantBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 250, 550, 280));

        logoutBtn.setBorder(null);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });
        getContentPane().add(logoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 21, 280, 90));

        parkBtn.setBorder(null);
        parkBtn.setBorderPainted(false);
        parkBtn.setContentAreaFilled(false);
        parkBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        parkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parkBtnActionPerformed(evt);
            }
        });
        getContentPane().add(parkBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 560, 290));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/geeks/icons/Main Dashboard-1.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, 2, 1360, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void restaurantBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restaurantBtnActionPerformed
        new RestaurantDashboard(userBean).setVisible(true);
        dispose();
    }//GEN-LAST:event_restaurantBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        new Login().setVisible(true);
        dispose();
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void parkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parkBtnActionPerformed
        new ParkDashboard(userBean).setVisible(true);
        dispose();
    }//GEN-LAST:event_parkBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton parkBtn;
    private javax.swing.JButton restaurantBtn;
    private javax.swing.JLabel userLbl;
    // End of variables declaration//GEN-END:variables
}

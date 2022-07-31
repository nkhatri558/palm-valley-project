/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.AccountBean;
import com.geeks.beans.OrderBean;
import com.geeks.beans.UserBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.OrderDao;
import com.geeks.dao.UserDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class OrderDaoImpl implements OrderDao{
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Boolean checkBarcode(String Barcode) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from orders where order_no = ?");
            ps.setString(1, Barcode);
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row > 0;
    }

    @Override
    public Integer addOrder(OrderBean orderBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into orders(order_no, customer_id, order_date, account_id, status, grand_total, amount_paid, active, created_by, created_date, modified_by, modified_date) values(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, orderBean.getOrderNo());
            if(orderBean.getCustomer() == null) {
                ps.setInt(2, 0);
            } else {
                ps.setInt(2, orderBean.getCustomer().getUserId());
            }
            
            ps.setDate(3, new Date(orderBean.getOrderDate().getTime()));
            ps.setInt(4, orderBean.getAccount().getAccountId());
            ps.setString(5, orderBean.getStatus());
            ps.setDouble(6, orderBean.getGrandTotal());
            ps.setDouble(7, orderBean.getAmountPaid());
            ps.setString(8, orderBean.getActive());
            ps.setInt(9, orderBean.getCreatedBy());
            ps.setTimestamp(10, orderBean.getCreatedDate());
            ps.setInt(11, orderBean.getModifiedBy());
            ps.setTimestamp(12, orderBean.getModifiedDate());
            row = ps.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
// Work is to resumed from here fetch all data
    @Override
    public OrderBean getOrderByOrderNo(String orderNo) {
        OrderBean orderBean = new OrderBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM orders where order_no = ? and active = '1' ORDER BY created_date DESC LIMIT 1;");
            ps.setString(1, orderNo);
            rs = ps.executeQuery();
            while(rs.next()) {
                orderBean.setOrderId(rs.getInt("order_id"));
                orderBean.setOrderNo(rs.getString("order_no"));
                AccountBean accountBean = new AccountBean();
                accountBean.setAccountId(rs.getInt("account_id"));
                orderBean.setAccount(accountBean);
                UserBean userBean = new UserBean();
                UserDao userDao = new UserDaoImpl();
                userBean = userDao.getUserById(rs.getInt("customer_id"));
                orderBean.setCustomer(userBean);
                orderBean.setOrderDate(rs.getDate("order_date"));
                orderBean.setCreatedBy(rs.getInt("created_by"));
                orderBean.setCreatedDate(rs.getTimestamp("created_date"));
                orderBean.setModifiedBy(rs.getInt("modified_by"));
                orderBean.setModifiedDate(rs.getTimestamp("modified_date"));
                orderBean.setGrandTotal(rs.getDouble("grand_total"));
                orderBean.setAmountPaid(rs.getDouble("amount_paid"));
                orderBean.setActive(rs.getString("active"));
                orderBean.setStatus(rs.getString("status"));
                
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderBean;
    }

    @Override
    public ResultSet getAllOrders() {
        try {
            con = DBConnection.getConnection();
            //ps = con.prepareStatement("SELECT o.`order_id`,o.order_no, u.username, o.order_date, o.status,o.`grand_total`, o.`amount_paid` FROM orders o JOIN users u ON o.`customer_id` = u.`user_id` WHERE o.active = '1';");
            ps = con.prepareStatement("SELECT o.`order_id`,o.order_no, u.username, o.order_date, o.status,o.`grand_total`+o.`grand_total`*0.18 AS count_total, o.`amount_paid` FROM orders o JOIN users u ON o.`customer_id` = u.`user_id` WHERE o.active = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public Integer deleteOrder(Integer orderId) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update orders set active = '0' where order_id = ?");
            ps.setInt(1, orderId);
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public Integer updateOrder(OrderBean orderBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("UPDATE orders SET customer_id = ?, STATUS = ?, grand_total = ?, amount_paid = ?, modified_by = ?, modified_date = ? WHERE order_id = ?;");
            ps.setInt(1, orderBean.getCustomer().getUserId());
            ps.setString(2, orderBean.getStatus());
            ps.setDouble(3, orderBean.getGrandTotal());
            ps.setDouble(4, orderBean.getAmountPaid());
            ps.setInt(5, orderBean.getModifiedBy());
            ps.setTimestamp(6, orderBean.getModifiedDate());
            ps.setInt(7, orderBean.getOrderId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
    
}

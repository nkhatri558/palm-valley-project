/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.OrderBean;
import com.geeks.beans.OrderDetailsBean;
import com.geeks.beans.ProductBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.OrderDao;
import com.geeks.dao.OrderDetailsDao;
import com.geeks.dao.ProductDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class OrderDetailsDaoImpl implements OrderDetailsDao {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Integer addOrderDetails(OrderDetailsBean orderDetailsBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into order_details (order_id, product_id, quantity, price, active, created_by, created_date, modified_by, modified_date) values(?,?,?,?,?,?,?,?,?);");
            ps.setInt(1, orderDetailsBean.getOrder().getOrderId());
            ps.setInt(2, orderDetailsBean.getProductBean().getProductId());
            ps.setInt(3, orderDetailsBean.getQuantity());
            ps.setDouble(4, orderDetailsBean.getPrice());
            ps.setString(5, orderDetailsBean.getActive());
            ps.setInt(6, orderDetailsBean.getCreatedBy());
            ps.setTimestamp(7, orderDetailsBean.getCreatedDate());
            ps.setInt(8, orderDetailsBean.getModifiedBy());
            ps.setTimestamp(9, orderDetailsBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDetailsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public ResultSet getOrderDetailsByOrderId(Integer id) {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT o.`order_details_id`, p.`name`, o.`quantity`, o.`price`, o.`price`*o.`quantity` AS total FROM order_details o JOIN product p ON o.`product_id` = p.`product_id` WHERE o.order_id = ? AND o.active = '1';");
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public OrderDetailsBean getOrderDetail(Integer id) {
        OrderDetailsBean orderDetailsBean = new OrderDetailsBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * from order_details where order_details_id = ? and active = '1';");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                orderDetailsBean.setOrderDetailsId(rs.getInt("order_details_id"));
                ProductDao productDao = new ProductDaoImpl();
                ProductBean productBean = productDao.getProductByProductId(rs.getInt("product_id"));
                orderDetailsBean.setProductBean(productBean);
                OrderBean orderBean = new OrderBean();
                orderBean.setOrderId(rs.getInt("order_id"));
                orderDetailsBean.setOrder(orderBean);
                orderDetailsBean.setQuantity(rs.getInt("quantity"));
                orderDetailsBean.setPrice(rs.getDouble("price"));
                orderDetailsBean.setActive(rs.getString("active"));
                orderDetailsBean.setCreatedBy(rs.getInt("created_by"));
                orderDetailsBean.setCreatedDate(rs.getTimestamp("created_date"));
                orderDetailsBean.setModifiedBy(rs.getInt("modified_by"));
                orderDetailsBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orderDetailsBean;
    }

    @Override
    public Integer updateOrderDetails(OrderDetailsBean orderDetailsBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("UPDATE order_details SET quantity = ?, modified_by = ?, modified_date = ? WHERE order_details_id = ?;");
            ps.setInt(1, orderDetailsBean.getQuantity());
            ps.setInt(2, orderDetailsBean.getModifiedBy());
            ps.setTimestamp(3, orderDetailsBean.getModifiedDate());
            ps.setInt(4, orderDetailsBean.getOrderDetailsId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDetailsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer delete(OrderDetailsBean orderDetailsBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("UPDATE order_details SET active = '0', modified_by = ?, modified_date = ? WHERE order_details_id = ?;");
            ps.setInt(1, orderDetailsBean.getModifiedBy());
            ps.setTimestamp(2, orderDetailsBean.getModifiedDate());
            ps.setInt(3, orderDetailsBean.getOrderDetailsId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDetailsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
    
}

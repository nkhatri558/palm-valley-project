/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.OrderBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface OrderDao {
    public Boolean checkBarcode(String Barcode);
    public Integer addOrder(OrderBean orderBean);
    public OrderBean getOrderByOrderNo(String orderNo);
    public ResultSet getAllOrders();
    public Integer deleteOrder(Integer orderId);
    public Integer updateOrder(OrderBean orderBean);
}

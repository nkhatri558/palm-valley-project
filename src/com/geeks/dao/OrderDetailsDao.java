/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.OrderDetailsBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface OrderDetailsDao {
    public Integer addOrderDetails(OrderDetailsBean orderDetailsBean);
    public ResultSet getOrderDetailsByOrderId(Integer id);
    public OrderDetailsBean getOrderDetail(Integer id);
    public Integer updateOrderDetails(OrderDetailsBean orderDetailsBean);
    public Integer delete(OrderDetailsBean orderDetailsBean);
}

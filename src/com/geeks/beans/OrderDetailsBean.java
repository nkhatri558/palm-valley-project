/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.beans;

import java.io.Serializable;

/**
 *
 * @author khatr
 */
public class OrderDetailsBean extends Bean implements Serializable{
    private Integer OrderDetailsId;
    private OrderBean order;
    private ProductBean productBean;
    private Integer quantity;
    private Double price;

    public Integer getOrderDetailsId() {
        return OrderDetailsId;
    }

    public void setOrderDetailsId(Integer OrderDetailsId) {
        this.OrderDetailsId = OrderDetailsId;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public ProductBean getProductBean() {
        return productBean;
    }

    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
}

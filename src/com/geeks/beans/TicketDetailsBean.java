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
public class TicketDetailsBean extends Bean implements Serializable {
    private Integer ticketDetailsId;
    private PackageBean packageBean;
    private TicketBean ticketBean;
    private Integer quantity;

    public TicketDetailsBean() {}
    
    public Integer getTicketDetailsId() {
        return ticketDetailsId;
    }

    public void setTicketDetailsId(Integer ticketDetailsBean) {
        this.ticketDetailsId = ticketDetailsBean;
    }

    public PackageBean getPackageBean() {
        return packageBean;
    }

    public void setPackageBean(PackageBean packageBean) {
        this.packageBean = packageBean;
    }

    public TicketBean getTicketBean() {
        return ticketBean;
    }

    public void setTicketBean(TicketBean ticketBean) {
        this.ticketBean = ticketBean;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author khatr
 */
public class TicketBean extends Bean implements Serializable {
    private Integer ticketId;
    private String ticketNo;
    private String mobileNo;
    private Timestamp date;
    
    public TicketBean() {
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getBarcode() {
        return ticketNo;
    }

    public void setBarcode(String barcode) {
        this.ticketNo = barcode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}

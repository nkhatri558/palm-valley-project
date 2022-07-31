/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.TicketBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface TicketDao {
    public Boolean checkBarcode(String barcode);
    public Integer add(TicketBean ticketBean);
    public TicketBean getTicketByTicketNo(String ticketNo);
    public ResultSet getAllTickets();
    public Integer update(TicketBean ticketBean);
    public Integer delete(TicketBean ticketBean);
 
}

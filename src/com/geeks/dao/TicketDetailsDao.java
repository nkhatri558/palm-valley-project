/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.TicketDetailsBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface TicketDetailsDao {
    public Integer add(TicketDetailsBean ticketDetailsBean);
    public ResultSet getTicketDetailsByTicketId(Integer id);
    public Integer update(TicketDetailsBean ticketDetailsBean);
    public TicketDetailsBean getTicketDetailsById(Integer id);
    public Integer delete(TicketDetailsBean ticketDetailsBean);
}

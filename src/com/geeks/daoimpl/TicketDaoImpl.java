/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.TicketBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.TicketDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class TicketDaoImpl implements TicketDao {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    
    @Override
    public Boolean checkBarcode(String barcode) {
         Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from ticket where barcode = ?");
            ps.setString(1, barcode);
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row > 0;
    }

    @Override
    public Integer add(TicketBean ticketBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into ticket (ticket_no, contact, date, active, created_by, created_date, modified_by, modified_date) values(?,?,?,?,?,?,?,?)");
            ps.setString(1, ticketBean.getBarcode());
            ps.setString(2, ticketBean.getMobileNo());
            ps.setTimestamp(3, ticketBean.getDate());
            ps.setString(4, ticketBean.getActive());
            ps.setInt(5, ticketBean.getCreatedBy());
            ps.setTimestamp(6, ticketBean.getCreatedDate());
            ps.setInt(7, ticketBean.getModifiedBy());
            ps.setTimestamp(8, ticketBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public TicketBean getTicketByTicketNo(String ticketNo) {
        TicketBean ticketBean = new TicketBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from ticket where ticket_no = ? and active = '1' ORDER BY created_date DESC LIMIT 1;");
            ps.setString(1, ticketNo);
            rs = ps.executeQuery();
            while (rs.next()) {
                ticketBean.setTicketId(rs.getInt("ticket_id"));
                ticketBean.setBarcode(ticketNo);
                ticketBean.setDate(rs.getTimestamp("date"));
                ticketBean.setMobileNo(rs.getString("contact"));
                ticketBean.setActive(rs.getString("active"));
                ticketBean.setCreatedBy(rs.getInt("created_by"));
                ticketBean.setCreatedDate(rs.getTimestamp("created_date"));
                ticketBean.setCreatedBy(rs.getInt("modified_by"));
                ticketBean.setCreatedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(TicketDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketBean;
    }

    @Override
    public ResultSet getAllTickets() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT t.ticket_id, t.ticket_no, t.contact, t.date, u.`username`, t.created_date, u2.`username`, t.modified_date FROM ticket t JOIN users u ON t.`created_by` = u.`user_id` JOIN users u2 ON t.`modified_by` = u2.user_id WHERE t.active = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public Integer update(TicketBean ticketBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update ticket set contact = ?, date = ?, modified_by = ?, modified_date = ? where ticket_id = ?");
            ps.setString(1, ticketBean.getMobileNo());
            ps.setTimestamp(2, ticketBean.getDate());
            ps.setInt(3, ticketBean.getModifiedBy());
            ps.setTimestamp(4, ticketBean.getModifiedDate());
            ps.setInt(5, ticketBean.getTicketId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer delete(TicketBean ticketBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update ticket set active = '0', modified_by = ?, modified_date = ? where ticket_id = ?");
            ps.setInt(1, ticketBean.getModifiedBy());
            ps.setTimestamp(2, ticketBean.getModifiedDate());
            ps.setInt(3, ticketBean.getTicketId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
    
}

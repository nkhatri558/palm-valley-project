/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.PackageBean;
import com.geeks.beans.TicketBean;
import com.geeks.beans.TicketDetailsBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.PackageDao;
import com.geeks.dao.TicketDetailsDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class TicketDetailsDaoImpl implements TicketDetailsDao{
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Integer add(TicketDetailsBean ticketDetailsBean) {
       Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into ticket_details(package_id, ticket_id, quantity, active, created_by, created_date, modified_by, modified_date) values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, ticketDetailsBean.getPackageBean().getPackageId());
            ps.setInt(2, ticketDetailsBean.getTicketBean().getTicketId());
            ps.setInt(3, ticketDetailsBean.getQuantity());
            ps.setString(4, ticketDetailsBean.getActive());
            ps.setInt(5, ticketDetailsBean.getCreatedBy());
            ps.setTimestamp(6, ticketDetailsBean.getCreatedDate());
            ps.setInt(7, ticketDetailsBean.getModifiedBy());
            ps.setTimestamp(8, ticketDetailsBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public ResultSet getTicketDetailsByTicketId(Integer id) {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT t.ticket_details_id, p.package_name, t.quantity, p.price, t.quantity*p.price AS total FROM ticket_details t JOIN package p ON t.`package_id` = p.`package_id` WHERE t.`ticket_id` = ? AND t.active = '1';");
            ps.setInt(1, id);
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(OrderDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public Integer update(TicketDetailsBean ticketDetailsBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update ticket_details set package_id = ?, quantity = ?, modified_by = ?, modified_date = ? where ticket_details_id = ?");
            ps.setInt(1, ticketDetailsBean.getPackageBean().getPackageId());
            ps.setInt(2, ticketDetailsBean.getQuantity());
            ps.setInt(3, ticketDetailsBean.getModifiedBy());
            ps.setTimestamp(4, ticketDetailsBean.getModifiedDate());
            ps.setInt(5, ticketDetailsBean.getTicketDetailsId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDetailsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public TicketDetailsBean getTicketDetailsById(Integer id) {
        TicketDetailsBean ticketDetailsBean = new TicketDetailsBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from ticket_details where ticket_details_id = ? and active = '1'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                ticketDetailsBean.setTicketDetailsId(id);
                PackageDao packageDao = new PackageDaoImpl();
                PackageBean packageBean = packageDao.getPackageById(rs.getInt("package_id"));
                ticketDetailsBean.setPackageBean(packageBean);
                TicketBean ticketBean = new TicketBean();
                ticketBean.setTicketId(rs.getInt("ticket_id"));
                ticketDetailsBean.setTicketBean(ticketBean);
                ticketDetailsBean.setQuantity(rs.getInt("quantity"));
                ticketDetailsBean.setActive(rs.getString("active"));
                ticketDetailsBean.setCreatedBy(rs.getInt("created_by"));
                ticketDetailsBean.setCreatedDate(rs.getTimestamp("created_date"));
                ticketDetailsBean.setModifiedBy(rs.getInt("modified_by"));
                ticketDetailsBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(TicketDetailsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ticketDetailsBean;
    }

    @Override
    public Integer delete(TicketDetailsBean ticketDetailsBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update ticket_details set active = '0', modified_by = ?, modified_date = ? where ticket_details_id = ?");
            ps.setInt(1, ticketDetailsBean.getModifiedBy());
            ps.setTimestamp(2, ticketDetailsBean.getModifiedDate());
            ps.setInt(3, ticketDetailsBean.getTicketDetailsId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TicketDetailsDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }
}
    


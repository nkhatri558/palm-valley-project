/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.TaxBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.TaxDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class TaxDaoImpl implements TaxDao {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Integer add(TaxBean taxBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into tax (percentage, date_from, date_to, active,created_by, created_date,modified_by,modified_date) values (?,?,?,?,?,?,?,?)");
            ps.setInt(1, taxBean.getPercentage());
            ps.setTimestamp(2, taxBean.getDateFrom());
            ps.setTimestamp(3, taxBean.getDateTo());
            ps.setString(4, taxBean.getActive());
            ps.setInt(5, taxBean.getCreatedBy());
            ps.setTimestamp(6, taxBean.getCreatedDate());
            ps.setInt(7, taxBean.getModifiedBy());
            ps.setTimestamp(8, taxBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer update(TaxBean taxBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update tax set percentage = ?, date_from = ?, date_to = ?, modified_by = ?, modified_date = ? where tax_id = ?");
            ps.setInt(1, taxBean.getPercentage());
            ps.setTimestamp(2, taxBean.getDateFrom());
            ps.setTimestamp(3, taxBean.getDateTo());
            ps.setInt(4, taxBean.getModifiedBy());
            ps.setTimestamp(5, taxBean.getModifiedDate());
            ps.setInt(6, taxBean.getTaxId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public ResultSet getAllTaxes() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT t.`tax_id`, t.`percentage`, t.`date_from`, t.`date_to`, u.`username`,t.`created_date`, u1.`username`, t.`modified_date` FROM tax t JOIN users u ON t.`created_by` = u.`user_id` JOIN users u1 ON t.`modified_by` = u1.`user_id` WHERE t.`active` = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public TaxBean getTaxById(Integer id) {
        TaxBean taxBean = new TaxBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from tax where tax_id = ? and active = '1'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                taxBean.setTaxId(id);
                taxBean.setPercentage(rs.getInt("percentage"));
                taxBean.setDateFrom(rs.getTimestamp("date_from"));
                taxBean.setDateTo(rs.getTimestamp("date_to"));
                taxBean.setActive(rs.getString("active"));
                taxBean.setCreatedBy(rs.getInt("created_by"));
                taxBean.setCreatedDate(rs.getTimestamp("created_date"));
                taxBean.setModifiedBy(rs.getInt("modified_by"));
                taxBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return taxBean;
    }

    @Override
    public Integer delete(TaxBean taxBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update tax set active = '0', modified_by = ?, modified_date = ? where tax_id = ?");
            //ps.setString(1, "0");
            ps.setInt(1, taxBean.getModifiedBy());
            ps.setTimestamp(2, taxBean.getModifiedDate());
            ps.setInt(3, taxBean.getTaxId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public TaxBean getCurrentTax() {
        TaxBean taxBean = new TaxBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("");
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return taxBean;
    }
    
    
}

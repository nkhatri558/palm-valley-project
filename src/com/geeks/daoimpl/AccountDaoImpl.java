/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.AccountBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.AccountDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class AccountDaoImpl implements AccountDao {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;
    @Override
    public Integer addAccount(AccountBean accountBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into account (account_name, contact, description, active,created_by, created_date,modified_by,modified_date) values (?,?,?,?,?,?,?,?)");
            ps.setString(1, accountBean.getAccountName());
            ps.setString(2, accountBean.getContact());
            ps.setString(3, accountBean.getDescription());
            ps.setString(4, accountBean.getActive());
            ps.setInt(5, accountBean.getCreatedBy());
            ps.setTimestamp(6, accountBean.getCreatedDate());
            ps.setInt(7, accountBean.getModifiedBy());
            ps.setTimestamp(8, accountBean.getModifiedDate());
            row = ps.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public ResultSet getAllAccounts() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT a.account_id, a.account_name, a.contact, a.description, a.active, u1.username AS created_by ,a.created_date, u2.username AS modified_by, a.modified_date FROM account a JOIN users u1 ON  a.`created_by` = u1.`user_id` JOIN users u2 ON a.`modified_by` = u2.`user_id` WHERE a.active = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public Integer updateAccount(AccountBean accountBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update account set account_name = ?, contact = ?, description = ?, modified_by = ?, modified_date = ? where account_id = ?");
            ps.setString(1, accountBean.getAccountName());
            ps.setString(2, accountBean.getContact());
            ps.setString(3, accountBean.getDescription());
            ps.setInt(4, accountBean.getModifiedBy());
            ps.setTimestamp(5, accountBean.getModifiedDate());
            ps.setInt(6, accountBean.getAccountId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public AccountBean getAccountById(Integer id) {
        AccountBean accountBean = new AccountBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from account where account_id = ? and active = '1'");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()) {
                accountBean.setAccountId(rs.getInt("account_id"));
                accountBean.setAccountName(rs.getString("account_name"));
                accountBean.setContact(rs.getString("contact"));
                accountBean.setDescription(rs.getString("description"));
                accountBean.setActive(rs.getString("active"));
                accountBean.setCreatedBy(rs.getInt("created_by"));
                accountBean.setCreatedDate(rs.getTimestamp("created_date"));
                accountBean.setModifiedBy(rs.getInt("modified_by"));
                accountBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return accountBean;
    }

    @Override
    public Integer deleteAccount(AccountBean accountBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update account set active = '0', modified_by = ?, modified_date = ? where account_id = ?");
            ps.setInt(1, accountBean.getModifiedBy());
            ps.setTimestamp(2, accountBean.getModifiedDate());
            ps.setInt(3, accountBean.getAccountId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public AccountBean getAccountByName(String name) {
        AccountBean accountBean = new AccountBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from account where account_name = ? and active = '1'");
            ps.setString(1,name);
            rs = ps.executeQuery();
            while(rs.next()) {
                accountBean.setAccountId(rs.getInt("account_id"));
                accountBean.setAccountName(rs.getString("account_name"));
                accountBean.setContact(rs.getString("contact"));
                accountBean.setDescription(rs.getString("description"));
                accountBean.setActive(rs.getString("active"));
                accountBean.setCreatedBy(rs.getInt("created_by"));
                accountBean.setCreatedDate(rs.getTimestamp("created_date"));
                accountBean.setModifiedBy(rs.getInt("modified_by"));
                accountBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accountBean;
    }

    @Override
    public List<AccountBean> getAccounts() {
        List<AccountBean> accounts = new ArrayList<>();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from account where active = '1'");
            rs = ps.executeQuery();
            while(rs.next()) {
                AccountBean accountBean = new AccountBean();
                accountBean.setAccountId(rs.getInt("account_id"));
                accountBean.setAccountName(rs.getString("account_name"));
                accounts.add(accountBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }
    
    
}

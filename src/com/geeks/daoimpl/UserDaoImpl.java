/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.UserBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.UserDao;
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
public class UserDaoImpl implements UserDao{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    @Override
    public UserBean login(String username, String password) {
        UserBean userBean = new UserBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from users where username = ? and password = ? and active = '1'");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                userBean.setUserId(rs.getInt("user_id"));
                userBean.setUsername(rs.getString("username"));
                userBean.setDob(rs.getDate("dob"));
                userBean.setContact(rs.getString("contact"));
                userBean.setNic(rs.getString("nic"));
                userBean.setPassword(rs.getString("password"));
                userBean.setUserType(rs.getString("user_type"));
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return userBean;
    }

    @Override
    public ResultSet getAllUsersResultSet() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT u.user_id, u.username, u.password,u.dob,u.contact,u.nic,u.user_type, u.active, u1.username, u.created_date, u2.username, u.modified_date FROM users u JOIN users u1 ON u.`created_by` = u1.`user_id` JOIN users u2 ON u.`modified_by` = u2.`user_id` WHERE u.active = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public Integer addUser(UserBean userBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into users (username,password,dob,contact,nic,user_type,active,created_by,created_date,modified_by,modified_date) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1,userBean.getUsername());
            ps.setString(2, userBean.getPassword());
            ps.setDate(3,  new java.sql.Date(userBean.getDob().getTime()));
            ps.setString(4, userBean.getContact());
            ps.setString(5, userBean.getNic());
            ps.setString(6, userBean.getUserType());
            ps.setString(7, userBean.getActive());
            ps.setInt(8, userBean.getCreatedBy());
            ps.setTimestamp(9, userBean.getCreatedDate());
            ps.setInt(10, userBean.getModifiedBy());
            ps.setTimestamp(11, userBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer deleteUser(UserBean userBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update users set active = ?, modified_by = ?, modified_date = ? where user_id = ?");
            ps.setString(1, "0");
            ps.setInt(2, userBean.getModifiedBy());
            ps.setTimestamp(3, userBean.getModifiedDate());
            ps.setInt(4, userBean.getUserId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer updateUser(UserBean userBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update users set username = ?, password = ?,contact = ?, nic = ?, user_type = ? ,modified_by = ?, modified_date = ?, dob = ? where user_id = ?");
            ps.setString(1, userBean.getUsername());
            ps.setString(2, userBean.getPassword());
            ps.setString(3, userBean.getContact());
            ps.setString(4, userBean.getNic());
            ps.setString(5, userBean.getUserType());
            ps.setInt(6, userBean.getModifiedBy());
            ps.setTimestamp(7, userBean.getModifiedDate());
            ps.setDate(8, new java.sql.Date(userBean.getDob().getTime()));
            ps.setInt(9, userBean.getUserId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public UserBean getUserById(Integer id) {
        UserBean userBean = new UserBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from users where user_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                userBean.setUserId(rs.getInt("user_id"));
                userBean.setUsername(rs.getString("username"));
                userBean.setDob(rs.getDate("dob"));
                userBean.setContact(rs.getString("contact"));
                userBean.setNic(rs.getString("nic"));
                userBean.setPassword(rs.getString("password"));
                userBean.setUserType(rs.getString("user_type"));
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userBean;
    }

    @Override
    public List<UserBean> getCustomers() {
        List<UserBean> list = new ArrayList<>();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from users where user_type = 'CUSTOMER' and active = '1'");
            rs = ps.executeQuery();
            while(rs.next()) {
                UserBean userBean = new UserBean();
                userBean.setUserId(rs.getInt("user_id"));
                userBean.setUsername(rs.getString("username"));
                list.add(userBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public UserBean getUserByName(String name) {
        UserBean userBean = new UserBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from users where username = ?");
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                userBean.setUserId(rs.getInt("user_id"));
                userBean.setUsername(rs.getString("username"));
                userBean.setDob(rs.getDate("dob"));
                userBean.setContact(rs.getString("contact"));
                userBean.setNic(rs.getString("nic"));
                userBean.setPassword(rs.getString("password"));
                userBean.setUserType(rs.getString("user_type"));
            }
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userBean;
    }
    
}

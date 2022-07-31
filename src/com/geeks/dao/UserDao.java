/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.UserBean;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author khatr
 */
public interface UserDao {
    public UserBean login(String username, String password);
    public ResultSet getAllUsersResultSet();
    public Integer addUser(UserBean userBean);
    public Integer deleteUser(UserBean userBean);
    public Integer updateUser(UserBean userBean);
    public UserBean getUserById(Integer id);
    public List<UserBean> getCustomers();
    public UserBean getUserByName(String name);
}

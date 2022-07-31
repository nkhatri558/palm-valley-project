/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.AccountBean;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author khatr
 */
public interface AccountDao {
    public Integer addAccount(AccountBean accountBean);
    public ResultSet getAllAccounts();
    public Integer updateAccount(AccountBean accountBean);
    public AccountBean getAccountById(Integer id);
    public AccountBean getAccountByName(String name);
    public List<AccountBean> getAccounts();
    public Integer deleteAccount(AccountBean accountBean);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.AccountBean;
import com.geeks.beans.ProductBean;
import com.geeks.beans.StockBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.StockDao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class StockDaoImpl  implements StockDao{
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Integer addStock(StockBean stockBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into stock (name, account_id, product_id, quantity, whole_sale_price, retail_price, date, active, created_by, created_date, modified_by,modified_date) values (?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setString(1, stockBean.getStockName());
            ps.setInt(2, stockBean.getAccountBean().getAccountId());
            ps.setInt(3, stockBean.getProductBean().getProductId());
            ps.setInt(4, stockBean.getQuantity());
            ps.setDouble(5, stockBean.getWholeSalePrice());
            ps.setDouble(6, stockBean.getRetailPrice());
            ps.setDate(7, (Date) stockBean.getDate());
            ps.setString(8, stockBean.getActive());
            ps.setInt(9, stockBean.getCreatedBy());
            ps.setTimestamp(10, stockBean.getCreatedDate());
            ps.setInt(11, stockBean.getModifiedBy());
            ps.setTimestamp(12, stockBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer updateStock(StockBean stockBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update stock set name = ?, account_id = ?, product_id = ?, quantity = ?, whole_sale_price = ?, retail_price = ?, date = ?,modified_by = ?, modified_date = ?  where stock_id = ?");
            ps.setString(1, stockBean.getStockName());
            ps.setInt(2, stockBean.getAccountBean().getAccountId());
            ps.setInt(3, stockBean.getProductBean().getProductId());
            ps.setInt(4, stockBean.getQuantity());
            ps.setDouble(5, stockBean.getWholeSalePrice());
            ps.setDouble(6, stockBean.getRetailPrice());
            ps.setDate(7, new java.sql.Date(stockBean.getDate().getTime()));
            ps.setInt(8, stockBean.getModifiedBy());
            ps.setTimestamp(9, stockBean.getModifiedDate());
            ps.setInt(10, stockBean.getStockId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer deleteStock(StockBean stockBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update stock set active = '0', modified_by = ?, modified_date = ? where stock_id = ?");
            ps.setInt(1, stockBean.getModifiedBy());
            ps.setTimestamp(2, stockBean.getModifiedDate());
            ps.setInt(3, stockBean.getStockId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public ResultSet getAllStockResultSet() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT s.stock_id, s.`name`, a.account_name, p.name, s.`quantity`, s.`whole_sale_price`, s.`retail_price`, s.`date`, s.`active`, u1.username, s.`created_date`, u2.username, s.`modified_date` FROM stock s JOIN account a ON s.`account_id` = a.`account_id` JOIN product p ON s.`product_id` = p.`product_id` JOIN users u1 ON s.`created_by` = u1.`user_id` JOIN users u2 ON s.`modified_by` = u2.`user_id` WHERE s.`active` = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }

    @Override
    public StockBean getStockByStockId(Integer id) {
        StockBean stockBean = new StockBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from stock where stock_id = ? and active = '1'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                stockBean.setStockId(rs.getInt("stock_id"));
                stockBean.setStockName(rs.getString("name"));
                AccountBean accountBean = new AccountBean();
                accountBean.setAccountId(rs.getInt("account_id"));
                stockBean.setAccountBean(accountBean);
                ProductBean productBean = new ProductBean();
                productBean.setProductId(rs.getInt("product_id"));
                stockBean.setProductBean(productBean);
                stockBean.setQuantity(rs.getInt("quantity"));
                stockBean.setWholeSalePrice(rs.getDouble("whole_sale_price"));
                stockBean.setRetailPrice(rs.getDouble("retail_price"));
                stockBean.setDate(rs.getTimestamp("date"));
                stockBean.setCreatedBy(rs.getInt("created_by"));
                stockBean.setCreatedDate(rs.getTimestamp("created_date"));
                stockBean.setModifiedBy(rs.getInt("modified_by"));
                stockBean.setModifiedDate(rs.getTimestamp("modified_date"));
                
            }
        } catch (Exception ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stockBean;
    }

    @Override
    public StockBean getLastStockByProductId(ProductBean productBean) {
        StockBean stockBean = new StockBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM stock WHERE product_id = ? AND active = '1' ORDER BY stock_id DESC LIMIT 1 ;");
            ps.setInt(1, productBean.getProductId());
            rs = ps.executeQuery();
            while(rs.next()) {
                stockBean.setStockId(rs.getInt("stock_id"));
                stockBean.setStockName(rs.getString("name"));
                AccountBean accountBean = new AccountBean();
                accountBean.setAccountId(rs.getInt("account_id"));
                stockBean.setAccountBean(accountBean);
                stockBean.setProductBean(productBean);
                stockBean.setQuantity(rs.getInt("quantity"));
                stockBean.setWholeSalePrice(rs.getDouble("whole_sale_price"));
                stockBean.setRetailPrice(rs.getDouble("retail_price"));
                stockBean.setDate(rs.getTimestamp("date"));
                stockBean.setCreatedBy(rs.getInt("created_by"));
                stockBean.setCreatedDate(rs.getTimestamp("created_date"));
                stockBean.setModifiedBy(rs.getInt("modified_by"));
                stockBean.setModifiedDate(rs.getTimestamp("modified_date"));
                
            }
        } catch (Exception ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stockBean;
    }
    
}

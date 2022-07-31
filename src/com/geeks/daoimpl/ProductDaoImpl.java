/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.CategoryBean;
import com.geeks.beans.ProductBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.ProductDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class ProductDaoImpl implements ProductDao {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    @Override
    public Integer addProduct(ProductBean productBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into product (category_id,name,barcode,price,size, quantity, active, created_by, created_date, modified_by, modified_date) values (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, productBean.getCategory().getCategoryId());
            ps.setString(2, productBean.getProductName());
            ps.setString(3, productBean.getBarcode());
            ps.setDouble(4, productBean.getPrice());
            ps.setInt(5, productBean.getSize());
            ps.setInt(6, productBean.getQuantity());
            ps.setString(7, productBean.getActive());
            ps.setInt(8, productBean.getCreatedBy());
            ps.setTimestamp(9, productBean.getCreatedDate());
            ps.setInt(10, productBean.getModifiedBy());
            ps.setTimestamp(11, productBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer updateProduct(ProductBean productBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update product set category_id = ?, name = ?, barcode = ?, price = ?, size = ?, quantity = ?, modified_by = ?, modified_date = ? where product_id = ?");
            ps.setInt(1, productBean.getCategory().getCategoryId());
            ps.setString(2, productBean.getProductName());
            ps.setString(3, productBean.getBarcode());
            ps.setDouble(4, productBean.getPrice());
            ps.setInt(5, productBean.getSize());
            ps.setInt(6, productBean.getQuantity());
            ps.setInt(7, productBean.getModifiedBy());
            ps.setTimestamp(8, productBean.getModifiedDate());
            ps.setInt(9, productBean.getProductId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public Integer deleteProduct(ProductBean productBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update product set active = '0', modified_by = ?, modified_date=? where product_id = ?");
            ps.setInt(1, productBean.getModifiedBy());
            ps.setTimestamp(2, productBean.getModifiedDate());
            ps.setInt(3, productBean.getProductId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public ResultSet getAllProducts() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT p.product_id, c.category_name, p.name, p.barcode, p.price, p.size, p.quantity, p.active, u1.username AS created_by, p.created_date, u2.username AS modified_by, p.modified_date FROM product p JOIN category c ON p.`category_id` = c.`category_id` JOIN users u1 ON p.`created_by` = u1.`user_id` JOIN users u2 ON p.`modified_by`= u2.`user_id` WHERE p.`active` = '1';");
            rs = ps.executeQuery();
            
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public ProductBean getProductByProductId(Integer id) {
        ProductBean productBean = new ProductBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from product where product_id = ? and active = '1'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                productBean.setProductId(rs.getInt("product_id"));
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setCategoryId(rs.getInt("category_id"));
                productBean.setCategory(categoryBean);
                productBean.setProductName(rs.getString("name"));
                productBean.setBarcode(rs.getString("barcode"));
                productBean.setPrice(rs.getDouble("price"));
                productBean.setSize(rs.getInt("size"));
                productBean.setQuantity(rs.getInt("quantity"));
                productBean.setActive(rs.getString("active"));
                productBean.setCreatedBy(rs.getInt("created_by"));
                productBean.setCreatedDate(rs.getTimestamp("created_date"));
                productBean.setModifiedBy(rs.getInt("modified_by"));
                productBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productBean;
    }

    @Override
    public Boolean checkBarcode(String barcode) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection(); 
            ps = con.prepareStatement("select * from product where barcode = ?");
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row > 0;
    }

    @Override
    public ProductBean getProductByProductName(String productName) {
        ProductBean productBean = new ProductBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from product where name = ? and active = '1'");
            ps.setString(1, productName);
            rs = ps.executeQuery();
            while(rs.next()) {
                productBean.setProductId(rs.getInt("product_id"));
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setCategoryId(rs.getInt("category_id"));
                productBean.setCategory(categoryBean);
                productBean.setProductName(rs.getString("name"));
                productBean.setBarcode(rs.getString("barcode"));
                productBean.setPrice(rs.getDouble("price"));
                productBean.setSize(rs.getInt("size"));
                productBean.setQuantity(rs.getInt("quantity"));
                productBean.setActive(rs.getString("active"));
                productBean.setCreatedBy(rs.getInt("created_by"));
                productBean.setCreatedDate(rs.getTimestamp("created_date"));
                productBean.setModifiedBy(rs.getInt("modified_by"));
                productBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return productBean;
    }

    @Override
    public List<ProductBean> getProducts() {
        List<ProductBean> products = new ArrayList<>();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from product where active = '1'");
            rs = ps.executeQuery();
            while(rs.next()) {
                ProductBean productBean = new ProductBean();
                productBean.setProductId(rs.getInt("product_id"));
                productBean.setProductName(rs.getString("name"));
                products.add(productBean);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.CategoryBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.CategoryDao;
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
public class CategoryDaoImpl implements CategoryDao {
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    public Integer addCategory(CategoryBean categoryBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into category (category_name, description, active, created_by, created_date, modified_by, modified_date) values (?,?,?,?,?,?,?)");
            ps.setString(1, categoryBean.getCategoryName());
            ps.setString(2, categoryBean.getDescription());
            ps.setString(3, categoryBean.getActive());
            ps.setInt(4, categoryBean.getCreatedBy());
            ps.setTimestamp(5, categoryBean.getCreatedDate());
            ps.setInt(6, categoryBean.getModifiedBy());
            ps.setTimestamp(7, categoryBean.getModifiedDate());
            row = ps.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public Integer updateCategory(CategoryBean categoryBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update category set category_name = ?, description = ?, modified_by = ?, modified_date=? where category_id = ?");
            ps.setString(1, categoryBean.getCategoryName());
            ps.setString(2, categoryBean.getDescription());
            ps.setInt(3, categoryBean.getModifiedBy());
            ps.setTimestamp(4, categoryBean.getModifiedDate());
            ps.setInt(5, categoryBean.getCategoryId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public Integer deleteCategory(CategoryBean categoryBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update category set active = '0', modified_by = ?, modified_date=? where category_id = ?");
            ps.setInt(1, categoryBean.getModifiedBy());
            ps.setTimestamp(2, categoryBean.getModifiedDate());
            ps.setInt(3, categoryBean.getCategoryId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return row;
    }

    @Override
    public CategoryBean getCategoryById(Integer id) {
        CategoryBean categoryBean = new CategoryBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from category where category_id = ? and active = '1'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                categoryBean.setCategoryId(rs.getInt("category_id"));
                categoryBean.setCategoryName(rs.getString("category_name"));
                categoryBean.setDescription(rs.getString("description"));
                categoryBean.setActive(rs.getString("active"));
                categoryBean.setCreatedBy(rs.getInt("created_by"));
                categoryBean.setCreatedDate(rs.getTimestamp("created_date"));
                categoryBean.setModifiedBy(rs.getInt("modified_by"));
                categoryBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return categoryBean;
    }

    @Override
    public ResultSet getCategoriesResultSet() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("SELECT c.category_id, c.category_name, c.description, c.active, c1.username, c.created_date, c2.username, c.modified_date FROM category c JOIN users c1 ON c.`created_by` = c1.`user_id` JOIN users c2 ON c.`modified_by` = c2.`user_id` WHERE c.active = '1'; ");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public List<CategoryBean> getCategories() {
        List<CategoryBean> categories = new ArrayList<>();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from category where active = '1'");
            rs = ps.executeQuery();
            while(rs.next()) {
                CategoryBean category = new CategoryBean();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                categories.add(category);
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return categories;
    }

    @Override
    public CategoryBean getCategoyrByName(String name) {
        CategoryBean categoryBean = new CategoryBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from category where category_name = ? and active = '1'");
            ps.setString(1, name);
            rs = ps.executeQuery();
            while(rs.next()) {
                categoryBean.setCategoryId(rs.getInt("category_id"));
                categoryBean.setCategoryName(rs.getString("category_name"));
                categoryBean.setDescription(rs.getString("description"));
                categoryBean.setActive(rs.getString("active"));
                categoryBean.setCreatedBy(rs.getInt("created_by"));
                categoryBean.setCreatedDate(rs.getTimestamp("created_date"));
                categoryBean.setModifiedBy(rs.getInt("modified_by"));
                categoryBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return categoryBean;
    }
    
}

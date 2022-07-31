/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.CategoryBean;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author khatr
 */
public interface CategoryDao {
    public Integer addCategory(CategoryBean categoryBean);
    public Integer updateCategory(CategoryBean categoryBean);
    public Integer deleteCategory(CategoryBean categoryBean);
    public CategoryBean getCategoryById(Integer id);
    public CategoryBean getCategoyrByName(String name);
    public ResultSet getCategoriesResultSet();
    public List<CategoryBean> getCategories();
}

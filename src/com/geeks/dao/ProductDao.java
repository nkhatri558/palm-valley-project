/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.ProductBean;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author khatr
 */
public interface ProductDao {
    public Integer addProduct(ProductBean productBean);
    public Integer updateProduct(ProductBean productBean);
    public Integer deleteProduct(ProductBean productBean);
    public ResultSet getAllProducts();
    public ProductBean getProductByProductId(Integer id);
    public ProductBean getProductByProductName(String productName);
    public Boolean checkBarcode(String barcode);
    public List<ProductBean> getProducts();
}

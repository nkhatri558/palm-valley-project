/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.ProductBean;
import com.geeks.beans.StockBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface StockDao {
    public Integer addStock(StockBean stockBean);
    public Integer updateStock(StockBean stockBean);
    public Integer deleteStock(StockBean stockBean);
    public ResultSet getAllStockResultSet();
    public StockBean getStockByStockId(Integer id);
    public StockBean getLastStockByProductId(ProductBean productBean);
}

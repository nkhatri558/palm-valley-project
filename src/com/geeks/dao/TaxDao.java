/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.TaxBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface TaxDao {
    public Integer add(TaxBean taxBean);
    public Integer update(TaxBean taxBean);
    public ResultSet getAllTaxes();
    public TaxBean getTaxById(Integer id);
    public Integer delete(TaxBean taxBean);
    public TaxBean getCurrentTax();
}

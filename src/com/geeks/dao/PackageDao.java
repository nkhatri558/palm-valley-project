/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.dao;

import com.geeks.beans.PackageBean;
import java.sql.ResultSet;

/**
 *
 * @author khatr
 */
public interface PackageDao {
    public Integer add(PackageBean packageBean);
    public ResultSet getAllPackages();
    public Integer update(PackageBean packageBean);
    public PackageBean getPackageById(Integer id);
    public Integer delete(PackageBean packageBean);
    public PackageBean getPackageByName(String packageName);
    
}

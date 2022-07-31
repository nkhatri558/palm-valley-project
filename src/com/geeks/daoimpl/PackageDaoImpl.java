/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geeks.daoimpl;

import com.geeks.beans.PackageBean;
import com.geeks.connection.DBConnection;
import com.geeks.dao.PackageDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author khatr
 */
public class PackageDaoImpl implements PackageDao{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    @Override
    public Integer add(PackageBean packageBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("insert into package (package_name, price, count, active,created_by, created_date,modified_by,modified_date) values (?,?,?,?,?,?,?,?)");
            ps.setString(1, packageBean.getPackageName());
            ps.setDouble(2, packageBean.getPrice());
            ps.setInt(3, packageBean.getCount());
            ps.setString(4, packageBean.getActive());
            ps.setInt(5, packageBean.getCreatedBy());
            ps.setTimestamp(6, packageBean.getCreatedDate());
            ps.setInt(7, packageBean.getModifiedBy());
            ps.setTimestamp(8, packageBean.getModifiedDate());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public ResultSet getAllPackages() {
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select p.`package_id`, p.`package_name`, p.`price`, p.`count`, u.`username`, p.`created_date`, u1.`username`, p.`modified_date` from package p join users u on p.`created_by` = u.`user_id` join users u1 on p.`modified_by` = u1.`user_id` where p.active = '1';");
            rs = ps.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(PackageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    @Override
    public Integer update(PackageBean packageBean) {
        Integer row = 0;
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update package set package_name = ?, price = ?, count = ?, modified_by = ?, modified_date = ? where package_id = ?");
            ps.setString(1, packageBean.getPackageName());
            ps.setDouble(2, packageBean.getPrice());
            ps.setInt(3, packageBean.getCount());
            ps.setInt(4, packageBean.getModifiedBy());
            ps.setTimestamp(5, packageBean.getModifiedDate());
            ps.setInt(6, packageBean.getPackageId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(TaxDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public PackageBean getPackageById(Integer id) {
        PackageBean packageBean = new PackageBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from package where package_id = ? and active = '1'");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()) {
                packageBean.setPackageId(id);
                packageBean.setPackageName(rs.getString("package_name"));
                packageBean.setCount(rs.getInt("count"));
                packageBean.setPrice(rs.getDouble("price"));
                packageBean.setActive(rs.getString("active"));
                packageBean.setCreatedBy(rs.getInt("created_by"));
                packageBean.setCreatedDate(rs.getTimestamp("created_date"));
                packageBean.setModifiedBy(rs.getInt("modified_by"));
                packageBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(PackageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return packageBean;
    }

    @Override
    public Integer delete(PackageBean packageBean) {
        Integer row = 0;
         try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("update package set active = '0', modified_by = ?, modified_date = ? where package_id = ?");
            ps.setInt(1, packageBean.getModifiedBy());
            ps.setTimestamp(2, packageBean.getModifiedDate());
            ps.setInt(3, packageBean.getPackageId());
            row = ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row;
    }

    @Override
    public PackageBean getPackageByName(String packageName) {
        PackageBean packageBean = new PackageBean();
        try {
            con = DBConnection.getConnection();
            ps = con.prepareStatement("select * from package where package_name = ? and active = '1'");
            ps.setString(1, packageName);
            rs = ps.executeQuery();
            while(rs.next()) {
                packageBean.setPackageId(rs.getInt("package_id"));
                packageBean.setPackageName(rs.getString("package_name"));
                packageBean.setCount(rs.getInt("count"));
                packageBean.setPrice(rs.getDouble("price"));
                packageBean.setActive(rs.getString("active"));
                packageBean.setCreatedBy(rs.getInt("created_by"));
                packageBean.setCreatedDate(rs.getTimestamp("created_date"));
                packageBean.setModifiedBy(rs.getInt("modified_by"));
                packageBean.setModifiedDate(rs.getTimestamp("modified_date"));
            }
        } catch (Exception ex) {
            Logger.getLogger(PackageDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return packageBean;
    }
    
    
}

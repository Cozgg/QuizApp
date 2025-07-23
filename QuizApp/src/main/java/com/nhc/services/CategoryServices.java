/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services;

import com.nhc.pojo.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CategoryServices extends BaseServices<Category> {

    @Override
    public PreparedStatement getStm(Connection con) throws SQLException {
        return con.prepareCall("SELECT * FROM category");
    }

    @Override
    public List<Category> getResults(ResultSet rs) throws SQLException {
        List<Category> cates = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Category c = new Category(id, name);
            cates.add(c);
        }
        return cates;
    }

}

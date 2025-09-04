/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services.questions;

import com.nhc.pojo.Question;
import com.nhc.services.BaseServices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public abstract class BaseQuestionServices extends BaseServices<Question>  {
    public abstract String getSQL(List<Object> params);
    
    @Override
    public PreparedStatement getStm(Connection con) throws SQLException {
        List<Object> params = new ArrayList<>();
        
        PreparedStatement stm = con.prepareCall(getSQL(params));
        
        for(int i = 0; i < params.size(); i++){
            stm.setObject(i + 1, params.get(i));
        }
        return stm;
    }

    @Override
    public List<Question> getResults(ResultSet rs) throws SQLException {
        List<Question> questions = new ArrayList<>();

        while (rs.next()) {
            Question q = new Question.Builder(rs.getInt("id"), rs.getString("content")).build();
            questions.add(q);
        }
        return questions;
    }
}

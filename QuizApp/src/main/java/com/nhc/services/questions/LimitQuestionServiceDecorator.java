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
public class LimitQuestionServiceDecorator extends QuestionDecorator{
    
    private int num;

    public LimitQuestionServiceDecorator(BaseQuestionServices decorator, int num) {
        super(decorator);
        this.num = num;
    }

//    @Override
//    public PreparedStatement getSQL(Connection con) throws SQLException {
//        PreparedStatement stm = con.prepareCall("SELECT * FROM choice ORDER BY rand() LIMIT ?");
//        
//        stm.setInt(1, num);
//        return stm;
//    }
//
//    @Override
//    public List<Question> getResults(ResultSet rs) throws SQLException {
//        List<Question> questions = new ArrayList<>();
//
//        while (rs.next()) {
//            int id = rs.getInt("id");
//            BaseServices s = new ChoiceService(id);
//            Question q = new Question.Builder(id, rs.getString("content")).addAllChoice(s.list()).build();
//            questions.add(q);
//        }
//        return questions;
//    }

    @Override
    public String getSQL(List<Object> params) {
        String sql = this.decorator.getSQL(params) + " ORDER BY RAND() LIMIT ?";
        params.add(this.num);
        return sql;
    }

    @Override
    public List<Question> list() throws SQLException {
        List<Question> questions = super.list();
        
        for(var q : questions){
            BaseServices s = new ChoiceService(q.getId());
            q.setChoices(s.list());
        }
        return questions;
    }
    
    
    
    
    
}

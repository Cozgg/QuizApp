/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services.questions;

import com.nhc.pojo.Choice;
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
public class ChoiceService extends BaseServices<Choice>{
    private int questionId;

    public ChoiceService(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public PreparedStatement getStm(Connection con) throws SQLException {
        PreparedStatement stm = con.prepareCall("SELECT * FROM choice WHERE question_id = ?");
        stm.setInt(1, questionId);
        return stm;
    }

    @Override
    public List<Choice> getResults(ResultSet rs) throws SQLException {
       List<Choice> choices = new ArrayList<>();

        while (rs.next()) {
            Choice c = new Choice(rs.getInt("id"), rs.getString("content"), rs.getBoolean("is_correct"));
            choices.add(c);
        }
        return choices;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services.questions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class KeywordQuestionServiceDecorator extends QuestionServices {

    private String kw;

    public KeywordQuestionServiceDecorator(String kw) {
        this.kw = kw;
    }

    @Override
    public PreparedStatement getStm(Connection con) throws SQLException {
        PreparedStatement stm = con.prepareCall("SELECT * FROM question WHERE content like concat('%', ? , '%')");
        
        stm.setString(1, kw);
        return stm;
    }

}

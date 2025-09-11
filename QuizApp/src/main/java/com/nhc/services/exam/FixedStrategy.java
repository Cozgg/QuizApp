/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services.exam;

import com.nhc.pojo.Question;
import com.nhc.services.questions.BaseQuestionServices;
import com.nhc.services.questions.LevelQuestionServiceDecorator;
import com.nhc.services.questions.LimitQuestionServiceDecorator;
import com.nhc.utils.MyConfig;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class FixedStrategy extends ExamStrategy {

    @Override
    public List<Question> getQuestion() throws SQLException {
        List<Question> question = new ArrayList<>();

        for (int i = 0; i < MyConfig.RATES.length; i++) {
            BaseQuestionServices s = new LimitQuestionServiceDecorator(new LevelQuestionServiceDecorator(MyConfig.quesService, i + 1), (int) (MyConfig.RATES[i] * MyConfig.NUM_QUES));
            question.addAll(s.list());
        }
        
        return question;
    }

}

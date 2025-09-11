/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.nhc.services.exam;

import com.nhc.pojo.Question;
import com.nhc.services.questions.BaseQuestionServices;
import com.nhc.services.questions.LimitQuestionServiceDecorator;
import com.nhc.utils.MyConfig;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author admin
 */
public class SpecificStrategy extends ExamStrategy {
    public int num;
    
    public SpecificStrategy(int num){
        this.num = num;
    }
    
    @Override
    public List<Question> getQuestion() throws SQLException{
        BaseQuestionServices s = new LimitQuestionServiceDecorator(MyConfig.quesService, num);
        return s.list();
        
    }
}

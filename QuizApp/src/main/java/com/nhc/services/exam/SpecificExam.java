/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.nhc.services.exam;

import com.nhc.pojo.Question;
import com.nhc.services.questions.BaseQuestionServices;
import com.nhc.services.questions.LimitQuestionServicesDecorator;
import com.nhc.utils.MyConfigs;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author admin
 */
public class SpecificExam extends ExamStrategy {
    public int num;
    
    public SpecificExam(int num){
        this.num = num;
    }
    
    @Override
    public List<Question> getQuestion() throws SQLException{
        BaseQuestionServices s = new LimitedQuestionServicesDecorator(MyConfig.quesService, this.num);
        return s.list();
    }
}

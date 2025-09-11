/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.nhc.services.exam;


import com.nhc.pojo.Exam;
import com.nhc.pojo.Question;
import com.nhc.utils.MyConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public abstract class ExamStrategy {
    public abstract List<Question> getQuestion() throws SQLException;
    
    public void saveExam(List<Question> questions) throws SQLException{
        Connection conn = MyConnector.getInstance().connect();
        
        
        Exam e = new Exam(questions);
        conn.setAutoCommit(false);
        String sql = "INSERT INTO exam(title, creation_date) VALUES(?,?)";
        
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setString(1, e.getTitle());
        stm.setString(2, e.getCreationDate().toString());
        
        if(stm.executeUpdate()> 0){
            int eId = -1;
            ResultSet r = stm.getGeneratedKeys();
            if(r.next())
                eId = r.getInt(1);
            sql = "INSERT INTO exam_quesiton(exam_id, question_id) VALUES(?,?)";
            
            stm = conn.prepareStatement(sql);
            stm.setInt(1, eId);
            
            for(var q : questions){
                stm.setInt(2, q.getId());
                stm.executeUpdate();
            }
            conn.commit();
        } else 
            conn.rollback();
       
    }
}

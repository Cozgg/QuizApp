/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.services.questions;

import java.util.List;

/**
 *
 * @author admin
 */
public class CategoryQuestionServiceDecorator extends QuestionDecorator{

    private int num;

    public CategoryQuestionServiceDecorator(BaseQuestionServices decorator, int num) {
        super(decorator);
        this.num = num;
    }
    
    
    
    @Override
    public String getSQL(List<Object> params) {
        String sql = this.decorator.getSQL(params) + "AND category_id=?";
        params.add(this.num);
        
        return sql;
    }
    
}

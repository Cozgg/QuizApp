/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.utils;

import com.nhc.services.BaseServices;
import com.nhc.services.CategoryServices;
import com.nhc.services.LevelServices;
import com.nhc.services.questions.BaseQuestionServices;
import com.nhc.services.questions.QuestionServices;
import com.nhc.services.questions.UpdateQuestionServices;

/**
 *
 * @author admin
 */
public class MyConfig {

    public static final BaseServices cateService = new CategoryServices();
    public static final BaseQuestionServices quesService = new QuestionServices();
    public static final UpdateQuestionServices uQService = new UpdateQuestionServices();
    public static final BaseServices lvlService = new LevelServices();
    
}

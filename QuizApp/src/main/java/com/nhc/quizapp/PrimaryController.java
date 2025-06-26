package com.nhc.quizapp;

import com.nhc.utils.MyAlert;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrimaryController {

    public void handleQuestionManagement(ActionEvent event){
        MyAlert.getInstance().getMsg("Cozg dep trai chua mo");
        
    }
    public void handlePracticeButton(ActionEvent event){
        MyAlert.getInstance().getMsg("Coming soon...");
    }
}

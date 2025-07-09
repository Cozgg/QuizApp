package com.nhc.quizapp;

import com.nhc.utils.MyAlert;
import com.nhc.utils.MyStage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class PrimaryController {

    public void handleQuestionManagement(ActionEvent event) throws IOException {
        MyStage.getInstance().showScreen("question.fxml");
    }

    public void handlePractice(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }

    public void handleTest(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }

    public void handleSignUp(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }

    public void handleSignIn(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }
}

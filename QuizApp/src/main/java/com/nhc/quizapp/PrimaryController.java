package com.nhc.quizapp;

import com.nhc.utils.MyAlert;
import com.nhc.utils.MyStage;
import java.io.IOException;
import javafx.event.ActionEvent;

public class PrimaryController {

    public void handleQuestionManagement(ActionEvent event) throws IOException {
          MyStage.getInstance().showStage("question.fxml");
    }

    public void handlePracticeButton(ActionEvent event){
        MyAlert.getInstance().getMsg("Coming soon...");
    }
}

package com.nhc.quizapp;

import com.nhc.utils.MyAlert;
import com.nhc.utils.MyStage;
import com.nhc.utils.themes.Theme;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


public class PrimaryController implements Initializable{
    @FXML private ComboBox<Theme> cbthemes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbthemes.setItems(FXCollections.observableArrayList(Theme.values())); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void handleQuestionManagement(ActionEvent event) throws IOException {
        MyStage.getInstance().showScreen("question.fxml");
    }

    public void handlePractice(ActionEvent event) throws IOException{
        MyStage.getInstance().showScreen("practice.fxml");
    }

    public void handleTest(ActionEvent event) throws IOException{
        MyStage.getInstance().showScreen("exam.fxml");
    }

    public void handleSignUp(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }

    public void handleSignIn(ActionEvent event){
        MyAlert.getInstance().showMsg("Coming soon...");
    }

    public void handleChangeThemes(){
       this.cbthemes.getSelectionModel().getSelectedItem().updateTheme(this.cbthemes.getScene());
    }
    
}

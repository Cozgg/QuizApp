/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhc.quizapp;

import com.nhc.pojo.Question;
import com.nhc.services.BaseServices;
import com.nhc.services.questions.LimitQuestionServiceDecorator;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class PracticeController implements Initializable {

    @FXML
    private TextField txtNum;
    @FXML
    private Text txtContent;
    @FXML
    private VBox vboxChoices;
    @FXML 
    private Text txtResult;
    
    private List<Question> questions;
    private int currentQuestion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void handleStart(ActionEvent event) throws SQLException{
        int num = Integer.parseInt(this.txtNum.getText());
        
        BaseServices lQuestions = new LimitQuestionServiceDecorator(num);
        this.questions = lQuestions.list();
        
        currentQuestion = 0;
        loadQuestion();
    }
    
    private void loadQuestion(){
        Question q = this.questions.get(currentQuestion);
        this.txtContent.setText(q.getContent());
        
        ToggleGroup tg = new ToggleGroup();
        this.vboxChoices.getChildren().clear();
        for(var c : q.getChoices()){
            RadioButton rdo = new RadioButton(c.getContent());
            rdo.setToggleGroup(tg);
            
            vboxChoices.getChildren().add(rdo);
        }
        
    }
    
    public void handlePrev(ActionEvent event){
        if (questions != null && this.currentQuestion < questions.size() - 1 && this.currentQuestion > -1) {
            this.txtResult.setText("");
            this.currentQuestion--;
            this.loadQuestion();
        }
    }
    
    public void handleCheck(ActionEvent event){
        Question q = this.questions.get(this.currentQuestion);
        for (int i = 0; i < q.getChoices().size(); i++){
            if(q.getChoices().get(i).isIsCorrect()){
                RadioButton r = (RadioButton) this.vboxChoices.getChildren().get(i);
                
                if(r.isSelected()){
                    this.txtResult.setText("ĐÚNG RỒI");
                    this.txtResult.setStyle("-fx-fill: green");
                } else{
                    this.txtResult.setText("SAI RỒI");
                    this.txtResult.setStyle("-fx-fill: red");
                }
            }
        }
    }
    
    public void handleNext(ActionEvent event){
         if (questions != null && this.currentQuestion < questions.size() - 1) {
            this.txtResult.setText("");
            this.currentQuestion++;
            this.loadQuestion();
        }
    }
    
    

}

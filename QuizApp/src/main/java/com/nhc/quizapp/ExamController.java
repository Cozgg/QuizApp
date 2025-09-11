/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package com.nhc.quizapp;

import javafx.collections.FXCollections;
import com.nhc.services.exam.ExamType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;


/**
 * FXML Controller class
 *
 * @author admin
 */
public class ExamController implements Initializable {
   
    
    @FXML private ComboBox<ExamType> cbType;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbType.setItems(FXCollections.observableArrayList(ExamType.values()));
            
        } catch (Exception e) {
            
        } 
    }    

}

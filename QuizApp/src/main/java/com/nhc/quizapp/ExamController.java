/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhc.quizapp;

import com.nhc.pojo.Choice;
import com.nhc.pojo.Question;
import com.nhc.services.exam.ExamStrategy;
import javafx.collections.FXCollections;
import com.nhc.services.exam.ExamType;
import com.nhc.services.exam.FixedStrategy;
import com.nhc.services.exam.SpecificStrategy;
import com.nhc.utils.MyAlert;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
public class ExamController implements Initializable {

    @FXML
    private ComboBox<ExamType> cbType;
    @FXML
    private TextField txtNum;
    @FXML
    ListView<Question> lvQuestion;

    private Map<Integer, Choice> answer = new HashMap<>();
    private List<Question> questions;
    private ExamStrategy s;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.cbType.setItems(FXCollections.observableArrayList(ExamType.values()));

        this.txtNum.setVisible(false);
        this.cbType.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (this.cbType.getSelectionModel().getSelectedItem() == ExamType.SPECIFIC) {
                this.txtNum.setVisible(true);
            } else {
                this.txtNum.setVisible(false);
            }
        });

        this.lvQuestion.setCellFactory(param -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody

                if (question == null || empty == true) {
                    setGraphic(null);
                } else {
                    VBox b = new VBox(5);
                    b.setStyle("-fx-border-width:1;-fx-border-color:gray;-fx-padding:6;");

                    Text t = new Text(question.getContent());
                    b.getChildren().add(t);

                    ToggleGroup g = new ToggleGroup();

                    for (var c : question.getChoices()) {
                        RadioButton r = new RadioButton(c.getContent());
                        r.setToggleGroup(g);

                        if (answer.get(question.getId()) == c) {
                            r.setSelected(true);
                        }

                        r.setOnAction(e -> {
                            if (r.isSelected()) {
                                answer.put(question.getId(), c);
                            }
                        });

                        b.getChildren().add(r);
                    }
                    setGraphic(b);
                }
            }

        });

    }

    public void handleStart(ActionEvent e) {
        s = new FixedStrategy();
        if (this.cbType.getSelectionModel().getSelectedItem() == ExamType.SPECIFIC) {
            s = new SpecificStrategy(Integer.parseInt(this.txtNum.getText()));
        }

        try {
            this.questions = s.getQuestion();
            Collections.shuffle(questions);
            this.lvQuestion.setItems(FXCollections.observableList(questions));
        } catch (SQLException ex) {
            Logger.getLogger(ExamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void markHandle(ActionEvent e) {
        int count = 0;
        for (var c : answer.values()) {
            if (c.isIsCorrect()) {
                count++;
            }
        }
        MyAlert.getInstance().showMsg(String.format("Ban da tra loi dung %d/%d", count, this.questions.size()));
    }
    
    public void saveHandle(ActionEvent e ) throws SQLException{
        Optional<ButtonType> t = MyAlert.getInstance().showMsg("Ban chac chan luu bai thi", Alert.AlertType.CONFIRMATION);
        
        if(t.isPresent() && t.get() == ButtonType.OK){
            s.saveExam(questions);
        }
    }
}

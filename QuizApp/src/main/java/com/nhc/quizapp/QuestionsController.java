/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.nhc.quizapp;

import com.nhc.pojo.Category;
import com.nhc.pojo.Choice;
import com.nhc.pojo.Level;
import com.nhc.pojo.Question;
import com.nhc.services.BaseServices;
import com.nhc.services.CategoryServices;
import com.nhc.services.LevelServices;
import com.nhc.services.questions.CategoryQuestionServiceDecorator;
import com.nhc.services.questions.KeywordQuestionServiceDecorator;
import com.nhc.services.questions.LevelQuestionServiceDecorator;
import com.nhc.services.questions.QuestionServices;
import com.nhc.services.questions.UpdateQuestionServices;
import com.nhc.utils.MyAlert;
import com.nhc.utils.MyConfig;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuestionsController implements Initializable {

    @FXML
    private ComboBox<Category> cbCates;
    @FXML
    private ComboBox<Category> cbSearchCates;
    @FXML
    private ComboBox<Level> cbLevels;
    @FXML
    private ComboBox<Level> cbSearchLevels;
    @FXML
    private VBox vboxChoices;
    @FXML
    private TextArea txtContent;
    @FXML
    private ToggleGroup toggleChoice;
    @FXML
    private TableView<Question> tbQuestion;
    @FXML
    private TextField txtSearch;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (this.toggleChoice == null) {
            this.toggleChoice = new ToggleGroup();
        }

        try {
            this.cbCates.setItems(FXCollections.observableList(MyConfig.cateService.list()));
            this.cbSearchCates.setItems(FXCollections.observableList(MyConfig.cateService.list()));

            this.cbLevels.setItems(FXCollections.observableList(MyConfig.lvlService.list()));
            this.cbSearchLevels.setItems(FXCollections.observableList(MyConfig.lvlService.list()));

            this.loadColumns();
            this.tbQuestion.setItems(FXCollections.observableList(MyConfig.quesService.list()));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            MyAlert.getInstance().showMsg("Không thể tải dữ liệu danh mục hoặc cấp độ!", Alert.AlertType.ERROR);
        }
        
        this.txtSearch.textProperty().addListener( a -> {
            BaseServices searchService = new KeywordQuestionServiceDecorator(MyConfig.quesService, txtSearch.getText());
            try {
                this.tbQuestion.setItems(FXCollections.observableList(searchService.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        
        this.cbSearchCates.getSelectionModel().selectedItemProperty().addListener(p ->{
            BaseServices searchService = new CategoryQuestionServiceDecorator(MyConfig.quesService, this.cbSearchCates.getSelectionModel().getSelectedItem().getId());
            try {
                this.tbQuestion.setItems(FXCollections.observableList(searchService.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
        this.cbSearchLevels.getSelectionModel().selectedItemProperty().addListener(p ->{
            BaseServices searchService = new LevelQuestionServiceDecorator(MyConfig.quesService, this.cbSearchLevels.getSelectionModel().getSelectedItem().getId());
            try {
                this.tbQuestion.setItems(FXCollections.observableList(searchService.list()));
            } catch (SQLException ex) {
                Logger.getLogger(QuestionsController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });

    }

    public void addChoice(ActionEvent event) {
        HBox h = new HBox(10);
        h.getStyleClass().add("Main");

        RadioButton rdo = new RadioButton();
        rdo.setToggleGroup(toggleChoice);

        TextField txt = new TextField();
        txt.setPromptText("Nội dung lựa chọn");
        txt.setPrefWidth(300);

        h.getChildren().addAll(rdo, txt);

        this.vboxChoices.getChildren().add(h);
    }

    public void handleAddQuestion(ActionEvent event) {
        if (this.txtContent.getText().isBlank()) {
            MyAlert.getInstance().showMsg("Vui lòng nhập nội dung câu hỏi!", Alert.AlertType.WARNING);
            return;
        }

        if (this.cbCates.getSelectionModel().getSelectedItem() == null) {
            MyAlert.getInstance().showMsg("Vui lòng chọn danh mục!", Alert.AlertType.WARNING);
            return;
        }

        if (this.cbLevels.getSelectionModel().getSelectedItem() == null) {
            MyAlert.getInstance().showMsg("Vui lòng chọn cấp độ!", Alert.AlertType.WARNING);
            return;
        }

        if (this.vboxChoices.getChildren().isEmpty()) {
            MyAlert.getInstance().showMsg("Vui lòng thêm ít nhất một lựa chọn!", Alert.AlertType.WARNING);
            return;
        }

        if (this.toggleChoice.getSelectedToggle() == null) {
            MyAlert.getInstance().showMsg("Vui lòng chọn một đáp án đúng!", Alert.AlertType.WARNING);
            return;
        }

        for (Node n : this.vboxChoices.getChildren()) {
            HBox h = (HBox) n;
            TextField txt = (TextField) h.getChildren().get(1);
            if (txt.getText().isBlank()) {
                MyAlert.getInstance().showMsg("Nội dung lựa chọn không được để trống!", Alert.AlertType.WARNING);
                return;
            }
        }

        try {
            Question.Builder b = new Question.Builder(this.txtContent.getText(),
                    this.cbCates.getSelectionModel().getSelectedItem(),
                    this.cbLevels.getSelectionModel().getSelectedItem());

            for (Node c : this.vboxChoices.getChildren()) {
                HBox h = (HBox) c;
                RadioButton rdo = (RadioButton) h.getChildren().get(0);
                TextField txt = (TextField) h.getChildren().get(1);

                b.addChoice(new Choice(txt.getText(), rdo.isSelected()));
            }

            MyConfig.uQService.addQuestion(b.build());

            this.txtContent.clear();
            this.cbCates.getSelectionModel().clearSelection();
            this.cbLevels.getSelectionModel().clearSelection();
            this.vboxChoices.getChildren().clear();

            MyAlert.getInstance().showMsg("Thêm câu hỏi thành công!", Alert.AlertType.INFORMATION);

        } catch (SQLException ex) {
            MyAlert.getInstance().showMsg("Thêm câu hỏi thất bại! Lỗi SQL: " + ex.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception ex) {
            MyAlert.getInstance().showMsg("Đã có lỗi xảy ra: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void loadColumns() {
        TableColumn colId = new TableColumn("ID");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colId.setPrefWidth(100);

        TableColumn colContent = new TableColumn("Nội dung câu hỏi");
        colContent.setCellValueFactory(new PropertyValueFactory("content"));
        colContent.setPrefWidth(300);
        //xoa
//        TableColumn colAction = new TableColumn("Thao tac");
//        colAction.setCellFactory(p -> {
//            TableCell cell = new TableCell();
//            
//            Button b = new Button("Xoa");
//            b.setOnAction(event ->{
//                Optional<ButtonType> t = MyAlert.getInstance().showMsg("Xoa cau hoi thi cac lua chon cung xoa theo, ban co muon xoa", Alert.AlertType.CONFIRMATION);
//                if(t.isPresent() && t.get().equals(ButtonType.OK)){
//                    Question q = (Question) cell.getTableRow().getItem();
//                    
//                }
//            });
//            return null;
//        });

        this.tbQuestion.getColumns().clear();
        this.tbQuestion.getColumns().addAll(colId, colContent);
    }
}

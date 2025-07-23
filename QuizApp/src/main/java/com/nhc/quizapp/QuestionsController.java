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
import com.nhc.services.UpdateQuestionServices;
import com.nhc.utils.MyAlert;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    private ComboBox<Level> cbLevels;
    @FXML
    private VBox vboxChoices;
    @FXML
    private TextArea txtContent;
    @FXML
    private ToggleGroup toggleChoice;

    private final static BaseServices cateService = new CategoryServices();
    private final static BaseServices lvlService = new LevelServices();
    private final static UpdateQuestionServices uQService = new UpdateQuestionServices();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Khởi tạo ToggleGroup nếu nó chưa được khởi tạo trong FXML
        if (this.toggleChoice == null) {
            this.toggleChoice = new ToggleGroup();
        }
        
        try {
            this.cbCates.setItems(FXCollections.observableList(cateService.list()));
            this.cbLevels.setItems(FXCollections.observableList(lvlService.list()));
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            MyAlert.getInstance().showMsg("Không thể tải dữ liệu danh mục hoặc cấp độ!", Alert.AlertType.ERROR);
        }
    }

    public void addChoice(ActionEvent event) {
        HBox h = new HBox(10); // Thêm khoảng cách giữa các phần tử
        h.getStyleClass().add("Main");

        RadioButton rdo = new RadioButton();
        rdo.setToggleGroup(toggleChoice);

        TextField txt = new TextField();
        txt.setPromptText("Nội dung lựa chọn");
        txt.setPrefWidth(300); // Đặt chiều rộng mặc định

        h.getChildren().addAll(rdo, txt);

        this.vboxChoices.getChildren().add(h);
    }

    public void handleAddQuestion(ActionEvent event) {
        // --- BẮT ĐẦU KIỂM TRA DỮ LIỆU ---
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
        
        // Kiểm tra xem có lựa chọn nào bị bỏ trống không
        for (Node n : this.vboxChoices.getChildren()) {
            HBox h = (HBox) n;
            TextField txt = (TextField) h.getChildren().get(1);
            if (txt.getText().isBlank()) {
                MyAlert.getInstance().showMsg("Nội dung lựa chọn không được để trống!", Alert.AlertType.WARNING);
                return;
            }
        }
        // --- KẾT THÚC KIỂM TRA DỮ LIỆU ---

        try {
            // Dữ liệu đã hợp lệ, tiến hành tạo câu hỏi
            Question.Builder b = new Question.Builder(this.txtContent.getText(),
                    this.cbCates.getSelectionModel().getSelectedItem(),
                    this.cbLevels.getSelectionModel().getSelectedItem());

            for (Node c : this.vboxChoices.getChildren()) {
                HBox h = (HBox) c;
                RadioButton rdo = (RadioButton) h.getChildren().get(0);
                TextField txt = (TextField) h.getChildren().get(1);
                
                b.addChoice(new Choice(txt.getText(), rdo.isSelected()));
            }

            uQService.addQuestion(b.build());
            
            // Xóa dữ liệu trên form sau khi thêm thành công
            this.txtContent.clear();
            this.cbCates.getSelectionModel().clearSelection();
            this.cbLevels.getSelectionModel().clearSelection();
            this.vboxChoices.getChildren().clear();

            MyAlert.getInstance().showMsg("Thêm câu hỏi thành công!", Alert.AlertType.INFORMATION);

        } catch (SQLException ex) {
            // Cung cấp thông báo lỗi chi tiết hơn
            MyAlert.getInstance().showMsg("Thêm câu hỏi thất bại! Lỗi SQL: " + ex.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception ex) {
            // Bắt các lỗi không mong muốn khác
            MyAlert.getInstance().showMsg("Đã có lỗi xảy ra: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
}

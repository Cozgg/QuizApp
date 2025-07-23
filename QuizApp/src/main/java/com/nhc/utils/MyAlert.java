/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nhc.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author admin
 */
public class MyAlert {
    private static MyAlert INSTANCE;
    private final Alert alert;

    private MyAlert() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("quiz app");
        alert.setHeaderText("quiz app");
    }
    
    public static MyAlert getInstance(){
        if(INSTANCE == null)
            INSTANCE = new MyAlert();
        return INSTANCE;
    }
    
    public void showMsg(String content){
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void showMsg(String content, Alert.AlertType type){
        alert.setContentText(content);
        alert.setAlertType(type);
        alert.showAndWait();
    }
}
package com.example.finall1.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController
{

    @FXML
    Circle avt,avt2,avt3;
    @FXML
    AnchorPane messageArea;
    @FXML
    TextField messageField;
    @FXML
    JFXButton btnSendMsg;

    @FXML
    GridPane messageContainer;

    public void sendMessage() {
        String msg = messageField.getText();
        System.out.println(msg);
        messageField.setText("");
        if (!msg.replace(" ","").isEmpty()) {
            try {
                // Load file FXML chứa MessageLabel
                URL url = getClass().getResource("/com/example/finall1/message.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                VBox messagePane = loader.load();
                MessageLabelController controller = loader.getController();

                // Thiết lập đoạn văn bản cho MessageLabel
                controller.setMessage(msg);

                // Thêm MessageLabel vào messageContainer
                messageContainer.add(messagePane,1,0);

                // Cuộn đến phần tử cuối cùng của messageContainer

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    // Khởi tạo UI
    private void initUI(){
        //init avt circle
        Image im = new Image("C:\\Users\\Tu Dep Trai\\IdeaProjects\\finall1\\src\\main\\resources\\com\\example\\finall1\\image\\avt.jpg",false);
        avt.setFill(new ImagePattern(im));
        avt.smoothProperty();
        Image im1 = new Image("C:\\Users\\Tu Dep Trai\\IdeaProjects\\finall1\\src\\main\\resources\\com\\example\\finall1\\image\\avt.jpg",false);
        avt2.setFill(new ImagePattern(im1));
        avt2.smoothProperty();
        avt3.setFill(new ImagePattern(im1));
        avt3.smoothProperty();
        if (messageField != null) {
        btnSendMsg.setOnAction(event -> {
            sendMessage();
        });
        messageField.setOnKeyPressed(event -> {
                if (event.getCode() ==  KeyCode.ENTER) {
                    sendMessage();
                }
            });
        }


    }
    public void initialize(){
        initUI();
    }



}

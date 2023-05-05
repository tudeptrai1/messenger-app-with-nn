package com.example.finall1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.VBox;

public class MessageLabelController {
    @FXML
    private Label message;
    @FXML
    private VBox messageContainer;

    public void initialize() {
        double labelWidth = message.getLayoutBounds().getWidth();
        double vboxWidth = labelWidth > 200 ? 200 : labelWidth;
        messageContainer.setPrefWidth(vboxWidth);
    }
    public void setMessage(String a) {
        message.setText(a);
    }
}
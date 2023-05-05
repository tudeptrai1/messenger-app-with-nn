package com.example.finall1.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML private JFXTextField txtUsername;
    @FXML private JFXPasswordField txtPassword;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        if(txtUsername.getText().equals("admin")&&txtPassword.getText().equals("12345")) {
            Stage stage = new Stage();
            URL url = new File("C:\\Users\\Tu Dep Trai\\IdeaProjects\\finall1\\src\\main\\resources\\com\\example\\finall1\\MainView.fxml").toURI().toURL();
            URL urlcss = new File("C:\\Users\\Tu Dep Trai\\IdeaProjects\\finall1\\src\\main\\resources\\com\\example\\finall1\\css\\style.css").toURI().toURL();

            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(urlcss.toExternalForm());
            stage.show();
            ((Node)mouseEvent.getSource()).getScene().getWindow().hide();
        }
    }
}

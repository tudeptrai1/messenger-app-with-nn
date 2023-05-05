package com.example.finall1.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = new File("C:\\Users\\Tu Dep Trai\\IdeaProjects\\finall1\\src\\main\\resources\\com\\example\\finall1\\hello-view.fxml").toURI().toURL();
        URL urlcss = new File("C:\\Users\\Tu Dep Trai\\IdeaProjects\\finall1\\src\\main\\resources\\com\\example\\finall1\\css\\Main.css").toURI().toURL();

        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(urlcss.toExternalForm());
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}
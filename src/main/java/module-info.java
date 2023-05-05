module com.example.finall1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires java.sql;
    requires gson;


    opens com.example.finall1 to javafx.fxml;

    exports com.example.finall1.system;
    opens com.example.finall1.system to javafx.fxml;
    exports com.example.finall1.controller;
    opens com.example.finall1.controller to javafx.fxml;

}
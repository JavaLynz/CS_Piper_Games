package com.example.gruppcadettsplitterpipergames.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class LoginPage extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        Scene loginScene = new Scene(root, 500,350);
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);
        AnchorPane.setLeftAnchor(container,30.0);
        AnchorPane.setRightAnchor(container,30.0);
        AnchorPane.setTopAnchor(container,30.0);
        AnchorPane.setBottomAnchor(container,30.0);
        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(-40);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);



        HBox loginUI = new HBox(20);
        loginUI.setAlignment(Pos.TOP_CENTER);
        ComboBox loginCombo = new ComboBox<>();
        Button loginBtn = new Button("Login");




        container.setStyle("-fx-background-color: silver; -fx-background-radius: 5");


        root.getChildren().addAll(container);
        container.getChildren().addAll(logo, loginUI);
        loginUI.getChildren().addAll(loginCombo, loginBtn);

        stage.setScene(loginScene);

    }
}

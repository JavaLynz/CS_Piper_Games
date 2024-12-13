package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.StaffDAO;
import com.example.gruppcadettsplitterpipergames.entities.Staff;
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
import java.util.HashMap;

public class LoginPage extends Application {
    StaffDAO staffDAO = new StaffDAO();

    @Override
    public void start(Stage stage) throws Exception {
        //Components and styling for basic layout
        AnchorPane root = new AnchorPane();
        stage.setResizable(false);
        Scene loginScene = new Scene(root, 600,350);
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: silver; -fx-background-radius: 5; -fx-font-size: 18;");
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


        //UI to select a staff member from dropdown box and log in with their ID
        HBox loginUI = new HBox(20);
        loginUI.setAlignment(Pos.TOP_CENTER);
        ComboBox loginDropdown = new ComboBox<>();
        loginDropdown.setOnMouseClicked(mouseEvent -> loginDropdown.setStyle(""));
        loginDropdown.setMinWidth(300);
        HashMap<String, Staff> staffHashMap = new HashMap<>();
        for (Staff staff: staffDAO.getAllStaff()){
            String fullName = staff.getFirstName() + " \""+staff.getNickName()+"\" "+staff.getLastName();
            staffHashMap.put(fullName,staff);
            loginDropdown.getItems().add(fullName);
        }

        Button loginBtn = new Button("Login");
        loginBtn.setOnMouseClicked(mouseEvent -> {
            try {
                Staff chosenStaff = staffHashMap.get((String) loginDropdown.getValue());
                System.out.println("StaffID: " + chosenStaff.getStaffId()+ " logged into system.");
                //TODO: Add link to next page in application.
            } catch (Exception e) {
                loginDropdown.setStyle("-fx-background-color: indianred");
                System.out.println("Invalid login attempt.");
            }
        });


        root.getChildren().addAll(container);
        container.getChildren().addAll(logo, loginUI);
        loginUI.getChildren().addAll(loginDropdown, loginBtn);
        stage.setScene(loginScene);
    }
}

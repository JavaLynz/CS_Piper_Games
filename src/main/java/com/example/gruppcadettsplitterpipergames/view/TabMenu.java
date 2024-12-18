package com.example.gruppcadettsplitterpipergames.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class TabMenu{
    private TabPane root = new TabPane();
    private StaffFX staffFX = new StaffFX(this.root);


    public Scene tabMenuScene(Stage stage){
        stage.setResizable(true);
        Tab staffTab = new Tab("Staff", staffFX.getStaffTab());

        this.root.getTabs().addAll(staffTab);
        this.root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            createLogoutPrompt(stage);
        });


        Tab logoutTab = new Tab();
        logoutTab.setGraphic(logoutBtn);
        logoutTab.setStyle("-fx-padding: 0; right:0");
        this.root.getTabs().add(logoutTab);

        Scene scene = new Scene(this.root);
        return scene;
    }

    public void createLogoutPrompt(Stage mainStage){
        LoginPage login = new LoginPage();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        VBox container = new VBox(10);
        container.setPadding(new Insets(5));

        Text alert = new Text("Are you sure you want to log out?");

        HBox btnContainer = new HBox(20);
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnMouseClicked(mouseEvent -> {
            try {
                mainStage.setScene(login.getLoginScene(mainStage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            stage.close();
        });
        Button closeBtn = new Button("Back to App");
        closeBtn.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        btnContainer.getChildren().addAll(confirmBtn,closeBtn);
        container.getChildren().addAll(alert, btnContainer);
        root.getChildren().add(container);
        stage.setScene(new Scene(root));
        stage.show();
    }
}

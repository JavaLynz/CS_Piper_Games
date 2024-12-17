package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.HelloApplication;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class TabMenu{
    private TabPane root = new TabPane();
    private StaffFX staffFX = new StaffFX(this.root);


    public Scene tabMenuScene(Stage stage){
        stage.setResizable(true);
        this.root.getTabs().add(staffFX.createStaffTab(stage));
        this.root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        LoginPage login = new LoginPage();
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            try {
                stage.setScene(login.getLoginScene(stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        Tab logoutTab = new Tab();
        logoutTab.setGraphic(logoutBtn);
        logoutTab.setStyle("-fx-padding: 0; right:0");
        this.root.getTabs().add(logoutTab);

        Scene scene = new Scene(this.root);
        return scene;
    }
}

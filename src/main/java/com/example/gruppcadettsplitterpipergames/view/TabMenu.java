package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.HelloApplication;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class TabMenu{
    private TabPane root = new TabPane();
    private StaffFX staffFX = new StaffFX(this.root);


    public Scene tabMenuScene(Stage stage, Scene firstScene){
        this.root.getTabs().add(staffFX.createStaffTab());
        this.root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);


        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            stage.setScene(firstScene);
        });


        Tab logoutTab = new Tab();
        logoutTab.setGraphic(logoutBtn);
        logoutTab.setStyle("-fx-padding: 0; right:0");
        this.root.getTabs().add(logoutTab);

        Scene scene = new Scene(this.root);
        return scene;
    }
}

package com.example.gruppcadettsplitterpipergames.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.io.FileNotFoundException;


public class TabMenu{
    private TabPane root = new TabPane();
    private HashMap<Integer,String> currentUser;
    private StaffFX staffFX = new StaffFX(this.root);



    public Scene tabMenuScene(Stage stage) throws FileNotFoundException{
        stage.setResizable(true);
        staffFX.setCurrentUser(this.currentUser);

        Tab staffTab = new Tab("Staff", staffFX.getStaffTab());
        staffTab.setClosable(false);
        Tab teamTab = new Tab("Team", new TeamFX().getView());
        teamTab.setClosable(false);
        Tab teamMatchesTab = new Tab("TeamMatches");
        teamMatchesTab.setClosable(false);
        Tab playerMatchesTab = new Tab("PlayerMatches");
        playerMatchesTab.setClosable(false);
        Tab gamesTab = new Tab("Games", new GameFX().getGamesView());
        gamesTab.setClosable(false);
        Tab playerTab = new Tab("Player", new PlayerFX().getPlayerView());
        playerTab.setClosable(false);

        this.root.getTabs().addAll(staffTab, playerTab, teamTab, gamesTab, playerMatchesTab, teamMatchesTab);
        this.root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            createLogoutPrompt(stage);
        });


        Tab logoutTab = new Tab();
        logoutTab.setGraphic(logoutBtn);
        logoutTab.setStyle("-fx-padding: 0; right:0");
        this.root.getTabs().add(logoutTab);

        return new Scene(this.root);
    }

    public void createLogoutPrompt(Stage mainStage){
        LoginPage login = new LoginPage();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        VBox container = new VBox(10);
        AnchorPane.setTopAnchor(container,5.0);
        AnchorPane.setBottomAnchor(container,5.0);
        AnchorPane.setLeftAnchor(container,5.0);
        AnchorPane.setRightAnchor(container,5.0);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.TOP_CENTER);

        Text alert = new Text("Are you sure you want to log out?");

        HBox btnContainer = new HBox(20);
        btnContainer.setAlignment(Pos.CENTER);
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

        container.setStyle("-fx-background-color:silver; -fx-background-radius:5;");
        btnContainer.getChildren().addAll(confirmBtn,closeBtn);
        container.getChildren().addAll(alert, btnContainer);
        root.getChildren().add(container);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public HashMap<Integer, String> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(HashMap<Integer, String> currentUser) {
        this.currentUser = currentUser;
    }
}

package com.example.gruppcadettsplitterpipergames.view;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class TabMenu {
    private TabPane root = new TabPane();

    public Scene getTabMenuScene(Stage stage) throws FileNotFoundException {


        Tab staffTab = new Tab("Staff");
        Tab playerTab;
        Tab teamTab = new Tab("Team");
        Tab teamMatchesTab = new Tab("TeamMatches");
        Tab playerMatchesTab = new Tab("PlayerMatches");
        Tab gamesTab = new Tab("Games", new GameFX().getGamesView());
        playerTab = new Tab("Player", new PlayerFX().getPlayerView());

        root.getTabs().addAll(staffTab, playerTab, teamTab, gamesTab, playerMatchesTab, teamMatchesTab);
        root.setTabMaxWidth(600);

        return new Scene(root, 800, 600);


    }
}

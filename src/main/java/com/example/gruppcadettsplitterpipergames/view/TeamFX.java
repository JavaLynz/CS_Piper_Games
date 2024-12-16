package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.*;

public class TeamFX {

    private BorderPane view;
    private TeamsDAO teamsDAO;
    private ObservableList<Team> teamList;


    public TeamFX() {
        teamsDAO = new TeamsDAO();
        view = new BorderPane();
        teamList = FXCollections.observableArrayList();

        HBox Hbuttons = new HBox();
        VBox buttons = new VBox(20);
        HBox labels = new HBox(500);

        Button btnAdd = new Button("Add new team");
        btnAdd.setPrefSize(200, 50);
        Button btnUpdate = new Button("Update team");
        btnUpdate.setPrefSize(200, 50);
        Button btnDelete = new Button("Delete Team");
        btnDelete.setPrefSize(200, 50);
        Button btnRead = new Button("Search");
        btnRead.setPrefSize(200, 50);
        Hbuttons.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnRead);
        view.setBottom(Hbuttons);
//        Hbuttons.setAlignment(Pos.BOTTOM_CENTER);
        //Hbuttons.setAlignment(Pos.CENTER_RIGHT);
        Hbuttons.setPadding(new Insets(0, 0, 0, 0));

        view.setLeft(labels);
        labels.setAlignment(Pos.TOP_LEFT);

        TableView<Team> teamTable = createTableView();
        teamTable.setPrefWidth(800);
        teamTable.setPrefHeight(350);
        labels.setFillHeight(true);
        labels.getChildren().add(teamTable);

        loadTeamsFromDatabase(teamTable);

    }

    private TableView<Team> createTableView() {
        TableView<Team> teamTable = new TableView<>();

        // Lag-kolumn
        TableColumn<Team, String> nameCol = new TableColumn<>("Team Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(200);

        // Spel-kolumn
        TableColumn<Team, String> gameCol = new TableColumn<>("Game");
        gameCol.setCellValueFactory(cellData -> {
            Game game = cellData.getValue().getGame();
            return new SimpleStringProperty(game != null ? game.getGameName() : "No game");
        });
        gameCol.setPrefWidth(200);

        teamTable.getColumns().addAll(nameCol, gameCol);
        return teamTable;
    }

    private void loadTeamsFromDatabase(TableView<Team> teamTable) {
        teamList.setAll(teamsDAO.getAllTeams());
        teamTable.setItems(teamList);
    }


    public BorderPane getView() {
        return view;
    }

    public void setView(BorderPane view) {
        this.view = view;
    }
}

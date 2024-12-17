package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.*;

public class TeamFX {

    private BorderPane view;
    private TeamsDAO teamsDAO;
    private GamesDAO gameDAO;
    private ObservableList<Team> teamList;


    public TeamFX() {
        gameDAO = new GamesDAO();
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

        btnAdd.setOnAction(event -> {
            System.out.println("Add new team");

            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Add new team");
            dialog.setHeaderText("Enter team details");

            TextField teamNameField = new TextField();
            teamNameField.setPromptText("Enter team name");

            ComboBox<Game> gameCombo = new ComboBox<>();
            gameCombo.setPromptText("Select a game");
            gameCombo.getItems().addAll(gameDAO.getAllGames());

            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);
//            gridPane.setPadding(new Insets(20, 50, 10, 10));

            gridPane.add(new Label("Team Name"), 0, 0);
            gridPane.add(teamNameField, 1, 0);
            gridPane.add(new Label("Game Name"), 0, 1);
            gridPane.add(gameCombo, 1, 1);

            dialog.getDialogPane().setContent(gridPane);

            Button buttonAdd = new Button("Add team");
            Button buttonCancel = new Button("Cancel");

            HBox buttonBox = new HBox(10, buttonAdd, buttonCancel);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            gridPane.add(buttonBox, 1, 2);

            buttonAdd.setOnAction(e -> {
                String teamName = teamNameField.getText();
                Game selectedGame = gameCombo.getValue();

                if (teamName.isEmpty() || selectedGame == null) {
                    System.out.println("Invalid");
                } else {
                    Team newTeam = new Team(teamName, selectedGame);
                    teamsDAO.saveTeam(newTeam);
                    teamTable.getItems().add(newTeam);
                    dialog.close();
                }

            });

            buttonCancel.setOnAction(e -> {
                System.out.println("cancel");
                dialog.setResult(Boolean.TRUE);
                dialog.close();

            });

            dialog.showAndWait();


        });

        btnRead.setOnAction(event -> {
            System.out.println("Read team");
        });

        btnDelete.setOnAction(event -> {
            System.out.println("Delete team");

            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Delete team");
            dialog.setHeaderText("Choose team to delete");

            ComboBox<Team> teamCombo = new ComboBox<>();
            teamCombo.setPromptText("Select a team to delete");
            teamCombo.getItems().addAll(teamsDAO.getAllTeams());

            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);
            gridPane.add(teamCombo, 1, 0);

            Button btnDeleteTeam = new Button("Delete team");
            Button btnCancelDeleteTeam = new Button("Cancel");

            dialog.getDialogPane().setContent(gridPane);
            gridPane.add(btnDeleteTeam, 1, 1);
            gridPane.add(btnCancelDeleteTeam, 2, 1);



            btnCancelDeleteTeam.setOnAction(e -> {
                System.out.println("Cancel");
                dialog.setResult(Boolean.TRUE);
                dialog.close();
            });

            btnDeleteTeam.setOnAction(e -> {
                Team teamToDelete = teamCombo.getValue();
                if (teamToDelete != null) {
                    teamsDAO.deleteTeam(teamToDelete);
                    teamTable.getItems().removeIf(t -> t.getId() == teamToDelete.getId());
                    teamTable.refresh();
                    System.out.println("Team " + teamToDelete + " was deleted!");
//                    dialog.setResult(Boolean.TRUE);
                    dialog.close();
                } else {
                    System.out.println("No team selected");
                }
            });



            dialog.showAndWait();


        });

        btnUpdate.setOnAction(event -> {
            System.out.println("Update team");
        });

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

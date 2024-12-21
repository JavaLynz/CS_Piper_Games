package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.TeamMatchesDAO;
import com.example.gruppcadettsplitterpipergames.entities.TeamMatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.List;

public class TeamMatchesFX {

    private Tab teamMatchesTab;
    private TableView<TeamMatches> tableView;
    private ObservableList<TeamMatches> teamMatchesList;

    private TeamMatchesDAO teamMatchesDAO;

    public TeamMatchesFX() {
        this.teamMatchesDAO = new TeamMatchesDAO(); // DAO instance to manage database operations
        initializeUI();
    }

    private void initializeUI() {
        teamMatchesTab = new Tab("Team Matches");
        BorderPane root = new BorderPane();

        // TableView setup
        tableView = new TableView<>();

        TableColumn<TeamMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new ReadOnlyObjectWrapper<>(data.getValue().getMatchId()));

        TableColumn<TeamMatches, String> team1Column = new TableColumn<>("Team 1");
        team1Column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTeam1Name()));

        TableColumn<TeamMatches, String> team2Column = new TableColumn<>("Team 2");
        team2Column.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTeam2Name()));

        TableColumn<TeamMatches, String> winnerColumn = new TableColumn<>("Winner");
        winnerColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getWinner()));

        TableColumn<TeamMatches, String> loserColumn = new TableColumn<>("Loser");
        loserColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getLoser()));

        TableColumn<TeamMatches, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getMatchDate()));

        tableView.getColumns().addAll(idColumn, team1Column, team2Column, winnerColumn, loserColumn, dateColumn);

        // Load initial data
        teamMatchesList = FXCollections.observableArrayList();
        loadMatches();
        tableView.setItems(teamMatchesList);

        Label placeholder = new Label("No matches available.");
        tableView.setPlaceholder(placeholder);

        root.setCenter(tableView);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Button addButton = new Button("Add Match");
        Button updateButton = new Button("Update Match");
        Button deleteButton = new Button("Delete Match");

        addButton.setOnAction(e -> addMatch());
        updateButton.setOnAction(e -> updateMatch());
        deleteButton.setOnAction(e -> deleteMatch());

        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);
        root.setBottom(buttonBox);

        teamMatchesTab.setContent(root);
    }

    private void loadMatches() {
        List<TeamMatches> matches = teamMatchesDAO.showMatches();
        teamMatchesList.setAll(matches);
    }

    private void addMatch() {
        // Logic to add a match (e.g., open a dialog to input details)
        TeamMatches newMatch = new TeamMatches("Team A", "Team B", "Team A", "Team B", "2024-12-18");
        boolean success = teamMatchesDAO.addMatch(newMatch);
        if (success) {
            teamMatchesList.add(newMatch);
        }
    }

    private void updateMatch() {
        TeamMatches selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            // Logic to update the selected match (e.g., open a dialog to edit details)
            selectedMatch.setWinner("Updated Winner");
            selectedMatch.setLoser("Updated Loser");
            teamMatchesDAO.updateMatch(selectedMatch);
            tableView.refresh();
        }
    }

    private void deleteMatch() {
        TeamMatches selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            boolean success = teamMatchesDAO.removeMatchById(selectedMatch.getMatchId());
            if (success) {
                teamMatchesList.remove(selectedMatch);
            }
        }
    }

    public Tab getTeamMatchesTab() {
        return teamMatchesTab;
    }
}
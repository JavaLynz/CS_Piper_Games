package com.example.gruppcadettsplitterpipergames.view;


import com.example.gruppcadettsplitterpipergames.DAO.TeamMatchesDAO;
import com.example.gruppcadettsplitterpipergames.entities.TeamMatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TeamMatchesFX {

    private Tab teamMatchesTab;
    private TableView<TeamMatches> tableView;
    private ObservableList<TeamMatches> teamMatchesList;
    private TeamMatchesDAO teamMatchesDAO;

    public TeamMatchesFX() {
        teamMatchesDAO = new TeamMatchesDAO();
        initializeUI();
        loadTeamMatches();
    }

    private void initializeUI() {
        tableView = new TableView<>();
        TableColumn<TeamMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().teamMatchIdProperty().asObject());

        TableColumn<TeamMatches, String> teamColumn = new TableColumn<>("Team");
        teamColumn.setCellValueFactory(data -> data.getValue().teamNameProperty());

        TableColumn<TeamMatches, String> matchColumn = new TableColumn<>("Match");
        matchColumn.setCellValueFactory(data -> data.getValue().matchNameProperty());

        tableView.getColumns().addAll(idColumn, teamColumn, matchColumn);

        Button addButton = new Button("Add new match");
        Button updateButton = new Button("Update match");
        Button deleteButton = new Button("Delete match");
        Button searchButton = new Button("Search match");

        addButton.setOnAction(event -> addTeamMatch());
        updateButton.setOnAction(event -> updateTeamMatch());
        deleteButton.setOnAction(event -> deleteTeamMatch());
        searchButton.setOnAction(event -> searchTeamMatch());

        HBox buttons = new HBox(10, addButton, updateButton, deleteButton, searchButton);
        buttons.setStyle("-fx-padding: 10;");

        BorderPane layout = new BorderPane();
        layout.setCenter(tableView);
        layout.setBottom(buttons);

        teamMatchesTab = new Tab("Team Matches", layout);
    }

    public Tab getTeamMatchesTab() {
        return teamMatchesTab;
    }

    private void loadTeamMatches() {
        teamMatchesList = FXCollections.observableArrayList(teamMatchesDAO.getAllTeamMatches());
        tableView.setItems(teamMatchesList);
    }

    private void addTeamMatch() {
        // Skapa ruta för att fylla i data
    }

    private void updateTeamMatch() {
        TeamMatches selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Skriv en uppdateringsruta
        } else {
            showAlert("Varning!", "No valid choice.");
        }
    }

    private void deleteTeamMatch() {
        TeamMatches selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            teamMatchesDAO.deleteTeamMatch(selected.getId());
            teamMatchesList.remove(selected);
        } else {
            showAlert("No selection", "Please select a match to delete.");
        }
    }

    private void searchTeamMatch() {
        // Sökmekanik här
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
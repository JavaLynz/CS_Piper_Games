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
        // TableView setup
        tableView = new TableView<>();
        TableColumn<TeamMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().teamMatchIdProperty().asObject());

        TableColumn<TeamMatches, String> teamColumn = new TableColumn<>("Team");
        teamColumn.setCellValueFactory(data -> data.getValue().teamNameProperty());

        TableColumn<TeamMatches, String> matchColumn = new TableColumn<>("Match");
        matchColumn.setCellValueFactory(data -> data.getValue().matchNameProperty());

        tableView.getColumns().addAll(idColumn, teamColumn, matchColumn);

        // Buttons setup
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button searchButton = new Button("Search");

        // Button actions
        addButton.setOnAction(event -> addTeamMatch());
        updateButton.setOnAction(event -> updateTeamMatch());
        deleteButton.setOnAction(event -> deleteTeamMatch());
        searchButton.setOnAction(event -> searchTeamMatch());

        // Layout
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
        // Load data from the database using DAO
        teamMatchesList = FXCollections.observableArrayList(teamMatchesDAO.getAllTeamMatches());
        tableView.setItems(teamMatchesList);
    }

    private void addTeamMatch() {
        // Example: Open a dialog to input data
        System.out.println("Add Team Match: Open input dialog here");
    }

    private void updateTeamMatch() {
        TeamMatches selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Update Team Match: " + selected);
        } else {
            showAlert("No selection", "Please select a match to update.");
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
        System.out.println("Search Team Match: Implement search logic here.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
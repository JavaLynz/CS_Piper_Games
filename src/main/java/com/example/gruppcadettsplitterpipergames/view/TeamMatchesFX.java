package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.TeamMatchesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import com.example.gruppcadettsplitterpipergames.entities.TeamMatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TeamMatchesFX {

    private Tab teamMatchesTab;
    private TableView<TeamMatches> tableView;
    private TeamMatchesDAO teamMatchesDAO;
    private TeamsDAO teamDAO;
    private ObservableList<TeamMatches> teamMatchesList;

    public TeamMatchesFX() {
        // Updated DAO calls to the new naming style:
        teamMatchesDAO = new TeamMatchesDAO();
        teamDAO = new TeamsDAO();
        // Replaces 'showTeamMatches()' with 'getAllTeamMatches()'
        teamMatchesList = FXCollections.observableArrayList(teamMatchesDAO.getAllTeamMatches());
        initializeUI();
    }

    private void initializeUI() {
        teamMatchesTab = new Tab("Team Matches");

        // TableView Setup
        tableView = new TableView<>();
        tableView.setItems(teamMatchesList);

        // Updated reference to match the entity's field/method: getMatchId()
        TableColumn<TeamMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMatchId()));

        // If you do not have getMatchName() in the entity,
        // either remove this column or create a computed value (e.g. team1 + " vs " + team2).
        TableColumn<TeamMatches, String> matchInfoColumn = new TableColumn<>("Match Info");
        matchInfoColumn.setCellValueFactory(data -> {
            TeamMatches tm = data.getValue();
            // Example: combine team names if you want a 'match name' style
            String displayName = tm.getTeam1Name() + " vs " + tm.getTeam2Name();
            return new javafx.beans.property.SimpleStringProperty(displayName);
        });

        tableView.getColumns().addAll(idColumn, matchInfoColumn);

        Label emptyTableLabel = new Label("No team matches available.");
        tableView.setPlaceholder(emptyTableLabel);

        HBox buttonBox = new HBox(10);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: center;");

        Button addButton = new Button("Add Match");
        Button updateButton = new Button("Update Match");
        Button deleteButton = new Button("Delete Match");
        Button refreshButton = new Button("Refresh");

        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, refreshButton);

        // Updated to reference new naming for DAO calls
        addButton.setOnAction(e -> addMatch());
        updateButton.setOnAction(e -> updateMatch());
        deleteButton.setOnAction(e -> deleteMatch());
        refreshButton.setOnAction(e -> refreshMatches());

        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setBottom(buttonBox);

        teamMatchesTab.setContent(root);
    }

    private void showMatchForm(TeamMatches existingMatch, boolean isUpdate) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(isUpdate ? "Update Match" : "Add New Match");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Label team1Label = new Label("Team 1 Name:");
        TextField team1Field = new TextField();
        Label team2Label = new Label("Team 2 Name:");
        TextField team2Field = new TextField();

        Label resultLabel = new Label("Result:");
        TextField resultField = new TextField();

        Label matchDateLabel = new Label("Match Date:");
        TextField matchDateField = new TextField();

        // If updating an existing match, populate the fields
        if (isUpdate && existingMatch != null) {
            team1Field.setText(existingMatch.getTeam1Name());
            team2Field.setText(existingMatch.getTeam2Name());
            resultField.setText(existingMatch.getResult());
            matchDateField.setText(existingMatch.getMatchDate());
        }

        // Optional: handle 'Game' selection if relevant (similar to your PlayerMatchesFX approach)
        // or a list of teams if your entity truly has a collection of Team objects.

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(team1Label, 0, 0);
        grid.add(team1Field, 1, 0);
        grid.add(team2Label, 0, 1);
        grid.add(team2Field, 1, 1);
        grid.add(resultLabel, 0, 2);
        grid.add(resultField, 1, 2);
        grid.add(matchDateLabel, 0, 3);
        grid.add(matchDateField, 1, 3);

        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");

        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox dialogLayout = new VBox(10, grid, buttonBox);
        dialogLayout.setPadding(new Insets(20));
        Scene dialogScene = new Scene(dialogLayout, 400, 250);
        dialogStage.setScene(dialogScene);

        okButton.setOnAction(e -> {
            if (!team1Field.getText().isEmpty()
                    && !team2Field.getText().isEmpty()
                    && !matchDateField.getText().isEmpty()) {

                TeamMatches match = (existingMatch == null) ? new TeamMatches() : existingMatch;
                match.setTeam1Name(team1Field.getText());
                match.setTeam2Name(team2Field.getText());
                match.setResult(resultField.getText());
                match.setMatchDate(matchDateField.getText());

                if (isUpdate) {
                    // Updated: teamMatchesDAO.updateTeamMatch(...)
                    teamMatchesDAO.updateTeamMatch(match);
                } else {
                    // Updated: teamMatchesDAO.saveTeamMatch(...)
                    teamMatchesDAO.saveTeamMatch(match);
                }
                refreshMatches();
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Fields with * are required!", ButtonType.OK);
                alert.showAndWait();
            }
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        dialogStage.showAndWait();
    }

    private void addMatch() {
        showMatchForm(null, false);
    }

    private void updateMatch() {
        TeamMatches selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            showMatchForm(selectedMatch, true);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to update!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void deleteMatch() {
        TeamMatches selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            // Updated: teamMatchesDAO.deleteTeamMatch(...)
            teamMatchesDAO.deleteTeamMatch(selectedMatch);
            refreshMatches();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to delete!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void refreshMatches() {
        // Updated: getAllTeamMatches() instead of showTeamMatches()
        teamMatchesList.setAll(teamMatchesDAO.getAllTeamMatches());
        tableView.refresh();
    }

    public Tab getTeamMatchesTab() {
        return teamMatchesTab;
    }
}
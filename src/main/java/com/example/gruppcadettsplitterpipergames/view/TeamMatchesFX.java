//CF
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

        teamMatchesDAO = new TeamMatchesDAO();
        teamDAO = new TeamsDAO();

        teamMatchesList = FXCollections.observableArrayList(teamMatchesDAO.getAllTeamMatches());
        initializeUI();
    }

    private void initializeUI() {
        teamMatchesTab = new Tab("Team Matches");


        tableView = new TableView<>();
        tableView.setItems(teamMatchesList);

        TableColumn<TeamMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMatchId()));

        TableColumn<TeamMatches, String> matchInfoColumn = new TableColumn<>("Match Info");
        matchInfoColumn.setCellValueFactory(data -> {
            TeamMatches tm = data.getValue();

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

        Label team1Label = new Label("Team 1:");
        ComboBox<String> team1Dropdown = new ComboBox<>();
        team1Dropdown.setItems(FXCollections.observableArrayList(teamDAO.getAllTeams().stream().map(Team::getName).toList()));

        Label team2Label = new Label("Team 2:");
        ComboBox<String> team2Dropdown = new ComboBox<>();
        team2Dropdown.setItems(FXCollections.observableArrayList(teamDAO.getAllTeams().stream().map(Team::getName).toList()));

        Label resultLabel = new Label("Result:");
        TextField resultField = new TextField();

        Label matchDateLabel = new Label("Match Date (YYYY-MM-DD):");
        TextField matchDateField = new TextField();

        if (isUpdate && existingMatch != null) {
            team1Dropdown.setValue(existingMatch.getTeam1Name());
            team2Dropdown.setValue(existingMatch.getTeam2Name());
            resultField.setText(existingMatch.getResult());
            matchDateField.setText(existingMatch.getMatchDate());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(team1Label, 0, 0);
        grid.add(team1Dropdown, 1, 0);
        grid.add(team2Label, 0, 1);
        grid.add(team2Dropdown, 1, 1);
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
        Scene dialogScene = new Scene(dialogLayout, 400, 300);
        dialogStage.setScene(dialogScene);

        okButton.setOnAction(e -> {
            if (team1Dropdown.getValue() != null
                    && team2Dropdown.getValue() != null
                    && !resultField.getText().isEmpty()
                    && !matchDateField.getText().isEmpty()) {

                TeamMatches match = (existingMatch == null) ? new TeamMatches() : existingMatch;
                match.setTeam1Name(team1Dropdown.getValue());
                match.setTeam2Name(team2Dropdown.getValue());
                match.setResult(resultField.getText());
                match.setMatchDate(matchDateField.getText());

                if (isUpdate) {
                    teamMatchesDAO.updateTeamMatch(match);
                } else {
                    teamMatchesDAO.saveTeamMatch(match);
                }
                refreshMatches();
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "All fields are required!", ButtonType.OK);
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
            teamMatchesDAO.deleteTeamMatch(selectedMatch);
            refreshMatches();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to delete!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void refreshMatches() {
        teamMatchesList.setAll(teamMatchesDAO.getAllTeamMatches());
        System.out.println("Refreshed matches: " + teamMatchesList); // Add this
        tableView.refresh();
    }

    public Tab getTeamMatchesTab() {
        return teamMatchesTab;
    }
}
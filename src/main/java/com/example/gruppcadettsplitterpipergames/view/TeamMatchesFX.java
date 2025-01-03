//CF
package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamMatchesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TeamMatchesFX {

    private Tab teamMatchesTab;
    private TableView<TeamMatches> tableView;
    private TeamMatchesDAO teamMatchesDAO;
    private TeamsDAO teamsDAO;
    private GamesDAO gamesDAO;
    private ObservableList<TeamMatches> teamMatchesList;

    public TeamMatchesFX() {
        teamMatchesDAO = new TeamMatchesDAO();
        teamsDAO = new TeamsDAO();
        gamesDAO = new GamesDAO();
        teamMatchesList = FXCollections.observableArrayList(teamMatchesDAO.getAllTeamMatches());
        initializeUI();
    }

    private void initializeUI() {
        teamMatchesTab = new Tab("Team Matches");

        tableView = new TableView<>();
        tableView.setItems(teamMatchesList);

        TableColumn<TeamMatches, Integer> matchIdColumn = new TableColumn<>("Match ID");
        matchIdColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getMatchId()));

        TableColumn<TeamMatches, String> gameColumn = new TableColumn<>("Game");
        gameColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getGame().getGameName()));

        TableColumn<TeamMatches, String> team1Column = new TableColumn<>("Team 1");
        team1Column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam1Name()));

        TableColumn<TeamMatches, String> team2Column = new TableColumn<>("Team 2");
        team2Column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam2Name()));

        TableColumn<TeamMatches, String> resultColumn = new TableColumn<>("Result");
        resultColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getResult()));

        TableColumn<TeamMatches, String> dateColumn = new TableColumn<>("Match Date");
        dateColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getMatchDate()));

        tableView.getColumns().addAll(matchIdColumn, gameColumn, team1Column, team2Column, resultColumn, dateColumn);

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

        Button showDecidedButton = new Button("Show Decided");
        showDecidedButton.setOnAction(e -> showDecidedMatches());

        Button showUndecidedButton = new Button("Show Undecided");
        showUndecidedButton.setOnAction(e -> showUndecidedMatches());


        VBox rightBox = new VBox(10, showDecidedButton, showUndecidedButton);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setBottom(buttonBox);
        root.setRight(rightBox);

        teamMatchesTab.setContent(root);
    }

    private void showMatchForm(TeamMatches existingMatch, boolean isUpdate) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(isUpdate ? "Update Match" : "Add New Match");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        ComboBox<Game> gameComboBox = new ComboBox<>();
        gameComboBox.getItems().addAll(gamesDAO.getAllGames());
        gameComboBox.setPromptText("Select a game");

        ComboBox<Team> team1ComboBox = new ComboBox<>();
        team1ComboBox.setPromptText("Select Team 1");

        ComboBox<Team> team2ComboBox = new ComboBox<>();
        team2ComboBox.setPromptText("Select Team 2");

        TextField resultField = new TextField();
        resultField.setPromptText("Enter match result (e.g., 2-1)");

        TextField matchDateField = new TextField();
        matchDateField.setPromptText("Enter match date (e.g., YYYY-MM-DD)");

        if (isUpdate && existingMatch != null) {
            gameComboBox.setValue(existingMatch.getGame());
            team1ComboBox.setValue(new Team(existingMatch.getTeam1Name(), existingMatch.getGame()));
            team2ComboBox.setValue(new Team(existingMatch.getTeam2Name(), existingMatch.getGame()));
            resultField.setText(existingMatch.getResult());
            matchDateField.setText(existingMatch.getMatchDate());
        }

        gameComboBox.setOnAction(e -> {
            Game selectedGame = gameComboBox.getValue();
            if (selectedGame != null) {
                List<Team> teams = teamsDAO.getTeamsByGame(selectedGame);
                team1ComboBox.getItems().setAll(teams);
                team2ComboBox.getItems().setAll(teams);

                team1ComboBox.setValue(null);
                team2ComboBox.setValue(null);
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Game:"), 0, 0);
        grid.add(gameComboBox, 1, 0);
        grid.add(new Label("Team 1:"), 0, 1);
        grid.add(team1ComboBox, 1, 1);
        grid.add(new Label("Team 2:"), 0, 2);
        grid.add(team2ComboBox, 1, 2);
        grid.add(new Label("Result:"), 0, 3);
        grid.add(resultField, 1, 3);
        grid.add(new Label("Match Date:"), 0, 4);
        grid.add(matchDateField, 1, 4);

        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Cancel");
        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox dialogLayout = new VBox(10, grid, buttonBox);
        dialogLayout.setPadding(new Insets(20));

        Scene dialogScene = new Scene(dialogLayout, 400, 350);
        dialogStage.setScene(dialogScene);

        okButton.setOnAction(e -> {
            if (gameComboBox.getValue() != null &&
                    team1ComboBox.getValue() != null &&
                    team2ComboBox.getValue() != null &&
                    !matchDateField.getText().isEmpty()) {

                TeamMatches match = (existingMatch == null) ? new TeamMatches() : existingMatch;
                match.setGame(gameComboBox.getValue());
                match.setTeam1Name(team1ComboBox.getValue().getName());
                match.setTeam2Name(team2ComboBox.getValue().getName());
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
        List<TeamMatches> allMatches = teamMatchesDAO.getAllTeamMatches();
        teamMatchesList.setAll(allMatches);
        tableView.setItems(teamMatchesList);
        tableView.refresh();
    }

    private void showDecidedMatches() {
        LocalDate today = LocalDate.now();
        List<TeamMatches> filtered = new ArrayList<>();
        for (TeamMatches tm : teamMatchesList) {
            try {
                LocalDate matchDay = LocalDate.parse(tm.getMatchDate());
                if (matchDay.isBefore(today)) {
                    filtered.add(tm);
                }
            } catch (DateTimeParseException ex) {
                System.err.println("Invalid date format: " + tm.getMatchDate());
            }
        }
        tableView.setItems(FXCollections.observableArrayList(filtered));
    }

    private void showUndecidedMatches() {
        LocalDate today = LocalDate.now();
        List<TeamMatches> filtered = new ArrayList<>();
        for (TeamMatches tm : teamMatchesList) {
            try {
                LocalDate matchDay = LocalDate.parse(tm.getMatchDate());
                if (!matchDay.isBefore(today)) {
                    filtered.add(tm);
                }
            } catch (DateTimeParseException ex) {
                System.err.println("Invalid date format: " + tm.getMatchDate());
            }
        }
        tableView.setItems(FXCollections.observableArrayList(filtered));
    }

    public Tab getTeamMatchesTab() {
        return teamMatchesTab;
    }
}
//CF
package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.PlayerMatchesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.entities.PlayerMatches;
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

public class PlayerMatchesFX {

    private Tab playerMatchesTab;
    private TableView<PlayerMatches> tableView;

    private PlayerMatchesDAO playerMatchesDAO;
    private PlayerDAO playerDAO;
    private GamesDAO gamesDAO;

    private ObservableList<PlayerMatches> playerMatchesList;

    public PlayerMatchesFX() {
        playerMatchesDAO = new PlayerMatchesDAO();
        playerDAO = new PlayerDAO();
        gamesDAO = new GamesDAO();

        playerMatchesList = FXCollections.observableArrayList(
                playerMatchesDAO.getAllPlayerMatches()
        );

        initializeUI();
    }

    private void initializeUI() {
        playerMatchesTab = new Tab("Player Matches");

        tableView = new TableView<>();
        tableView.setItems(playerMatchesList);

        TableColumn<PlayerMatches, Integer> idColumn = new TableColumn<>("Match ID");
        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPlayerMatchId()));

        TableColumn<PlayerMatches, String> gameColumn = new TableColumn<>("Game");
        gameColumn.setCellValueFactory(data -> {
            Game g = data.getValue().getGame();
            return new javafx.beans.property.SimpleStringProperty(
                    (g != null) ? g.getGameName() : "No Game"
            );
        });

        TableColumn<PlayerMatches, String> player1Column = new TableColumn<>("Player 1");
        player1Column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPlayer1Name()));

        TableColumn<PlayerMatches, String> player2Column = new TableColumn<>("Player 2");
        player2Column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPlayer2Name()));

        TableColumn<PlayerMatches, String> resultColumn = new TableColumn<>("Result");
        resultColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getResult()));

        TableColumn<PlayerMatches, String> dateColumn = new TableColumn<>("Match Date");
        dateColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getMatchDate()));

        tableView.getColumns().addAll(
                idColumn,
                gameColumn,
                player1Column,
                player2Column,
                resultColumn,
                dateColumn
        );

        Label emptyLabel = new Label("No matches available.");
        tableView.setPlaceholder(emptyLabel);

        HBox bottomButtons = new HBox(10);
        bottomButtons.setAlignment(Pos.CENTER);
        bottomButtons.setPadding(new Insets(10));

        Button addBtn = new Button("Add Match");
        Button updateBtn = new Button("Update Match");
        Button deleteBtn = new Button("Delete Match");
        Button refreshBtn = new Button("Refresh");

        bottomButtons.getChildren().addAll(addBtn, updateBtn, deleteBtn, refreshBtn);

        addBtn.setOnAction(e -> addMatch());
        updateBtn.setOnAction(e -> updateMatch());
        deleteBtn.setOnAction(e -> deleteMatch());
        refreshBtn.setOnAction(e -> refreshMatches());

        VBox rightBox = new VBox(10);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(10));

        Button showDecidedBtn = new Button("Show Decided");
        showDecidedBtn.setOnAction(e -> showDecidedMatches());

        Button showUndecidedBtn = new Button("Show Undecided");
        showUndecidedBtn.setOnAction(e -> showUndecidedMatches());

        rightBox.getChildren().addAll(showDecidedBtn, showUndecidedBtn);

        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setBottom(bottomButtons);
        root.setRight(rightBox);

        playerMatchesTab.setContent(root);
    }

    private void showMatchForm(PlayerMatches existingMatch, boolean isUpdate) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(isUpdate ? "Update Match" : "Add New Match");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        ComboBox<Game> gameCombo = new ComboBox<>();
        gameCombo.setPromptText("Select a game");
        gameCombo.getItems().addAll(gamesDAO.getAllGames());

        ComboBox<Player> player1Combo = new ComboBox<>();
        player1Combo.setPromptText("Select Player 1");

        ComboBox<Player> player2Combo = new ComboBox<>();
        player2Combo.setPromptText("Select Player 2");

        TextField resultField = new TextField();
        resultField.setPromptText("Result (undecided, 2-1)");

        TextField dateField = new TextField();
        dateField.setPromptText("YYYY-MM-DD");

        gameCombo.setOnAction(e -> {
            Game chosenGame = gameCombo.getValue();
            if (chosenGame != null) {
                List<Player> playersForGame = playerDAO.getPlayersByGame(chosenGame);
                player1Combo.getItems().setAll(playersForGame);
                player2Combo.getItems().setAll(playersForGame);

                player1Combo.setValue(null);
                player2Combo.setValue(null);
            }
        });

        if (isUpdate && existingMatch != null) {
            if (existingMatch.getGame() != null) {
                gameCombo.setValue(existingMatch.getGame());
                gameCombo.getOnAction().handle(null);

                Player p1 = findPlayerByName(player1Combo.getItems(), existingMatch.getPlayer1Name());
                Player p2 = findPlayerByName(player2Combo.getItems(), existingMatch.getPlayer2Name());
                player1Combo.setValue(p1);
                player2Combo.setValue(p2);
            }
            resultField.setText(existingMatch.getResult());
            dateField.setText(existingMatch.getMatchDate());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Game:"),        0, 0);
        grid.add(gameCombo,                1, 0);
        grid.add(new Label("Player 1:"),   0, 1);
        grid.add(player1Combo,             1, 1);
        grid.add(new Label("Player 2:"),   0, 2);
        grid.add(player2Combo,             1, 2);
        grid.add(new Label("Result:"),     0, 3);
        grid.add(resultField,              1, 3);
        grid.add(new Label("Match Date:"), 0, 4);
        grid.add(dateField,                1, 4);

        Button okBtn = new Button("Ok");
        Button cancelBtn = new Button("Cancel");
        HBox formButtons = new HBox(10, okBtn, cancelBtn);
        formButtons.setAlignment(Pos.CENTER);
        formButtons.setPadding(new Insets(10));

        VBox dialogLayout = new VBox(10, grid, formButtons);
        dialogLayout.setPadding(new Insets(20));
        Scene dialogScene = new Scene(dialogLayout, 450, 330);
        dialogStage.setScene(dialogScene);

        okBtn.setOnAction(e -> {
            if (gameCombo.getValue() != null
                    && player1Combo.getValue() != null
                    && player2Combo.getValue() != null
                    && !dateField.getText().isEmpty()) {

                PlayerMatches match = (existingMatch == null) ? new PlayerMatches() : existingMatch;
                match.setGame(gameCombo.getValue());
                match.setPlayer1Name(
                        player1Combo.getValue().getFirstName() + " "
                                + player1Combo.getValue().getLastName()
                );
                match.setPlayer2Name(
                        player2Combo.getValue().getFirstName() + " "
                                + player2Combo.getValue().getLastName()
                );
                match.setResult(resultField.getText());
                match.setMatchDate(dateField.getText());

                if (isUpdate) {
                    playerMatchesDAO.updatePlayerMatch(match);
                } else {
                    playerMatchesDAO.savePlayerMatch(match);
                }
                refreshMatches();
                dialogStage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,
                        "All fields are required!",
                        ButtonType.OK);
                alert.showAndWait();
            }
        });

        cancelBtn.setOnAction(e -> dialogStage.close());
        dialogStage.showAndWait();
    }

    private Player findPlayerByName(List<Player> players, String storedName) {
        if (storedName == null) return null;

        for (Player p : players) {
            String fullName = p.getFirstName() + " " + p.getLastName();
            if (fullName.equalsIgnoreCase(storedName)) {
                return p;
            }
        }
        return null;
    }

    private void addMatch() {
        showMatchForm(null, false);
    }

    private void updateMatch() {
        PlayerMatches selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showMatchForm(selected, true);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to update", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void deleteMatch() {
        PlayerMatches selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            playerMatchesDAO.deletePlayerMatch(selected);
            refreshMatches();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to delete", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void refreshMatches() {
        playerMatchesList.setAll(playerMatchesDAO.getAllPlayerMatches());
        tableView.setItems(playerMatchesList);
        tableView.refresh();
    }

    private void showDecidedMatches() {
        LocalDate today = LocalDate.now();
        List<PlayerMatches> filtered = new ArrayList<>();
        for (PlayerMatches pm : playerMatchesList) {
            try {
                LocalDate matchDay = LocalDate.parse(pm.getMatchDate());
                if (matchDay.isBefore(today)) {
                    filtered.add(pm);
                }
            } catch (DateTimeParseException ex) {
                System.err.println("Invalid date: " + pm.getMatchDate());
            }
        }
        tableView.setItems(FXCollections.observableArrayList(filtered));
    }

    private void showUndecidedMatches() {
        LocalDate today = LocalDate.now();
        List<PlayerMatches> filtered = new ArrayList<>();
        for (PlayerMatches pm : playerMatchesList) {
            try {
                LocalDate matchDay = LocalDate.parse(pm.getMatchDate());
                if (!matchDay.isBefore(today)) {
                    filtered.add(pm);
                }
            } catch (DateTimeParseException ex) {
                System.err.println("Invalid date: " + pm.getMatchDate());
            }
        }
        tableView.setItems(FXCollections.observableArrayList(filtered));
    }

    public Tab getPlayerMatchesTab() {
        return playerMatchesTab;
    }
}
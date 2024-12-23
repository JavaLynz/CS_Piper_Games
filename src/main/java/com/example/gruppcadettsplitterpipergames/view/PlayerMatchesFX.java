//CF
package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.PlayerMatchesDAO;
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

public class PlayerMatchesFX {

    private Tab playerMatchesTab;
    private TableView<PlayerMatches> tableView;
    private PlayerMatchesDAO playerMatchesDAO;
    private ObservableList<PlayerMatches> playerMatchesList;

    public PlayerMatchesFX() {
        playerMatchesDAO = new PlayerMatchesDAO();
        playerMatchesList = FXCollections.observableArrayList(playerMatchesDAO.getAllPlayerMatches());
        initializeUI();
    }

    private void initializeUI() {
        playerMatchesTab = new Tab("Player Matches");

        tableView = new TableView<>();
        tableView.setItems(playerMatchesList);

        TableColumn<PlayerMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPlayerMatchId()));

        TableColumn<PlayerMatches, String> player1Column = new TableColumn<>("Player 1");
        player1Column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPlayer1Name()));

        TableColumn<PlayerMatches, String> player2Column = new TableColumn<>("Player 2");
        player2Column.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getPlayer2Name()));

        TableColumn<PlayerMatches, String> resultColumn = new TableColumn<>("Result");
        resultColumn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getResult()));

        tableView.getColumns().addAll(idColumn, player1Column, player2Column, resultColumn);

        Label emptyTableLabel = new Label("No matches available.");
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

        playerMatchesTab.setContent(root);
    }

    private void showMatchForm(PlayerMatches existingMatch, boolean isUpdate) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(isUpdate ? "Update Match" : "Add New Match");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        Label player1Label = new Label("Player 1:");
        TextField player1Field = new TextField();
        Label player2Label = new Label("Player 2:");
        TextField player2Field = new TextField();
        Label resultLabel = new Label("Result:");
        TextField resultField = new TextField();

        if (isUpdate && existingMatch != null) {
            player1Field.setText(existingMatch.getPlayer1Name());
            player2Field.setText(existingMatch.getPlayer2Name());
            resultField.setText(existingMatch.getResult());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.add(player1Label, 0, 0);
        grid.add(player1Field, 1, 0);
        grid.add(player2Label, 0, 1);
        grid.add(player2Field, 1, 1);
        grid.add(resultLabel, 0, 2);
        grid.add(resultField, 1, 2);

        Button okButton = new Button("Ok");
        Button cancelButton = new Button("Avbryt");

        HBox buttonBox = new HBox(10, okButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        VBox dialogLayout = new VBox(10, grid, buttonBox);
        dialogLayout.setPadding(new Insets(20));
        Scene dialogScene = new Scene(dialogLayout, 300, 200);
        dialogStage.setScene(dialogScene);

        okButton.setOnAction(e -> {
            if (!player1Field.getText().isEmpty()
                    && !player2Field.getText().isEmpty()
                    && !resultField.getText().isEmpty()) {

                PlayerMatches match = (existingMatch == null) ? new PlayerMatches() : existingMatch;
                match.setPlayer1Name(player1Field.getText());
                match.setPlayer2Name(player2Field.getText());
                match.setResult(resultField.getText());

                if (isUpdate) {
                    playerMatchesDAO.updatePlayerMatch(match);
                } else {
                    playerMatchesDAO.savePlayerMatch(match);
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
        PlayerMatches selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            showMatchForm(selectedMatch, true);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to update!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void deleteMatch() {
        PlayerMatches selectedMatch = tableView.getSelectionModel().getSelectedItem();
        if (selectedMatch != null) {
            playerMatchesDAO.deletePlayerMatch(selectedMatch);
            refreshMatches();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No match selected to delete!", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void refreshMatches() {
        playerMatchesList.setAll(playerMatchesDAO.getAllPlayerMatches());
        tableView.refresh();
    }

    public Tab getPlayerMatchesTab() {
        return playerMatchesTab;
    }
}
package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.PlayerMatchesDAO;
import com.example.gruppcadettsplitterpipergames.entities.PlayerMatches;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PlayerMatchesFX {

    private Tab playerMatchesTab;
    private TableView<PlayerMatches> tableView;
    private ObservableList<PlayerMatches> playerMatchesList;
    private PlayerMatchesDAO playerMatchesDAO;

    public PlayerMatchesFX() {
        playerMatchesDAO = new PlayerMatchesDAO();
        initializeUI();
        loadPlayerMatches();
    }

    private void initializeUI() {

        tableView = new TableView<>();
        TableColumn<PlayerMatches, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data->data.getValue().playerMatchIdProperty().asObjekt());

        TableColumn<PlayerMatches, String> matchDetailsColumn = new TableColumn<>("Player");
        playerColumn.setCellValueFaxtory(data->data.getValue().playerNameProperty());

        TableColumn<PlayerMatches,String> matchColumn = new TableColumn<>(Match);
        matchColumn.setValueFactory(data->data.getValue().matchNameProperty());

        tableView.getColumns().addAll(idColumn, playerColumn, matchColumn);

        Button addButton = new Button("Add new match");
        Button updateButton = new Button("Update match");
        Button deleteButton = new Button("Delete match");
        Button searchButton = new Button("Search");

        addButton.setOnAction(event->addPlayerMatch());
        updateButton.setOnAction(event->updatePlayerMatch());
        deleteButton.setOnAction(event->deletePlayerMatch());
        searchButton.setOnAction(event->searchPlayerMatch());

        HBox buttons = new HBox(10, addButton, updateButton, deleteButton, searchButton);
        buttons.setStyle("-fx-padding: 10;");

        BorderPane layout = new BorderPane();
        layout.setCenter(tableView);
        layout.setBottom(buttons);

        playerMatchesTab = new Tab("PlayerMatches", layout);
    }

    public Tab getPlayerMatchesTab() {
        return playerMatchesTab;
    }

    private void loadPlayerMatches(){
        playerMatchesList = FXCollections.observableArrayList(playerMatchesDAO.getAllPlayerMatches());
        tableView.setItems(playerMatchesList);
    }

    private void addPlayerMatch(){
        //Dialogruta för att skriva in datan.
    }

    private void updatePlayerMatch(){
        PlayerMatches selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null){
            //Skriv en uppdateringsruta.
        }
        else{
            showAlert("Varning!", "No valid choice.");
        }
    }

    private void deletePlayerMatch(){
        PlayerMatches selected = tableView.getSelectionModel().getSelectedItem();
        if(selected != null){
            playerMatchesDAO.deletePlayerMatch(selected.getId());
            playerMatchesList.remove(selected);
        }
        else {
            showAlert("Varning!", "No valid choise.");
        }
    }

    private void searchPlayerMatch(){
        // Sökmekanik här
    }

    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddGameBox {       // Lynsey Fox

    private GamesDAO gamesDAO;

    public AddGameBox(GamesDAO gamesDAO) {
        this.gamesDAO = gamesDAO;
    }
    public void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add New Game");
        window.setMinWidth(400);
        window.setMinHeight(500);

        Label label = new Label();
        label.setText("Add Game");
        label.setAlignment(Pos.CENTER);
        label.setStyle(" -fx-font-size: 20");

        Label label2 = new Label("Enter new game name:");
        TextField getGameName = new TextField();
        getGameName.setMaxWidth(100);


        Button save = new Button("Save");
        save.setDefaultButton(true);
        save.setOnAction(e -> {
                if(!getGameName.getText().isEmpty()){
                    String givenGameName = getGameName.getText();
                    gamesDAO.saveGame(new Game(givenGameName));
                }else{
                    getGameName.requestFocus();
                    getGameName.setStyle("-fx-background-color: red");
            }

            System.out.println("Game added: "+gamesDAO.getAllGames().toString());
            window.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> window.close());


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, label2, getGameName, save, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

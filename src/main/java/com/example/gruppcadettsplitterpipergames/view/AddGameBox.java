package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AddGameBox {       // Lynsey Fox

    private GamesDAO gamesDAO;
    public boolean result = false;

    public AddGameBox(GamesDAO gamesDAO) {
        this.gamesDAO = gamesDAO;
    }
    public boolean display() throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add New Game");
        window.setMinWidth(400);
        window.setMinHeight(500);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

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
                    Game gameToAdd = new Game(givenGameName);
                    gamesDAO.saveGame(gameToAdd);
                    result = true;
                    try {
                        confirmPopup(gameToAdd);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }else{
                    getGameName.requestFocus();
                    getGameName.setStyle("-fx-background-color: red");
            }

            System.out.println("Game added: "+gamesDAO.getAllGames().toString());
            window.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> {
            window.close();
            result = false;
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,label, label2, getGameName, save, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return result;
    }

    private void confirmPopup(Game gameToAdd) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Game: "+ gameToAdd.getGameName() + " has been added to the database");
        AnchorPane root = new AnchorPane();
        root.setPrefSize(250,250);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        window.setAlwaysOnTop(true);
    }
}

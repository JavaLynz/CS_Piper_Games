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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameUpdateBox {

    public boolean result = false;

    public boolean display(Game gameToUpdate, GamesDAO gamesDAO) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Game");
        window.setMinWidth(400);
        window.setMinHeight(500);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Label label = new Label();
        label.setText("Update Game: " + gameToUpdate.getGameName());
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 20");

        Label gameNameLabel = new Label("Update game name:");
        TextField gameName = new TextField();
        gameName.setMaxWidth(100);

        Button update = new Button("Update");
        update.setDefaultButton(true);
        update.setOnAction(e -> {
            gameToUpdate.setGameName(gameName.getText());
            gamesDAO.updateGame(gameToUpdate);
            try {
                confirmPopup(gameToUpdate);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            window.close();
            result=true;
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> {
            window.close();
            result = false;
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,label, gameNameLabel, gameName,update, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
     private void confirmPopup(Game gameToUpdate) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Game: "+ gameToUpdate.getGameName() + " has been updated");
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

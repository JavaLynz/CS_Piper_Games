package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DeleteConfirmBox {     // Lynsey Fox

    boolean answer;

    public boolean display(Game gameToDelete, GamesDAO gamesDAO) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Delete");
        window.setMinWidth(400);
        window.setResizable(false);
        window.setMinHeight(400);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Label title = new Label("Confirm Delete");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");
        Label label = new Label();
        label.setText("Are you sure you want to delete " + gameToDelete.getGameName() +"?");

        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");
        delete.setOnAction(e -> {
            System.out.println("number of games: " + gamesDAO.getAllGames().size());
            System.out.println("Game deleted: " + gameToDelete.getGameName());
            gamesDAO.deleteGame(gameToDelete);
            answer = true;
            try {
                confirmPopup(gameToDelete);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("number of games: " + gamesDAO.getAllGames().size());
            window.close();
        });
        cancel.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(logo,title,label, delete, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return answer;

    }

    private void confirmPopup(Game gameToDelete) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Game: "+ gameToDelete.getGameName() + " has been deleted");
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

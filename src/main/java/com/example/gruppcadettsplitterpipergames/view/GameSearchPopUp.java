package com.example.gruppcadettsplitterpipergames.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameSearchPopUp {      //Lynsey Fox
    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Game Search");
        window.setMinWidth(400);
        window.setMinHeight(500);

        Label title = new Label();
        title.setText("Search: ");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");

        Label label = new Label();
        label.setText("Choose search term: ");



        Button search = new Button("Search");
        search.setDefaultButton(true);
        search.setOnAction(e -> {
            //Search with hash map?
            window.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> window.close());


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, search, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

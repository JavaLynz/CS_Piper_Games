package com.example.gruppcadettsplitterpipergames.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameUpdateBox {

    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Game");
        window.setMinWidth(400);
        window.setMinHeight(500);

        Label label = new Label();
        label.setText("Update Game: ");
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 20");



        Button update = new Button("Update");
        update.setDefaultButton(true);
        update.setOnAction(e -> {
            //Search with hash map?
            window.close();
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> window.close());


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, update, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}

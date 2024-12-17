package com.example.gruppcadettsplitterpipergames.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DeleteConfirmBox {

    boolean answer;

    public boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Delete");
        window.setMinWidth(400);
        Label label = new Label();
        label.setText("Are you sure you want to delete?");

        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");
        delete.setOnAction(e -> {
            answer = true;
            window.close();
        });
        cancel.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, delete, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return answer;

    }
}

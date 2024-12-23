package com.example.gruppcadettsplitterpipergames.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class QuitConfirmBox {

    boolean answer;

    public boolean display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Quit");
        window.setMinWidth(400);
        Label label = new Label();
        label.setText("Are you sure you want to quit?");

        Button yes = new Button("Yes");
        Button no = new Button("No");
        yes.setOnAction(e -> {
            answer = true;
            Platform.exit();
        });
        no.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return answer;


    }
}

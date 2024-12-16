package com.example.gruppcadettsplitterpipergames.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class TabMenu extends Application {
    private TabPane root = new TabPane();
    private StaffFX staffFX = new StaffFX(this.root);

    @Override
    public void start(Stage stage) throws Exception {

        this.root.getTabs().add(staffFX.createStaffTab());

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}

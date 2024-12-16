package com.example.gruppcadettsplitterpipergames.view;

import javafx.scene.Scene;
import javafx.scene.control.TabPane;

public class TabMenu{
    private TabPane root = new TabPane();
    private StaffFX staffFX = new StaffFX(this.root);


    public Scene tabMenuScene(){
        this.root.getTabs().add(staffFX.createStaffTab());
        Scene scene = new Scene(this.root);
        return scene;
    }
}

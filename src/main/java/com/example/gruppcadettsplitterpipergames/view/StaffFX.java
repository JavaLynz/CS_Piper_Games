package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.entities.Staff;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class StaffFX {
    //StaffDAO staffDAO = new StaffDAO();

    public StaffFX() {
    }

    public Tab createStaffTab(){
        Tab staffTab = new Tab("Staff");

        AnchorPane root = new AnchorPane();
        VBox container = new VBox(30);
        container.setAlignment(Pos.TOP_CENTER);
        AnchorPane.setTopAnchor(container,0.0);
        AnchorPane.setBottomAnchor(container,5.0);
        AnchorPane.setLeftAnchor(container,5.0);
        AnchorPane.setRightAnchor(container,5.0);
        Text title = new Text("Staff");

        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold");
        container.setStyle("-fx-background-color: silver; -fx-background-radius:0 0 5 5;");

        container.getChildren().addAll(title, createStaffSelect(container));
        root.getChildren().addAll(container);
        staffTab.setContent(root);
        return staffTab;
    }

    public VBox createStaffSelect(VBox outerContainer){
        VBox container = new VBox(10);
        container.setAlignment(Pos.TOP_CENTER);

        ComboBox<Object> staffSelect = new ComboBox<>();
        staffSelect.setMinWidth(300);

        Button selectOne = new Button("Select");
        Button selectAll = new Button("Select All");

        selectAll.setPrefSize(80, 30);
        selectOne.setPrefSize(80,30);

        container.getChildren().addAll(staffSelect, selectOne, selectAll);
        return container;
    }

    public VBox createSingleEdit(Staff staffMember, VBox outerContainer){
        VBox container = new VBox(10);
        return container;
    }

    public VBox createGroupEdit(List<Staff> staffList){
        VBox container = new VBox(10);
        return container;
    }
}

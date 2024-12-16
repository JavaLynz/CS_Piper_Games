package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.StaffDAO;
import com.example.gruppcadettsplitterpipergames.entities.Staff;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaffFX {
    StaffDAO staffDAO = new StaffDAO();
    Tab staffTab = new Tab("Staff");

    public StaffFX() {
    }

    public Tab createStaffTab(){

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
        this.staffTab.setContent(root);
        return this.staffTab;
    }

    public VBox createStaffSelect(VBox outerContainer){
        List<Staff> staffList = new ArrayList<>();

        VBox container = new VBox(10);
        container.setAlignment(Pos.TOP_CENTER);

        HBox containerOne = new HBox(10);
        HBox containerMult = new HBox(10);
        HBox containerAll = new HBox(10);

        ComboBox<Object> staffSelectOne = new ComboBox<>();
        ComboBox<Object> staffSelectMult = new ComboBox<>();

        staffSelectOne.setMinWidth(250);
        staffSelectMult.setMinWidth(250);
        HashMap<String, Staff> staffHashMap = new HashMap<>();
        for (Staff staff : staffDAO.getAllStaff()){
            String fullName = staff.getFirstName() + " \""+ staff.getNickName()+"\" "+ staff.getLastName();
            staffHashMap.put(fullName, staff);
            staffSelectOne.getItems().add(fullName);
            staffSelectMult.getItems().add(fullName);
        }


        TextArea staffChoices = new TextArea();
        staffChoices.setEditable(false);
        staffChoices.setPrefRowCount(3);
        staffChoices.setPrefColumnCount(15);



        Button selectOne = new Button("Select");
        selectOne.setOnMouseClicked(mouseEvent -> {
            String choice = (String) staffSelectOne.getValue();
            container.getChildren().removeAll(containerOne, containerMult, containerAll);
            container.getChildren().add(createSingleEdit(staffHashMap.get(choice), container));
        });


        Button addChoice = new Button("Add");
        addChoice.setOnMouseClicked(mouseEvent -> {
            String choice = (String) staffSelectMult.getValue();
            if (!staffList.contains(staffHashMap.get(choice))){
                staffChoices.appendText(choice+"\n");
                staffList.add(staffHashMap.get(choice));
            }
        });

        Button resetChoices = new Button("Reset");
        resetChoices.setOnMouseClicked(mouseEvent -> {
            staffChoices.setText("");
            staffList.clear();
        });
        Button selectMult = new Button("Select Multiple");
        Button selectAll = new Button("Select All");



        selectAll.setPrefSize(80, 30);
        selectOne.setPrefSize(80,30);

        containerOne.getChildren().addAll(staffSelectOne, selectOne);
        containerMult.getChildren().addAll(staffSelectMult, addChoice, staffChoices, resetChoices, selectMult);
        containerAll.getChildren().addAll(selectAll);
        container.getChildren().addAll(containerOne, containerMult, containerAll);
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

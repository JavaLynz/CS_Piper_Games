package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.AddressDAO;
import com.example.gruppcadettsplitterpipergames.DAO.StaffDAO;
import com.example.gruppcadettsplitterpipergames.entities.Staff;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaffFX {
    StaffDAO staffDAO = new StaffDAO();
    AddressDAO addressDAO = new AddressDAO();
    Tab staffTab = new Tab("Staff");
    TabPane root;

    public StaffFX() {
    }

    public StaffFX(TabPane parentScene) {
        this.root = parentScene;
    }

    public Tab createStaffTab(){

        AnchorPane root = new AnchorPane();
        VBox container = new VBox(30);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(0, 20,20,20));
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
        this.root.setPrefSize(800,300);

        HBox containerMult = new HBox(10);
        HBox containerAll = new HBox(10);
        containerMult.setAlignment(Pos.CENTER);
        containerAll.setAlignment(Pos.CENTER);


        ComboBox<Object> staffSelectMult = new ComboBox<>();

        staffSelectMult.setMinWidth(250);
        HashMap<String, Staff> staffHashMap = new HashMap<>();
        for (Staff staff : staffDAO.getAllStaff()){
            String fullName = staff.getFirstName() + " \""+ staff.getNickName()+"\" "+ staff.getLastName();
            staffHashMap.put(fullName, staff);
            staffSelectMult.getItems().add(fullName);
        }


        TextArea staffChoices = new TextArea();
        staffChoices.setEditable(false);
        staffChoices.setPrefRowCount(3);
        staffChoices.setPrefColumnCount(15);



        Button selectMult = new Button("Select");
        selectMult.setDisable(true);
        selectMult.setOnMouseClicked(mouseEvent -> {
            outerContainer.getChildren().remove(container);
            outerContainer.getChildren().add(createEdit(staffList, outerContainer));
        });
        Button addChoice = new Button("Add");
        addChoice.setOnMouseClicked(mouseEvent -> {
            String choice = (String) staffSelectMult.getValue();
            if (!staffList.contains(staffHashMap.get(choice))){
                staffChoices.appendText(choice+"\n");
                staffList.add(staffHashMap.get(choice));
            }
            if (staffList.size()>0){
                selectMult.setDisable(false);
            }
            if (staffList.size()> 1){
                selectMult.setText("Select Multiple");
            }
        });

        Button resetChoices = new Button("Reset");
        resetChoices.setOnMouseClicked(mouseEvent -> {
            staffChoices.setText("");
            staffList.clear();
            selectMult.setDisable(true);
            selectMult.setText("Select");
        });

        Button selectAll = new Button("Select All");
        selectAll.setOnMouseClicked(mouseEvent -> {
            staffList.clear();
            staffList.addAll(staffDAO.getAllStaff());
            outerContainer.getChildren().remove(container);
            outerContainer.getChildren().add(createEdit(staffList, outerContainer));
        });



        selectAll.setPrefSize(80, 30);
        selectMult.setPrefSize(80,30);

        containerMult.getChildren().addAll(staffSelectMult, addChoice, staffChoices, resetChoices, selectMult);
        containerAll.getChildren().addAll(selectAll);
        container.getChildren().addAll(containerMult, containerAll);
        return container;
    }

    public VBox createEdit(List<Staff> staffList, VBox outerContainer){
        VBox container = new VBox(10);
        ObservableList<Staff> tableData = FXCollections.observableArrayList();
        tableData.setAll(staffList);

        TableView staffTable = new TableView<>();

        TableColumn idCol = new TableColumn<>("ID");

        TableColumn firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty()
        );
        TableColumn lastNameCol = new TableColumn<>("Last Name");
        TableColumn nickNameCol = new TableColumn<>("Nickname");
        TableColumn addressStreetCol = new TableColumn<>("Street Address");
        TableColumn addressDistrictCol = new TableColumn<>("District");
        TableColumn addressCityCol = new TableColumn<>("City");
        TableColumn addressPostcodeCol = new TableColumn<>("Postcode");
        TableColumn addressCountryCol = new TableColumn<>("Country");
        TableColumn emailCol = new TableColumn<>("e-mail");
        staffTable.getColumns().addAll(idCol, firstNameCol,lastNameCol,nickNameCol,addressStreetCol,addressDistrictCol,addressCityCol,addressPostcodeCol,addressCountryCol,emailCol);


        staffTable.setItems(tableData);



        TextArea txt = new TextArea();
        for (Staff staff: staffList){
            txt.appendText(staff.getFirstName()+ "\n");
        }
        Button btn = new Button("Back");
        btn.setOnMouseClicked(mouseEvent -> {
            outerContainer.getChildren().remove(container);
            outerContainer.getChildren().add(createStaffSelect(outerContainer));
        });
        container.getChildren().addAll(staffTable, txt,btn);
        return container;
    }
}

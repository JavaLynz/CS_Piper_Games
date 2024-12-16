package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.AddressDAO;
import com.example.gruppcadettsplitterpipergames.DAO.StaffDAO;
import com.example.gruppcadettsplitterpipergames.entities.Staff;
import jakarta.persistence.Table;
import javafx.beans.property.SimpleIntegerProperty;
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

import javax.security.auth.callback.Callback;
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
        container.getChildren().addAll(containerMult, containerAll, addNewStaff());
        return container;
    }

    public VBox createEdit(List<Staff> staffList, VBox outerContainer){
        VBox container = new VBox(10);
        ObservableList<Staff> tableData = FXCollections.observableArrayList();
        tableData.setAll(staffList);

        TableView staffTable = new TableView<>();

        TableColumn<Staff, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStaffId())));

        TableColumn<Staff, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));

        TableColumn<Staff, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

        TableColumn<Staff, String> nickNameCol = new TableColumn<>("Nickname");
        nickNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNickName()));

        TableColumn<Staff, String> addressStreetCol = new TableColumn<>("Street Address");
        addressStreetCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));

        TableColumn<Staff, String> addressDistrictCol = new TableColumn<>("District");
        addressDistrictCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getDistrict()));

        TableColumn<Staff, String> addressCityCol = new TableColumn<>("City");
        addressCityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));

        TableColumn<Staff, String> addressPostcodeCol = new TableColumn<>("Postcode");
        addressPostcodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostcode()));

        TableColumn<Staff, String> addressCountryCol = new TableColumn<>("Country");
        addressCountryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCountry()));

        TableColumn<Staff, String> emailCol = new TableColumn<>("e-mail");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TableColumn<Staff,Void> updateCol =  new TableColumn<>("Update");
        updateCol.setCellFactory(col -> new TableCell<Staff,Void>(){
            Button updateBtn = new Button("Update");
            {
                updateBtn.setOnMouseClicked(mouseEvent -> {
                    System.out.println("Works");

                    for (int i = 0; i < staffTable.getColumns().size()-2; i++){
                        TableColumn selectedCol = (TableColumn) staffTable.getColumns().get(i);
                        System.out.println(selectedCol.getCellObservableValue(getIndex()).getValue());
                    }

                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(updateBtn);
                }
            }
        });
        TableColumn<Staff,Void> deleteCol =  new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<Staff,Void>(){
            Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnMouseClicked(mouseEvent -> {
                    System.out.println("Works");

                    for (int i = 0; i < staffTable.getColumns().size()-2; i++){
                        TableColumn selectedCol = (TableColumn) staffTable.getColumns().get(i);
                        System.out.println(selectedCol.getCellObservableValue(getIndex()).getValue());
                    }

                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(deleteBtn);
                }
            }
        });

        staffTable.getColumns().addAll(idCol, firstNameCol,lastNameCol,nickNameCol,addressStreetCol,addressDistrictCol, addressCityCol, addressPostcodeCol, addressCountryCol, emailCol, updateCol, deleteCol);
        staffTable.setItems(tableData);



        Button backBtn = new Button("Back");
        backBtn.setOnMouseClicked(mouseEvent -> {
            outerContainer.getChildren().remove(container);
            outerContainer.getChildren().add(createStaffSelect(outerContainer));
        });

        container.getChildren().addAll(staffTable, addNewStaff(), backBtn);
        return container;
    }

    public HBox addNewStaff(){
        HBox container = new HBox();
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField nickname = new TextField();
        nickname.setPromptText("Nickname");
        ComboBox<String> streetAddress = new ComboBox<>();
        streetAddress.setPromptText("Street Address");
        streetAddress.setEditable(true);
        TextField district = new TextField();
        district.setPromptText("District");
        TextField city = new TextField();
        city.setPromptText("City");
        TextField postcode = new TextField();
        postcode.setPromptText("Postcode");
        TextField country = new TextField();
        country.setPromptText("Country");

        TextField email = new TextField();
        email.setPromptText("E-mail");

        Button addStaff = new Button("Add new");


        container.getChildren().addAll(firstName,lastName,nickname,streetAddress,district,city,postcode,country,email, addStaff);
        return container;
    }

}

package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.AddressDAO;
import com.example.gruppcadettsplitterpipergames.DAO.StaffDAO;
import com.example.gruppcadettsplitterpipergames.entities.Address;
import com.example.gruppcadettsplitterpipergames.entities.Staff;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class StaffFX {
    private StaffDAO staffDAO = new StaffDAO();
    private AddressDAO addressDAO = new AddressDAO();
    private TabPane parent;
    private TableView staffTable;
    private AnchorPane staffTab;
    private HashMap<Integer,String> currentUser;

    public StaffFX() {
    }

    public StaffFX(TabPane parent) {
        this.parent = parent;
    }

    public void createStaffTab() throws FileNotFoundException {
        this.parent.setMinSize(1000,400);
        this.staffTab = new AnchorPane();
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(0, 5,5,5));
        AnchorPane.setTopAnchor(container,0.0);
        AnchorPane.setBottomAnchor(container,5.0);
        AnchorPane.setLeftAnchor(container,5.0);
        AnchorPane.setRightAnchor(container,5.0);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(50,50,40);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(100);
        Text title = new Text("Staff");

        BorderPane header = new BorderPane();
        header.setRight(logo);
        header.setCenter(title);

        this.staffTable = createStaffTable();

        HBox btnContainer = new HBox(5);
        btnContainer.setAlignment(Pos.CENTER);

        Button addBtn = new Button("Add new staff");
        addBtn.setOnMouseClicked(mouseEvent -> {
            createPopup(1, null);
        });
        Button searchBtn = new Button("Search");
        searchBtn.setOnMouseClicked(mouseEvent -> {
            createPopup(2, null);
        });
        addBtn.setPrefSize(200,40);
        searchBtn.setPrefSize(200,40);

        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold");
        container.setStyle("-fx-background-color: silver; -fx-background-radius:0 0 5 5;");

        fillTable(staffDAO.getAllStaff());;
        btnContainer.getChildren().addAll(addBtn,searchBtn);
        container.getChildren().addAll(header, staffTable, btnContainer);
        staffTab.getChildren().addAll(container);
    }

    public TableView createStaffTable(){

        TableView staffTable = new TableView<>();

        TableColumn<Staff, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStaffId())));
        idCol.setPrefWidth(30);
        TableColumn<Staff, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        firstNameCol.setPrefWidth(100);
        TableColumn<Staff, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        lastNameCol.setPrefWidth(100);
        TableColumn<Staff, String> nickNameCol = new TableColumn<>("Nickname");
        nickNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNickName()));
        nickNameCol.setPrefWidth(70);
        TableColumn<Staff, String> addressStreetCol = new TableColumn<>("Street Address");
        addressStreetCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
        addressStreetCol.setPrefWidth(150);
        TableColumn<Staff, String> addressDistrictCol = new TableColumn<>("District");
        addressDistrictCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getDistrict()));
        addressDistrictCol.setPrefWidth(70);
        TableColumn<Staff, String> addressCityCol = new TableColumn<>("City");
        addressCityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
        addressCityCol.setPrefWidth(70);
        TableColumn<Staff, String> addressPostcodeCol = new TableColumn<>("Postcode");
        addressPostcodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostcode()));
        addressPostcodeCol.setPrefWidth(70);
        TableColumn<Staff, String> addressCountryCol = new TableColumn<>("Country");
        addressCountryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCountry()));
        addressCountryCol.setPrefWidth(50);
        TableColumn<Staff, String> emailCol = new TableColumn<>("e-mail");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        emailCol.setPrefWidth(150);
        TableColumn<Staff,Void> updateCol =  new TableColumn<>("Update");
        updateCol.setCellFactory(col -> new TableCell<>(){
            Button updateBtn = new Button("Update");
            {
                updateBtn.setOnMouseClicked(mouseEvent -> {
                    TableColumn idColumn = (TableColumn) staffTable.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    createPopup(3,staffDAO.getStaffById(id));
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
        updateCol.setPrefWidth(65);
        TableColumn<Staff,Void> deleteCol =  new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<>(){
            Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnMouseClicked(mouseEvent -> {
                    TableColumn idColumn = (TableColumn) staffTable.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    createPopup(4,staffDAO.getStaffById(id));
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
        deleteCol.setPrefWidth(60);

        staffTable.getColumns().addAll(idCol, firstNameCol,lastNameCol,nickNameCol,addressStreetCol,addressDistrictCol, addressCityCol, addressPostcodeCol, addressCountryCol, emailCol, updateCol, deleteCol);
        return staffTable;
    }

    public void fillTable(List<Staff> rawData){
        ObservableList<Staff> tableData = FXCollections.observableArrayList();
        tableData.setAll(rawData);
        this.staffTable.setItems(tableData);

    }


    public void createPopup(int index, Staff staff){
        Stage stage = new Stage();

        AnchorPane root = new AnchorPane();
        root.setPrefSize(300,300);
        VBox content = null;

        switch (index){
            case 1:
                content = fillAddPopup(stage, root);
                break;
            case 2:
                content = fillSearchPopup(stage, root);
                break;
            case 3:
                content = fillUpdatePopup(stage, staff);
                break;
            case 4:
                content = fillDeletePopup(stage,staff,root);
                break;
            default:
                break;
        }

        AnchorPane.setTopAnchor(content,5.0);
        AnchorPane.setBottomAnchor(content,5.0);
        AnchorPane.setLeftAnchor(content,5.0);
        AnchorPane.setRightAnchor(content,5.0);

        Button closeBtn = new Button("Close");
        closeBtn.setOnMouseClicked(mouseEvent -> stage.close());

        content.setStyle("-fx-background-color:silver; -fx-background-radius:5");
        content.getChildren().add(closeBtn);

        root.getChildren().add(content);
        stage.setScene(new Scene(root));
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public VBox fillAddPopup(Stage stage, AnchorPane root){
        root.setPrefSize(400,250);
        VBox container = new VBox(10);
        container.setPadding(new Insets(5));
        stage.setTitle("Add new staff member");

        HBox nameRow = new HBox(5);
        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        TextField nickname = new TextField();
        nickname.setPromptText("Nickname");
        nameRow.getChildren().addAll(firstName,lastName,nickname);
        firstName.setOnMouseClicked(actionEvent -> firstName.setStyle(""));
        lastName.setOnMouseClicked(actionEvent -> lastName.setStyle(""));
        nickname.setOnMouseClicked(actionEvent -> nickname.setStyle(""));

        HBox addressRow1 = new HBox(5);
        ComboBox<String> streetAddress = new ComboBox<>();
        HashMap<String, Address> addressHashMap= new HashMap<>();
        for (Address address: addressDAO.getAllAddress()){
            addressHashMap.put(address.getAddress(),address);
            streetAddress.getItems().add(address.getAddress());
        }

        streetAddress.setPromptText("Street Address");
        streetAddress.setEditable(true);
        addressRow1.getChildren().addAll(streetAddress);
        streetAddress.setOnMouseClicked(actionEvent -> streetAddress.setStyle(""));

        HBox addressRow2 = new HBox(5);
        TextField district = new TextField();
        district.setPromptText("District");
        TextField city = new TextField();
        city.setPromptText("City");
        addressRow2.getChildren().addAll(district,city);
        district.setOnMouseClicked(actionEvent -> district.setStyle(""));
        city.setOnMouseClicked(actionEvent -> city.setStyle(""));

        HBox addressRow3 = new HBox(5);
        TextField postcode = new TextField();
        postcode.setPromptText("Postcode");
        TextField country = new TextField();
        country.setPromptText("Country");
        addressRow3.getChildren().addAll(postcode,country);
        postcode.setOnMouseClicked(actionEvent -> postcode.setStyle(""));
        country.setOnMouseClicked(actionEvent -> country.setStyle(""));


        AtomicBoolean existingAddress = new AtomicBoolean(false);
        streetAddress.setOnAction(actionEvent -> {
            if (!streetAddress.getValue().equalsIgnoreCase(null)){
                try {
                    Address tempAddress = addressHashMap.get(streetAddress.getValue());
                    existingAddress.set(true);
                    district.setText(tempAddress.getDistrict());
                    city.setText(tempAddress.getCity());
                    postcode.setText(tempAddress.getPostcode());
                    country.setText(tempAddress.getCountry());
                } catch (Exception e) {
                    district.clear();
                    city.clear();
                    postcode.clear();
                    country.clear();
                    existingAddress.set(false);
                }
            }
        });

        HBox emailRow = new HBox();
        TextField email = new TextField();
        email.setPromptText("E-mail");
        emailRow.getChildren().addAll(email);
        email.setOnMouseClicked(mouseEvent -> email.setStyle(""));

        Button addStaff = new Button("Add new");
        container.getChildren().addAll(nameRow,addressRow1,addressRow2,addressRow3,emailRow, addStaff);

        addStaff.setOnMouseClicked(mouseEvent -> {
            Staff newStaff = new Staff(firstName.getText(),lastName.getText(),nickname.getText(),email.getText());
            Address newAddress = new Address(streetAddress.getValue(), district.getText(), city.getText(), postcode.getText(),country.getText());
            if (!firstName.getText().equalsIgnoreCase("")
                    && !lastName.getText().equalsIgnoreCase("")
                    && !nickname.getText().equalsIgnoreCase("")
                    && !streetAddress.getValue().equalsIgnoreCase("")
                    && !city.getText().equalsIgnoreCase("")
                    && !postcode.getText().equalsIgnoreCase("")
                    && !country.getText().equalsIgnoreCase("")
                    && !email.getText().equalsIgnoreCase("")){
                try {
                    if (existingAddress.get()){
                        newAddress = addressHashMap.get(streetAddress.getValue());
                        newAddress.getStaff().add(newStaff);
                        addressDAO.updateAddress(newAddress);
                        staffDAO.saveStaff(newStaff);
                        newStaff.setAddress(newAddress);
                        staffDAO.updateStaff(newStaff);
                    }
                    newStaff.setAddress(newAddress);
                    newAddress.getStaff().add(newStaff);
                    staffDAO.saveStaff(newStaff);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                fillTable(staffDAO.getAllStaff());
                stage.close();

            } else {
                for (Node row : container.getChildren()){
                    if (row instanceof HBox){
                        for (Node field : ((HBox) row).getChildren()){
                            if (field instanceof TextField textField){
                                if (textField.getText().equalsIgnoreCase("")){
                                    textField.setStyle("-fx-background-color:indianred");
                                }
                            }
                        }
                    }
                }
                if (streetAddress.getValue() == null){
                    streetAddress.setStyle("-fx-background-color:indianred");
                }
            }
        });


        return container;
    }

    public VBox fillSearchPopup(Stage stage, AnchorPane root){
        root.setPrefHeight(250);
        VBox container = new VBox(10);
        container.setPadding(new Insets(5));
        stage.setTitle("Search for staff members");

        HashMap<String, Staff> staffHashMap = new HashMap<>();
        List<Staff> staffSelection = new ArrayList<>();

        ComboBox<Object> staffDropdown = new ComboBox<>();
        for (Staff staff: staffDAO.getAllStaff()){
            String fullName = staff.getFirstName() + " \""+ staff.getNickName()+"\" "+ staff.getLastName();
            staffHashMap.put(fullName, staff);
            staffDropdown.getItems().add(fullName);
        }

        TextArea staffChoices = new TextArea();
        staffChoices.setEditable(false);
        staffChoices.setPrefRowCount(3);
        staffChoices.setPrefColumnCount(15);

        Button multBtn = new Button("Select");
        multBtn.setDisable(true);
        multBtn.setOnMouseClicked(mouseEvent -> {
            fillTable(staffSelection);
            stage.close();
        });


        Button addChoice = new Button("Add");
        addChoice.setOnMouseClicked(mouseEvent -> {
            String choice = (String) staffDropdown.getValue();
            if (!staffSelection.contains(staffHashMap.get(choice))){
                staffChoices.appendText(choice+"\n");
                staffSelection.add(staffHashMap.get(choice));
            }
            if (!staffSelection.isEmpty()){
                multBtn.setDisable(false);
            }
            if (staffSelection.size()> 1){
                multBtn.setText("Select Multiple");
            }
        });

        Button allBtn = new Button("Select All");
        allBtn.setOnMouseClicked(mouseEvent -> {
            fillTable(staffDAO.getAllStaff());
            stage.close();
        });

        container.getChildren().addAll(staffDropdown, addChoice,staffChoices,multBtn,allBtn);
        return container;
    }

    public VBox fillUpdatePopup(Stage stage, Staff staff){
        VBox container = new VBox(5);
        stage.setTitle("Update "+ staff.getFirstName());
        container.setFillWidth(true);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.TOP_CENTER);

        HBox row1 = new HBox(10);
        row1.setAlignment(Pos.CENTER_LEFT);
        Label fNameLabel = new Label("First Name:");
        fNameLabel.setPrefWidth(80);
        TextField firstName = new TextField(staff.getFirstName());
        row1.getChildren().addAll(fNameLabel,firstName);

        HBox row2 = new HBox(10);
        row2.setAlignment(Pos.CENTER_LEFT);
        Label lNameLabel = new Label("Last Name:");
        lNameLabel.setPrefWidth(80);
        TextField lastName = new TextField(staff.getLastName());
        row2.getChildren().addAll(lNameLabel,lastName);

        HBox row3 = new HBox(10);
        row3.setAlignment(Pos.CENTER_LEFT);
        Label nNameLabel = new Label("Nickname:");
        nNameLabel.setPrefWidth(80);
        TextField nickname = new TextField(staff.getNickName());
        row3.getChildren().addAll(nNameLabel,nickname);

        HBox row4 = new HBox(10);
        row4.setAlignment(Pos.CENTER_LEFT);
        Label addressLabel = new Label("Street Address:");
        addressLabel.setPrefWidth(80);
        TextField streetAddress = new TextField(staff.getAddress().getAddress());
        row4.getChildren().addAll(addressLabel,streetAddress);

        HBox row5 = new HBox(10);
        row5.setAlignment(Pos.CENTER_LEFT);
        Label districtLabel = new Label("District:");
        districtLabel.setPrefWidth(80);
        TextField district = new TextField(staff.getAddress().getDistrict());
        row5.getChildren().addAll(districtLabel,district);

        HBox row6 = new HBox(10);
        row6.setAlignment(Pos.CENTER_LEFT);
        Label cityLabel = new Label("City:");
        cityLabel.setPrefWidth(80);
        TextField city = new TextField(staff.getAddress().getCity());
        row6.getChildren().addAll(cityLabel,city);

        HBox row7 = new HBox(10);
        row7.setAlignment(Pos.CENTER_LEFT);
        Label postcodeLabel = new Label("Postcode:");
        postcodeLabel.setPrefWidth(80);
        TextField postcode = new TextField(staff.getAddress().getPostcode());
        row7.getChildren().addAll(postcodeLabel,postcode);

        HBox row8 = new HBox(10);
        row8.setAlignment(Pos.CENTER_LEFT);
        Label countryLabel = new Label("Country:");
        countryLabel.setPrefWidth(80);
        TextField country = new TextField(staff.getAddress().getCountry());
        row8.getChildren().addAll(countryLabel,country);

        HBox row9 = new HBox(10);
        row9.setAlignment(Pos.CENTER_LEFT);
        Label emailLabel = new Label("Email:");
        emailLabel.setPrefWidth(80);
        TextField email = new TextField(staff.getEmail());
        row9.getChildren().addAll(emailLabel,email);


        Button updateBtn = new Button("Update");
        updateBtn.setOnMouseClicked(mouseEvent -> {
            staff.setFirstName(firstName.getText());
            staff.setLastName(lastName.getText());
            staff.setNickName(nickname.getText());
            staff.setEmail(email.getText());

            Address checkAddress = staff.getAddress();
            Address tempAddress = new Address(streetAddress.getText(),district.getText(),city.getText(),postcode.getText(),country.getText());

            if (!tempAddress.getAddress().equalsIgnoreCase(checkAddress.getAddress())
                    || !tempAddress.getDistrict().equalsIgnoreCase(checkAddress.getDistrict())
                    || !tempAddress.getCity().equalsIgnoreCase(checkAddress.getCity())
                    || !tempAddress.getPostcode().equalsIgnoreCase(checkAddress.getPostcode())
                    || !tempAddress.getCountry().equalsIgnoreCase(checkAddress.getCountry())){
                fillAddressCheck(staff,tempAddress);
            } else {
                staffDAO.updateStaff(staff);
                fillTable(staffDAO.getAllStaff());
            }

            System.out.println(checkAddress.getAddress() + " "+ tempAddress.getAddress());
            stage.close();

        });
        container.getChildren().addAll(row1,row2,row3,row4,row5,row6,row7,row8,row9,updateBtn);
        return container;
    }
    public void fillAddressCheck(Staff staff, Address address){
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(5));
        AnchorPane.setTopAnchor(container,5.0);
        AnchorPane.setBottomAnchor(container,5.0);
        AnchorPane.setLeftAnchor(container,5.0);
        AnchorPane.setRightAnchor(container,5.0);

        Text choiceText = new Text("Do you want to edit the existing address or create a new one?");
        HBox btnContainer = new HBox(20);
        btnContainer.setAlignment(Pos.CENTER);
        Button editBtn = new Button("Edit existing");
        editBtn.setOnMouseClicked(mouseEvent -> {
            address.setAddressId(staff.getAddress().getAddressId());
            staffDAO.updateStaff(staff);
            addressDAO.updateAddress(address);
            fillTable(staffDAO.getAllStaff());
            stage.close();
        });

        Button createBtn = new Button("Create New");
        createBtn.setOnMouseClicked(mouseEvent -> {
            addressDAO.saveAddress(address);
            staff.setAddress(address);
            staffDAO.updateStaff(staff);
            fillTable(staffDAO.getAllStaff());
            stage.close();
        });


        container.setStyle("-fx-background-color:silver; -fx-background-radius: 5");
        btnContainer.getChildren().addAll(editBtn,createBtn);
        container.getChildren().addAll(choiceText,btnContainer);
        root.getChildren().add(container);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public VBox fillDeletePopup(Stage stage, Staff staff, AnchorPane root){
        VBox container = new VBox(10);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.TOP_CENTER);
        stage.setTitle("Delete "+ staff.getFirstName());

        Text alert = new Text();
        alert.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(alert);
        if (currentUser.containsKey(staff.getStaffId()) && currentUser.containsValue(staff.getFullName()) ){
            alert.setText("You can not delete yourself from the database.");
        } else {
            alert.setText("Are you sure you want to delete:\n\n"+ staff.getFullName());
            Button deleteBtn = new Button("Delete");
            deleteBtn.setOnMouseClicked(mouseEvent -> {
                staffDAO.deleteStaff(staff);
                stage.close();
                fillTable(staffDAO.getAllStaff());
            });
            container.getChildren().add(deleteBtn);
        }
        root.setPrefHeight(100);
        return container;
    }

    //GETTERS AND SETTERS

    public AnchorPane getStaffTab() throws FileNotFoundException {
        createStaffTab();
        return staffTab;
    }

    public HashMap<Integer, String> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(HashMap<Integer, String> currentUser) {
        this.currentUser = currentUser;
    }
}

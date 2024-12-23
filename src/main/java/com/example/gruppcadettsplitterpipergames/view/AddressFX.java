package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.AddressDAO;
import com.example.gruppcadettsplitterpipergames.entities.Address;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddressFX {
    private AddressDAO addressDAO = new AddressDAO();
    private TabPane parent;
    private TableView addressTable;
    private AnchorPane addressTab;

    public AddressFX() {
    }

    public AddressFX(TabPane parent) {
        this.parent = parent;
    }

    public void createAddressTab() throws FileNotFoundException {

        this.parent.setMinSize(800,400);
        this.addressTab = new AnchorPane();
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
        Text title = new Text("Addresses");

        BorderPane header = new BorderPane();
        header.setRight(logo);
        header.setCenter(title);

        this.addressTable = createAddressTable();

        HBox btnContainer = new HBox(5);
        btnContainer.setAlignment(Pos.CENTER);

        Button addBtn = new Button("Add new address");
        addBtn.setOnMouseClicked(mouseEvent -> {
            createPopup(1, null);
        });
        Button searchBtn = new Button("Search");
        searchBtn.setOnMouseClicked(mouseEvent -> {
            createPopup(2, null);
        });
        addBtn.setPrefSize(200,40);
        searchBtn.setPrefSize(200,40);

        btnContainer.getChildren().addAll(addBtn, searchBtn);
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold");
        container.setStyle("-fx-background-color: silver; -fx-background-radius:0 0 5 5;");
        container.getChildren().addAll(header, addressTable, btnContainer);
        addressTab.getChildren().addAll(container);
    }

    public TableView createAddressTable(){
        TableView table = new TableView<>();
        TableColumn<Address,String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAddressId())));
        idCol.setPrefWidth(30);
        TableColumn<Address, String> streetCol = new TableColumn<>("Street Address");
        streetCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        streetCol.setPrefWidth(150);
        TableColumn<Address, String> districtCol = new TableColumn<>("District");
        districtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDistrict()));
        districtCol.setPrefWidth(70);
        TableColumn<Address, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        cityCol.setPrefWidth(70);
        TableColumn<Address, String> postcodeCol = new TableColumn<>("Postcode");
        postcodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPostcode()));
        postcodeCol.setPrefWidth(70);
        TableColumn<Address, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        countryCol.setPrefWidth(50);
        TableColumn<Address,Void> updateCol =  new TableColumn<>("Update");
        updateCol.setCellFactory(col -> new TableCell<>(){
            Button updateBtn = new Button("Update");
            {
                updateBtn.setOnMouseClicked(mouseEvent -> {
                    TableColumn idColumn = (TableColumn) addressTable.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    createPopup(3,addressDAO.getAddressById(id));
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
        TableColumn<Address,Void> deleteCol =  new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<>(){
            Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnMouseClicked(mouseEvent -> {
                    TableColumn idColumn = (TableColumn) addressTable.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    createPopup(4,addressDAO.getAddressById(id));
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

        table.getColumns().addAll(idCol, streetCol,districtCol,cityCol,postcodeCol,countryCol, updateCol, deleteCol);
        return table;
    }

    public void fillTable(List<Address> rawData){
        ObservableList<Address> tableData = FXCollections.observableArrayList();
        tableData.setAll(rawData);
        this.addressTable.setItems(tableData);
    }

    public void createPopup(int index, Address address){
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
                content = fillUpdatePopup(stage, address);
                break;
            case 4:
                content = fillDeletePopup(stage, address, root);
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
        stage.setTitle("Add new address");

        HBox addressRow1 = new HBox(5);
        TextField streetAddress = new TextField();
        streetAddress.setPromptText("Street Address");
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

        Button addAddress = new Button("Add new");
        container.getChildren().addAll(addressRow1,addressRow2,addressRow3, addAddress);

        addAddress.setOnMouseClicked(mouseEvent -> {
           Address newAddress = new Address(streetAddress.getText(), district.getText(), city.getText(), postcode.getText(),country.getText());
            if (!streetAddress.getText().equalsIgnoreCase("")
                    && !city.getText().equalsIgnoreCase("")
                    && !postcode.getText().equalsIgnoreCase("")
                    && !country.getText().equalsIgnoreCase("")){
                try {
                    addressDAO.saveAddress(newAddress);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                fillTable(addressDAO.getAllAddress());
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
            }
        });
        return container;
    }

    public VBox fillSearchPopup(Stage stage, AnchorPane root){
        root.setPrefHeight(250);
        VBox container = new VBox(10);
        container.setPadding(new Insets(5));
        stage.setTitle("Search addresses");

        HashMap<String, Address> addressHashMap = new HashMap<>();
        List<Address> addressSelection = new ArrayList<>();

        ComboBox<Object> addressDropdown = new ComboBox<>();
        for (Address address: addressDAO.getAllAddress()){
            String fullAddress = address.getAddress() + ", "+address.getCity()+", "+address.getPostcode();
            addressHashMap.put(fullAddress, address);
            addressDropdown.getItems().add(fullAddress);
        }

        TextArea addressChoices = new TextArea();
        addressChoices.setEditable(false);
        addressChoices.setPrefRowCount(3);
        addressChoices.setPrefColumnCount(15);

        Button multBtn = new Button("Select");
        multBtn.setDisable(true);
        multBtn.setOnMouseClicked(mouseEvent -> {
            fillTable(addressSelection);
            stage.close();
        });


        Button addChoice = new Button("Add");
        addChoice.setOnMouseClicked(mouseEvent -> {
            String choice = (String) addressDropdown.getValue();
            if (!addressSelection.contains(addressHashMap.get(choice))){
                addressChoices.appendText(choice+"\n");
                addressSelection.add(addressHashMap.get(choice));
            }
            if (!addressSelection.isEmpty()){
                multBtn.setDisable(false);
            }
            if (addressSelection.size()> 1){
                multBtn.setText("Select Multiple");
            }
        });

        Button allBtn = new Button("Select All");
        allBtn.setOnMouseClicked(mouseEvent -> {
            fillTable(addressDAO.getAllAddress());
            stage.close();
        });

        container.getChildren().addAll(addressDropdown, addChoice, addressChoices,multBtn,allBtn);
        return container;
    }

    public VBox fillUpdatePopup(Stage stage, Address address){
        VBox container = new VBox(5);
        stage.setTitle("Update "+ address.getAddress());
        container.setFillWidth(true);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.TOP_CENTER);

        HBox row1 = new HBox(10);
        row1.setAlignment(Pos.CENTER_LEFT);
        Label addressLabel = new Label("Street Address:");
        addressLabel.setPrefWidth(80);
        TextField streetAddress = new TextField(address.getAddress());
        row1.getChildren().addAll(addressLabel,streetAddress);

        HBox row2 = new HBox(10);
        row2.setAlignment(Pos.CENTER_LEFT);
        Label districtLabel = new Label("District:");
        districtLabel.setPrefWidth(80);
        TextField district = new TextField(address.getDistrict());
        row2.getChildren().addAll(districtLabel,district);

        HBox row3 = new HBox(10);
        row3.setAlignment(Pos.CENTER_LEFT);
        Label cityLabel = new Label("City:");
        cityLabel.setPrefWidth(80);
        TextField city = new TextField(address.getCity());
        row3.getChildren().addAll(cityLabel,city);

        HBox row4 = new HBox(10);
        row4.setAlignment(Pos.CENTER_LEFT);
        Label postcodeLabel = new Label("Postcode:");
        postcodeLabel.setPrefWidth(80);
        TextField postcode = new TextField(address.getPostcode());
        row4.getChildren().addAll(postcodeLabel,postcode);

        HBox row5 = new HBox(10);
        row5.setAlignment(Pos.CENTER_LEFT);
        Label countryLabel = new Label("Country:");
        countryLabel.setPrefWidth(80);
        TextField country = new TextField(address.getCountry());
        row5.getChildren().addAll(countryLabel,country);


        Button updateBtn = new Button("Update");
        updateBtn.setOnMouseClicked(mouseEvent -> {
            address.setAddress(streetAddress.getText());
            address.setDistrict(district.getText());
            address.setCity(city.getText());
            address.setPostcode(postcode.getText());
            address.setCountry(country.getText());

            try {
                addressDAO.updateAddress(address);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            stage.close();

        });
        container.getChildren().addAll(row1,row2,row3,row4, row5, updateBtn);
        return container;
    }


    public VBox fillDeletePopup(Stage stage, Address address, AnchorPane root){
        VBox container = new VBox(10);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.TOP_CENTER);
        stage.setTitle("Delete "+ address.getAddress());

        Text alert = new Text();
        alert.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(alert);
        if (!address.getStaff().isEmpty() || !address.getPlayers().isEmpty()){
            alert.setText("There are currently " + (address.getStaff().size() + address.getPlayers().size()) +
                    " people living at this address.\n\n" +
                    "Unable to delete address");
        } else {
            alert.setText("Are you sure you want to delete:\n\n" +
                    address.getAddress()+ "\n" +
                    address.getDistrict()+ "\n" +
                    address.getCity()+ "\n" +
                    address.getPostcode()+ "\n" +
                    address.getCountry());
            Button deleteBtn = new Button("Delete");
            deleteBtn.setOnMouseClicked(mouseEvent -> {
                try {
                    System.out.println("Deleted Here");
                    addressDAO.deleteAddress(address);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                stage.close();
                fillTable(addressDAO.getAllAddress());
            });
            container.getChildren().add(deleteBtn);
        }
        root.setPrefHeight(100);
        return container;
    }



    //GETTERS AND SETTERS

    public AnchorPane getAddressTab() throws FileNotFoundException {
        createAddressTab();
        fillTable(addressDAO.getAllAddress());
        return addressTab;
    }

    public void setAddressTab(AnchorPane addressTab) {
        this.addressTab = addressTab;
    }
}

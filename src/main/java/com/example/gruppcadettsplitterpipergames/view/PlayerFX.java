package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.AddressDAO;
import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Address;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerFX {         //Lynsey Fox

    private ObservableList<Player> playerList;
    private PlayerDAO playerDAO;
    private AnchorPane playerView;
    GamesDAO gamesDAO = new GamesDAO();
    private TableView<Player> playerTableView;

    public PlayerFX() throws FileNotFoundException {
        this.playerDAO = new PlayerDAO();

        playerList = FXCollections.observableArrayList();
        playerView = new AnchorPane();

        VBox container = new VBox(30);
        container.setAlignment(Pos.TOP_CENTER);
        container.setPadding(new Insets(0, 20, 20, 20));
        Label title = new Label("Players");
        title.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");
        title.setAlignment(Pos.TOP_CENTER);
        container.setStyle("-fx-background-color: silver");
        HBox buttonHolder = new HBox(30);
        buttonHolder.setAlignment(Pos.CENTER);
        // logo code from Benjamin
        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(50,50,40);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(100);
        BorderPane header = new BorderPane();header.setRight(logo);header.setCenter(title);

        playerTableView = createPlayerTableView();
        playerTableView.setPrefHeight(250);

        container.getChildren().addAll(header, title, playerTableView, buttonHolder);
        container.setMinWidth(800);
        container.setMinHeight(600);

        Button searchGames = new Button("Search");
        searchGames.setOnAction(e -> playerSearchPopUp());


        Button addGame = new Button("Add Player");
        addGame.setOnAction(e1 -> {
            if (addPlayerBox().get()) {
                loadPlayersFromDB(playerDAO.getAllPlayers());
                }
            });

        Button refreshTable = new Button("Refresh Table");
        refreshTable.setOnAction(e1 -> loadPlayersFromDB(playerDAO.getAllPlayers()));

        buttonHolder.getChildren().addAll(refreshTable, searchGames, addGame);
        buttonHolder.setStyle("-fx-background-color: silver");
        buttonHolder.setAlignment(Pos.BOTTOM_CENTER);

        AnchorPane.setBottomAnchor(container, 5.0);
        AnchorPane.setLeftAnchor(container, 5.0);
        AnchorPane.setRightAnchor(container, 5.0);
        AnchorPane.setTopAnchor(container, 0.0);
        playerView.getChildren().addAll(container);
        playerView.setPadding(new Insets(15));

        loadPlayersFromDB(playerDAO.getAllPlayers());
    }

    public TableView<Player> createPlayerTableView() {
        TableView<Player> playerTableView = new TableView<>();

        TableColumn<Player, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPlayerId())));
        idCol.setPrefWidth(50);

        TableColumn<Player, String> firstNameCol = new TableColumn<>("Player First Name");
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        firstNameCol.setPrefWidth(200);

        TableColumn<Player, String> NicknameCol = new TableColumn<>("Player Nick Name");
        NicknameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNickName()));
        NicknameCol.setPrefWidth(200);

        TableColumn<Player, String> lastNameCol = new TableColumn<>("Player Last Name");
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        lastNameCol.setPrefWidth(200);

        TableColumn<Player,String> gameCol = new TableColumn<>("Game");
        gameCol.setCellValueFactory(cellData -> {
            Game game = cellData.getValue().getGame();
            return new SimpleStringProperty(game != null ? game.getGameName() : "");
        });
        gameCol.setPrefWidth(200);

        TableColumn<Player, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(cellData -> {
            Team team = cellData.getValue().getTeam();
            return new SimpleStringProperty(team != null ? team.getName() : "");
        });
        teamCol.setPrefWidth(200);

        TableColumn<Player, String> streetAddressCol = new TableColumn<>("Street Address");
        streetAddressCol.setCellValueFactory(cellData -> {
            Address address = cellData.getValue().getAddress();
            return new SimpleStringProperty(address != null ? address.getAddress() : "");
        });
        streetAddressCol.setPrefWidth(200);

        TableColumn<Player, String> districtCol = new TableColumn<>("District");
        districtCol.setCellValueFactory(cellData -> {
            Address address = cellData.getValue().getAddress();
            return new SimpleStringProperty(address != null ? address.getDistrict() : "");
        });
        districtCol.setPrefWidth(200);

        TableColumn<Player, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(cellData -> {
                    Address address = cellData.getValue().getAddress();
                    return new SimpleStringProperty(address != null ? address.getCity() : "");
        });
        cityCol.setPrefWidth(200);

        TableColumn<Player,String> postcodeCol = new TableColumn<>("Postcode");
        postcodeCol.setCellValueFactory(cellData -> {
                    Address address = cellData.getValue().getAddress();
                    return new SimpleStringProperty(address != null ? address.getPostcode() : "");
        });
        postcodeCol.setPrefWidth(200);

        TableColumn<Player, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(cellData -> {
                    Address address = cellData.getValue().getAddress();
                    return new SimpleStringProperty(address != null ? address.getCountry() : "");
        });
        countryCol.setPrefWidth(200);

        TableColumn<Player,String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        emailCol.setPrefWidth(200);

        TableColumn<Player,Void> updateCol =  new TableColumn<>("Update");
        updateCol.setCellFactory(col -> new TableCell<Player,Void>(){
            Button updatePlayer = new Button("Update Player");

            {
                updatePlayer.setOnAction(e1 -> {
                    TableColumn idColumn =  playerTableView.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    Player playerToUpdate = playerDAO.getPlayerById(id);
                    if(playerUpdateBox(playerToUpdate)){
                        loadPlayersFromDB(playerDAO.getAllPlayers());
                        }
                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(updatePlayer);
                }
            }
        });
        TableColumn<Player,Void> deleteCol =  new TableColumn<>("Delete");
        deleteCol.setCellFactory(col -> new TableCell<Player, Void>(){
            Button deletePlayer = new Button("Delete Player");
            {
                deletePlayer.setOnAction(e1 -> {
                    TableColumn idColumn = playerTableView.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    Player playerToDelete = playerDAO.getPlayerById(id);
                    //debugging line
                    System.out.println("Player to be deleted: " + playerToDelete.toString());
                    playerDAO.deletePlayerById(playerToDelete.getPlayerId());
                    if(deleteConfirmBox(playerToDelete).get()){
                            loadPlayersFromDB(playerDAO.getAllPlayers());
                    }
                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty){
                    setGraphic(null);
                } else {
                    setGraphic(deletePlayer);
                }
            }
        });

        playerTableView.getColumns().addAll(idCol, firstNameCol, NicknameCol, lastNameCol, gameCol, teamCol, streetAddressCol, districtCol, cityCol, postcodeCol, countryCol, emailCol, updateCol, deleteCol);
        return playerTableView;

    }

    private boolean playerUpdateBox(Player playerToUpdate) {
        AtomicBoolean result = new AtomicBoolean(false);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Game");
        window.setMinWidth(400);
        window.setMinHeight(500);

        Label label = new Label();
        label.setText("Update Player: " + playerToUpdate.getFirstName() + " " + playerToUpdate.getNickName() + " " + playerToUpdate.getLastName());
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 20");
        label.setWrapText(true);

        Label firstNameLabel = new Label("Update player's first name: ");
        TextField firstName = new TextField();
        firstName.setText(playerToUpdate.getFirstName());
        firstName.setMaxWidth(100);
        HBox row1 = new HBox(firstNameLabel, firstName);

        Label nickNameLabel = new Label("Update player's nick name: ");
        TextField nickName = new TextField();
        nickName.setText(playerToUpdate.getNickName());
        nickName.setMaxWidth(100);
        HBox row2 = new HBox(nickNameLabel, nickName);

        Label lastNameLabel = new Label("Update player's last name: ");
        TextField lastName = new TextField();
        lastName.setText(playerToUpdate.getLastName());
        lastName.setMaxWidth(100);
        HBox row3 = new HBox(lastNameLabel, lastName);

        Label chooseGameLabel = new Label("Choose a game: ");
        ComboBox<String> chooseGame = new ComboBox<>();
        chooseGame.getSelectionModel().select(playerToUpdate.getGame().getGameName());
        chooseGame.setMaxWidth(100);
        List<Game> gamesToSearch = new ArrayList<>();
        HashMap<String, Game> gameHashMap = new HashMap<>();

        for(Game game : gamesDAO.getAllGames()){
            gameHashMap.put(game.getGameName(), game);
            chooseGame.getItems().add(game.getGameName());
        }



        Label chooseTeamLabel = new Label("Choose a team (Optional): ");
        ComboBox<String> chooseTeam = new ComboBox<>();
        chooseTeam.setMaxWidth(100);
        if(playerToUpdate.getTeam() != null && playerToUpdate.getTeam().getName() != null){
            chooseTeam.getSelectionModel().select(playerToUpdate.getTeam().getName());
        }
        HashMap<String, Team> teamHashMap = new HashMap<>();

        Button confirmGame = new Button("Confirm game choice");
        confirmGame.setOnAction(e1 -> {
            gamesToSearch.add(gameHashMap.get(chooseGame.getSelectionModel().getSelectedItem()));
            System.out.println("Game chosen: "+gameHashMap.get(chooseGame.getSelectionModel().getSelectedItem()).getGameName());
        });

            for(Team team : new TeamsDAO().getTeamsByGameNames(gamesToSearch)){
                teamHashMap.put(team.getName(), team);
                chooseTeam.getItems().add(team.getName());
            }

        HBox row4 = new HBox(chooseGameLabel, chooseGame, confirmGame);
        HBox row5 = new HBox(chooseTeamLabel, chooseTeam);

        Label chooseAddressLabel = new Label("Choose an existing address: ");
        chooseAddressLabel.setMaxWidth(100);
        ComboBox<String> chooseAddress = new ComboBox<>();
        chooseAddress.setMaxWidth(100);
        HashMap<String, Address> addressHashMap = new HashMap<>();
        for(Address address: new AddressDAO().getAllAddress()){
            addressHashMap.put(address.getAddress(), address);
            chooseAddress.getItems().add(address.getAddress());
        }

        HBox row6 = new HBox(chooseAddressLabel, chooseAddress);

        Label addNewAddressLabel = new Label("Add a new address: ");

        Label newStreetAddressLabel = new Label("Add a new street address: ");
        newStreetAddressLabel.setMaxWidth(100);
        TextField newStreetAddress = new TextField();
        newStreetAddress.setMaxWidth(100);
        HBox row7 = new HBox(newStreetAddressLabel, newStreetAddress);

        Label newDistrictLabel = new Label("Choose a district: ");
        newDistrictLabel.setMaxWidth(100);
        TextField newDistrict = new TextField();
        newDistrict.setMaxWidth(100);

        HBox row8 = new HBox(newDistrictLabel, newDistrict);

        Label newCityLabel = new Label("Enter a city: ");
        newCityLabel.setMaxWidth(100);
        TextField newCity = new TextField();
        newCity.setMaxWidth(100);

        HBox row9 = new HBox(newCityLabel, newCity);

        Label newPostcodeLabel = new Label("Enter a postcode: ");
        newPostcodeLabel.setMaxWidth(100);
        TextField newPostcode = new TextField();
        newPostcode.setMaxWidth(100);

        HBox row10 = new HBox(newPostcodeLabel, newPostcode);

        Label newCountryLabel = new Label("Enter a country: ");
        newCountryLabel.setMaxWidth(100);
        TextField newCountry = new TextField();
        newCountry.setMaxWidth(100);

        HBox row11 = new HBox(newCountryLabel, newCountry);

        Label newEmailLabel = new Label("Enter a email: ");
        newEmailLabel.setMaxWidth(100);
        TextField newEmail = new TextField();
        newEmail.setMaxWidth(100);

        HBox row12 = new HBox(newEmailLabel, newEmail);


        Button update = new Button("Update");
        update.setDefaultButton(true);
        update.setOnAction(e -> {
            playerToUpdate.setFirstName(firstName.getText().trim());
            playerToUpdate.setNickName(nickName.getText());
            playerToUpdate.setLastName(lastName.getText());
            playerToUpdate.setGame(gameHashMap.get(chooseGame.getSelectionModel().getSelectedItem()));
            playerToUpdate.setTeam(teamHashMap.get(chooseTeam.getSelectionModel().getSelectedItem()));
            playerToUpdate.setEmail(newEmail.getText());
            //set Address if choosing from existing
            if(chooseAddress.getSelectionModel().getSelectedItem() != null){
                playerToUpdate.setAddress(addressHashMap.get(chooseAddress.getSelectionModel().getSelectedItem()));
            }else{
                //save new address
                Address newAddress = new Address();
                newAddress.setAddress(newStreetAddress.getText());
                newAddress.setDistrict(newDistrict.getText());
                newAddress.setCity(newCity.getText());
                newAddress.setPostcode(newPostcode.getText());
                newAddress.setCountry(newCountry.getText());
                new AddressDAO().saveAddress(newAddress);
                playerToUpdate.setAddress(newAddress);
            }

            playerDAO.updatePlayer(playerToUpdate);
            window.close();
            result.set(true);
            System.out.println(result.get());
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> {
            window.close();
            result.set(false);
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label,row1, row2, row3,row4,row5, row6, addNewAddressLabel,row7,row8,row9,row10,row11,row12,update, cancel);
        layout.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result.get();
    }


    public void loadPlayersFromDB(List<Player> playersToShow) {

        playerList.setAll(playersToShow);
        this.playerTableView.setItems(playerList);
    }

    private void playerSearchPopUp() {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Player Search");
        window.setMinWidth(400);
        window.setMinHeight(500);

        TableView<Player> viewSearchedPlayer = createPlayerTableView();
        viewSearchedPlayer.setPrefHeight(150);
        ObservableList<Player> searchedPlayerList = FXCollections.observableArrayList();

        Label title = new Label();
        title.setText("Search: ");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");

        Label label = new Label();
        label.setText("Choose search term: ");

        Label gameBoxLabel = new Label("Game/s Played:");
        Label teamBoxLabel = new Label("Team:");

        ObservableList<Game> gamesChoiceList = FXCollections.observableArrayList();
        HashMap<String, Game> gameHashMap = new HashMap<>();
        for (Game game: gamesDAO.getAllGames()) {
            gameHashMap.put(game.getGameName(), game);
            gamesChoiceList.add(game);
        }
        ListView<CheckBox> gamesChoice = new ListView<>();
        gamesChoice.setMaxHeight(100);

        for(Game game : gamesChoiceList) {
            CheckBox checkBox = new CheckBox(game.getGameName());
            gamesChoice.getItems().add(checkBox);
        }

        ComboBox teamChoice = new ComboBox();
        HashMap<String, Team> teamsHashMap = new HashMap<>();
        for (Team team: new TeamsDAO().getAllTeams()){
            teamsHashMap.put(team.getName(),team);
            teamChoice.getItems().add(team.getName());
        }
        teamChoice.setOnAction(e-> teamChoice.getSelectionModel().getSelectedItem());


        HBox comboBoxes = new HBox(10);
        comboBoxes.getChildren().addAll(gameBoxLabel, gamesChoice, teamBoxLabel, teamChoice);
        comboBoxes.setAlignment(Pos.CENTER);


        Button reset = new Button("Reset");
        reset.setOnAction(e-> {
            searchedPlayerList.clear();
            viewSearchedPlayer.setItems(searchedPlayerList);
            for( CheckBox checkBox : gamesChoice.getItems()) {
                checkBox.setSelected(false);
            }
            teamChoice.getSelectionModel().clearSelection();
        });

        Button searchByTeam = new Button("Search by Team");
        searchByTeam.setOnAction(e -> {

                Team teamToSearch = teamsHashMap.get(teamChoice.getSelectionModel().getSelectedItem());
                List<Player> playersFound = playerDAO.getPlayersByTeam(teamToSearch);
                System.out.println("Found " + playersFound.size() + " players in team " + teamToSearch.getName());
                searchedPlayerList.setAll(playersFound);
                viewSearchedPlayer.setItems(searchedPlayerList);
            });

        Button searchByGame = new Button("Search by Game");
        searchByGame.setOnAction(e -> {

                List<Game> gamesToSearch = new ArrayList<>();
                for(CheckBox checkBox: gamesChoice.getItems()){
                    if(checkBox.isSelected()){
                        gamesToSearch.add(gameHashMap.get(checkBox.getText()));
                    }
                }
                List<Player> playersFound = playerDAO.getPlayersByGame(gamesToSearch);
                searchedPlayerList.setAll(playersFound);
                viewSearchedPlayer.setItems(searchedPlayerList);
                System.out.println("Found " + playersFound.size() + " players in selected games: " + gamesToSearch.toString());

        });

        Button cancel = new Button("Close");
        cancel.setOnAction(e-> window.close());

        HBox buttonHolder = new HBox(10);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.getChildren().addAll(searchByGame, searchByTeam, reset,cancel);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(title,label ,comboBoxes, viewSearchedPlayer,buttonHolder);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(viewSearchedPlayer, new Insets(10));

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private AtomicBoolean addPlayerBox() {
        AtomicBoolean result = new AtomicBoolean(false);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add New Player");
        window.setMinWidth(400);
        window.setMinHeight(500);

        Label label = new Label();
        label.setText("Add Player");
        label.setAlignment(Pos.CENTER);
        label.setStyle(" -fx-font-size: 20");

        Label firstNameLabel = new Label("Enter player's first name:");
        TextField getFirstName = new TextField();
        getFirstName.setMaxWidth(100);

        Label nickNameLabel = new Label("Enter player's nick name:");
        TextField getNickName = new TextField();
        getNickName.setMaxWidth(100);

        Label lastNameLabel = new Label("Enter player's last name:");
        TextField getLastName = new TextField();
        getLastName.setMaxWidth(100);

        Label gameLabel = new Label("Select Game");
        ComboBox<String> addPlayerGame = new ComboBox<String>();
        List<Game> gamesToSearch = new ArrayList<>();
        HashMap<String, Game> gameHashMap = new HashMap<>();

        for(Game game : gamesDAO.getAllGames()){
            gameHashMap.put(game.getGameName(), game);
            addPlayerGame.getItems().add(game.getGameName());
        }

        Label teamLabel = new Label("Optional: Select Team");
        ComboBox addPlayerTeam = new ComboBox();
        HashMap<String, Team> teamHashMap = new HashMap<>();

        Button confirmGameButton = new Button("Confirm Game");
        confirmGameButton.setOnAction(e-> {
            gamesToSearch.add(gameHashMap.get(addPlayerGame.getSelectionModel().getSelectedItem()));

            for(Team team : new TeamsDAO().getTeamsByGameNames(gamesToSearch)){
                teamHashMap.put(team.getName(), team);
                addPlayerTeam.getItems().add(team.getName());
            }
        });
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Button save = new Button("Save");
        save.setDefaultButton(true);
        save.setOnAction(e-> {

            String givenNickName = getNickName.getText().trim();
            String givenFirstName = getFirstName.getText().trim();
            String givenLastName = getLastName.getText().trim();
            Game selectedGame = gameHashMap.get((String)addPlayerGame.getSelectionModel().getSelectedItem());
            Team selectedTeam = teamHashMap.get(String.valueOf(addPlayerTeam.getSelectionModel().getSelectedItem()));
            boolean valid = true;
            System.out.println("new player: " + givenFirstName + " " + givenLastName + " " + selectedGame.getGameName());

            getFirstName.setStyle("-fx-background-color: white");
            getNickName.setStyle("-fx-background-color: white");
            getLastName.setStyle("-fx-background-color: white");
            addPlayerGame.setStyle("-fx-background-color: white");
            addPlayerTeam.setStyle("-fx-background-color: white");
            errorLabel.setText("");

            if(givenFirstName.isEmpty()) {
                getFirstName.setStyle("-fx-background-color: red");
                valid = false;
            }

            if(givenNickName.isEmpty()) {
                getNickName.setStyle("-fx-background-color: red");
                valid = false;
            }

            if(givenLastName.isEmpty()) {
                getLastName.setStyle("-fx-background-color: red");
                valid = false;
            }

            if(valid){
                Player playerToAdd = new Player(givenFirstName,givenNickName,givenLastName);
                playerDAO.savePlayer(playerToAdd);
                playerToAdd.setGame(selectedGame);
                playerDAO.updatePlayer(playerToAdd);
                if(selectedTeam != null){
                    playerToAdd.setTeam(selectedTeam);
                    playerDAO.updatePlayer(playerToAdd);
                }
                //testing
                if(playerDAO.savePlayer(playerToAdd)){
                    System.out.println("Player " + playerToAdd.getFirstName() + " " + playerToAdd.getLastName() + " has been added");
                }else {
                    System.out.println("Player not added");
                }


                loadPlayersFromDB(playerDAO.getAllPlayers());
                window.close();
                result.set(true);

            }

        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> {
            window.close();
            result.set(false);
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, firstNameLabel, getFirstName, nickNameLabel,getNickName, lastNameLabel, getLastName,gameLabel, addPlayerGame, confirmGameButton, teamLabel, addPlayerTeam, save, cancel);
        layout.setAlignment(Pos.CENTER);

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return result;
    }

        //den har funktion har fungerat tidigare men inte längre, jag har försökt att justera flera saker men inte lyckades
    public AtomicBoolean deleteConfirmBox(Player playerToDelete){
        AtomicBoolean result = new AtomicBoolean(false);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Delete");
        window.setMinWidth(400);
        window.setResizable(false);
        window.setMinHeight(400);

        Label title = new Label("Confirm Delete");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");
        Label label = new Label();
        label.setText("Are you sure you want to delete " + playerToDelete.getFirstName() + " " + playerToDelete.getNickName() + " " + playerToDelete.getLastName() + "?");

        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");
        delete.setOnAction(e -> {
            playerDAO.deletePlayer(playerToDelete);
            System.out.println("DeletePlayer status: " + playerDAO.deletePlayer(playerToDelete));
            window.close();
            loadPlayersFromDB(playerDAO.getAllPlayers());

        });
        cancel.setOnAction(e -> {
            result.set(false);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(title,label, delete, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return result;

    }

    public AnchorPane getPlayerView() {
        return playerView;
    }

    public void setPlayerView(AnchorPane playerView) {
        this.playerView = playerView;
    }
}

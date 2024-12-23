package com.example.gruppcadettsplitterpipergames.view;

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
                    playerToDelete.setAddress(null);
                    playerDAO.updatePlayer(playerToDelete);
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

        Label firstNameLabel = new Label("Update player's first name: " + playerToUpdate.getFirstName());
        TextField firstName = new TextField();
        firstName.setPromptText(playerToUpdate.getFirstName());
        firstName.setMaxWidth(100);

        Label nickNameLabel = new Label("Update player's nick name: " + playerToUpdate.getNickName());
        TextField nickName = new TextField();
        nickName.setMaxWidth(100);

        Label lastNameLabel = new Label("Update player's last name: "+ playerToUpdate.getLastName());
        TextField lastName = new TextField();
        lastName.setMaxWidth(100);

        Button update = new Button("Update");
        update.setDefaultButton(true);
        update.setOnAction(e -> {
            if(!firstName.getText().isEmpty()){
                playerToUpdate.setFirstName(firstName.getText());
            } else if (!nickName.getText().isEmpty()) {
                playerToUpdate.setNickName(nickName.getText());
            } else if (!lastName.getText().isEmpty()) {
                playerToUpdate.setLastName(lastName.getText());
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
        layout.getChildren().addAll(label, firstNameLabel, firstName,nickNameLabel, nickName, lastNameLabel, lastName,update, cancel);
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
        ComboBox addPlayerGame = new ComboBox();
        List<Game> gamesToSearch = new ArrayList<>();
        HashMap<String, Game> gameHashMap = new HashMap<>();

        for(Game game : gamesDAO.getAllGames()){
            gameHashMap.put(game.getGameName(), game);
            addPlayerGame.getItems().add(game.getGameName());
        }

        Label teamLabel = new Label("Optional: Select Team");
        ComboBox addPlayerTeam = new ComboBox();
        HashMap<String, Team> teamHashMap = new HashMap<>();

        addPlayerGame.setOnAction(e-> {
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

            if( ((String) addPlayerGame.getSelectionModel().getSelectedItem()).isEmpty()) {
                addPlayerGame.setStyle("-fx-background-color: red");
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
        layout.getChildren().addAll(label, firstNameLabel, getFirstName, nickNameLabel,getNickName, lastNameLabel, getLastName,gameLabel, addPlayerGame, teamLabel, addPlayerTeam, save, cancel);
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

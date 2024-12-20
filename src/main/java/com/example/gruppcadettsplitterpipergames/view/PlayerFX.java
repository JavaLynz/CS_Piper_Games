package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
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

    private ObservableList<Player> playerList = null;
    private PlayerDAO playerDAO;
    private AnchorPane playerView;
    GamesDAO gamesDAO = new GamesDAO();

    public PlayerFX() throws FileNotFoundException {
        playerDAO = new PlayerDAO();

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

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80, 80, 70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        TableView<Player> playerTableView = createPlayerTableView();
        playerTableView.setPrefHeight(250);

        container.getChildren().addAll(logo, title, playerTableView, buttonHolder);
        container.setMinWidth(800);
        container.setMinHeight(600);

        Button searchGames = new Button("Search");
        searchGames.setOnAction(e -> {

            try {
                playerSearchPopUp();

            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        });


        Button addGame = new Button("Add Player");
        addGame.setOnAction(e1 -> {
            try {
                if (addPlayerBox().get()) {
                    playerTableView.refresh();

                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button refreshTable = new Button("Refresh Table");
        refreshTable.setOnAction(e1 -> {
            loadPlayersFromDB(playerTableView);
        });

        buttonHolder.getChildren().addAll(refreshTable, searchGames, addGame);
        buttonHolder.setStyle("-fx-background-color: silver");
        buttonHolder.setAlignment(Pos.BOTTOM_CENTER);

        AnchorPane.setBottomAnchor(container, 5.0);
        AnchorPane.setLeftAnchor(container, 5.0);
        AnchorPane.setRightAnchor(container, 5.0);
        AnchorPane.setTopAnchor(container, 0.0);
        playerView.getChildren().addAll(container);
        playerView.setPadding(new Insets(15));

        loadPlayersFromDB(playerTableView);
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
        gameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGame().getGameName()));
        gameCol.setPrefWidth(200);

        TableColumn<Player, String> teamCol = new TableColumn<>("Team");
        teamCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTeam().getName()));
        teamCol.setPrefWidth(200);

        TableColumn<Player, String> streetAddressCol = new TableColumn<>("Street Address");
        streetAddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
        streetAddressCol.setPrefWidth(200);

        TableColumn<Player, String> districtCol = new TableColumn<>("District");
        districtCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getDistrict()));
        districtCol.setPrefWidth(200);

        TableColumn<Player, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
        cityCol.setPrefWidth(200);

        TableColumn<Player,String> postcodeCol = new TableColumn<>("Postcode");
        postcodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostcode()));
        postcodeCol.setPrefWidth(200);

        TableColumn<Player, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCountry()));
        countryCol.setPrefWidth(200);

        TableColumn<Player,String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        emailCol.setPrefWidth(200);

        TableColumn<Player,Void> updateCol =  new TableColumn<>("Update");
        updateCol.setCellFactory(col -> new TableCell<Player,Void>(){
            Button updatePlayer = new Button("Update Player");

            {
                updatePlayer.setOnAction(e1 -> {
                    TableColumn idColumn = (TableColumn) playerTableView.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    Player playerToUpdate = playerDAO.getPlayerById(id);
                    try {
                        if(playerUpdateBox(playerToUpdate)){
                            loadPlayersFromDB(playerTableView);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Player updated: " + playerToUpdate + ". Number of players: " + playerDAO.getAllPlayers().size());

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
                    TableColumn idColumn = (TableColumn) playerTableView.getColumns().get(0);
                    int id = Integer.parseInt ((String) idColumn.getCellObservableValue(getIndex()).getValue());
                    Player playerToDelete = playerDAO.getPlayerById(id);
                    try {
                        if(deleteConfirmBox(playerToDelete).get()){
                            loadPlayersFromDB(playerTableView);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
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

    private boolean playerUpdateBox(Player playerToUpdate) throws FileNotFoundException {
        AtomicBoolean result = new AtomicBoolean(false);

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Game");
        window.setMinWidth(400);
        window.setMinHeight(500);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Label label = new Label();
        label.setText("Update Player: " + playerToUpdate.getFirstName() + " " + playerToUpdate.getNickName() + " " + playerToUpdate.getLastName());
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 20");
        label.setWrapText(true);

        Label firstNameLabel = new Label("Update player's first name:");
        TextField firstName = new TextField();
        firstName.setPromptText(playerToUpdate.getFirstName());
        firstName.setMaxWidth(100);
        if (firstName.getText()!= null){
            String playerFirstName = firstName.getText();
        }

        Label nickNameLabel = new Label("Update player's nick name:");
        TextField nickName = new TextField();
        nickName.setMaxWidth(100);
        if (nickName.getText()!= null){
            String playerNickName = nickName.getText();
        }

        Label lastNameLabel = new Label("Update player's last name:");
        TextField lastName = new TextField();
        lastName.setMaxWidth(100);
        if (lastName.getText()!= null){
            String playerLastName = lastName.getText();
        }

        Button update = new Button("Update");
        update.setDefaultButton(true);
        update.setOnAction(e -> {
            if(firstName.getText() != null){
                playerToUpdate.setFirstName(firstName.getText());
            }

            playerDAO.updatePlayer(playerToUpdate);
            try {
                confirmUpdatePopup(playerToUpdate);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            window.close();
            result.set(true);
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e-> {
            window.close();
            result.set(false);
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,label, firstNameLabel, firstName,nickNameLabel, nickName, lastNameLabel, lastName,update, cancel);
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
    private void confirmUpdatePopup(Player playerToUpdate) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Player: "+ playerToUpdate.getFirstName() + " " + playerToUpdate.getNickName() + " " + playerToUpdate.getLastName() + " has been updated");
        label.setWrapText(true);
        AnchorPane root = new AnchorPane();
        root.setPrefSize(350,350);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        window.setAlwaysOnTop(true);
    }


    private void loadPlayersFromDB(TableView<Player> playerTableView) {

        playerList.setAll(playerDAO.getAllPlayers());
        playerTableView.setItems(playerList);
    }

    private void playerSearchPopUp() throws FileNotFoundException {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Player Search");
        window.setMinWidth(400);
        window.setMinHeight(500);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        TableView<Player> viewSearchedPlayer = createPlayerTableView();
        viewSearchedPlayer.setPrefHeight(150);
        ObservableList searchedPlayerList = FXCollections.observableArrayList();


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


        HBox comboBoxes = new HBox(10);
        comboBoxes.getChildren().addAll(gameBoxLabel, gamesChoice, teamBoxLabel, teamChoice);
        comboBoxes.setAlignment(Pos.CENTER);


        Button reset = new Button("Reset");
        reset.setOnAction(e-> viewSearchedPlayer.getItems().clear());
        Button search = new Button("Search");
        search.setDefaultButton(true);
        search.setOnAction(e -> {
            if(gamesChoice.getSelectionModel().getSelectedItems().isEmpty()){
                Team teamToSearch = teamsHashMap.get(teamChoice.getSelectionModel().getSelectedItem());
                List<Player> playersFound = playerDAO.getPlayersByTeam(teamToSearch);
                System.out.println("Found " + playersFound.size() + " players in team " + teamToSearch.getName());
                searchedPlayerList.setAll(playersFound);
                viewSearchedPlayer.setItems(searchedPlayerList);
            }else{
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
            }

        });

        Button cancel = new Button("Close");
        cancel.setOnAction(e-> window.close());

        HBox buttonHolder = new HBox(10);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.getChildren().addAll(search, reset,cancel);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,title,label ,comboBoxes, viewSearchedPlayer,buttonHolder);
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

    private AtomicBoolean addPlayerBox() throws FileNotFoundException {
        AtomicBoolean result = new AtomicBoolean(false);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add New Player");
        window.setMinWidth(400);
        window.setMinHeight(500);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

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
        HashMap<String, Game> gameHashMap = new HashMap<>();
        for(Game game : gamesDAO.getAllGames()){
            gameHashMap.put(game.getGameName(), game);
            addPlayerGame.getItems().add(game.getGameName());
        }

        Label teamLabel = new Label("Optional: Select Team");
        ComboBox addPlayerTeam = new ComboBox();
        HashMap<String, Team> teamHashMap = new HashMap<>();
        for(Team team : new TeamsDAO().getAllTeams()){
            teamHashMap.put(team.getName(), team);
            addPlayerTeam.getItems().add(team.getName());
        }

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Button save = new Button("Save");
        save.setDefaultButton(true);
        save.setOnAction(e-> {

            String givenNickName = getNickName.getText().trim();
            String givenFirstName = getFirstName.getText().trim();
            String givenLastName = getLastName.getText().trim();
            Game selectedGame = gameHashMap.get(addPlayerGame.getSelectionModel().getSelectedItem());
            Team selectedTeam = teamHashMap.get(addPlayerTeam.getSelectionModel().getSelectedItem());
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

            if(selectedGame == null) {
                addPlayerGame.setStyle("-fx-background-color: red");
                valid = false;
            }

            if(valid){
                Player playerToAdd = new Player(givenFirstName,givenNickName,givenLastName, selectedGame);
                playerDAO.savePlayer(playerToAdd);
                if(selectedTeam != null){
                    playerToAdd.setTeam(selectedTeam);
                }
                playerList.add(playerToAdd);
                loadPlayersFromDB(createPlayerTableView());
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
        layout.getChildren().addAll(logo,label, firstNameLabel, getFirstName, nickNameLabel,getNickName, lastNameLabel, getLastName,gameLabel, addPlayerGame, teamLabel, addPlayerTeam, save, cancel);
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

    private void confirmPopup(Player playerToAdd){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Player: "+ playerToAdd.getFirstName() + " "+ playerToAdd.getNickName() + " " + playerToAdd.getLastName() +" has been added to the database");
        label.setWrapText(true);
        AnchorPane root = new AnchorPane();
        root.setPrefSize(250,350);

        VBox layout = new VBox(20);
        layout.getChildren().addAll( label, closeButton);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        window.setAlwaysOnTop(true);
    }

    public AtomicBoolean deleteConfirmBox(Player playerToDelete) throws FileNotFoundException {
        AtomicBoolean result = new AtomicBoolean(false);
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Confirm Delete");
        window.setMinWidth(400);
        window.setResizable(false);
        window.setMinHeight(400);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Label title = new Label("Confirm Delete");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");
        Label label = new Label();
        label.setText("Are you sure you want to delete " + playerToDelete.getFirstName() + " " + playerToDelete.getNickName() + " " + playerToDelete.getLastName() + "?");

        Button delete = new Button("Delete");
        Button cancel = new Button("Cancel");
        delete.setOnAction(e -> {
            playerDAO.deletePlayer(playerToDelete);
            try {
                confirmDeletePopup(playerToDelete);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            playerList.remove(playerToDelete);
            loadPlayersFromDB(createPlayerTableView());
            window.close();
        });
        cancel.setOnAction(e -> {
            result.set(false);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(logo,title,label, delete, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();

        return result;

    }

    private void confirmDeletePopup(Player playerToDelete) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Player: "+ playerToDelete.getFirstName() + " " + playerToDelete.getNickName() + " " + playerToDelete.getLastName() + " has been deleted");
        label.setWrapText(true);
        AnchorPane root = new AnchorPane();
        root.setPrefSize(150,150);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        root.getChildren().add(layout);

        AnchorPane.setLeftAnchor(layout, 5.0);
        AnchorPane.setRightAnchor(layout, 5.0);
        AnchorPane.setTopAnchor(layout, 5.0);
        AnchorPane.setBottomAnchor(layout, 5.0);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
        window.setAlwaysOnTop(true);
    }

    public AnchorPane getPlayerView() {
        return playerView;
    }

    public void setPlayerView(AnchorPane playerView) {
        this.playerView = playerView;
    }
}

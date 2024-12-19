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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerFX {         //Lynsey Fox

    private ObservableList<Player> playerList;
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
            //search game pop up box
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
                    loadPlayersFromDB(playerTableView);
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
        AnchorPane.setBottomAnchor(playerView, 10.0);
        AnchorPane.setLeftAnchor(playerView, 10.0);
        AnchorPane.setRightAnchor(playerView, 10.0);
        AnchorPane.setTopAnchor(playerView, 10.0);
        playerView.getChildren().addAll(container);
        playerView.setPadding(new Insets(15));
        playerView.setPrefWidth(800);
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

        playerTableView.getColumns().addAll(idCol, lastNameCol, updateCol, deleteCol);
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
            playerToUpdate.setFirstName(firstName.getText());
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

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result.get();
    }
    private void confirmUpdatePopup(Player playerToUpdate) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Player: "+ playerToUpdate.getFirstName() + " " + playerToUpdate.getNickName() + " " + playerToUpdate.getLastName() + " has been updated");
        label.setWrapText(true);
        AnchorPane root = new AnchorPane();
        root.setPrefSize(150,350);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,label, closeButton);
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
        System.out.println(playerDAO.getAllPlayers().toString());
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


        Label title = new Label();
        title.setText("Search: ");
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 20");

        Label label = new Label();
        label.setText("Choose search term: ");


        Label gameBoxLabel = new Label("Game/s Played:");
        Label teamBoxLabel = new Label("Team:");

        ComboBox gameChoice = new ComboBox();
        HashMap<String, Game> gamesHashMap = new HashMap<>();
        for (Game game: gamesDAO.getAllGames()){
            gamesHashMap.put(String.valueOf(game.getGameName()),game);
            gameChoice.getItems().add(String.valueOf(game.getGameName()));
        }

        ComboBox teamChoice = new ComboBox();
        HashMap<String, Team> teamsHashMap = new HashMap<>();
        for (Team team: new TeamsDAO().getAllTeams()){
            teamsHashMap.put(team.getName(),team);
            teamChoice.getItems().add(team.getName());
        }


        HBox comboBoxes = new HBox(10);
        comboBoxes.getChildren().addAll(gameBoxLabel, gameChoice, teamBoxLabel, teamChoice);
        comboBoxes.setAlignment(Pos.CENTER);

        Button search = new Button("Search");
        search.setDefaultButton(true);
        search.setOnAction(e -> {
            if(gameChoice.getSelectionModel().getSelectedItem() == null){
                List<Player> playersToGet = teamsHashMap.get(teamChoice.getSelectionModel().getSelectedItem()).getPlayers();
                viewSearchedPlayer.getItems().add((Player) playersToGet);
            }else{
                List <Player> playersToGet = gamesHashMap.get(gameChoice.getSelectionModel().getSelectedItem()).getPlayers();
                viewSearchedPlayer.getItems().addAll(playersToGet);
            }

        });

        Button cancel = new Button("Close");
        cancel.setOnAction(e-> window.close());

        HBox buttonHolder = new HBox(10);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.getChildren().addAll(search, cancel);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,title,label ,comboBoxes, viewSearchedPlayer,buttonHolder);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(viewSearchedPlayer, new Insets(10));


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

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);

        Button save = new Button("Save");
        save.setDefaultButton(true);
        save.setOnAction(e-> {

            String givenNickName = getNickName.getText().trim();
            String givenFirstName = getFirstName.getText().trim();
            String givenLastName = getLastName.getText().trim();
            boolean valid = true;

            getFirstName.setStyle("-fx-background-color: white");
            getNickName.setStyle("-fx-background-color: white");
            getLastName.setStyle("-fx-background-color: white");
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
        layout.getChildren().addAll(logo,label, firstNameLabel, getNickName, save, cancel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return result;
    }

    private void confirmPopup(Player playerToAdd) throws FileNotFoundException {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Player: "+ playerToAdd.getFirstName() + " "+ playerToAdd.getNickName() + " " + playerToAdd.getLastName() +" has been added to the database");
        label.setWrapText(true);
        AnchorPane root = new AnchorPane();
        root.setPrefSize(150,350);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo, label, closeButton);
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
            System.out.println("number of players: " + playerDAO.getAllPlayers().size());
            System.out.println("Player to be deleted: " + playerToDelete.getFirstName() + " " + playerToDelete.getLastName());
            playerDAO.deletePlayer(playerToDelete);
            try {
                confirmDeletePopup(playerToDelete);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("number of players: " + playerDAO.getAllPlayers().size());
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

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(80,80,70);
        logo.setTranslateY(0);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(160.0);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        Label label = new Label("Player: "+ playerToDelete.getFirstName() + " " + playerToDelete.getNickName() + " " + playerToDelete.getLastName() + " has been deleted");
        label.setWrapText(true);
        AnchorPane root = new AnchorPane();
        root.setPrefSize(150,150);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(logo,label, closeButton);
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

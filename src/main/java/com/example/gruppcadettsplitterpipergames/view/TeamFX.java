package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamFX {

    private BorderPane view;
    private TeamsDAO teamsDAO = new TeamsDAO();
    private GamesDAO gameDAO;
    private PlayerDAO playerDAO;
    private ObservableList<Team> teamList;
    private TableView<Team> teamTable;

    public TeamFX() throws FileNotFoundException {
        gameDAO = new GamesDAO();
        teamsDAO = new TeamsDAO();
        playerDAO = new PlayerDAO();
        teamTable = new TableView<>();

        view = new BorderPane();
        teamList = FXCollections.observableArrayList();

        ListView<Player> playersInTeamList = new ListView<>();

        Button btnAdd = new Button("Add new team");
        btnAdd.setPrefSize(150, 50);
        Button btnUpdate = new Button("Update team");
        btnUpdate.setPrefSize(150, 50);
        Button btnDelete = new Button("Delete Team");
        btnDelete.setPrefSize(150, 50);
        Button btnRead = new Button("Filter by Game");
        btnRead.setPrefSize(150, 50);
        Button refreshButton = new Button("Refresh");
        refreshButton.setPrefSize(150, 50);

        HBox buttonsBox = new HBox(20);
        buttonsBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnRead, refreshButton);
        buttonsBox.setPadding(new Insets(0, 0, 40, 40));
        view.setBottom(buttonsBox);


        HBox tableBox = new HBox(20);
        TableView<Team> teamTable = createTableView();
        teamTable.setPrefWidth(800);
        teamTable.setStyle(" -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");

        VBox playerListBox = new VBox(5);
        Label emptyPlayerListLabel = new Label("No players to view");
        playersInTeamList.setPlaceholder(emptyPlayerListLabel);
        playersInTeamList.setPrefWidth(150);
        playersInTeamList.setPrefHeight(300);
        playersInTeamList.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 2;");

        Label playerListLabel = new Label("Players in selected team");
        playerListLabel.setStyle("-fx-font-weight: bold");

        playerListBox.setAlignment(Pos.TOP_CENTER);
        playerListBox.getChildren().addAll(playerListLabel, playersInTeamList);

        tableBox.setAlignment(Pos.TOP_LEFT);
        tableBox.setFillHeight(false);
        tableBox.getChildren().addAll(teamTable, playerListBox);
        view.setLeft(tableBox);

        ImageView logo = new ImageView(new Image(new FileInputStream("src/main/resources/logo.png")));
        Circle logoClip = new Circle(50, 50, 40);
        logo.setClip(logoClip);
        logo.setPreserveRatio(true);
        logo.setFitHeight(100);

        Text title = new Text("Teams");
        title.setStyle("-fx-font-size: 24;-fx-font-weight: bold;");

        BorderPane header = new BorderPane();
        header.setRight(logo);
        header.setCenter(title);
        view.setTop(header);

        loadTeamsFromDatabase(teamTable);

        // Visa lista med spelare när lag väljs i tabellen
        teamTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Team team = teamsDAO.getTeamById(newValue.getId());
                List<Player> players = team.getPlayers();
                System.out.println("Players in team: " + players);

                playersInTeamList.getItems().setAll(players);
            }
        });
        // CellFactory för att visa förnamn + efternamn till listan
        playersInTeamList.setCellFactory(param -> new ListCell<Player>() {
            @Override
            protected void updateItem(Player player, boolean empty) {
                super.updateItem(player, empty);
                if (empty || player == null) {
                    setText(null);
                } else {
                    setText(player.getFirstName() + " " + player.getLastName());
                }
            }
        });



        refreshButton.setOnAction(event -> {
            loadTeamsFromDatabase(teamTable);
        });


        // CREATE
        btnAdd.setOnAction(event -> {
            System.out.println("Add new team");

            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Add new team");
            dialog.setHeaderText("Enter team details");

            TextField teamNameField = new TextField();
            teamNameField.setPromptText("Enter team name");

            ComboBox<Game> gameCombo = new ComboBox<>();
            gameCombo.setPromptText("Select a game");
            gameCombo.getItems().addAll(gameDAO.getAllGames());

            // CellFactory för att skriva ut spelnamnen i ComboBox
            gameCombo.setCellFactory(param -> new ListCell<Game>() {
                @Override
                protected void updateItem(Game game, boolean empty) {
                    super.updateItem(game, empty);
                    if (empty || game == null) {
                        setText(null);
                    } else {
                        setText(game.getGameName());
                    }
                }
            });

            gameCombo.setButtonCell((new ListCell<Game>() {
                @Override
                protected void updateItem(Game game, boolean empty) {
                    super.updateItem(game, empty);
                    if (empty || game == null) {
                        setText(null);
                    } else {
                        setText(game.getGameName());
                    }
                }
            }));


            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.add(new Label("Team Name"), 0, 0);
            gridPane.add(teamNameField, 1, 0);
            gridPane.add(new Label("Game Name"), 0, 1);
            gridPane.add(gameCombo, 1, 1);

            dialog.getDialogPane().setContent(gridPane);

            Button buttonAdd = new Button("Add team");
            Button buttonCancel = new Button("Close");

            HBox buttonBox = new HBox(10, buttonAdd, buttonCancel);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
//            gridPane.add(buttonBox, 0, 2);
            gridPane.add(buttonAdd, 0, 3);
            gridPane.add(buttonCancel, 2, 3);

            buttonAdd.setOnAction(e -> {
                String teamName = teamNameField.getText();
                Game selectedGame = gameCombo.getValue();

                if (teamName.isEmpty() || selectedGame == null) {
                    System.out.println("Invalid");
                } else {
                    Team newTeam = new Team(teamName, selectedGame);
                    teamsDAO.saveTeam(newTeam);
                    teamTable.getItems().add(newTeam);
                    dialog.close();
                }

            });

            buttonCancel.setOnAction(e -> {
                System.out.println("cancel");
                dialog.setResult(Boolean.TRUE);
                dialog.close();

            });

            dialog.showAndWait();


        });

        // READ
        btnRead.setOnAction(event -> {
            System.out.println("Read team");

            ObservableList<Team> teamByGameList = FXCollections.observableArrayList();
            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Filter by game");
            dialog.setHeaderText("Select games you want to list");

            Button buttonFilterByGame = new Button("Filter by selected games");
            Button buttonCloseFilterByGame = new Button("Close");

            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);
            gridPane.add(buttonFilterByGame, 0, 4);
            gridPane.add(buttonCloseFilterByGame, 1, 4);

            dialog.getDialogPane().setContent(gridPane);

            VBox checkBoxes = new VBox(10);
            for (Game game : gameDAO.getAllGames()) {
                CheckBox checkBox = new CheckBox(game.getGameName());
                checkBox.setUserData(game);
                checkBoxes.getChildren().add(checkBox);
            }

            gridPane.add(checkBoxes, 0, 0);

            buttonFilterByGame.setOnAction(e -> {

                List<Game> selectedGames = new ArrayList<>();

                for (Node node : checkBoxes.getChildren()) {
                    if (node instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) node;
                        if (checkBox.isSelected()) {
                            Game game = (Game) checkBox.getUserData();
                            selectedGames.add(game);
                        }
                    }
                }

                ObservableList<Team> teamsByGameList = FXCollections.observableArrayList();

                if (selectedGames.isEmpty()) {
                    teamsByGameList.addAll(teamsDAO.getAllTeams());
                } else {

                    List<Team> filteredTeams = teamsDAO.getTeamsByGameNames(selectedGames);
                    teamsByGameList.addAll(filteredTeams);
                }
                teamTable.setItems(teamsByGameList);
            });

            buttonCloseFilterByGame.setOnAction(e -> {
                System.out.println("Close");
                dialog.setResult(Boolean.TRUE);
                dialog.close();
            });

            dialog.showAndWait();
        });






        // DELETE
        btnDelete.setOnAction(event -> {
           Team selectedTeam = teamTable.getSelectionModel().getSelectedItem();
           if(selectedTeam != null) {
               Dialog<Boolean> dialog = new Dialog<>();
               dialog.setTitle("Delete team");
               dialog.setHeaderText("Do you want to delete the selected team?");

               Button btnDeleteTeam = new Button("Delete");
               Button btnCancel = new Button("Cancel");

               GridPane gridPane = new GridPane();
               gridPane.setHgap(20);
               dialog.getDialogPane().setContent(gridPane);
               gridPane.add(btnDeleteTeam, 1, 0);
               gridPane.add(btnCancel,3 , 0);

               btnDeleteTeam.setOnAction(e -> {
                   teamsDAO.deleteTeam(selectedTeam);
                   teamTable.getItems().remove(selectedTeam);
                   teamTable.refresh();
                   System.out.println("Team " + selectedTeam.getName() + " deleted");
                   dialog.setResult(Boolean.TRUE);
                   dialog.close();
               });

               btnCancel.setOnAction(e -> {
                   System.out.println("Cancel");
                   dialog.setResult(Boolean.TRUE);
                   dialog.close();
               });

                dialog.showAndWait();
           } else {
               System.out.println("No team selected");
           }
            });


            // CellFactory för att skriva ut lagnamnen i ComboBox
//            teamCombo.setCellFactory(param -> new ListCell<Team>() {
//                @Override
//                protected void updateItem(Team team, boolean empty) {
//                    super.updateItem(team, empty);
//                    if (empty || team == null) {
//                        setText(null);
//                    } else {
//                        setText(team.getName());
//                    }
//                }
//            });
//
//            teamCombo.setButtonCell((new ListCell<Team>() {
//                @Override
//                protected void updateItem(Team team, boolean empty) {
//                    super.updateItem(team, empty);
//                    if (empty || team == null) {
//                        setText(null);
//                    } else {
//                        setText(team.getName());
//                    }
//                }
//            }));


//            GridPane gridPane = new GridPane();
//            gridPane.setHgap(20);
//            gridPane.setVgap(10);
////            gridPane.add(teamCombo, 1, 0);
//
//            Button btnDeleteTeam = new Button("Delete team");
//            Button btnCancelDeleteTeam = new Button("Close");
//
//            dialog.getDialogPane().setContent(gridPane);
//            gridPane.add(btnDeleteTeam, 1, 3);
//            gridPane.add(btnCancelDeleteTeam, 2, 3);
//
//            btnCancelDeleteTeam.setOnAction(e -> {
//                System.out.println("Cancel");
//                dialog.setResult(Boolean.TRUE);
//                dialog.close();
//            });
//
//            btnDeleteTeam.setOnAction(e -> {
//                Team teamToDelete = teamCombo.getValue();
//                if (teamToDelete != null) {
//                    teamsDAO.deleteTeam(teamToDelete);
//                    teamTable.getItems().removeIf(t -> t.getId() == teamToDelete.getId());
//                    teamTable.refresh();
//                    System.out.println("Team " + teamToDelete + " was deleted!");
//                    dialog.close();
//                } else {
//                    System.out.println("No team selected");
//                }
//            });
//            dialog.showAndWait();
//        });

        // UPDATE
        btnUpdate.setOnAction(event -> {
            System.out.println("Update team");
            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Update team");
            dialog.setHeaderText("Update team");


            TextField changeNameField = new TextField();
            changeNameField.setPromptText("Change name");
            ComboBox<Team> teamCombo = new ComboBox<>();
            teamCombo.setPromptText("Select a team to update");
            teamCombo.getItems().addAll(teamsDAO.getAllTeams());


// CellFactory för att skriva ut lagnamnen i ComboBox
            teamCombo.setCellFactory(param -> new ListCell<Team>() {
                @Override
                protected void updateItem(Team team, boolean empty) {
                    super.updateItem(team, empty);
                    if (empty || team == null) {
                        setText(null);
                    } else {
                        setText(team.getName());
                    }
                }
            });

            teamCombo.setButtonCell((new ListCell<Team>() {
                @Override
                protected void updateItem(Team team, boolean empty) {
                    super.updateItem(team, empty);
                    if (empty || team == null) {
                        setText(null);
                    } else {
                        setText(team.getName());
                    }
                }
            }));

            ComboBox<Game> gameCombo = new ComboBox<>();
            gameCombo.setPromptText("Select a game");
            gameCombo.getItems().addAll(gameDAO.getAllGames());


            // CellFactory för att skriva ut spelnamnen i ComboBox
            gameCombo.setCellFactory(param -> new ListCell<Game>() {
                @Override
                protected void updateItem(Game game, boolean empty) {
                    super.updateItem(game, empty);
                    if (empty || game == null) {
                        setText(null);
                    } else {
                        setText(game.getGameName());
                    }
                }
            });

            gameCombo.setButtonCell((new ListCell<Game>() {
                @Override
                protected void updateItem(Game game, boolean empty) {
                    super.updateItem(game, empty);
                    if (empty || game == null) {
                        setText(null);
                    } else {
                        setText(game.getGameName());
                    }
                }
            }));


            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);
            gridPane.add(teamCombo, 1, 0);
            gridPane.add(changeNameField, 1, 1);
            gridPane.add(gameCombo, 1, 2);
            Button btnUpdateTeam = new Button("Update team");
            Button btnManagePlayers = new Button("Manage players");
            Button btnCancelUpdateTeam = new Button("Close");
            dialog.getDialogPane().setContent(gridPane);
            gridPane.add(btnUpdateTeam, 1, 6);
            gridPane.add(btnCancelUpdateTeam, 2, 6);
            gridPane.add(btnManagePlayers, 1, 3);

            btnManagePlayers.setDisable(true);
            gameCombo.setDisable(true);
            teamCombo.valueProperty().addListener((obervable, oldValue, newValue) -> {
                if (newValue != null) {
                    btnManagePlayers.setDisable(false);
                    gameCombo.setDisable(false);
                } else {
                    btnManagePlayers.setDisable(true);
                    gameCombo.setDisable(true);
                }
            });


            btnManagePlayers.setOnAction(e -> {
                Dialog<Boolean> dialogManagePlayers = new Dialog();
                dialogManagePlayers.setTitle("Manage players");
                dialogManagePlayers.setHeaderText("Manage players");
                Team teamToUpdate = teamCombo.getValue();


                ListView<Player> playersInTeam = new ListView<>();
                playersInTeam.getItems().addAll(teamToUpdate.getPlayers());
                playersInTeam.setCellFactory(param -> new ListCell<Player>() {
                    @Override
                    protected void updateItem(Player player, boolean empty) {
                        super.updateItem(player, empty);
                        if (empty || player == null) {
                            setText(null);
                        } else {
                            setText(player.getFirstName() + " " + player.getLastName());
                        }
                    }
                });


                ListView<Player> playersList = new ListView<>();
                playersList.getItems().addAll(playerDAO.getAllPlayers());
                playersList.setCellFactory(param -> new ListCell<Player>() {
                    @Override
                    protected void updateItem(Player player, boolean empty) {
                        super.updateItem(player, empty);
                        if (empty || player == null) {
                            setText(null);
                        } else {
                            setText(player.getFirstName() + " " + player.getLastName());
                        }
                    }
                });


                Button btnAddPlayer = new Button("Add player to team");
                Button btnRemovePlayer = new Button("Remove player from team");
                Button btnCloseManagePlayers = new Button("Close");

                GridPane gridPaneManagePlayers = new GridPane();
                Label playersLabel = new Label("Players");
                gridPaneManagePlayers.add(playersLabel, 0, 0);
                gridPaneManagePlayers.add(playersList, 0, 1);

                gridPaneManagePlayers.add(btnAddPlayer, 0, 3);
                gridPaneManagePlayers.add(btnRemovePlayer, 3, 3);
                gridPaneManagePlayers.add(btnCloseManagePlayers, 4, 5);

                Label playerAvailableLabel = new Label("Players in team");
                gridPaneManagePlayers.add(playerAvailableLabel, 3, 0);
                gridPaneManagePlayers.add(playersInTeam, 3, 1);

                dialogManagePlayers.getDialogPane().setContent(gridPaneManagePlayers);

                btnAddPlayer.setOnAction(e2 -> {
                    Player selectedPlayer = playersList.getSelectionModel().getSelectedItem();
                    if (selectedPlayer != null) {
                        teamToUpdate.addPlayer(selectedPlayer);
                        playersList.getItems().remove(selectedPlayer);
                        playersInTeam.getItems().add(selectedPlayer);
                        playerDAO.updatePlayer(selectedPlayer);
                    }

                });

                btnRemovePlayer.setOnAction(e2 -> {
                    Player selectedPlayer = playersInTeam.getSelectionModel().getSelectedItem();
                    if (selectedPlayer != null) {
                        teamToUpdate.removePlayer(selectedPlayer);
                        playersInTeam.getItems().remove(selectedPlayer);
                        playersList.getItems().add(selectedPlayer);
                        playerDAO.updatePlayer(selectedPlayer);
                    }
                });

                btnCloseManagePlayers.setOnAction(e2 -> {
                    dialogManagePlayers.setResult(Boolean.TRUE);
                    dialogManagePlayers.close();
                });

                dialogManagePlayers.showAndWait();
            });

            btnUpdateTeam.setOnAction(e -> {
                String newName = changeNameField.getText().trim();  // Tar bort eventuella mellanslag innan och efter texten
                Team teamToUpdate = teamCombo.getValue();
                Game selectedGame = gameCombo.getValue();

                if (teamToUpdate == null) {
                    System.out.println("You must choose a team");
                } else if (teamToUpdate != null) {
                    if (!newName.isEmpty()) {
                        teamToUpdate.setName(newName);
                    }
                    if (selectedGame != null) {
                        teamToUpdate.setGame(selectedGame);
                    }
                    teamsDAO.updateTeam(teamToUpdate);
                    teamTable.getItems().setAll(teamsDAO.getAllTeams());
                    System.out.println("Team " + teamToUpdate + " has been updated!");
                }
            });

            btnCancelUpdateTeam.setOnAction(e -> {
                System.out.println("Cancel");
                dialog.setResult(Boolean.TRUE);
                dialog.close();
            });

            dialog.showAndWait();
        });

    }


    // Skapa lagtabell
    private TableView<Team> createTableView() {
        TableView<Team> teamTable = new TableView<>();

        //Id-kolumn
        TableColumn<Team, Integer> idCol = new TableColumn<>("Team ID");
        idCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        idCol.setPrefWidth(80);


        // Lagnamn-kolumn
        TableColumn<Team, String> nameCol = new TableColumn<>("Team Name");
        nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        nameCol.setPrefWidth(200);

        // Spel-kolumn
        TableColumn<Team, String> gameCol = new TableColumn<>("Game");
        gameCol.setCellValueFactory(cellData -> {
            Game game = cellData.getValue().getGame();
            return new SimpleStringProperty(game != null ? game.getGameName() : "No game");
        });
        gameCol.setPrefWidth(200);

        teamTable.getColumns().addAll(idCol,nameCol, gameCol);
        return teamTable;
    }

    public void loadTeamsFromDatabase(TableView<Team> teamTable) {
        teamList.clear();
        teamList.setAll(teamsDAO.getAllTeams());
        teamTable.setItems(teamList);
    }


    public BorderPane getView() {
        return view;
    }

    public void setView(BorderPane view) {
        this.view = view;
    }

    public TableView<Team> getTeamTable() {
        return teamTable;
    }

    public void setTeamTable(TableView<Team> teamTable) {
        this.teamTable = teamTable;
    }
}

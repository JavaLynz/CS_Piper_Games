package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.sql.*;
import java.util.List;

public class TeamFX {

    private BorderPane view;
    private TeamsDAO teamsDAO;
    private GamesDAO gameDAO;
    private PlayerDAO playerDAO;
    private ObservableList<Team> teamList;


    public TeamFX() {
        gameDAO = new GamesDAO();
        teamsDAO = new TeamsDAO();
        playerDAO = new PlayerDAO();
        view = new BorderPane();
        teamList = FXCollections.observableArrayList();
        ListView<Player> playersInTeamList = new ListView();


        HBox buttonsBox = new HBox();
        HBox tableBox = new HBox(20);
        VBox playerListBox = new VBox(5);


        Button btnAdd = new Button("Add new team");
        btnAdd.setPrefSize(200, 50);
        Button btnUpdate = new Button("Update team");
        btnUpdate.setPrefSize(200, 50);
        Button btnDelete = new Button("Delete Team");
        btnDelete.setPrefSize(200, 50);
        Button btnRead = new Button("Search");
        btnRead.setPrefSize(200, 50);
        buttonsBox.getChildren().addAll(btnAdd, btnUpdate, btnDelete, btnRead);
        view.setBottom(buttonsBox);
        buttonsBox.setPadding(new Insets(0, 0, 0, 0));

        view.setLeft(tableBox);
        tableBox.setAlignment(Pos.TOP_LEFT);

        TableView<Team> teamTable = createTableView();
        teamTable.setPrefWidth(500);
        tableBox.setFillHeight(true);

        playersInTeamList.setMaxHeight(200);
        Label playerListLabel = new Label("Players in selected team");
        playerListLabel.setStyle("-fx-font-weight: bold");
        playerListBox.setAlignment(Pos.TOP_CENTER);

        playerListBox.getChildren().addAll(playerListLabel,playersInTeamList);
        tableBox.getChildren().addAll(teamTable, playerListBox);

        loadTeamsFromDatabase(teamTable);





        teamTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Team team = teamsDAO.getTeamById(newValue.getId()); // Hämta laget från databasen
                List<Player> players = team.getPlayers(); // Få spelarna från databasen
                System.out.println("Players in team: " + players);

                playersInTeamList.getItems().setAll(players);
            }
        });

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
            gridPane.setHgap(20);
            gridPane.setVgap(10);
//            gridPane.setPadding(new Insets(20, 50, 10, 10));

            gridPane.add(new Label("Team Name"), 0, 0);
            gridPane.add(teamNameField, 1, 0);
            gridPane.add(new Label("Game Name"), 0, 1);
            gridPane.add(gameCombo, 1, 1);

            dialog.getDialogPane().setContent(gridPane);

            Button buttonAdd = new Button("Add team");
            Button buttonCancel = new Button("Close");

            HBox buttonBox = new HBox(10, buttonAdd, buttonCancel);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);
            gridPane.add(buttonBox, 1, 2);

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

//            ComboBox<Game> gameCombo = new ComboBox<>();
//            gameCombo.setPromptText("Select a game");
//            gameCombo.getItems().addAll(gameDAO.getAllGames());
//            view.setTop(gameCombo);

            ObservableList<Team> teamByGameList = FXCollections.observableArrayList();


            String query = "SELECT t.team_id, t.team_name, t.game_id, g.name AS game_name " +
                    "FROM teams t " +
                    "LEFT JOIN games g ON t.game_id = g.id";


            try (Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cadettsplitterspipergames", "root", "MySQL574921!");
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT team_id, team_name FROM teams")) {

                while (resultSet.next()) {
                    int teamId = resultSet.getInt("team_id");
                    String teamName = resultSet.getString("team_name");

                    // Skriv ut lagets ID och namn i konsolen
                    System.out.println("Team ID: " + teamId + ", Team Name: " + teamName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Could not retrieve teams from the database.");
            }


            teamTable.setItems(teamByGameList);

        });

        // DELETE
        btnDelete.setOnAction(event -> {
            System.out.println("Delete team");

            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Delete team");
            dialog.setHeaderText("Choose team to delete");

            ComboBox<Team> teamCombo = new ComboBox<>();
            teamCombo.setPromptText("Select a team to delete");
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


            GridPane gridPane = new GridPane();
            gridPane.setHgap(20);
            gridPane.setVgap(10);
            gridPane.add(teamCombo, 1, 0);

            Button btnDeleteTeam = new Button("Delete team");
            Button btnCancelDeleteTeam = new Button("Close");

            dialog.getDialogPane().setContent(gridPane);
            gridPane.add(btnDeleteTeam, 1, 1);
            gridPane.add(btnCancelDeleteTeam, 2, 1);

            btnCancelDeleteTeam.setOnAction(e -> {
                System.out.println("Cancel");
                dialog.setResult(Boolean.TRUE);
                dialog.close();
            });

            btnDeleteTeam.setOnAction(e -> {
                Team teamToDelete = teamCombo.getValue();
                if (teamToDelete != null) {
                    teamsDAO.deleteTeam(teamToDelete);
                    teamTable.getItems().removeIf(t -> t.getId() == teamToDelete.getId());
                    teamTable.refresh();
                    System.out.println("Team " + teamToDelete + " was deleted!");
                    dialog.close();
                } else {
                    System.out.println("No team selected");
                }
            });
            dialog.showAndWait();
        });

        // UPDATE
        btnUpdate.setOnAction(event -> {
            System.out.println("Update team");
            Dialog<Boolean> dialog = new Dialog<>();
            dialog.setTitle("Update team");
            dialog.setHeaderText("Update team");



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
            gridPane.add(gameCombo, 1, 1);
            Button btnUpdateTeam = new Button("Update team");
            Button btnManagePlayers = new Button("Manage players");
            Button btnCancelUpdateTeam = new Button("Close");
            dialog.getDialogPane().setContent(gridPane);
            gridPane.add(btnUpdateTeam, 1, 6);
            gridPane.add(btnCancelUpdateTeam, 2, 8);
            gridPane.add(btnManagePlayers, 1, 2);

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


                ListView<Player> playersInTeam = new ListView();
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


                ListView<Player> playersList = new ListView();
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
                Team teamToUpdate = teamCombo.getValue();
                Game selectedGame = gameCombo.getValue();
                if (teamToUpdate == null || selectedGame == null) {
                    System.out.println("Invalid");

                } else {
                    teamToUpdate.setGame(selectedGame);
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


    // Skapa tabell
    private TableView<Team> createTableView() {
        TableView<Team> teamTable = new TableView<>();

        //Id-kolumn


        // Lag-kolumn
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

        teamTable.getColumns().addAll(nameCol, gameCol);
        return teamTable;
    }

    private void loadTeamsFromDatabase(TableView<Team> teamTable) {
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
}

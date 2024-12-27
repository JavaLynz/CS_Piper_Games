package com.example.gruppcadettsplitterpipergames.view;

import com.example.gruppcadettsplitterpipergames.DAO.GamesDAO;
import com.example.gruppcadettsplitterpipergames.DAO.PlayerDAO;
import com.example.gruppcadettsplitterpipergames.DAO.TeamsDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.io.FileNotFoundException;


public class TabMenu{
    private TabPane root = new TabPane();
    private HashMap<Integer,String> currentUser;
    private StaffFX staffFX = new StaffFX(this.root);
    private TeamFX teamFX;
    private TeamsDAO teamsDAO = new TeamsDAO();
    private PlayerMatchesFX playerMatchesFX;
    private TeamMatchesFX teamMatchesFX;
    private AddressFX addressFX = new AddressFX(this.root);
    private PlayerFX playerFX = new PlayerFX();
    private GameFX gameFX = new GameFX();
    private LoginPage loginPage;
    private Scene scene;

    public TabMenu() throws FileNotFoundException {
        playerMatchesFX = new PlayerMatchesFX();
        teamMatchesFX = new TeamMatchesFX();
    }


    public Scene tabMenuScene(Stage stage) throws FileNotFoundException{
        teamFX = new TeamFX();

        root.setPrefWidth(1100);
        stage.setResizable(true);
        staffFX.setCurrentUser(this.currentUser);

        Tab staffTab = new Tab("Staff", staffFX.getStaffTab());
        staffTab.setClosable(false);
        Tab teamTab = new Tab("Team", teamFX.getView());

        teamTab.setOnSelectionChanged(event -> {
            if (teamTab.isSelected()) {
                System.out.println("Team tab selected - loading teams...");
                teamFX.loadTeamsFromDatabase(teamFX.getTeamTable());
            }
        });
        teamTab.setClosable(false);
        Tab playerMatchesTab = playerMatchesFX.getPlayerMatchesTab();
        playerMatchesTab.setClosable(false);
        Tab teamMatchesTab = teamMatchesFX.getTeamMatchesTab();
        teamMatchesTab.setClosable(false);
        Tab gamesTab = new Tab("Games", gameFX.getGamesView());
        gamesTab.setClosable(false);
        gamesTab.setOnSelectionChanged(event -> {gameFX.loadGamesFromDB(new GamesDAO().getAllGames());});
        Tab playerTab = new Tab("Player", playerFX.getPlayerView());
        playerTab.setClosable(false);
        playerTab.setOnSelectionChanged(e-> playerFX.loadPlayersFromDB(new PlayerDAO().getAllPlayers()));
        Tab addressTab = new Tab("Address", addressFX.getAddressTab());


        this.root.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            try {
                createLogoutPrompt(stage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });


        Tab logoutTab = new Tab();
        logoutTab.setGraphic(logoutBtn);
        logoutTab.setStyle("-fx-padding: 0; right:0");
        if (this.root.getTabs().isEmpty()){
            this.root.getTabs().addAll(staffTab, playerTab, teamTab, gamesTab, playerMatchesTab, teamMatchesTab, addressTab, logoutTab);
        }

        scene = new Scene(this.root);
        return scene;
    }

    public void createLogoutPrompt(Stage mainStage) throws FileNotFoundException {
        //LoginPage login = new LoginPage();
        Stage stage = new Stage();
        AnchorPane root = new AnchorPane();
        VBox container = new VBox(10);
        AnchorPane.setTopAnchor(container,5.0);
        AnchorPane.setBottomAnchor(container,5.0);
        AnchorPane.setLeftAnchor(container,5.0);
        AnchorPane.setRightAnchor(container,5.0);
        container.setPadding(new Insets(5));
        container.setAlignment(Pos.TOP_CENTER);

        Text alert = new Text("Are you sure you want to log out?");

        HBox btnContainer = new HBox(20);
        btnContainer.setAlignment(Pos.CENTER);
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setOnMouseClicked(mouseEvent -> {
            try {
                scene.setRoot(new AnchorPane());
                this.loginPage.setTabMenu(this);
                mainStage.setScene(this.loginPage.getLoginScene(mainStage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            stage.close();
        });
        Button closeBtn = new Button("Back to App");
        closeBtn.setOnMouseClicked(mouseEvent -> {
            stage.close();
        });

        container.setStyle("-fx-background-color:silver; -fx-background-radius:5;");
        btnContainer.getChildren().addAll(confirmBtn,closeBtn);
        container.getChildren().addAll(alert, btnContainer);
        root.getChildren().add(container);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public HashMap<Integer, String> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(HashMap<Integer, String> currentUser) {
        this.currentUser = currentUser;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }
}

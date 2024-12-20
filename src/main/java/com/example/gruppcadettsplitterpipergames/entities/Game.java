package com.example.gruppcadettsplitterpipergames.entities;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")

public class Game {     //Lynsey Fox


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", nullable = false)
    private int gameId;

    @Column(name = "game_name", nullable = false)
    private String gameName;

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL, mappedBy = "playerId")
    private List<Player> players;

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL, mappedBy = "id")
    private List<Team> teams;

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public Game() {}

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

@Override
    public String toString() {
        return "ID: " + gameId + ", " + gameName;
}

}

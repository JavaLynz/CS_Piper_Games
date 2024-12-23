package com.example.gruppcadettsplitterpipergames.entities;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
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

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Player> players = new ArrayList<>();

    @OneToMany(orphanRemoval = true,cascade = CascadeType.ALL, mappedBy = "game", fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Team> teams = new ArrayList<>();

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

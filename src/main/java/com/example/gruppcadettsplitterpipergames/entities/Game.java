package com.example.gruppcadettsplitterpipergames.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "games")

public class Game {     //Lynsey Fox


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int gameId;

    @Column(name = "game_name")
    private String gameName;

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

    @Override
    public String toString() {
        return gameName;
    }
}

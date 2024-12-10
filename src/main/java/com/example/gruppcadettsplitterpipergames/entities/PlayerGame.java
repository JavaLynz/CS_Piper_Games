package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "players_games")

public class PlayerGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_game_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "player_game_id")
    private List<PlayerMatch> playerMatches;

    public PlayerGame(int id, Player player, Game game) {
        this.id = id;
        this.player = player;
        this.game = game;
    }
    public PlayerGame() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

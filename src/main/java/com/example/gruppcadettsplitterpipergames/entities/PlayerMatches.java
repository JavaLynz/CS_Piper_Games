//CF
package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "player_matches")
public class PlayerMatches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "match_id")
    private int playerMatchId;

    @Column(name = "player1_name", nullable = false, length = 50)
    private String player1Name;

    @Column(name = "player2_name", nullable = false, length = 50)
    private String player2Name;

    @Column(name = "result", nullable = false, length = 20)
    private String result;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @Column(name = "match_date", length = 10)
    private String matchDate;

    @Transient
    public String getGameTitle() {
        return (game != null) ? game.getGameName() : null;
    }

    public PlayerMatches() {}

    public PlayerMatches(String player1Name, String player2Name, String result, Game game, String matchDate) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.result = result;
        this.game = game;
        this.matchDate = matchDate;
    }

    public int getPlayerMatchId() {
        return playerMatchId;
    }

    public void setPlayerMatchId(int playerMatchId) {
        this.playerMatchId = playerMatchId;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getMatchDate() {
        return matchDate;
    }
    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    @Override
    public String toString() {
        return "PlayerMatches{" +
                "playerMatchId=" + playerMatchId +
                ", player1Name='" + player1Name + '\'' +
                ", player2Name='" + player2Name + '\'' +
                ", result='" + result + '\'' +
                ", game=" + game +
                '}';
    }
}
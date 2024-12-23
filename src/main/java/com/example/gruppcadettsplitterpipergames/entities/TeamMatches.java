//CF
package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "team_matches")
public class TeamMatches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "team1_name", nullable = false, length = 50)
    private String team1Name;

    @Column(name = "team2_name", nullable = false, length = 50)
    private String team2Name;

    @Column(name = "result", length = 20)
    private String result;

    @Column(name = "match_date", nullable = false)
    private String matchDate;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    public TeamMatches() {}

    public TeamMatches(String team1Name, String team2Name, String result, String matchDate, Game game) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.result = result;
        this.matchDate = matchDate;
        this.game = game;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

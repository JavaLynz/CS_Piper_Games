//CF
package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;
import java.util.List;

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

    @Column(name = "score_team1", nullable = false)
    private int scoreTeam1;

    @Column(name = "score_team2", nullable = false)
    private int scoreTeam2;

    @Column(name = "match_date", nullable = false)
    private String matchDate;

    public TeamMatches() {}

    public TeamMatches(String team1Name, String team2Name, int scoreTeam1, int scoreTeam2, String matchDate) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
        this.matchDate = matchDate;
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

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }
}

//När personalen hanterar lag ska dom kunna lägga till och ta bort befintliga spelarprofiler i laget.
//Ett lag är alltid knutet till ett visst spel och en spelare får bara ingå i ett lag.


package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")

public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    private int id;

    @Column (name = "team_name", length = 50, nullable = false)
    private String name;


    // Flera lag till ett spel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    // Ett lag till en spelare
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>();

    // Flera lag till flera matcher
//    @ManyToMany
//    @JoinTable(name = "team_team_matches", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "team_match_id"))
//    private List<TeamMatch> teamMatches = new ArrayList<>();


    public Team() {}


    public Team(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

//    public List<TeamMatch> getTeamMatches() {
//        return teamMatches;
//    }
//
//    public void setTeamMatches(List<TeamMatch> teamMatches) {
//        this.teamMatches = teamMatches;
//    }

}

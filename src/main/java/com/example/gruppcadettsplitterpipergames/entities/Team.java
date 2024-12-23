package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false)
    private int id;

    @Column(name = "team_name", length = 50, nullable = false)
    private String name;

    // Flera lag till ett spel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Se till att laget tas bort om spelet tas bort
    private Game game;

    // Ett lag till flera spelare
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE) // Se till att spelare tas bort om laget tas bort
    private List<Player> players = new ArrayList<>();

    // Flera lag till flera matcher
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "team_team_matches", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "team_match_id"))
    @OnDelete(action = OnDeleteAction.CASCADE) // Se till att relationen till matcher tas bort när laget tas bort
    private List<TeamMatches> teamMatches = new ArrayList<>();

    // Konstruktorer
    public Team() {}

    public Team(String name, Game game) {
        this.name = name;
        this.game = game;
    }

    // Lägg till en spelare i laget
    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            player.setTeam(this);
        }
    }

    // Ta bort en spelare från laget
    public void removePlayer(Player player) {
        if (players.contains(player)) {
            players.remove(player);
            player.setTeam(null);
        }
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

    public List<TeamMatches> getTeamMatches() {
        return teamMatches;
    }

    public void setTeamMatches(List<TeamMatches> teamMatches) {
        this.teamMatches = teamMatches;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", game=" + (game != null ? game.getGameId() : "null") +
                ", playersCount=" + (players != null ? players.size() : 0) +
                ", teamMatchesCount=" + (teamMatches != null ? teamMatches.size() : 0) +
                '}';
    }
}

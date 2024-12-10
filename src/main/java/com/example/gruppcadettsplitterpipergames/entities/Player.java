package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "players")

public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int playerId;

    @Column(name = "player_first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "player_last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "player_nick_name", length = 20, nullable = false)
    private String nickName;
/*
    @ManyToOne
    @JoinColumn(name = "addressId")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private List<Team> teams = new ArrayList<>();

    @OneToMany (mappedBy ="playerMatchId")
    @JoinColumn(name = "player_match")
    private List<PlayerMatch> playerMatches = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "game_id")
    private List<Game> games = new ArrayList<>();
*/

    public Player() {
    }

    public Player(String firstName, String lastName, String nickName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /*
    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getTeams() {
        return teams;
    }

    public void setTeams(int teams) {
        this.teams = teams;
    }

    public int getPmatches() {
        return pmatches;
    }

    public void setPmatches(int pmatch) {
        this.pmatches = pmatch;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

     */
}



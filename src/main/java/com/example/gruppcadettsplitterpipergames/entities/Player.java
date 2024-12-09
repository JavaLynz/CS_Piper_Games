package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "players")

public class Player {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private int id;

    @Column(name = "player_first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "player_last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "player_nick_name", length = 20, nullable = false)
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private int address_id;

    @OneToMany
    @JoinColumn(name = "team_id")
    private Team team_id;

    @OneToMany
    @JoinColumn(name = "player_match_id")
    private PlayerMatch pmatch;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    public Player() {
    }

    public Player(int id, String firstName, String lastName, String nickName, Address address, Team team, PlayerMatch pmatch, Game game) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.address = address;
        this.team = team;
        this.pmatch = pmatch;
        this.game = game;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public PlayerMatch getPmatch() {
        return pmatch;
    }

    public void setPmatch(PlayerMatch pmatch) {
        this.pmatch = pmatch;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}



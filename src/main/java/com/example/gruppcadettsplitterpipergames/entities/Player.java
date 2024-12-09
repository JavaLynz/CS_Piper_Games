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
    private int addressId;

    @OneToMany
    @JoinColumn(name = "team_id")
    private int teamId;

    @OneToMany
    @JoinColumn(name = "player_match_id")
    private int pmatchId;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private int gameId;


    public Player() {
    }

    public Player(int id, String firstName, String lastName, String nickName, int addressId, int teamId, int pmatchId, int gameId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.addressId = addressId;
        this.teamId = teamId;
        this.pmatchId = pmatchId;
        this.gameId = gameId;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPmatchId() {
        return pmatchId;
    }

    public void setPmatchId(int pmatchId) {
        this.pmatchId = pmatchId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}



package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "players")

public class Player {   //Lynsey Fox


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

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    /*
        @OneToMany (mappedBy ="playerMatchId")
        private List<PlayerMatch> playerMatches = new ArrayList<>();
    */
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    public Player() {
    }

    public Player(String firstName, String lastName, String nickName, int addressId, int gameID) {
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
    /*
        public int getPmatches() {
            return pmatches;
        }

        public void setPmatches(int pmatch) {
            this.pmatches = pmatch;
        }
    */
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


}



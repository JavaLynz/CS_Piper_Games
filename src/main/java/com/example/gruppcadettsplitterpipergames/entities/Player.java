package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "email", length = 20, nullable = true)
    private String email;

    @ManyToOne (fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "address_id",foreignKey = @ForeignKey(name = "fk_address_id"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Address address;

    @ManyToOne (fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", foreignKey = @ForeignKey(name = "fk_team_id"))
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Team team;

    @OneToMany (mappedBy ="matchId",fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<PlayerMatches> playerMatches = new ArrayList<>();

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name = "fk_game_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;




    public Player() {
    }

    public Player(String firstName, String nickName, String lastName) {
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

    public List<PlayerMatches> getPmatches() {
         return playerMatches;
    }

    public void setPmatches(int pmatch) {
        this.playerMatches = playerMatches;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

@Override
    public String toString() {
        return "\r\n" + firstName + " '"+ nickName + "' " + lastName;
}

}



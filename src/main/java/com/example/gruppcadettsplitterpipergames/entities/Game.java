package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "games")

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private int id;

    @Column(name = "game_name")
    private String gameName;

    @OneToMany (mappedBy = "game_id")
    private List<PlayerGame> gamesPlayed;
}

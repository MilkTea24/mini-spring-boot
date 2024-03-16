package com.example.week13.p05;

import jakarta.persistence.*;

@Entity
public class Player {
    @Id
    @GeneratedValue
    @Column(name = "player_id")
    private int id;


    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    public void setClub(Club club) {
        this.club = club;
    }
}

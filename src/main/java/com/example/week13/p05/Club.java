package com.example.week13.p05;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Entity
public class Club {
    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "club")
    private final List<Player> players = new ArrayList<>();


    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Long getId() {
        return id;
    }

    public Object getName() {
        return name;
    }
}

package com.example.week13.p05;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club save(Club club);
}

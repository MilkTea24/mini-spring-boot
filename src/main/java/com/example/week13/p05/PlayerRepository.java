package com.example.week13.p05;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player find(long l);
}

package com.example.week13.p05;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FifaJpaTemplateTest {

    public static void main(String[] args) {

        disableLogging();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pnu.cse");

        EntityManager em = emf.createEntityManager();

        RepositoryFactoryBean<PlayerRepository, Player, Long> playerRepoFactory = new RepositoryFactoryBean<>(PlayerRepository.class, Player.class, em);

        PlayerRepository playerRepository = playerRepoFactory.createRepository();

        RepositoryFactoryBean<ClubRepository, Club, Long> clubRepoFactory = new RepositoryFactoryBean<>(ClubRepository.class, Club.class, em);

        ClubRepository clubRepository = clubRepoFactory.createRepository();



        Club club = new Club();

        club.setName("PNU");

        Player player = playerRepository.find(1L);

        player.setClub(club);

        club.getPlayers().add(player);

        clubRepository.save(club);



        System.out.printf("%d, %s, %s\n", club.getId(), club.getName(), club.getPlayers());

    }



    private static void disableLogging() {

        LogManager logManager = LogManager.getLogManager();

        Logger logger = logManager.getLogger("");

        logger.setLevel(Level.OFF);

    }

}



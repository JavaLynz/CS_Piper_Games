package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.PlayerMatches;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class PlayerMatchesDAO {

    private EntityManagerFactory emf;

    public PlayerMatchesDAO() {
        emf = Persistence.createEntityManagerFactory("cadettsplitterspipergames");
    }

    public List<PlayerMatches> showMatches() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT pm FROM PlayerMatches pm", PlayerMatches.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void addMatch(PlayerMatches match) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(match);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeMatch(PlayerMatches match) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PlayerMatches toDelete = em.find(PlayerMatches.class, match.getPlayerMatchId());
            if (toDelete != null) {
                em.remove(toDelete);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void updateMatch(PlayerMatches match) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PlayerMatches toUpdate = em.find(PlayerMatches.class, match.getPlayerMatchId());
            if (toUpdate != null) {
                toUpdate.setPlayer1Name(match.getPlayer1Name());
                toUpdate.setPlayer2Name(match.getPlayer2Name());
                toUpdate.setResult(match.getResult());
                toUpdate.setGame(match.getGame());
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
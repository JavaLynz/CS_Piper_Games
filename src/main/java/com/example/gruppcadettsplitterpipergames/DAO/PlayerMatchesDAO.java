package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.PlayerMatches;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("myconfig");

    // CREATE
    public boolean savePlayerMatch(PlayerMatches playerMatch) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(playerMatch);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // READ ONE
    public PlayerMatches getPlayerMatchById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        PlayerMatches matchToReturn = entityManager.find(PlayerMatches.class, id);
        entityManager.close();
        return matchToReturn;
    }

    // READ ALL
    public List<PlayerMatches> getAllPlayerMatches() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<PlayerMatches> matchesToReturn = new ArrayList<>();
        TypedQuery<PlayerMatches> query = entityManager.createQuery(
                "SELECT pm FROM PlayerMatches pm", PlayerMatches.class);
        matchesToReturn.addAll(query.getResultList());
        entityManager.close();
        return matchesToReturn;
    }

    // UPDATE
    public void updatePlayerMatch(PlayerMatches matchToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(matchToUpdate)) {
                System.out.println("PlayerMatch is in the database");
                entityManager.persist(matchToUpdate);
            } else {
                System.out.println("PlayerMatch is not in the database");
                PlayerMatches revivedMatch = entityManager.merge(matchToUpdate);
                System.out.println("Merged match with ID: " + revivedMatch.getPlayerMatchId());
            }
            entityManager.merge(matchToUpdate);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // DELETE
    public void deletePlayerMatch(PlayerMatches matchToDelete) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(matchToDelete)) {
                matchToDelete = entityManager.merge(matchToDelete);
            }
            entityManager.remove(matchToDelete);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public boolean deletePlayerMatchById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            PlayerMatches matchToDelete = entityManager.find(PlayerMatches.class, id);
            if (matchToDelete != null) {
                entityManager.remove(matchToDelete);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}
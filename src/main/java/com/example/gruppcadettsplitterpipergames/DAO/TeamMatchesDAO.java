package com.example.dao;

import com.example.entities.PlayerMatches;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class TeamMatchDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");


    public List<PlayerMatches> getAllMatches() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<PlayerMatches> matches = new ArrayList<>();
        try {
            TypedQuery<PlayerMatches> query = entityManager.createQuery("SELECT m FROM PlayerMatches m", PlayerMatches.class);
            matches = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error retrieving matches: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return matches;
    }

    public boolean addMatch(PlayerMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error adding match: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public boolean updateMatch(PlayerMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(match);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error updating match: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public boolean removeMatch(PlayerMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(match)) {
                match = entityManager.merge(match);
            }
            entityManager.remove(match);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error removing match: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public boolean removeMatchById(int matchId) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            PlayerMatches match = entityManager.find(PlayerMatches.class, matchId);
            if (match != null) {
                entityManager.remove(match);
                transaction.commit();
                return true;
            } else {
                System.out.println("Match with ID " + matchId + " not found.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error removing match by ID: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}

//CF
package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.TeamMatches;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TeamMatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("myconfig");

    // CREATE
    public boolean saveTeamMatch(TeamMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // READ ALL
    public List<TeamMatches> getAllTeamMatches() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<TeamMatches> matches = new ArrayList<>();
        try {
            TypedQuery<TeamMatches> query = entityManager.createQuery(
                    "SELECT t FROM TeamMatches t", TeamMatches.class
            );
            matches = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return matches;
    }

    // READ ONE
    public TeamMatches getTeamMatchById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        TeamMatches match = null;
        try {
            match = entityManager.find(TeamMatches.class, id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return match;
    }

    // UPDATE
    public boolean updateTeamMatch(TeamMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(match)) {
                match = entityManager.merge(match);
            }
            // If you want to do more update logic, you can place it here
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // DELETE
    public boolean deleteTeamMatch(TeamMatches match) {
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
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    // DELETE BY ID
    public boolean deleteTeamMatchById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TeamMatches match = entityManager.find(TeamMatches.class, id);
            if (match != null) {
                entityManager.remove(match);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}

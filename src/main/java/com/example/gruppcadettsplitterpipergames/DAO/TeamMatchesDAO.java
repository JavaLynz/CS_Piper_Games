//CF
package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.TeamMatches;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TeamMatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");


    public boolean addTeamMatch(TeamMatches teamMatch) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(teamMatch);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error adding TeamMatch: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }


    public TeamMatches getTeamMatchById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return entityManager.find(TeamMatches.class, id);
        } finally {
            entityManager.close();
        }
    }


    public List<TeamMatches> getAllTeamMatches() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<TeamMatches> teamMatches = new ArrayList<>();
        try {
            TypedQuery<TeamMatches> query = entityManager.createQuery("SELECT tm FROM TeamMatches tm", TeamMatches.class);
            teamMatches = query.getResultList();
        } finally {
            entityManager.close();
        }
        return teamMatches;
    }


    public boolean updateTeamMatch(TeamMatches teamMatch) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(teamMatch)) {
                entityManager.merge(teamMatch);
            }
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

    public boolean removeTeamMatch(TeamMatches teamMatch) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(teamMatch)) {
                teamMatch = entityManager.merge(teamMatch);
            }
            entityManager.remove(teamMatch);
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


    public boolean removeTeamMatchById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TeamMatches teamMatch = entityManager.find(TeamMatches.class, id);
            if (teamMatch != null) {
                entityManager.remove(teamMatch);
                transaction.commit();
                return true;
            } else {
                System.out.println("TeamMatch Id" + id + "finns ej.");
                return false;
            }
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

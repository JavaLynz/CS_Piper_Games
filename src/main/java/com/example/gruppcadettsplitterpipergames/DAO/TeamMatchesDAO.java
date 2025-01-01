//CF
package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import com.example.gruppcadettsplitterpipergames.entities.TeamMatches;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TeamMatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("myconfig");

    public boolean saveTeamMatch(TeamMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(match);
            transaction.commit();
            System.out.println("Team match saved" + match);
            return true;
        } catch (Exception e) {
            System.out.println("Error saving team match" + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public List<TeamMatches> getAllTeamMatches() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<TeamMatches> matches = new ArrayList<>();
        try {
            TypedQuery<TeamMatches> query = entityManager.createQuery(
                    "SELECT t FROM TeamMatches t", TeamMatches.class
            );
            matches = query.getResultList();
            System.out.println("Fetched team matches: " + matches.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return matches;
    }

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

    public boolean updateTeamMatch(TeamMatches match) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(match)) {
                match = entityManager.merge(match);
            }
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error updating team match: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

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
            System.out.println("Error deleting team match: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

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
            System.out.println("Error deleting team" + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    public List<Team> getTeamsByGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> teams = new ArrayList<>();
        try {
            TypedQuery<Team> query = entityManager.createQuery(
                    "SELECT t FROM Team t WHERE t.game = :game", Team.class
            );
            query.setParameter("game", game);
            teams = query.getResultList();
            System.out.println("Fetched " + teams.size() + " teams for game: " + game.getGameName());
        } catch (Exception e) {
            System.out.println("Error fetching teams" + e.getMessage());
        } finally {
            entityManager.close();
        }
        return teams;
    }
}

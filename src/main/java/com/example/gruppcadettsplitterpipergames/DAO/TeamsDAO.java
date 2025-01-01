package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

// Klass av Niklas

public class TeamsDAO {


    private EntityManagerFactory entityManagerFactory;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public TeamsDAO() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myconfig");
    }

    // Create
    public boolean saveTeam(Team team) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(team);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        }
    }


    // Read One
    public Team getTeamById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Team teamToReturn = entityManager.find(Team.class, id);
        entityManager.close();
        return teamToReturn;
    }

    // Read all
    public List<Team> getAllTeams(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> listToReturn = new ArrayList<>();
        TypedQuery<Team> result = entityManager.createQuery("FROM Team", Team.class);
        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    // Update
    public void updateTeam(Team teamToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(teamToUpdate)) {
                entityManager.persist(teamToUpdate);
            } else {
                entityManager.persist(entityManager.merge(teamToUpdate));
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    // Delete
    public void deleteTeam(Team teamToDelete) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(teamToDelete)) {
                teamToDelete = entityManager.merge(teamToDelete);
            }
            entityManager.remove(teamToDelete);
            transaction.commit();
            Team found = entityManager.find(Team.class, teamToDelete.getId());
            if (found == null) {
                System.out.println("Team successfully deleted from DB");
            } else {
                System.out.println("Team was NOT deleted from DB");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }

        } finally {
            entityManager.close();
        }
    }

    // Delete by id
    public void deleteTeamById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Team teamToDelete = entityManager.find(Team.class, id);
           if(!entityManager.contains(teamToDelete)) {
               teamToDelete = entityManager.merge(teamToDelete);
           }
           entityManager.remove(teamToDelete);
           transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public List<Team> getTeamsByGameNames(List<Game> selectedGames) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> teams = new ArrayList<>();
        try {
            String queryString = "SELECT t FROM Team t WHERE t.game IN :selectedGames";
            TypedQuery<Team> query = entityManager.createQuery(queryString, Team.class);
            query.setParameter("selectedGames", selectedGames);
            teams = query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
        return teams;
    }
    public Team getTeamByName(String name) {
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            return em.createQuery("SELECT t FROM Team t WHERE t.name = :name", Team.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle the case where no team is found
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    public List<Team> getTeamsByGame(Game selectedGame) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Team> teams = new ArrayList<>();
        try {
            String queryString = "SELECT t FROM Team t WHERE t.game = :selectedGame";
            TypedQuery<Team> query = entityManager.createQuery(queryString, Team.class);
            query.setParameter("selectedGame", selectedGame);
            teams = query.getResultList();
        } catch (Exception e) {
            System.out.println("Error fetching teams by game: " + e.getMessage());
        } finally {
            entityManager.close();
        }
        return teams;
    }

}

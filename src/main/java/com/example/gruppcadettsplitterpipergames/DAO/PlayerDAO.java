package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Game;
import com.example.gruppcadettsplitterpipergames.entities.Player;
import com.example.gruppcadettsplitterpipergames.entities.Team;
import jakarta.persistence.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerDAO {    //Lynsey Fox
//CRUD operations

private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

//CREATE
public boolean savePlayer(Player player) {
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    EntityTransaction transaction = null;
    try {
        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(player);
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

//READ ONE / ALL
public Player getPlayerById(int id){
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    Player playerToReturn = entityManager.find(Player.class, id);
    entityManager.close();
    return playerToReturn;
}

public List<Player> getAllPlayers() {
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    List<Player> playersToReturn = new ArrayList<>();
    TypedQuery<Player> result = entityManager.createQuery("SELECT p FROM Player p", Player.class);
    playersToReturn.addAll(result.getResultList());
    entityManager.close();
    return playersToReturn;
}

public List<Player> getPlayersByGame(Game game){
       return getPlayersByGame(Collections.singletonList(game));
    }

public List<Player> getPlayersByGame(List<Game> games){
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    List<Player> playersToReturn = new ArrayList<>();
    TypedQuery<Player> result = entityManager.createQuery("SELECT p FROM Player p WHERE p.game IN :games", Player.class);
    result.setParameter("games", games);
    playersToReturn.addAll(result.getResultList());
    entityManager.close();
    return playersToReturn;
}

    public List<Player> getPlayersByTeam(Team team){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Player> playersToReturn = new ArrayList<>();
        TypedQuery<Player> result = entityManager.createQuery("SELECT p FROM Player p WHERE p.team = :variable", Player.class);
        result.setParameter("variable", team);
        playersToReturn.addAll(result.getResultList());
        entityManager.close();
        return playersToReturn;
    }

//UPDATE
public void updatePlayer(Player playerToUpdate) {
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    EntityTransaction transaction = null;
    try {
        transaction = entityManager.getTransaction();
        transaction.begin();
        if(entityManager.contains(playerToUpdate)) {
            System.out.println("Player is in the database");
            entityManager.persist(playerToUpdate);
        }else {
            System.out.println("Player is not in the database");
            Player revivedPlayer =entityManager.merge(playerToUpdate);
            System.out.println(revivedPlayer.getFirstName() + " is now in the database");
        }
        entityManager.merge(playerToUpdate);
        transaction.commit();
    }catch (Exception e) {
        System.out.println(e.getMessage());
        if (entityManager != null && transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    } finally {
        entityManager.close();
    }
}

// DELETE
public void deletePlayer(Player playerToDelete) {
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    EntityTransaction transaction = null;
    try {
        transaction = entityManager.getTransaction();
        transaction.begin();
        if (!entityManager.contains(playerToDelete)) {
            playerToDelete = entityManager.merge(playerToDelete);
        }
        entityManager.remove(playerToDelete);
        transaction.commit();
    }catch (Exception e) {
        System.out.println(e.getMessage());
        if(entityManager != null && transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
    } finally {
        entityManager.close();
    }
}

public boolean deletePlayerById(int id) {
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    EntityTransaction transaction = null;
    try {
        transaction = entityManager.getTransaction();
        transaction.begin();
        Player playerToDelete = entityManager.find(Player.class, id);
        entityManager.remove(entityManager.contains(playerToDelete) ? playerToDelete : playerToDelete);
        transaction.commit();
        return true;
    }catch (Exception e) {
        System.out.println(e.getMessage());
        if (entityManager != null && transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        return false;
    }finally {
        entityManager.close();
    }
}













}

package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Player;
import jakarta.persistence.*;

import java.util.ArrayList;
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

public List<Player> getAllPlayers(){
    EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    List<Player> playersToReturn = new ArrayList<>();
    TypedQuery<Player> result = entityManager.createQuery("SELECT p FROM Player p", Player.class);
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
            entityManager.merge(playerToDelete);
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

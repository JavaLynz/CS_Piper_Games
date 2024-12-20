package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Game;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GamesDAO {         //Lynsey Fox

    //CRUD operations


    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //CREATE

    public boolean saveGame(Game game) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(game);
            transaction.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(transaction != null && entityManager != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
            return false;
        }finally{
            entityManager.close();
        }

    }

    //READ ONE / ALL

    public Game getGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Game gameToReturn = entityManager.find(Game.class, id);
        entityManager.close();
        return gameToReturn;
        }

    public List<Game> getAllGames() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Game> gamesToReturn = new ArrayList<Game>();
        TypedQuery<Game> result = entityManager.createQuery("SELECT g FROM Game g", Game.class);
        gamesToReturn.addAll(result.getResultList());
        entityManager.close();
        return gamesToReturn;

    }

    //UPDATE

    public void updateGame(Game gameToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(gameToUpdate)) {
                System.out.println("Game is in the database");
                entityManager.persist(gameToUpdate);

            }else {
                System.out.println("Game is not in the database");
                Game revivedGame = entityManager.merge(gameToUpdate);
                System.out.println("Game is now in the database");
            }
            entityManager.merge(gameToUpdate);
            transaction.commit();

        }catch(Exception e){
            System.out.println(e.getMessage());
            if(transaction != null && entityManager != null && entityManager.getTransaction().isActive()){
                transaction.rollback();

            }
        }finally{
            entityManager.close();
        }
    }

    //DELETE

    public void deleteGame(Game gameToDelete) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(!entityManager.contains(gameToDelete)) {
                gameToDelete = entityManager.merge(gameToDelete);
            }
            entityManager.remove(gameToDelete);
            transaction.commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(transaction != null && entityManager != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
        }finally{
            entityManager.close();
        }
    }

    public boolean deleteGameById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Game gameToDelete = entityManager.find(Game.class, id);
            entityManager.remove(entityManager.contains(gameToDelete) ? gameToDelete : entityManager.merge(gameToDelete));
            transaction.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(transaction != null && entityManager != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
            return false;
        }finally{
            entityManager.close();
        }
    }




}

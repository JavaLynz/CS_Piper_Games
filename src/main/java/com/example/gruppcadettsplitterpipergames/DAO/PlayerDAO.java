package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Player;
import jakarta.persistence.*;

public class PlayerDAO {
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












}

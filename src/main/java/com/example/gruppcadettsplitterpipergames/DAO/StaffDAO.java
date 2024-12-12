package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Staff;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
    //CRUD
    //CREATE
    public boolean saveStaff(Staff staff){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(staff);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
    //READ ONE
    public Staff getStaffById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Staff staffToReturn = entityManager.find(Staff.class, id);
            transaction.commit();
            return staffToReturn;
        } catch (Exception e){
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            return null;
        } finally {
            entityManager.close();
        }
    }

    //READ ALL
    public List<Staff> getAllStaff(){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Staff> staffToReturn = new ArrayList<>();
        TypedQuery<Staff> result = entityManager.createQuery("FROM Staff", Staff.class);
        staffToReturn.addAll(result.getResultList());
        return staffToReturn;
    }

    //UPDATE
    public void updateStaff(Staff staffToUpdate){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(staffToUpdate)){
                entityManager.persist(staffToUpdate);
            } else {
                entityManager.persist(entityManager.merge(staffToUpdate));
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
    //DELETE
    public void deleteStaff(Staff staffToDelete){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(staffToDelete)){
                staffToDelete = entityManager.merge(staffToDelete);
            }
            entityManager.remove(staffToDelete);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
    //DELETE BY ID
    public void deleteStaffById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Staff staffToDelete = entityManager.find(Staff.class, id);
            if (!entityManager.contains(staffToDelete)){
                staffToDelete = entityManager.merge(staffToDelete);
            }
            entityManager.remove(staffToDelete);
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            if (entityManager != null && transaction != null && transaction.isActive()){
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }
}

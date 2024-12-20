package com.example.gruppcadettsplitterpipergames.DAO;

import com.example.gruppcadettsplitterpipergames.entities.Address;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


public class AddressDAO {           //Lynsey Fox
    // CRUD operations

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //CREATE
    public boolean saveAddress(Address address) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(address);
            transaction.commit();
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }

    //READ ONE / ALL

    public Address getAddressById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Address addressToReturn = entityManager.find(Address.class, id);
        entityManager.close();
        return addressToReturn;
    }

    public List<Address> getAllAddress() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Address> addressesToReturn = new ArrayList<>();
        TypedQuery<Address> result = entityManager.createQuery("SELECT a FROM Address a", Address.class);
        addressesToReturn.addAll(result.getResultList());
        entityManager.close();
        return addressesToReturn;
    }

    //UPDATE

    public void updateAddress(Address addressToUpdate) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (entityManager.contains(addressToUpdate)) {
                System.out.println("Address is in the database");
                entityManager.persist(addressToUpdate);
            }else{
                System.out.println("Address is not in the database");
                Address revivedAddress = entityManager.merge(addressToUpdate);
                System.out.println("Address added to database");
            }
            entityManager.merge(addressToUpdate);
            transaction.commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
        }finally {
            entityManager.close();
        }
    }

    //DELETE

    public void deleteAddress(Address addressToDelete) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            if(!entityManager.contains(addressToDelete)) {
                entityManager.merge(addressToDelete);
            }
            entityManager.remove(addressToDelete);
            transaction.commit();
        }catch(Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
        }finally{
            entityManager.close();
        }
    }

    public boolean deleteAddressById(int id) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try{
            transaction = entityManager.getTransaction();
            transaction.begin();
            Address addressToDelete = entityManager.find(Address.class, id);
            entityManager.remove(entityManager.contains(addressToDelete) ? addressToDelete : addressToDelete);
            transaction.commit();
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            if(entityManager != null && transaction != null && entityManager.getTransaction().isActive()){
                transaction.rollback();
            }
            return false;
        } finally {
            entityManager.close();
        }
    }
}

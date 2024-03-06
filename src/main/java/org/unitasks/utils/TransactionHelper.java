package org.unitasks.utils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;
import java.util.function.Supplier;

public final class TransactionHelper {

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Metamodel metamodel;
    private static TransactionHelper transactionHelper;

    private TransactionHelper() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    public static TransactionHelper getTransactionHelper(){
        if (transactionHelper == null){
            transactionHelper = new TransactionHelper();
        }
        return transactionHelper;
    }

    public void begin() {
        getEntityManagerIfClosed();
        entityManager.getTransaction().begin();
    }

    public void commit() {
        entityManager.getTransaction().commit();
    }

    public void rollback() {
        if (entityManager.getTransaction().isActive()
                || entityManager.getTransaction().getRollbackOnly()) {
            entityManager.getTransaction().rollback();
        }
    }

    private void getEntityManagerIfClosed() {
        if (!entityManager.isOpen()) {
            entityManager = HibernateUtil.getEntityManager();
        }
    }

    public EntityManager entityManager() {
        getEntityManagerIfClosed();
        return entityManager;
    }
    public void closeEntityManager(){
        entityManager.close();
    }

    public CriteriaBuilder criteriaBuilder() {
        if (criteriaBuilder == null) {
            criteriaBuilder = entityManager.getCriteriaBuilder();
        }
        return criteriaBuilder;
    }

    public Metamodel metamodel() {
        if (metamodel == null) {
            metamodel = entityManager.getMetamodel();
        }
        return metamodel;
    }

    public <T> void persist(T obj) {
        entityManager.persist(obj);
    }

    public <T, R> T find(Class<T> tClass, R id) {
        getEntityManagerIfClosed();
        return entityManager.find(tClass, id);
    }

    public <T> void remove(T obj) {
        entityManager.remove(obj);
    }

    public <T> void merge(T obj) {
        entityManager.merge(obj);
    }

    public <T> T transaction(Supplier<T> method) {
        begin();
        T obj = null;
        try {
            obj = method.get();
            commit();

        } catch (Exception e) {
            e.printStackTrace();
            rollback();
        }
        closeEntityManager();
        return obj;
    }

}

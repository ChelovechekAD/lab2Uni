package org.unitasks.DAO.Impl;

import org.unitasks.DAO.DAO;
import org.unitasks.utils.Constants;
import org.unitasks.utils.TransactionHelper;

import java.io.Serializable;
import java.util.function.Supplier;

public abstract class DAOImpl<T extends Serializable, R extends Number> implements DAO<T, R> {

    public TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();

    protected abstract Class<T> getClazz();

    public T save(T obj) {
        assert obj != null : Constants.NULL_EXCEPTION_MESSAGE;
        transactionHelper.persist(obj);
        return obj;
    }

    public T get(R id) {
        return transactionHelper.find(getClazz(), id);
    }

    public T update(T obj) {
        assert obj != null : Constants.NULL_EXCEPTION_MESSAGE;
        transactionHelper.merge(obj);
        return obj;
    }

    public boolean delete(R id) {

        Supplier<T> del = () -> {
            T obj = transactionHelper.find(getClazz(), id);
            assert obj != null : Constants.NULL_EXCEPTION_MESSAGE;
            transactionHelper.remove(obj);
            return transactionHelper.find(getClazz(), id);
        };
        return transactionHelper.transaction(del) == null;

    }

}

package org.unitasks.DAO.Impl;

import org.unitasks.DAO.DisciplineDAO;
import org.unitasks.models.Discipline;
import org.unitasks.utils.Constants;

import java.util.function.Supplier;

public class DisciplineDAOImpl extends DAOImpl<Discipline, Integer> implements DisciplineDAO {
    @Override
    protected Class<Discipline> getClazz() {
        return Discipline.class;
    }

    @Override
    public boolean delete(Integer id) {
        Supplier<Discipline> del = () -> {
            Discipline discipline = transactionHelper.find(getClazz(), id);
            assert discipline != null : Constants.NULL_EXCEPTION_MESSAGE;
            transactionHelper.entityManager()
                    .createQuery("delete from ClassUni c where c.classPK.discipline = " + discipline.getId())
                    .executeUpdate();
            transactionHelper.remove(discipline);
            return transactionHelper.find(getClazz(), id);
        };
        return transactionHelper.transaction(del) == null;
    }
}

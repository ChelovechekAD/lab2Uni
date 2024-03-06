package org.unitasks.DAO.Impl;

import org.unitasks.DAO.DisciplineDAO;
import org.unitasks.models.Discipline;

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
            transactionHelper.entityManager()
                    .createQuery("delete from ClassUni c where c.classPK.discipline = " + discipline.getId())
                    .executeUpdate();
            transactionHelper.remove(discipline);
            Discipline discipline1 = transactionHelper.find(getClazz(), id);
            transactionHelper.commit();
            return discipline1;
        };
        return transactionHelper.transaction(del) == null;
    }
}

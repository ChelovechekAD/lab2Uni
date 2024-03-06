package org.unitasks.DAO.Impl;

import org.unitasks.DAO.AuditoryDAO;
import org.unitasks.models.Auditory;
import org.unitasks.models.Discipline;
import org.unitasks.utils.HibernateUtil;

import javax.persistence.EntityManager;

public class AuditoryDAOImpl extends DAOImpl<Auditory, Integer> implements AuditoryDAO {
    @Override
    protected Class<Auditory> getClazz() {
        return Auditory.class;
    }

    @Override
    public boolean delete(Integer id) {
        EntityManager em = HibernateUtil.getEntityManager();
        transactionHelper.begin();
        try {
            Auditory auditory = transactionHelper.find(getClazz(), id);
            transactionHelper.remove(auditory);
            Auditory auditory1 = transactionHelper.find(getClazz(), id);
            transactionHelper.commit();
            return auditory1 == null;
        } catch (Exception e) {
            e.printStackTrace();
            transactionHelper.rollback();
        }
        return false;
    }
}

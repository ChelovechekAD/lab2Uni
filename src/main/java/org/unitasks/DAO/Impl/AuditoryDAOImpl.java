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

}

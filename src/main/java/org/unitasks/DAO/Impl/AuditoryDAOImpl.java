package org.unitasks.DAO.Impl;

import org.unitasks.DAO.AuditoryDAO;
import org.unitasks.models.Auditory;

public class AuditoryDAOImpl extends DAOImpl<Auditory, Integer> implements AuditoryDAO {
    @Override
    protected Class<Auditory> getClazz() {
        return Auditory.class;
    }

}

package org.unitasks.DAO.Impl;

import org.unitasks.DAO.ProfessorDAO;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;
import org.unitasks.utils.Constants;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.function.Supplier;

public class ProfessorDAOImpl extends DAOImpl<Professor, Integer> implements ProfessorDAO {
    @Override
    protected Class<Professor> getClazz() {
        return Professor.class;
    }

    @Override
    public boolean delete(Integer id){
        Supplier<Professor> del = () -> {
            Professor professor = transactionHelper.find(getClazz(), id);
            assert professor != null : Constants.NULL_EXCEPTION_MESSAGE;

            transactionHelper.entityManager()
                    .createQuery("delete from ClassUni c where c.classPK.professor = "
                            + professor.getId())
                    .executeUpdate();

            transactionHelper.entityManager()
                    .createNativeQuery("update lab2.discipline set professor_id = null where professor_id = "
                            + professor.getId()).executeUpdate();

            transactionHelper.remove(professor);
            return transactionHelper.find(getClazz(), id);
        };
        return transactionHelper.transaction(del) == null;
    }
}

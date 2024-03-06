package org.unitasks.services;

import org.unitasks.DTO.response.CountClassesEachDayDTOResponse;
import org.unitasks.models.Auditory;
import org.unitasks.models.ClassUni;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;
import org.unitasks.models.embeddable.ClassPK;
import org.unitasks.utils.TransactionHelper;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.time.DayOfWeek;
import java.util.List;

public final class InfoService {

    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
    private final CriteriaBuilder criteriaBuilder = transactionHelper.criteriaBuilder();
    private final Metamodel metamodel = transactionHelper.metamodel();

    public List<Professor> getAllProfWithClassesOnDayAndAuditoryNum(DayOfWeek dayOfWeek, String auditory) {

        CriteriaQuery<Professor> cq = criteriaBuilder.createQuery(Professor.class);
        Root<Professor> root = cq.from(Professor.class);

        EntityType<Professor> Professor_ = metamodel.entity(Professor.class);
        EntityType<Discipline> Discipline_ = metamodel.entity(Discipline.class);

        Join<Professor, Discipline> disciplineJoin = root.join(Professor_.getAttribute("disciplineList").getName());
        Join<Discipline, Auditory> auditoryJoin = disciplineJoin.join(Discipline_.getAttribute("auditoryList").getName());

        Predicate predicates = criteriaBuilder.conjunction();
        predicates.getExpressions().add(criteriaBuilder.equal(auditoryJoin.get("auditoryNum"), auditory));
        predicates.getExpressions().add(criteriaBuilder.equal(disciplineJoin.get("dayOfWeek"), dayOfWeek));

        cq.select(root).where(predicates);
        List<Professor> out = transactionHelper.entityManager().createQuery(cq).getResultList();
        transactionHelper.closeEntityManager();
        return out;
    }

    public List<Professor> getAllProfWithoutClassesOnTheSelectedDay(DayOfWeek dayOfWeek) {

        CriteriaQuery<Professor> cq = criteriaBuilder.createQuery(Professor.class);
        Root<Professor> root = cq.from(Professor.class);

        EntityType<Professor> Professor_ = metamodel.entity(Professor.class);

        Join<Professor, Discipline> disciplineJoin = root.join(Professor_.getAttribute("disciplineList").getName());

        cq.select(root).where(criteriaBuilder.notEqual(disciplineJoin.get("dayOfWeek"), dayOfWeek));
        List<Professor> out = transactionHelper.entityManager().createQuery(cq).getResultList();
        transactionHelper.closeEntityManager();
        return out;
    }

    public List<CountClassesEachDayDTOResponse> getAllDaysWithCountOfClassesMoreThen(int count) {

        CriteriaQuery<DayOfWeek> cq = criteriaBuilder.createQuery(DayOfWeek.class);
        Root<ClassUni> root = cq.from(ClassUni.class);

        EntityType<ClassUni> ClassUni_ = metamodel.entity(ClassUni.class);

        Join<ClassUni, ClassPK> classUniClassPKJoin = root.join(ClassUni_.getAttribute("classPK").getName());
        Join<ClassPK, Discipline> disciplineJoin = classUniClassPKJoin.join("discipline");
        //TODO Из селетка вернуть день недели и сумму занятий в этот день. Хз как запихнуть это в дто.
        cq.select(disciplineJoin.get("dayOfWeek")/*, CRITERIA_BUILDER.sum(root.get("countOfClass"))*/)
                .groupBy(disciplineJoin.get("dayOfWeek"))
                .having(criteriaBuilder.ge(criteriaBuilder.sum(root.get("countOfClass")), count));
        transactionHelper.entityManager().createQuery(cq).getResultList().forEach(System.out::println);
        return null;
    }


}

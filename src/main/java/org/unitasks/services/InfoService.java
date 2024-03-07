package org.unitasks.services;

import org.unitasks.DTO.request.ProfessorInfoDTORequest;
import org.unitasks.DTO.response.CountClassesEachDayDTOResponse;
import org.unitasks.DTO.response.ProfessorInfoDTOResponse;
import org.unitasks.models.Auditory;
import org.unitasks.models.ClassUni;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;
import org.unitasks.models.embeddable.ClassPK;
import org.unitasks.utils.TransactionHelper;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

public final class InfoService {

    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();
    private final CriteriaBuilder criteriaBuilder = transactionHelper.criteriaBuilder();
    private final Metamodel metamodel = transactionHelper.metamodel();

    public List<ProfessorInfoDTOResponse> getAllProfWithClassesOnDayAndAuditoryNum(ProfessorInfoDTORequest dto) {

        CriteriaQuery<Professor> cq = criteriaBuilder.createQuery(Professor.class);
        Root<Professor> root = cq.from(Professor.class);

        Join<Professor, Discipline> disciplineJoin = root.join("disciplineList");
        Join<Discipline, Auditory> auditoryJoin = disciplineJoin.join("auditoryList");

        Predicate predicates = criteriaBuilder.conjunction();
        predicates.getExpressions().add(criteriaBuilder.equal(auditoryJoin.get("auditoryNum"), dto.getAuditoryNum()));
        predicates.getExpressions().add(criteriaBuilder.equal(disciplineJoin.get("dayOfWeek"), dto.getDayOfWeek()));

        cq.select(root).where(predicates);
        List<Professor> out = transactionHelper.entityManager().createQuery(cq).getResultList();
        return out.stream()
                .map(p -> ProfessorInfoDTOResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .surname(p.getSurname())
                        .middleName(p.getMiddleName())
                        .countOfDisciplines(p.getDisciplineList().size())
                        .build())
                .collect(Collectors.toList());
    }

    public List<ProfessorInfoDTOResponse> getAllProfWithoutClassesOnTheSelectedDay(DayOfWeek dayOfWeek) {

        CriteriaQuery<Professor> cq = criteriaBuilder.createQuery(Professor.class);
        Root<Professor> root = cq.from(Professor.class);

        Join<Professor, Discipline> disciplineJoin = root.join("disciplineList");

        cq.select(root)
                .where(criteriaBuilder.notEqual(disciplineJoin.get("dayOfWeek"), dayOfWeek))
                .distinct(true);
        List<Professor> out = transactionHelper.entityManager().createQuery(cq).getResultList();
        return out.stream()
                .map(p -> ProfessorInfoDTOResponse.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .surname(p.getSurname())
                        .middleName(p.getMiddleName())
                        .countOfDisciplines(p.getDisciplineList().size())
                        .build())
                .collect(Collectors.toList());
    }

    public List<CountClassesEachDayDTOResponse> getDaysWithCountOfClassesGE(Long count) {

        CriteriaQuery<DayOfWeek> cq = criteriaBuilder.createQuery(DayOfWeek.class);
        Root<ClassUni> root = cq.from(ClassUni.class);

        Join<ClassUni, ClassPK> classUniClassPKJoin = root.join("classPK");
        Join<ClassPK, Discipline> disciplineJoin = classUniClassPKJoin.join("discipline");
        //TODO Из селетка вернуть день недели и сумму занятий в этот день. Хз как запихнуть это в дто.
        cq.select(disciplineJoin.get("dayOfWeek")/*, CRITERIA_BUILDER.sum(root.get("countOfClass"))*/)
                .groupBy(disciplineJoin.get("dayOfWeek"))
                .having(criteriaBuilder.ge(criteriaBuilder.sum(root.get("countOfClass")), count));
        transactionHelper.entityManager().createQuery(cq).getResultList().forEach(System.out::println);
        return null;
    }

    public List<DayOfWeek> getDaysByCountAuditoryGE(Long count){
        CriteriaQuery<DayOfWeek> cq = criteriaBuilder.createQuery(DayOfWeek.class);
        Root<Discipline> root = cq.from(Discipline.class);

        Join<Discipline, Auditory> auditoryJoin = root.join("auditoryList");

        cq.select(root.get("dayOfWeek"))
                .groupBy(root.get("dayOfWeek"))
                .having(criteriaBuilder.ge(criteriaBuilder.count(auditoryJoin.get("id")), count));

        return transactionHelper.entityManager().createQuery(cq).getResultList();
    }


}

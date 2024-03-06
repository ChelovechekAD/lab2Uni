package org.unitasks.services;

import org.unitasks.DAO.AuditoryDAO;
import org.unitasks.DAO.ClassUniDAO;
import org.unitasks.DAO.DisciplineDAO;
import org.unitasks.DAO.Impl.AuditoryDAOImpl;
import org.unitasks.DAO.Impl.ClassUniDAOImpl;
import org.unitasks.DAO.Impl.DisciplineDAOImpl;
import org.unitasks.DAO.Impl.ProfessorDAOImpl;
import org.unitasks.DAO.ProfessorDAO;
import org.unitasks.DTO.response.GeneratedDataDTOResponse;
import org.unitasks.models.Auditory;
import org.unitasks.models.ClassUni;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;
import org.unitasks.utils.Constants;
import org.unitasks.utils.DataGenerator;
import org.unitasks.utils.TransactionHelper;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class DataGenerateService {

    private final ProfessorDAO professorDAO = new ProfessorDAOImpl();
    private final DisciplineDAO disciplineDAO = new DisciplineDAOImpl();
    private final AuditoryDAO auditoryDAO = new AuditoryDAOImpl();
    private final ClassUniDAO classUniDAO = new ClassUniDAOImpl();

    private final TransactionHelper transactionHelper = TransactionHelper.getTransactionHelper();

    public GeneratedDataDTOResponse generateTestData(){

        List<Professor> professorList = DataGenerator.generateProfessorList(10);
        List<Discipline> disciplineList = DataGenerator.generateDisciplineList(30);
        List<Auditory> auditoryList = DataGenerator.generateAuditoryList(100);

        IntStream.range(0, disciplineList.size())
                .forEach(i -> IntStream.range(0, 10)
                        .forEach(j->disciplineList.get(i).addAuditory(
                                auditoryList.get(Constants.RANDOM.nextInt(auditoryList.size())))));

        IntStream.range(0, professorList.size())
                .forEach(i -> {
                    professorList.get(i).addDiscipline(disciplineList.get(i));
                    professorList.get(i).addDiscipline(disciplineList.get(10+i));
                    professorList.get(i).addDiscipline(disciplineList.get(20+i));
                });

        List<ClassUni> classUniList = DataGenerator.generateClassUniList(50, professorList, disciplineList);

        Supplier<Boolean> save = () -> {
            disciplineList.forEach(disciplineDAO::save);
            professorList.forEach(professorDAO::save);
            classUniList.forEach(classUniDAO::save);

            return true;
        };

        if (Boolean.TRUE.equals(transactionHelper.transaction(save))){

            return GeneratedDataDTOResponse.builder()
                    .classUniList(classUniList)
                    .auditoryList(auditoryList)
                    .disciplineList(disciplineList)
                    .professorList(professorList)
                    .build();

        }

        return null;

    }

}

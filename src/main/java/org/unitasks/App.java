package org.unitasks;

import org.unitasks.DAO.AuditoryDAO;
import org.unitasks.DAO.ClassUniDAO;
import org.unitasks.DAO.DisciplineDAO;
import org.unitasks.DAO.Impl.AuditoryDAOImpl;
import org.unitasks.DAO.Impl.ClassUniDAOImpl;
import org.unitasks.DAO.Impl.DisciplineDAOImpl;
import org.unitasks.DAO.Impl.ProfessorDAOImpl;
import org.unitasks.DAO.ProfessorDAO;
import org.unitasks.DTO.response.GeneratedDataDTOResponse;
import org.unitasks.controllers.TaskController;
import org.unitasks.models.Auditory;
import org.unitasks.models.ClassUni;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;
import org.unitasks.services.InfoService;
import org.unitasks.utils.Constants;
import org.unitasks.utils.DataGenerator;
import org.unitasks.utils.HibernateUtil;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class App 
{
    public static void main( String[] args )
    {
        AuditoryDAO auditoryDAO = new AuditoryDAOImpl();
        ClassUniDAO classUniDAO = new ClassUniDAOImpl();
        DisciplineDAO disciplineDAO = new DisciplineDAOImpl();
        ProfessorDAO professorDAO = new ProfessorDAOImpl();

        InfoService infoService = new InfoService();

        TaskController taskController = new TaskController();

        GeneratedDataDTOResponse generatedDataDTOResponse = taskController.doThis();

        if (generatedDataDTOResponse == null){
            throw new RuntimeException("NO DATA GENERATED");
        }

        List<Auditory> auditoryList = generatedDataDTOResponse.getAuditoryList();
        List<Discipline> disciplineList = generatedDataDTOResponse.getDisciplineList();
        List<ClassUni> classUniList = generatedDataDTOResponse.getClassUniList();
        List<Professor> professorList = generatedDataDTOResponse.getProfessorList();



        System.out.println(auditoryList.get(1));
        auditoryDAO.delete(auditoryList.get(1).getId());

        System.out.println(disciplineList.get(5));
        disciplineDAO.delete(disciplineList.get(5).getId());

        System.out.println(classUniList.get(0));
        classUniDAO.delete(classUniList.get(0).getId());

        List<Auditory> auditories = new ArrayList<>(disciplineList.get(0).getAuditoryList());
        infoService.getAllProfWithClassesOnDayAndAuditoryNum(disciplineList.get(0).getDayOfWeek(), auditories.get(0).getAuditoryNum())
                .forEach(System.out::println);
        infoService.getAllProfWithoutClassesOnTheSelectedDay(DayOfWeek.FRIDAY).forEach(System.out::println);
        infoService.getAllDaysWithCountOfClassesMoreThen(20);
    }

}

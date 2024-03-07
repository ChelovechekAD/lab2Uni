package org.unitasks;

import org.unitasks.DAO.AuditoryDAO;
import org.unitasks.DAO.ClassUniDAO;
import org.unitasks.DAO.DisciplineDAO;
import org.unitasks.DAO.Impl.AuditoryDAOImpl;
import org.unitasks.DAO.Impl.ClassUniDAOImpl;
import org.unitasks.DAO.Impl.DisciplineDAOImpl;
import org.unitasks.DAO.Impl.ProfessorDAOImpl;
import org.unitasks.DAO.ProfessorDAO;
import org.unitasks.DTO.request.ProfessorInfoDTORequest;
import org.unitasks.DTO.response.GeneratedDataDTOResponse;
import org.unitasks.controllers.TaskController;
import org.unitasks.models.Auditory;
import org.unitasks.models.ClassUni;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;
import org.unitasks.services.InfoService;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

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
        ProfessorInfoDTORequest professorInfoDTORequest = ProfessorInfoDTORequest.builder()
                .auditoryNum(auditories.get(0).getAuditoryNum())
                .dayOfWeek(disciplineList.get(0).getDayOfWeek())
                .build();

        System.out.println("\nService 1:");
        taskController.getProfInfoByParam(professorInfoDTORequest);

        System.out.println("\nService 2:");
        taskController.getProfInfoByParam(DayOfWeek.FRIDAY);

        infoService.getDaysWithCountOfClassesGE(20L);
        System.out.println();

        System.out.println("\nService 4:");
        infoService.getDaysByCountAuditoryGE(20L).forEach(System.out::println);
    }

}

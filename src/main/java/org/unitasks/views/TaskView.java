package org.unitasks.views;

import org.unitasks.DTO.response.CountClassesEachDayDTOResponse;
import org.unitasks.DTO.response.ProfessorInfoDTOResponse;

import java.time.DayOfWeek;
import java.util.List;

public final class TaskView {

    private TaskView() {

    }

    public static void printProfInfo(List<ProfessorInfoDTOResponse> responses) {
        responses.forEach(p -> {
            System.out.println("\nID: " + p.getId());
            System.out.println("Name :" + p.getName());
            System.out.println("Surname: " + p.getSurname());
            System.out.println("Middle Name: " + p.getMiddleName());
            System.out.println("Count Of Disciplines: " + p.getCountOfDisciplines());
        });
    }

    public static void printDaysByCountOfClassesGE(List<CountClassesEachDayDTOResponse> responses) {
        responses.forEach(d -> {
            System.out.println("\nDay: " + d.getDayOfWeek());
            System.out.println("Count of classes: " + d.getCountOfClasses());
        });
    }

    public static void printCountOfDaysByAuditoryGE(List<DayOfWeek> responses) {
        System.out.println("\n");
        responses.forEach(System.out::println);
    }

}

package org.unitasks.views;

import org.unitasks.DTO.response.ProfessorInfoDTOResponse;

import java.util.List;

public final class TaskView {

    private TaskView(){

    }

    public static void printProfInfo(List<ProfessorInfoDTOResponse> dto){
        dto.forEach(p->{
            System.out.println("\nID: " + p.getId());
            System.out.println("Name :" + p.getName());
            System.out.println("Surname: " + p.getSurname());
            System.out.println("Middle Name: " + p.getMiddleName());
            System.out.println("Count Of Disciplines: " + p.getCountOfDisciplines());
        });
    }

}

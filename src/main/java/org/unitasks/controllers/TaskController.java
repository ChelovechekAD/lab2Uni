package org.unitasks.controllers;

import org.unitasks.DTO.request.ProfessorInfoDTORequest;
import org.unitasks.DTO.response.GeneratedDataDTOResponse;
import org.unitasks.DTO.response.ProfessorInfoDTOResponse;
import org.unitasks.services.DataGenerateService;
import org.unitasks.services.InfoService;
import org.unitasks.views.TaskView;

import java.time.DayOfWeek;
import java.util.List;

public class TaskController{

    private final InfoService infoService = new InfoService();
    private final DataGenerateService dataGenerateService = new DataGenerateService();

    public GeneratedDataDTOResponse doThis(){
        return dataGenerateService.generateTestData();
    }

    public void getProfInfoByParam(ProfessorInfoDTORequest request){
        List<ProfessorInfoDTOResponse> response = infoService.getAllProfWithClassesOnDayAndAuditoryNum(request);
        TaskView.printProfInfo(response);
    }
    public void getProfInfoByParam(DayOfWeek dayOfWeek){
        List<ProfessorInfoDTOResponse> response = infoService.getAllProfWithoutClassesOnTheSelectedDay(dayOfWeek);
        TaskView.printProfInfo(response);
    }

}

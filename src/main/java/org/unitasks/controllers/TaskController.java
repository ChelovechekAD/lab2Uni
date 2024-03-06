package org.unitasks.controllers;

import org.unitasks.DTO.response.GeneratedDataDTOResponse;
import org.unitasks.services.DataGenerateService;

public class TaskController{

    public GeneratedDataDTOResponse doThis(){
        DataGenerateService dataGenerateService = new DataGenerateService();
        return dataGenerateService.generateTestData();
    }

}

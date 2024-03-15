package org.unitasks.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorInfoDTORequest {

    private DayOfWeek dayOfWeek;
    private String auditoryNum;

}

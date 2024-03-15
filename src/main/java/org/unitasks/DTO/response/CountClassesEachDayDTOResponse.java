package org.unitasks.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountClassesEachDayDTOResponse {
    private DayOfWeek dayOfWeek;
    private Long countOfClasses;
}

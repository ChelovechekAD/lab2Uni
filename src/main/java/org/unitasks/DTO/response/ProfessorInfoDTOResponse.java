package org.unitasks.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorInfoDTOResponse {

    private Integer id;
    private String name;
    private String surname;
    private String middleName;
    private Integer countOfDisciplines;


}

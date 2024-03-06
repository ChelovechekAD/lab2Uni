package org.unitasks.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unitasks.models.Auditory;
import org.unitasks.models.ClassUni;
import org.unitasks.models.Discipline;
import org.unitasks.models.Professor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneratedDataDTOResponse {

    private List<Professor> professorList;
    private List<Auditory> auditoryList;
    private List<ClassUni> classUniList;
    private List<Discipline> disciplineList;

}

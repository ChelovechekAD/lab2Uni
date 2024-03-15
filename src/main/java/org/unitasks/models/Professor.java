package org.unitasks.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "disciplineList")
@ToString(exclude = "disciplineList")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "professor")
public class Professor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column(name = "middle_name")
    private String middleName;

    @OneToMany
    @JoinColumn(name = "professor_id")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Discipline> disciplineList;

    public void addDiscipline(Discipline discipline) {
        disciplineList.add(discipline);
    }

    public void setDisciplineList(Set<Discipline> disciplineList) {
        this.disciplineList = disciplineList;
    }

    public void removeDiscipline(Discipline discipline) {
        disciplineList.remove(discipline);
    }

}

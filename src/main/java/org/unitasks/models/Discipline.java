package org.unitasks.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(name = "discipline")
public class Discipline implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column
    private String title;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "discipline_auditory",
            joinColumns = {@JoinColumn(name = "discipline_id")},
            inverseJoinColumns = {@JoinColumn(name = "auditory_id")}
    )
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Builder.Default
    private Set<Auditory> auditoryList = new HashSet<>();

    public void addAuditory(Auditory auditory){
        auditoryList.add(auditory);
    }

    public void setAuditoryList(Set<Auditory> auditoryList){
        this.auditoryList = auditoryList;
    }

    public void removeAuditory(Auditory auditory){
        auditoryList.remove(auditory);
        auditory.getDisciplineList().remove(this);
    }

}

package com.tarjetaza.domain.claim;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "claim_subject")
public class ClaimSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_subject_id")
    private Integer id;

    private Integer code;

    private String subject;

    @JsonIgnore
    @OneToMany(mappedBy = "claimSubject", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<SubjectConcept> subjectConcepts = new ArrayList<>();

    public ClaimSubject() { }

    public ClaimSubject(Integer code, String subject) {
        this.code = code;
        this.subject = subject;
    }

    public void addSubjectConcept(SubjectConcept subjectConcept) {
        subjectConcept.setClaimSubject(this);
        this.getSubjectConcepts().add(subjectConcept);
    }

}

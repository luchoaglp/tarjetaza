package com.tarjetaza.domain.claim;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "claim_subject_concepts")
public class SubjectConcept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_subject_concept_id")
    private Integer id;

    private Integer code;

    private String concept;

    @ManyToOne
    @JoinColumn(name = "claim_subject_id", nullable = false)
    private ClaimSubject claimSubject;

    public SubjectConcept() { }

    public SubjectConcept(Integer code, String concept) {
        this.code = code;
        this.concept = concept;
    }
}

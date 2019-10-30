package com.tarjetaza.domain.claim;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tarjetaza.domain.Request;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.tarjetaza.domain.claim.ClaimState.ABIERTO;

@Getter
@Setter
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private Long id;

    @Column(length = 255)
    private String observations;

    @Enumerated(EnumType.ORDINAL)
    private ClaimState claimState;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_subject_concept_id")
    private SubjectConcept subjectConcept;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    @JsonIgnore
    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    @NotNull
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Claim() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.claimState = ABIERTO;
    }

    public Claim(SubjectConcept subjectConcept, Request request) {
        this.subjectConcept = subjectConcept;
        this.request = request;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.claimState = ABIERTO;
    }
}

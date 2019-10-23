package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.tarjetaza.domain.CreditState.SOLICITADO;

@Getter
@Setter
@Entity
@Table(name = "credits")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long id;

    private Double amount;

    @JsonProperty("created_date")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonProperty("last_modified_date")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Enumerated(EnumType.ORDINAL)
    private CreditState creditState;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    public Credit() { }

    public Credit(Double amount, Request request) {
        this.amount = amount;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        request.getCredits().add(this);
        this.request = request;
        this.creditState = SOLICITADO;
    }

    /*
    public void setBatch(Batch batch) {
        batch.getCredits().add(this);
        this.batch = batch;
    }
    */

}

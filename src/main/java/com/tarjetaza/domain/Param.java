package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "params")
public class Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "param_id")
    private Long id;

    @Size(max = 50)
    @Column(name = "description")
    private String description;

    @Size(max = 50)
    @Column(name = "value")
    private String value;


    @JsonProperty("param_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paramDate;

    @JsonIgnore
    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    @NotNull
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Param() {
        this.paramDate  = LocalDate.now();
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public Param(String description, String value) {
        this.description = description;
        this.value = value;
    }
}

package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @JsonIgnore
    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    @NotNull
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Param() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public Param(String description, String value) {
        this.description = description;
        this.value = value;
    }
}

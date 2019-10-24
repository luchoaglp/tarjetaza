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
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    @Size(max = 20)
    @Column(name = "numero", nullable = false)
    private String numero;

    @JsonIgnore
    @NotNull
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    @NotNull
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Certificate() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public Certificate(String numero) {
        this.numero = numero;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }
}

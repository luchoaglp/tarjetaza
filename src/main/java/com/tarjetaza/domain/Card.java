package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 3, message = "{card.entidad.size}")
    @Column(length = 3)
    private String entidad;

    @NotBlank
    @Size(min = 3, max = 3, message = "{card.sucursal.size}")
    @Column(length = 3)
    private String sucursal;

    @NotBlank
    @Size(min = 8, max = 8, message = "{card.usuario.size}")
    @Column(length = 8)
    private String usuario;

    @NotBlank
    @Size(min = 19, max = 19, message = "{card.numero.size}")
    @Column(length = 19)
    private String numero;

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

    @OneToOne(mappedBy = "card")
    private Request request;

    public Card() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void setRequest(Request request) {
        this.request = request;
        request.setCard(this);
    }
}

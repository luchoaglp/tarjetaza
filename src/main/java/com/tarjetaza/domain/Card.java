package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @NotBlank
    @Size(min = 3, max = 3, message = "{card.entidad.size}")
    @Column(length = 3)
    private String entidad;

    @JsonIgnore
    @NotBlank
    @Size(min = 3, max = 3, message = "{card.sucursal.size}")
    @Column(length = 3)
    private String sucursal;

    @JsonIgnore
    @NotBlank
    @Size(min = 8, max = 8, message = "{card.usuario.size}")
    @Column(length = 8)
    private String usuario;

    @NotBlank
    @Size(min = 19, max = 19, message = "{card.numero.size}")
    @Column(length = 19)
    private String numero;

    @JsonIgnore
    //@JsonProperty("created_date")
    @NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @CreatedDate
    private LocalDateTime createdDate;

    @JsonIgnore
    //@JsonProperty("last_modified_date")
    @NotNull
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-03:00")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @JsonIgnore
    @OneToOne(mappedBy = "card")
    private Request request;

    /*
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
    */

    /*
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY)
    private List<Credit> credits;
    */

    public Card() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void setRequest(Request request) {
        this.request = request;
        request.setCard(this);
    }

    @JsonIgnore
    public String getCuenta() {
        return this.entidad + this.sucursal + this.usuario.replace("-", "");
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", entidad='" + entidad + '\'' +
                ", sucursal='" + sucursal + '\'' +
                ", usuario='" + usuario + '\'' +
                ", numero='" + numero + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}

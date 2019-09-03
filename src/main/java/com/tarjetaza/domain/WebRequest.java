package com.tarjetaza.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static com.tarjetaza.domain.WebRequestState.SOLICITUD_INGRESADA;

@Getter
@Setter
@Entity
@Table(name = "web_requests")
public class WebRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String nombre;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String apellido;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100)
    private String calle;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 20)
    private String puerta;

    @Size(max = 20)
    private String piso;

    @Size(max = 20)
    private String dpto;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "^[A-Z][0-9]{4}[A-Z]{3}?")
    private String cp;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 2)
    private String provincia;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String localidad;

    @Size(max = 75)
    private String telefono;

    @JsonProperty("codigo_documento")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 1)
    private String codigoDocumento;

    @JsonProperty("cuit_cuil")
    @NotNull
    @NotBlank
    @Size(min = 11, max = 11)
    @Column(name = "cuit_cuil", nullable = false, length = 11)
    private String cuitCuil;

    @JsonProperty("fec_nac")
    @NotNull
    @NotBlank
    @Size(min = 8, max = 8)
    private String fecNac;

    @JsonProperty("estado_civil")
    @NotNull
    @NotBlank
    @Size(min = 1, max = 1)
    @Column(name = "estado_civil", nullable = false, length = 1)
    private String estadoCivil;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 1)
    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 100)
    //@Email
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
    private String email;

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

    @Enumerated(EnumType.ORDINAL)
    private WebRequestState requestState;

    public WebRequest() {
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.requestState = SOLICITUD_INGRESADA;
    }

    public String getProvinciaNombre() {
        switch (this.provincia) {
            case "01":
                return "Capital Federal";
            case "02":
                return "Gran Buenos Aires";
            case "03":
                return "Buenos Aires";
            case "04":
                return "Catamarca";
            case "05":
                return "Cordoba";
            case "06":
                return "Corrientes";
            case "07":
                return "Chaco";
            case "08":
                return "Chubut";
            case "09":
                return "Entre Ríos";
            case "10":
                return "Formosa";
            case "11":
                return "Jujuy";
            case "12":
                return "La Pampa";
            case "13":
                return "La Rioja";
            case "14":
                return "Mendoza";
            case "15":
                return "Misiones";
            case "16":
                return "Misiones";
            case "17":
                return "Rio Negro";
            case "18":
                return "Salta";
            case "19":
                return "San Juan";
            case "20":
                return "San Luis";
            case "21":
                return "Santa Fe";
            case "22":
                return "Santa Cruz";
            case "23":
                return "Santiago del Estero";
            case "24":
                return "Tierra del Fuego";
            case "25":
                return "Tucuman";
                default:
                    return null;
        }
    }

    public String getCodigoDocumentoNombre() {
        switch(this.codigoDocumento) {
            case "0":
                return "No Informado";
            case "1":
                return "DNI";
            case "2":
                return "Libreta de Enrolamiento";
            case "3":
                return "Libreta Cívica";
            case "4":
                return "Cédula de Identidad";
            case "5":
                return "Pasaporte";
            case "6":
                return "C.D.U.";
            case "7":
                return "C.U.I.L.";
            case "8":
                return "C.U.I.T.";
                default:
                    return null;
        }
    }

    public String getCuitCuilFormatted() {
        return this.cuitCuil.substring(0, 2) + "-" +
                this.cuitCuil.substring(2, 10) + "-" +
                this.cuitCuil.substring(10);
    }

    public String getFecNacFormatted() {
        return this.fecNac.substring(0, 4) + "/" +
                this.fecNac.substring(4, 6) + "/" +
                this.fecNac.substring(6);
    }

    public String getEstadoCivilNombre() {
        switch(this.estadoCivil) {
            case "0":
                return "NO INFORMADO";
            case "1":
                return "SOLTERO/A";
            case "2":
                return "CASADO";
            case "3":
                return "VIUDO";
            case "4":
                return "SEPARADO/A";
            case "5":
                return "DIVORCIADO/A";
                default:
                    return null;
        }
    }

}

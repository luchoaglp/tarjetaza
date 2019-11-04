package com.tarjetaza.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "consumption_file_records")
public class ConsumptionFileRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id;

    @Column(name = "trx_id", nullable = false)
    private Integer trxId;

    @Column(name = "coupon", nullable = false)
    private Integer coupon;

    @Column(name = "trx_date_time", nullable = false)
    private LocalDateTime trxDateTime;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Size(max = 1)
    @Column(name = "sign", nullable = false)
    private String sign;

    @Size(max = 16)
    @Column(name = "card", nullable = false)
    private String card;

    @ManyToOne
    @JoinColumn(name = "consumption_file_id", nullable = false)
    private ConsumptionFile consumptionFile;

    public ConsumptionFileRecord() { }

    /*

    Integer trxId = Integer.valueOf(body.substring(1, 11)); // ID de Transacción (10)
            Integer coupon = Integer.valueOf(body.substring(26, 32)); // Número de Cupón (6)

            LocalDateTime trxDateTime = LocalDateTime.parse(
                    body.substring(37, 51), dateTimeFormatter
            ); // Fecha-Hora de Operación (AAAAMMDD)(HHMMSS)

            Double amount = Double.valueOf(body.substring(77, 86) + '.' + body.substring(86, 88)); // Importe de la Transacción 9(9)v99

            String sign = body.substring(88, 89); // Signo Importe de la Transacción (1 - Pos. / 2 - Neg.)
            String card = body.substring(136); // Filler / Primeros 12 dígitos de Número de Tarjeta // Últimos 4 dígitos de Número de Tarjeta

     */

}

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

    public String getStrSign() {
        if(sign.equals("1")) {
            return "";
        } else if(sign.equals("2")) {
            return "-";
        }
        return null;
    }

}

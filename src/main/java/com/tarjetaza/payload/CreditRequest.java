package com.tarjetaza.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreditRequest {

    @NotBlank
    private String numero;

    @NotNull
    private Double amount;

}

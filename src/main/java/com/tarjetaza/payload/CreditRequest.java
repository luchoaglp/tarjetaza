package com.tarjetaza.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreditRequest {

    @NotNull
    private Long id;

    @NotNull
    private Double amount;

}

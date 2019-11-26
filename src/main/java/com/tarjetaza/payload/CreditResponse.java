package com.tarjetaza.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("tx_id")
    private Long txId;

    private String message;

    public CreditResponse(String message) {
        this.message = message;
    }

    public CreditResponse(Long txId, String message) {
        this.txId = txId;
        this.message = message;
    }
}

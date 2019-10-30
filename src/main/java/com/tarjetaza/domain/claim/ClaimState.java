package com.tarjetaza.domain.claim;

public enum ClaimState {

    ABIERTO("Abierto"),
    CERRADO("Cerrado");

    private final String displayValue;

    private ClaimState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}

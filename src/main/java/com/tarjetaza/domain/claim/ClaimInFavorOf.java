package com.tarjetaza.domain.claim;

public enum ClaimInFavorOf {

    CLIENT("Cliente"),
    COMPANY("Empresa");

    private final String displayValue;

    private ClaimInFavorOf(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}

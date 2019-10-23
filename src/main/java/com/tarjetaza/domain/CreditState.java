package com.tarjetaza.domain;

public enum CreditState {

    SOLICITADO("Solicitado"),
    ACREDITADO("Acreditado"),
    RECHAZADO("Rechazado");

    private final String displayValue;

    private CreditState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}

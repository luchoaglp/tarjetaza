package com.tarjetaza.domain;

public enum WebRequestState {

    SOLICITUD_INGRESADA("Solicitud Ingresada"),
    SOLICITUD_ACEPTADA("Solicitud Aceptada"),
    SOLICITUD_RECHAZADA("Solicitud Rechazada"),
    TARJETA_PEDIDA("Tarjeta Pedida"),
    TARJETA_RECIBIDA("Tarjeta Recibida"),
    TARJETA_ENTREGADA("Tarjeta Entregada"),
    TARJETA_RECHAZADA("Tarjeta Rechazada");

    private final String displayValue;

    private WebRequestState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}

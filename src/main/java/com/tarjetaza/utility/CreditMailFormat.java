package com.tarjetaza.utility;

import com.tarjetaza.domain.Credit;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CreditMailFormat {

    public static String formattedHeader(int nroLote) {

        StringBuilder sb = new StringBuilder();

        sb.append("1"); // Tipo Registro (1)
        sb.append(StringUtils.leftPad(String.valueOf(nroLote), 6, "0")); // N° de Lote (6)
        sb.append(StringUtils.rightPad("", 10)); // Filler (10)
        sb.append(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ); // Fecha de Presentación (8)
        sb.append("462"); // Entidad (3)
        sb.append(StringUtils.rightPad("", 11)); // Filler (11)
        sb.append(StringUtils.rightPad("PAGO VIRTUAL DEL SUR", 26)); // Nombre Entidad (26)
        sb.append(StringUtils.rightPad("", 15)); // Filler (15)

        return sb.toString();
    }

    public static String formattedBody(int nroLote, int nroCorrelativoLote, Credit credit) {

        StringBuilder sb = new StringBuilder();

        sb.append("2"); // Tipo Registro (1)
        sb.append(StringUtils.leftPad(String.valueOf(nroLote), 6, "0")); // N° de Lote (6)
        sb.append("0000"); // Uso Interno (4)
        sb.append(StringUtils.leftPad(String.valueOf(nroCorrelativoLote), 6, "0")); // N° Correlativo x Lote (6)
        sb.append(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ); // Fecha Cobro (8)
        sb.append(credit.getRequest().getCard().getCuenta()); // Cuenta (13)
        // sb.append(credit.getCard().getCuenta()); // Cuenta (13)
        sb.append("   "); // Cód. de Cajero (3)
        sb.append("01"); // Moneda (2)
        sb.append("0000"); // Uso Interno (4)
        sb.append(
                StringUtils.leftPad(String.valueOf(credit.getAmount().intValue()), 10, "0")
        ).append("00"); // Importe (12)
        sb.append("001"); // Sucursal Cobradora (3) // Posible error
        sb.append("462"); // Entidad Cobradora (3) // Posible error
        sb.append("63"); // Código de Operación (2)
        sb.append("00"); // SubCódigo de Operación (2)
        sb.append("        "); // Número de Terminal (8)
        sb.append("000"); // Filler (3)

        return sb.toString();
    }

    public static String formattedFooter(int nroLote, int cantRegistros, int total) {

        StringBuilder sb = new StringBuilder();

        sb.append("9"); // Tipo Registro (1)
        sb.append(StringUtils.leftPad(String.valueOf(nroLote), 6, "0")); // N° de Lote (6)
        sb.append(StringUtils.rightPad("", 10)); // Filler (10)
        sb.append(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ); // Fecha de Proceso (8)
        sb.append("462"); // Entidad (3)
        sb.append(StringUtils.leftPad(String.valueOf(cantRegistros), 10, "0")); // Cantidad de Registros (10)
        sb.append(
                StringUtils.leftPad(String.valueOf(total), 13, "0")
        ).append("00"); // Total Informado (15)
        sb.append(StringUtils.rightPad("", 27)); // Filler (27)

        return sb.toString();
    }

}

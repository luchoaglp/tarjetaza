package com.tarjetaza.utility;

import com.tarjetaza.domain.Request;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RequestMailFormat {

    public static String formattedHeader() {

        StringBuilder sb = new StringBuilder();

        sb.append("62"); // Código de Marca (2)
        sb.append("00"); // Marca de Encabezado (2)
        sb.append("462"); // Código Entidad Emisora (3)
        sb.append("32"); // Versión de Diseño (2)
        sb.append(StringUtils.rightPad("", 8)); // Uso Interno (8)
        sb.append(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ); // Fecha de Proceso (8)
        sb.append(StringUtils.rightPad("", 8)); // Uso Interno (8)
        sb.append(StringUtils.rightPad("", 300)); // Uso Interno (300)
        sb.append(StringUtils.rightPad("", 30)); // Denominación del Contenido del Archivo (Comentario) (30)
        sb.append(StringUtils.rightPad("", 37)); // Uso interno (37)

        return sb.toString();
    }

    public static String formattedBody(Request request) {

        StringBuilder sb = new StringBuilder();

        sb.append("62"); // Código de Marca (2)
        sb.append("01"); // Código de Novedad (2) "01 | Alta de Cuenta"
        sb.append("462"); // Código Entidad Emisora (3)
        sb.append("001"); // Código Sucursal Emisora (3)
        sb.append("0000000"); // Número de Usuario (7)
        sb.append("0000000000000000"); // Número de Tarjeta (16)
        sb.append("T"); // Código de Producto (1) "T | Tradicional"
        sb.append("01"); // Tipo de Cuenta (2) "01 | Cuenta Normal"
        sb.append(request.getDocumento()); // Número de Solicitud Interna (8)
        sb.append(StringUtils.rightPad(request.getApellido(), 30)); // Apellido (30)
        sb.append(StringUtils.rightPad(request.getNombre(), 30)); // Nombre (30)
        sb.append(StringUtils.rightPad(request.getCalle(), 40)); // Calle (40)
        sb.append(StringUtils.rightPad(request.getPuerta(), 5)); // Puerta (5)
        sb.append(
                StringUtils.rightPad(request.getPiso() != null ? request.getPiso() : "", 2)
        ); // Piso (2)
        sb.append(
                StringUtils.rightPad(request.getDpto() != null ? request.getDpto() : "", 4)
        ); // Departamento (4)

        sb.append(request.getCp()); // Código Postal NUEVO Formato (8)
        sb.append(request.getProvincia()); // Código de Provincia (2)
        sb.append(StringUtils.rightPad(request.getLocalidad(), 25)); // Localidad (25)
        sb.append("000000000000000"); // Teléfono (15)
        sb.append(request.getCodigoDocumento()); // Código de Documento (1)
        sb.append("000").append(request.getDocumento()); // Número de Documento(11)
        sb.append("0"); // Código Forma de Pago (1) "0 | Sin Debito o Credito"
        sb.append("0"); // Tipo de Modalidad de Pago (1) "0 | NO INFORMADO"
        sb.append("001"); // Sucursal Cuenta a Debitar (3)
        sb.append("0000000000000"); // N° de Cuenta a Debitar (13)
        sb.append("000"); // Código de Promotor (3)
        sb.append("000"); // Grupo de Afinidad (3)
        sb.append(request.getCuitCuil()); // CUIT/CUIL (11)
        sb.append("000000"); // Límite de Compra Contado (6)
        sb.append("000000"); // Límite de Compra en Cuotas (6)
        sb.append("000000"); // Límite de Crédito (6)
        sb.append("000000"); // Límite de Adelantos de Efvo. (6)
        sb.append("0"); // Multipicador de Límites, Potencia de 10 (1)
        sb.append("00000"); // (5)
        sb.append("000000"); // Límite de Préstamos (6)
        sb.append("        "); // Fecha de Alta (8)
        sb.append(request.getFecNac()); // Fecha de Nacimiento (8)
        sb.append(request.getEstadoCivil()); // Estado Civil (1)
        sb.append(request.getSexo()); // Sexo (1)
        sb.append("4"); // Código de Cierre (1)
        sb.append("1"); // Código de Gasto Mensual (1) "1 | Normal"
        sb.append("1"); // Código de Tasas (1) "1 | Normal"
        sb.append("D4"); // Código de Distribución (2)
        sb.append("100"); // Porcentaje de Bonificación Cargo anual (3)
        sb.append("N"); // Mantiene Bonificación (1)
        sb.append("  "); // Codigo Motivo de Baja (2)
        sb.append(request.getDocumento()); // Numero de Control (8)
        sb.append("36"); // Duracion de Tarjeta (2)
        sb.append("NO"); // Marca de LINK (2)
        sb.append("01"); // Cantidad de Cuotas (2)
        sb.append("SI"); // Marca de Renovación (2)
        sb.append("01"); // Cía. De Seguros (2)
        sb.append("01"); // Tipo de Seguro (2)
        sb.append("01"); // Estado de Situación (2)
        sb.append(" "); // Uso Interno (1)
        sb.append(StringUtils.rightPad(request.getEmail(), 40)); // Correo electrónico (40)
        sb.append(StringUtils.rightPad("", 20)); // Codigo de Rechazo del ABM (20)

        return sb.toString();
    }

    public static String formattedFooter(int cantRegistros) {

        StringBuilder sb = new StringBuilder();

        sb.append("62"); // Código de Marca (2)
        sb.append("99"); // Marca de Pie (2)
        sb.append("462"); // Código Entidad Emisora (3)
        sb.append(StringUtils.leftPad(String.valueOf(cantRegistros), 10, "0")); // Cantidad de Registros (10)
        sb.append(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ); // Fecha de Proceso (8)
        sb.append(StringUtils.rightPad("", 375)); // Filler (375)

        return sb.toString();
    }

}

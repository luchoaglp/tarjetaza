package com.tarjetaza.utility;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BCRAFile {

    public static List<String> RITxx1xxxx(Integer cantTarjetas) {

        // TARJCRED.txt

        List<String> list = new ArrayList<>();

        list.add(RITxx1xxxx("11000", cantTarjetas));
        list.add(RITxx1xxxx("13100", cantTarjetas));
        list.add(RITxx1xxxx("14200", cantTarjetas));
        list.add(RITxx1xxxx("15200", cantTarjetas));
        list.add(RITxx1xxxx("16100", cantTarjetas));

        return list;
    }

    public static String RITxx1xxxx(String nroPartida, Integer cantTarjetas) {

        StringBuilder sb = new StringBuilder();

        sb.append("3208"); // Diseño = 3208 (fijo) (4)
        sb.append("70271"); // Entidad = 70271 (fijo) (5)
        sb.append(
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"))
        ); // Fecha (8)
        sb.append("RIT"); // RIT = Fijo (3)
        sb.append("0062"); // xxxx = Código de Marca (fijo) (4)
        sb.append(nroPartida); // Nro de Partida = 5 numeros (5)
        sb.append(
                StringUtils.leftPad(String.valueOf(cantTarjetas), 10, "0")
        ); // Cantidad de tarjetas en calle = 10 numeros (10)
        sb.append("N"); // Normal o Rectificativa = Siempre N de normal (1)
        sb.append(StringUtils.rightPad("", 10)); // 10 espacios en blanco (10)

        return sb.toString();
    }

}

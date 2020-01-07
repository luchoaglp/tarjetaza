package com.tarjetaza.utility;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BCRAFile {

    public static List<String> RITxx1xxxx(Integer cantTarjetas, LocalDate lastMonth) {

        List<String> xx1 = new ArrayList<>();

        xx1.add(RITxx1xxxx("11000", cantTarjetas, lastMonth));
        xx1.add(RITxx1xxxx("12000", cantTarjetas, lastMonth));

        /*
        xx1.add(RITxx1xxxx("13100", cantTarjetas, lastMonth));
        xx1.add(RITxx1xxxx("14200", cantTarjetas, lastMonth));
        xx1.add(RITxx1xxxx("15200", cantTarjetas, lastMonth));
        xx1.add(RITxx1xxxx("16100", cantTarjetas, lastMonth));
        */

        return xx1;
    }

    public static List<String> RITxx2xxxx(Integer consumption, LocalDate lastMonth) {

        List<String> xx2 = new ArrayList<>();

        xx2.add(RITxx2xxxx("21100", consumption, lastMonth));
        // xx2.add(RITxx2xxxx("22100", 0, lastMonth));
        // xx2.add(RITxx2xxxx("23100", 0, lastMonth));

        return xx2;
    }

    public static List<String> RITxx3xxxx(LocalDate dateFrom, String tasaMaxCompensatorio, String tasaMaxPunitorio) {

        List<String> xx3 = new ArrayList<>();

        xx3.add(RITxx3xxxx("32000", tasaMaxCompensatorio, dateFrom));
        xx3.add(RITxx3xxxx("33000", tasaMaxPunitorio, dateFrom));

        return xx3;
    }

    public static List<String> RITxx4xxxx(String min, String max, LocalDate lastMonth) {

        List<String> xx4 = new ArrayList<>();

        xx4.add(RITxx4xxxx("41200", min, max, lastMonth));

        return xx4;
    }

    private static String RITxx3xxxx(String nroPartida, String tasaMax, LocalDate dateFrom) {

        StringBuilder sb = new StringBuilder();

        sb.append("3210"); // Diseño = 3208 (fijo) (4)
        sb.append("70271"); // Entidad = 70271 (fijo) (5)
        sb.append(
                dateFrom.format(DateTimeFormatter.ofPattern("yyyyMM"))
        ); // Fecha (8)
        sb.append("RIT"); // RIT = Fijo (3)
        sb.append("0000"); // Fijo (4)
        sb.append(nroPartida); // Nro de Partida = 5 numeros (5)
        sb.append(tasaMax); // TasaMax  numeros (5)
        sb.append("N"); // Normal o Rectificativa = Siempre N de normal (1)
        sb.append(StringUtils.rightPad("", 15)); // 15 espacios en blanco (15)

        return sb.toString();
    }

    public static String RITxx1xxxx(String nroPartida, Integer cantTarjetas, LocalDate lastMonth) {

        StringBuilder sb = new StringBuilder();

        sb.append("3208"); // Diseño = 3208 (fijo) (4)
        sb.append("70271"); // Entidad = 70271 (fijo) (5)
        sb.append(
                lastMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
        ); // Fecha (8)
        sb.append("RIT"); // RIT = Fijo (3)
        sb.append("0006"); // xxxx = Código de Marca (fijo) (4)
        sb.append(nroPartida); // Nro de Partida = 5 numeros (5)
        sb.append(
                StringUtils.leftPad(String.valueOf(cantTarjetas), 10, "0")
        ); // Cantidad de tarjetas en calle = 10 numeros (10)
        sb.append("N"); // Normal o Rectificativa = Siempre N de normal (1)
        sb.append(StringUtils.rightPad("", 10)); // 10 espacios en blanco (10)

        return sb.toString();
    }

    private static String RITxx2xxxx(String nroPartida, Integer consumption, LocalDate lastMonth) {

        StringBuilder sb = new StringBuilder();

        sb.append("3209"); // Diseño = 3209 (fijo) (4)
        sb.append("70271"); // Entidad = 70271 (fijo) (5)
        sb.append(
                lastMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
        ); // Fecha (8)
        sb.append("RIT"); // RIT = Fijo (3)
        sb.append("0006"); // xxxx = Código de Marca (fijo) (4)
        sb.append(nroPartida); // Nro de Partida = 5 numeros (5)
        sb.append(
                StringUtils.leftPad(String.valueOf(consumption), 12, "0")
        ); // Importe Consumos facturados = 12 números entero (12)
        sb.append("N"); // Normal o Rectificativa = Siempre N de normal (1)
        sb.append(StringUtils.rightPad("", 8)); // 8 espacios en blanco (8)

        return sb.toString();
    }

    private static String RITxx4xxxx(String nroPartida, String min, String max, LocalDate lastMonth) {

        StringBuilder sb = new StringBuilder();

        sb.append("3211"); // Diseño = 3208 (fijo) (4)
        sb.append("70271"); // Entidad = 70271 (fijo) (5)
        sb.append(
                lastMonth.format(DateTimeFormatter.ofPattern("yyyyMM"))
        ); // Fecha (8)
        sb.append("RIT"); // RIT = Fijo (3)
        sb.append("0006"); // xxxx = Código de Marca (fijo) (4)
        sb.append(nroPartida); // Nro de Partida = 5 numeros (5)

        //sb.append("nnnnnnnnDDnnnnnnnnDD");
        sb.append(
                StringUtils.leftPad(String.valueOf(min), 8, "0")
        ).append("00"); // Min nnnnnnnnDD
        sb.append(
                StringUtils.leftPad(String.valueOf(max), 8, "0")
        ).append("00"); // Max nnnnnnnnDD

        sb.append("N"); // Normal o Rectificativa = Siempre N de normal (1)
        //sb.append(StringUtils.rightPad("", 10)); // 10 espacios en blanco (10)

        return sb.toString();
    }

}

package com.tarjetaza.utility;

import com.tarjetaza.domain.ConsumptionFile;
import com.tarjetaza.domain.ConsumptionFileRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsumptionFormat {

    public static ConsumptionFile fileToConsumptionFile(Path file) throws IOException {

        ConsumptionFile consumptionFile = new ConsumptionFile();

        List<String> list = Files.readAllLines(file);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        double total = 0;

        for(int i = 1; i < list.size() - 1; i++) {
            String body = list.get(i);

            Integer trxId = Integer.valueOf(body.substring(1, 11)); // ID de Transacción (10)
            Integer coupon = Integer.valueOf(body.substring(26, 32)); // Número de Cupón (6)

            LocalDateTime trxDateTime = LocalDateTime.parse(
                    body.substring(37, 51), dateTimeFormatter
            ); // Fecha-Hora de Operación (AAAAMMDD)(HHMMSS)

            Double amount = Double.valueOf(body.substring(77, 86) + '.' + body.substring(86, 88)); // Importe de la Transacción 9(9)v99

            String sign = body.substring(88, 89); // Signo Importe de la Transacción (1 - Pos. / 2 - Neg.)
            String card = body.substring(136); // Filler / Primeros 12 dígitos de Número de Tarjeta // Últimos 4 dígitos de Número de Tarjeta

            /*
            System.out.println(trxId);
            System.out.println(coupon);
            System.out.println(trxDateTime);
            System.out.println(amount);
            System.out.println(sign);
            System.out.println(card);
            */

            if(sign.equals("1")) {
                total += amount;
            } else if(sign.equals("2")) {
                total -= amount;
            }

            ConsumptionFileRecord record = new ConsumptionFileRecord();

            record.setTrxId(trxId);
            record.setCoupon(coupon);
            record.setTrxDateTime(trxDateTime);
            record.setAmount(amount);
            record.setSign(sign);
            record.setCard(card);

            consumptionFile.addRecord(record);
        }

        String trailer = list.get(list.size() - 1);

        LocalDate processDate = LocalDate.parse(trailer.substring(11, 19), dateFormatter); // Fecha de Proceso (AAAAMMDD)
        Integer numberOfRecords = Integer.valueOf(trailer.substring(54, 64)); // Cantidad de Registros (10)

        //System.out.println(processDate);
        //System.out.println(numberOfRecords);

        consumptionFile.setFileName(file.getFileName().toString());
        consumptionFile.setProcessDate(processDate);
        consumptionFile.setNumberOfRecords(numberOfRecords);
        consumptionFile.setAmount(total);

        return consumptionFile;
    }

}

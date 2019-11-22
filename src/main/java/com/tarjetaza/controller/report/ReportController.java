package com.tarjetaza.controller.report;

import com.tarjetaza.domain.*;
import com.tarjetaza.service.ConsumptionFileService;
import com.tarjetaza.service.CreditService;
import com.tarjetaza.service.RequestService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final RequestService requestService;
    private final CreditService creditService;
    private final ConsumptionFileService consumptionFileService;

    public ReportController(RequestService requestService, CreditService creditService, ConsumptionFileService consumptionFileService) {
        this.requestService = requestService;
        this.creditService = creditService;
        this.consumptionFileService = consumptionFileService;
    }

    @GetMapping
    public String reports() {
        return "reports/index";
    }

    @GetMapping("/requests")
    public void requests(HttpServletResponse response) throws Exception {

        List<Request> requests = requestService.findAllByOrderByIdDesc();

        generateRequestsXlsx(response, requests);
    }

    @GetMapping("/credits")
    public void credits(HttpServletResponse response) throws Exception {

        List<Credit> credits = creditService.findAllByOrderByIdDesc();

        generateCreditsXlsx(response, credits);
    }

    @GetMapping("/consumption")
    public void consumption(HttpServletResponse response) throws Exception {

        List<ConsumptionFile> consumptionFiles = consumptionFileService.findAllByOrderByIdDesc();

        generateConsumptionXlsx(response, consumptionFiles);
    }

    private void generateConsumptionXlsx(HttpServletResponse response, List<ConsumptionFile> consumptionFiles) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            Sheet sheet = workbook.createSheet("Consumos");
            sheet.setColumnWidth(0, 2500);
            sheet.setColumnWidth(1, 4000);
            sheet.setColumnWidth(2, 3000);
            sheet.setColumnWidth(3, 2500);
            sheet.setColumnWidth(4, 3500);
            sheet.setColumnWidth(5, 5000);
            sheet.setColumnWidth(6, 2500);
            sheet.setColumnWidth(7, 2000);
            sheet.setColumnWidth(8, 3000);
            sheet.setColumnWidth(9, 3000);
            sheet.setColumnWidth(10, 3500);
            sheet.setColumnWidth(11, 6500);
            sheet.setColumnWidth(12, 6500);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont headerFont = ((XSSFWorkbook) workbook).createFont();

            headerFont.setFontName("Arial");
            headerFont.setFontHeightInPoints((short) 10);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);

            headerStyle.setFont(headerFont);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Archivo");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(2);
            headerCell.setCellValue("Monto");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(3);
            headerCell.setCellValue("Registros");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(4);
            headerCell.setCellValue("Proceso");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(5);
            headerCell.setCellValue("Subido");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(6);
            headerCell.setCellValue("Detalle ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(7);
            headerCell.setCellValue("Signo");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(8);
            headerCell.setCellValue("Detalle Monto");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(9);
            headerCell.setCellValue("Transacción");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(10);
            headerCell.setCellValue("Cupón");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(11);
            headerCell.setCellValue("Tarjeta");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(12);
            headerCell.setCellValue("Fecha-Hora Transacción");
            headerCell.setCellStyle(headerStyle);

            CellStyle style = workbook.createCellStyle();

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();

            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 10);

            style.setWrapText(true);
            style.setFont(font);

            Cell cell;
            int rowIndex = 0;

            for(int i = 0; i < consumptionFiles.size(); i++) {

                ConsumptionFile consumptionFile = consumptionFiles.get(i);

                Row row = sheet.createRow(rowIndex + 1);

                cell = row.createCell(0);
                cell.setCellValue(consumptionFile.getId());
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(consumptionFile.getFileName());
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(consumptionFile.getAmount());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(consumptionFile.getNumberOfRecords());
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(consumptionFile.getProcessDate().format(dateFormatter));
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(consumptionFile.getCreatedDate().format(dateTimeFormatter));
                cell.setCellStyle(style);

                for(ConsumptionFileRecord record : consumptionFile.getConsumptionFileRecords()) {

                    cell = row.createCell(6);
                    cell.setCellValue(record.getId());
                    cell.setCellStyle(style);

                    cell = row.createCell(7);
                    cell.setCellValue(record.getStrSign());
                    cell.setCellStyle(style);

                    cell = row.createCell(8);
                    cell.setCellValue(record.getAmount());
                    cell.setCellStyle(style);

                    cell = row.createCell(9);
                    cell.setCellValue(record.getTrxId());
                    cell.setCellStyle(style);

                    cell = row.createCell(10);
                    cell.setCellValue(record.getCoupon());
                    cell.setCellStyle(style);

                    cell = row.createCell(11);
                    cell.setCellValue(record.getFormattedCard());
                    cell.setCellStyle(style);

                    cell = row.createCell(12);
                    cell.setCellValue(record.getTrxDateTime().format(dateTimeFormatter));
                    cell.setCellStyle(style);

                    rowIndex++;

                    row = sheet.createRow(rowIndex + 1);
                }

            }

            response.setHeader("Content-disposition", "attachment; filename=Tarjetaza Consumos.xlsx");
            workbook.write(response.getOutputStream());

        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    private void generateCreditsXlsx(HttpServletResponse response, List<Credit> credits) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            Sheet sheet = workbook.createSheet("Créditos");
            sheet.setColumnWidth(0, 2500);
            sheet.setColumnWidth(1, 2500);
            sheet.setColumnWidth(2, 2000);
            sheet.setColumnWidth(3, 7500);
            sheet.setColumnWidth(4, 7500);
            sheet.setColumnWidth(5, 3000);
            sheet.setColumnWidth(6, 2500);
            sheet.setColumnWidth(7, 4500);
            sheet.setColumnWidth(8, 4500);
            sheet.setColumnWidth(9, 4500);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont headerFont = ((XSSFWorkbook) workbook).createFont();

            headerFont.setFontName("Arial");
            headerFont.setFontHeightInPoints((short) 10);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);

            headerStyle.setFont(headerFont);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Virtual ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(2);
            headerCell.setCellValue("Lote");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(3);
            headerCell.setCellValue("Nombres");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(4);
            headerCell.setCellValue("Apellido");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(5);
            headerCell.setCellValue("Monto");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(6);
            headerCell.setCellValue("Usuario");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(7);
            headerCell.setCellValue("Estado");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(8);
            headerCell.setCellValue("Creado");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(9);
            headerCell.setCellValue("Actualizado");
            headerCell.setCellStyle(headerStyle);

            CellStyle style = workbook.createCellStyle();

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();

            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 10);

            style.setWrapText(true);
            style.setFont(font);

            Cell cell;

            for(int i = 0; i < credits.size(); i++) {

                Credit credit = credits.get(i);

                Row row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(credit.getId());
                cell.setCellStyle(style);

                Request request = credit.getRequest();

                cell = row.createCell(1);
                cell.setCellValue(request.getVirtualId() != null ? String.valueOf(request.getVirtualId()) : "");
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(credit.getBatch().getId());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(request.getNombre());
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(request.getApellido());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(credit.getAmount());
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(request.getCard().getUsuario());
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(credit.getCreditState().getDisplayValue());
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(credit.getCreatedDate().format(dateTimeFormatter));
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(credit.getLastModifiedDate().format(dateTimeFormatter));
                cell.setCellStyle(style);
            }

            response.setHeader("Content-disposition", "attachment; filename=Tarjetaza Creditos.xlsx");
            workbook.write(response.getOutputStream());

        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    private void generateRequestsXlsx(HttpServletResponse response, List<Request> requests) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            Sheet sheet = workbook.createSheet("Solicitudes");
            sheet.setColumnWidth(0, 2500);
            sheet.setColumnWidth(1, 2500);
            sheet.setColumnWidth(2, 7500);
            sheet.setColumnWidth(3, 7500);
            sheet.setColumnWidth(4, 8500);
            sheet.setColumnWidth(5, 3000);
            sheet.setColumnWidth(6, 2000);
            sheet.setColumnWidth(7, 2000);
            sheet.setColumnWidth(8, 3000);
            sheet.setColumnWidth(9, 6000);
            sheet.setColumnWidth(10, 7000);
            sheet.setColumnWidth(11, 4500);
            sheet.setColumnWidth(12, 4500);
            sheet.setColumnWidth(13, 3500);
            sheet.setColumnWidth(14, 3500);
            sheet.setColumnWidth(15, 4500);
            sheet.setColumnWidth(16, 2000);
            sheet.setColumnWidth(17, 9500);
            sheet.setColumnWidth(18, 17500);
            sheet.setColumnWidth(19, 3500);
            sheet.setColumnWidth(20, 4500);
            sheet.setColumnWidth(21, 4500);
            sheet.setColumnWidth(22, 4500);
            sheet.setColumnWidth(23, 2500);
            sheet.setColumnWidth(24, 3500);
            sheet.setColumnWidth(25, 2500);
            sheet.setColumnWidth(26, 2500);
            sheet.setColumnWidth(27, 4500);
            sheet.setColumnWidth(28, 4500);
            sheet.setColumnWidth(29, 4500);

            Row header = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();

            headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont headerFont = ((XSSFWorkbook) workbook).createFont();

            headerFont.setFontName("Arial");
            headerFont.setFontHeightInPoints((short) 10);
            headerFont.setColor(IndexedColors.WHITE.getIndex());
            headerFont.setBold(true);

            headerStyle.setFont(headerFont);

            Cell headerCell = header.createCell(0);
            headerCell.setCellValue("ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(1);
            headerCell.setCellValue("Virtual ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(2);
            headerCell.setCellValue("Nombres");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(3);
            headerCell.setCellValue("Apellido");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(4);
            headerCell.setCellValue("Calle");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(5);
            headerCell.setCellValue("Número");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(6);
            headerCell.setCellValue("Piso");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(7);
            headerCell.setCellValue("Dpto.");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(8);
            headerCell.setCellValue("CP");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(9);
            headerCell.setCellValue("Provincia");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(10);
            headerCell.setCellValue("Localidad");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(11);
            headerCell.setCellValue("Teléfono");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(12);
            headerCell.setCellValue("Tipo de Documento");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(13);
            headerCell.setCellValue("Cuit / Cuil");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(14);
            headerCell.setCellValue("Fecha de Nacimiento");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(15);
            headerCell.setCellValue("Estado Civil");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(16);
            headerCell.setCellValue("Sexo");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(17);
            headerCell.setCellValue("Email");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(18);
            headerCell.setCellValue("Observaciones");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(19);
            headerCell.setCellValue("Tarjeta Pedida");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(20);
            headerCell.setCellValue("Creada");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(21);
            headerCell.setCellValue("Actualizada");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(22);
            headerCell.setCellValue("Estado");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(23);
            headerCell.setCellValue("Cuenta ID");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(24);
            headerCell.setCellValue("Entidad");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(25);
            headerCell.setCellValue("Sucursal");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(26);
            headerCell.setCellValue("Usuario");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(27);
            headerCell.setCellValue("Número");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(28);
            headerCell.setCellValue("Cuenta Creada");
            headerCell.setCellStyle(headerStyle);

            headerCell = header.createCell(29);
            headerCell.setCellValue("Cuenta Actualizada");
            headerCell.setCellStyle(headerStyle);

            CellStyle style = workbook.createCellStyle();

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();

            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 10);

            style.setWrapText(true);
            style.setFont(font);

            Cell cell;

            for(int i = 0; i < requests.size(); i++) {

                Request request = requests.get(i);

                Row row = sheet.createRow(i + 1);

                cell = row.createCell(0);
                cell.setCellValue(request.getId());
                cell.setCellStyle(style);

                cell = row.createCell(1);
                cell.setCellValue(request.getVirtualId() != null ? String.valueOf(request.getVirtualId()) : "");
                cell.setCellStyle(style);

                cell = row.createCell(2);
                cell.setCellValue(request.getNombre());
                cell.setCellStyle(style);

                cell = row.createCell(3);
                cell.setCellValue(request.getApellido());
                cell.setCellStyle(style);

                cell = row.createCell(4);
                cell.setCellValue(request.getCalle());
                cell.setCellStyle(style);

                cell = row.createCell(5);
                cell.setCellValue(request.getPuerta());
                cell.setCellStyle(style);

                cell = row.createCell(6);
                cell.setCellValue(request.getPiso());
                cell.setCellStyle(style);

                cell = row.createCell(7);
                cell.setCellValue(request.getDpto());
                cell.setCellStyle(style);

                cell = row.createCell(8);
                cell.setCellValue(request.getCp());
                cell.setCellStyle(style);

                cell = row.createCell(9);
                cell.setCellValue(request.getProvinciaNombre() + " (" + request.getProvincia() + ")");
                cell.setCellStyle(style);

                cell = row.createCell(10);
                cell.setCellValue(request.getLocalidad());
                cell.setCellStyle(style);

                cell = row.createCell(11);
                cell.setCellValue(request.getTelefono());
                cell.setCellStyle(style);

                cell = row.createCell(12);
                cell.setCellValue(request.getCodigoDocumentoNombre() + " (" + request.getCodigoDocumento() + ")");
                cell.setCellStyle(style);

                cell = row.createCell(13);
                cell.setCellValue(request.getCuitCuilFormatted());
                cell.setCellStyle(style);

                cell = row.createCell(14);
                cell.setCellValue(request.getFecNacFormatted());
                cell.setCellStyle(style);

                cell = row.createCell(15);
                cell.setCellValue(request.getEstadoCivilNombre()+ " (" + request.getEstadoCivil() + ")");
                cell.setCellStyle(style);

                cell = row.createCell(16);
                cell.setCellValue(request.getSexo());
                cell.setCellStyle(style);

                cell = row.createCell(17);
                cell.setCellValue(request.getEmail());
                cell.setCellStyle(style);

                cell = row.createCell(18);
                cell.setCellValue(request.getObservaciones());
                cell.setCellStyle(style);

                LocalDate requestedCardDate = request.getRequestedCardDate();

                cell = row.createCell(19);
                cell.setCellValue(requestedCardDate != null ? requestedCardDate.format(dateFormatter) : "");
                cell.setCellStyle(style);

                cell = row.createCell(20);
                cell.setCellValue(request.getCreatedDate().format(dateTimeFormatter));
                cell.setCellStyle(style);

                cell = row.createCell(21);
                cell.setCellValue(request.getLastModifiedDate().format(dateTimeFormatter));
                cell.setCellStyle(style);

                cell = row.createCell(22);
                cell.setCellValue(request.getRequestState().getDisplayValue());
                cell.setCellStyle(style);

                Card card = request.getCard();

                cell = row.createCell(23);
                cell.setCellValue(card != null ? String.valueOf(card.getId()) : "");
                cell.setCellStyle(style);

                cell = row.createCell(24);
                cell.setCellValue(card != null ? card.getEntidad() : "");
                cell.setCellStyle(style);

                cell = row.createCell(25);
                cell.setCellValue(card != null ? card.getSucursal() : "");
                cell.setCellStyle(style);

                cell = row.createCell(26);
                cell.setCellValue(card != null ? card.getUsuario() : "");
                cell.setCellStyle(style);

                cell = row.createCell(27);
                cell.setCellValue(card != null ? card.getNumero() : "");
                cell.setCellStyle(style);

                cell = row.createCell(28);
                cell.setCellValue(card != null ? request.getCreatedDate().format(dateTimeFormatter) : "");
                cell.setCellStyle(style);

                cell = row.createCell(29);
                cell.setCellValue(card != null ? request.getLastModifiedDate().format(dateTimeFormatter) : "");
                cell.setCellStyle(style);
            }

            response.setHeader("Content-disposition", "attachment; filename=Tarjetaza Solicitudes.xlsx");
            workbook.write(response.getOutputStream());

        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }


}

package com.tarjetaza.controller.bcra;

import com.tarjetaza.service.ConsumptionFileService;
import com.tarjetaza.service.ParamService;
import com.tarjetaza.service.RequestService;
import com.tarjetaza.utility.BCRAFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bcra")
public class BCRAController {

    @Value("${file.path}")
    private String path;

    private final RequestService requestService;
    private final ConsumptionFileService consumptionFileService;
    private final ParamService paramService;

    public BCRAController(RequestService requestService, ConsumptionFileService consumptionFileService, ParamService paramService) {
        this.requestService = requestService;
        this.consumptionFileService = consumptionFileService;
        this.paramService = paramService;
    }

    @GetMapping("/select-date")
    public String users(Model model) {

        model.addAttribute("dateTo", LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
        model.addAttribute("year", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy")));

        return "bcra/select-date";
    }

    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam("year") String year,
                                                      @RequestParam("month") String month) throws IOException {


        LocalDate dateTo = LocalDate.parse(year + month + "01", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate dateFrom = dateTo.minusMonths(1);

        //date.with(TemporalAdjusters.firstDayOfMonth());

        //System.out.println("FECHA DESDE: " + dateFrom);
        //System.out.println("FECHA HASTA: " + dateTo);

        Integer cantTarjetas = requestService.findCountDeliveredCards(dateTo);
        Integer consumption = consumptionFileService.findConsumptionByDates(dateFrom, dateTo);

        //String sample = "320870271201911RIT006211000nnnnnnnnnnNbbbbbbbbbb";

        List<String> xxx = new ArrayList<>();

        List<String> xx1 = BCRAFile.RITxx1xxxx(cantTarjetas, dateFrom);
        List<String> xx2 = BCRAFile.RITxx2xxxx(consumption, dateFrom);
        /*
        List<String> xx4 = BCRAFile.RITxx4xxxx(paramService.findById(3L).getValue(),
                paramService.findById(2L).getValue(),
                dateFrom);
        */

        xxx.addAll(xx1);
        xxx.addAll(xx2);
        //xxx.addAll(xx4);
        // System.out.println("*** FILE ***");
        // System.out.println(sample + "; " + sample.length() + " (Example)");
        // for (String RITxx1xxxx : list) {
        //    System.out.println(RITxx1xxxx + "; " + RITxx1xxxx.length());
        // }

        //System.out.println("SUM: " + consumptionFileService.findConsumptionByDates(dateFrom, dateTo));

        String fileName = "TARJCRED.txt";

        Path file = Paths.get(path + fileName);

        Files.write(file, xxx, StandardCharsets.UTF_8);

        byte[] data = Files.readAllBytes(file);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName().toString())
                .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
                .body(resource);
    }

}

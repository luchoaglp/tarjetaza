package com.tarjetaza.controller.bcra;

import com.tarjetaza.service.RequestService;
import com.tarjetaza.utility.BCRAFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/bcra")
public class BCRAController {

    @Value("${file.path}")
    private String path;

    private final RequestService requestService;

    public BCRAController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping("/download")
    public ResponseEntity<ByteArrayResource> download() throws IOException {

        Integer cantTarjetas = requestService.findCountDeliveredCards();

        String sample = "320870271201911RIT006211000nnnnnnnnnnNbbbbbbbbbb";

        List<String> list = BCRAFile.RITxx1xxxx(cantTarjetas);

        System.out.println("*** FILE ***");
        System.out.println(sample + "; " + sample.length() + " (Example)");
        for (String RITxx1xxxx : list) {
            System.out.println(RITxx1xxxx + "; " + RITxx1xxxx.length());
        }

        String fileName = "TARJCRED.txt";

        Path file = Paths.get(path + fileName);

        Files.write(file, list, StandardCharsets.UTF_8);

        byte[] data = Files.readAllBytes(file);

        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getFileName().toString())
                .contentType(MediaType.APPLICATION_PDF).contentLength(data.length)
                .body(resource);
    }

}

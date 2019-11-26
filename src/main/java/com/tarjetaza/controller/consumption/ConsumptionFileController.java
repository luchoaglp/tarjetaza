package com.tarjetaza.controller.consumption;

import com.tarjetaza.domain.ConsumptionFile;
import com.tarjetaza.service.ConsumptionFileService;
import com.tarjetaza.utility.ConsumptionFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/consumption")
public class ConsumptionFileController {

    @Value("${file.path}")
    private String path;

    private final ConsumptionFileService consumptionFileService;

    public ConsumptionFileController(ConsumptionFileService consumptionFileService) {
        this.consumptionFileService = consumptionFileService;
    }

    @GetMapping
    public String credits(Model model) {

        model.addAttribute("consumption", consumptionFileService.findAllByOrderByIdDesc());

        return "consumption/index";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {

        model.addAttribute("records", consumptionFileService.findById(id).getConsumptionFileRecords());

        return "consumption/detail";
    }

    @GetMapping("/upload")
    public String upload() {
        return "consumption/upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile cfile,
                                   RedirectAttributes redirectAttributes) {

        if (cfile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Seleccione el archivo a procesar.");
            return "redirect:/uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = cfile.getBytes();

            Path file = Paths.get(path + cfile.getOriginalFilename());

            Files.write(file, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "Archivo procesado con éxito '" + cfile.getOriginalFilename() + "'");

            ConsumptionFile consumptionFile = ConsumptionFormat.fileToConsumptionFile(file);

            consumptionFileService.save(consumptionFile);

            /*
            try (Stream<String> stream = Files.lines(file)) {

                stream.forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }
            */

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "consumption/uploadStatus";
    }

}

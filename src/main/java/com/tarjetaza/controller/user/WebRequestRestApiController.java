package com.tarjetaza.controller.user;

import com.tarjetaza.domain.WebRequest;
import com.tarjetaza.service.WebRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class WebRequestRestApiController {

    private final WebRequestService webRequestService;

    public WebRequestRestApiController(WebRequestService webRequestService) {
        this.webRequestService = webRequestService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid WebRequest webRequest,
                                                      BindingResult result) {

        Map<String, String> response = new HashMap<>();

        if(result.hasErrors()) {
            response.put("response", "Se ha producido un error en el envío de tus datos. Intentá nuevamente.");
        } else {

            webRequestService.save(webRequest);

            response.put("response", "Tus datos han sido guardados con éxito, en breve nos estaremos comunicando con vos.");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

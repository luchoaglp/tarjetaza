package com.tarjetaza.controller.credit;

import com.tarjetaza.domain.Credit;
import com.tarjetaza.domain.Request;
import com.tarjetaza.payload.CreditRequest;
import com.tarjetaza.payload.CreditResponse;
import com.tarjetaza.service.CreditService;
import com.tarjetaza.service.RequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/credits")
public class CreditRestController {

    private final CreditService creditService;
    private final RequestService requestService;

    public CreditRestController(CreditService creditService, RequestService requestService) {
        this.creditService = creditService;
        this.requestService = requestService;
    }

    @PostMapping
    public ResponseEntity<CreditResponse> create(@Valid @RequestBody CreditRequest creditRequest) {

        Request request = requestService.findByVirtualId(creditRequest.getId());

        if(request == null) {
            return new ResponseEntity<>(new CreditResponse("El cliente no se encuentra registado en Tarjetaza"), HttpStatus.NOT_FOUND);
        }

        Credit credit = creditService.save(new Credit(creditRequest.getAmount(), request));

        return new ResponseEntity<>(new CreditResponse(credit.getId(), "OK"), HttpStatus.OK);
    }

}

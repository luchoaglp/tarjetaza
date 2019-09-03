package com.tarjetaza.service.impl;

import com.tarjetaza.domain.WebRequest;
import com.tarjetaza.repository.WebRequestRepository;
import com.tarjetaza.service.WebRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.tarjetaza.domain.WebRequestState.SOLICITUD_ACEPTADA;
import static com.tarjetaza.domain.WebRequestState.SOLICITUD_RECHAZADA;

@Service
public class WebRequestServiceImpl implements WebRequestService {

    private final WebRequestRepository webRequestRepository;

    public WebRequestServiceImpl(WebRequestRepository webRequestRepository) {
        this.webRequestRepository = webRequestRepository;
    }

    @Override
    public List<WebRequest> findAllByOrderByIdAsc() {
        return webRequestRepository.findAllByOrderByIdAsc();
    }

    @Override
    public WebRequest findById(Long id) {
        return webRequestRepository.findById(id).orElse(null);
    }

    @Override
    public List<WebRequest> findAll() {
        return webRequestRepository.findAll();
    }

    @Override
    public WebRequest save(WebRequest webRequest) {
        return webRequestRepository.save(webRequest);
    }

    @Override
    public void changeStatus(Long id, String op) {

        WebRequest request = findById(id);

        switch (op) {
            case "accept":
                request.setRequestState(SOLICITUD_ACEPTADA);
                break;
            case "reject":
                request.setRequestState(SOLICITUD_RECHAZADA);
                break;
        }

        webRequestRepository.save(request);
    }
}

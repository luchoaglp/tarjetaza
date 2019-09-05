package com.tarjetaza.service.impl;

import com.tarjetaza.domain.WebRequest;
import com.tarjetaza.repository.WebRequestRepository;
import com.tarjetaza.service.WebRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public WebRequest save(WebRequest request) {
        return webRequestRepository.save(request);
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

        request.setLastModifiedDate(LocalDateTime.now());

        webRequestRepository.save(request);
    }

    @Override
    public void edit(WebRequest request) {

        WebRequest entity = findById(request.getId());

        entity.setNombre(request.getNombre());
        entity.setApellido(request.getApellido());
        entity.setCalle(request.getCalle());
        entity.setPuerta(request.getPuerta());
        entity.setPiso(request.getPiso());
        entity.setDpto(request.getDpto());
        entity.setCp(request.getCp());
        entity.setProvincia(request.getProvincia());
        entity.setLocalidad(request.getLocalidad());
        entity.setTelefono(request.getTelefono());
        entity.setCodigoDocumento(request.getCodigoDocumento());
        entity.setCuitCuil(request.getCuitCuil());
        entity.setFecNac(request.getFecNac());
        entity.setEstadoCivil(request.getEstadoCivil());
        entity.setSexo(request.getSexo());
        entity.setEmail(request.getEmail());
        entity.setLastModifiedDate(LocalDateTime.now());

        webRequestRepository.save(entity);
    }
}

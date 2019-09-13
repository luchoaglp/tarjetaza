package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Request;
import com.tarjetaza.repository.RequestRepository;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tarjetaza.domain.RequestState.SOLICITUD_ACEPTADA;
import static com.tarjetaza.domain.RequestState.SOLICITUD_RECHAZADA;
import static com.tarjetaza.domain.RequestState.TARJETA_PEDIDA;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> findAllByOrderByIdAsc() {
        return requestRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Request findById(Long id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void changeStatus(Long id, String op) {

        Request request = findById(id);

        switch (op) {
            case "r_accept":
                request.setRequestState(SOLICITUD_ACEPTADA);
                break;
            case "r_reject":
                request.setRequestState(SOLICITUD_RECHAZADA);
                break;
            case "c_requested":
                request.setRequestState(TARJETA_PEDIDA);
                break;
        }

        request.setLastModifiedDate(LocalDateTime.now());

        requestRepository.save(request);
    }

    @Override
    public void changeStatus(Long[] ids, String op) {

        for(Long id : ids) {

            Request request = findById(id);

            switch (op) {
                case "r_accept":
                    request.setRequestState(SOLICITUD_ACEPTADA);
                    break;
                case "r_reject":
                    request.setRequestState(SOLICITUD_RECHAZADA);
                    break;
                case "c_requested":
                    request.setRequestState(TARJETA_PEDIDA);
                    break;
            }

            request.setLastModifiedDate(LocalDateTime.now());

            requestRepository.save(request);
        }
    }

    @Override
    public void edit(Request request) {

        Request entity = findById(request.getId());

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

        requestRepository.save(entity);
    }

    @Override
    public boolean existsByCuitCuil(String cuitCuil) {
        return requestRepository.existsByCuitCuil(cuitCuil);
    }
}

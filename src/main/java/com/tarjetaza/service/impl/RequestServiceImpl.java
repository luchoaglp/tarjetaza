package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Request;
import com.tarjetaza.domain.RequestState;
import com.tarjetaza.repository.RequestRepository;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.tarjetaza.domain.RequestState.*;

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
    public List<Request> findActiveOrderByIdAsc() {
        return requestRepository.findActiveOrderByIdAsc();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void changeStatus(Long id, String op) {

        Request request = findById(id);

        request.setRequestState(getRequestState(op));
        request.setLastModifiedDate(LocalDateTime.now());

        requestRepository.save(request);
    }

    @Override
    public void changeStatus(Long[] ids, String op) {

        for(Long id : ids) {

            Request request = findById(id);

            request.setRequestState(getRequestState(op));
            request.setLastModifiedDate(LocalDateTime.now());

            requestRepository.save(request);
        }
    }

    @Override
    public void edit(Request request) {

        Request entity = findById(request.getId());

        if(request.getVirtualId() != null) {
            entity.setVirtualId(request.getVirtualId());
        }
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

    @Override
    public boolean existsByVirtualId(Long virtualId) {
        return requestRepository.existsByVirtualId(virtualId);
    }

    @Override
    public Request findByCuitCuil(String cuitCuil) {
        return requestRepository.findByCuitCuil(cuitCuil);
    }

    private RequestState getRequestState(String op) {

        switch (op) {
            case "r_entered":
                return SOLICITUD_INGRESADA;
            case "r_accept":
                return SOLICITUD_ACEPTADA;
            case "r_reject":
                return SOLICITUD_RECHAZADA;
            case "c_requested":
                return TARJETA_PEDIDA;
            case "c_delivered":
                return TARJETA_ENTREGADA;
            case "c_reject":
                return TARJETA_RECHAZADA;
        }

        return null;
    }
}

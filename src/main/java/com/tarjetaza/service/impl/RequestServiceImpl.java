package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Card;
import com.tarjetaza.domain.Request;
import com.tarjetaza.domain.RequestState;
import com.tarjetaza.repository.RequestRepository;
import com.tarjetaza.service.RequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<Request> findAllByOrderByIdDesc() {
        return requestRepository.findAllByOrderByIdDesc();
    }

    @Override
    public List<Request> findWithCardOrderByIdAsc() {
        return requestRepository.findWithCardOrderByIdAsc();
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
    public List<Request> findInProcessOrderByIdDesc() {
        return requestRepository.findInProcessOrderByIdDesc();
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

        if(op.equals("c_requested")) {
            request.setRequestedCardDate(LocalDate.now());
        }

        requestRepository.save(request);
    }

    @Override
    public void changeStatus(Long[] ids, String op) {

        for(Long id : ids) {

            Request request = findById(id);

            request.setRequestState(getRequestState(op));
            request.setLastModifiedDate(LocalDateTime.now());

            if(op.equals("c_requested")) {
                request.setRequestedCardDate(LocalDate.now());
            }

            requestRepository.save(request);
        }
    }

    @Override
    public void edit(Request request) {

        Request entity = findById(request.getId());

        if(request.getVirtualId() != null) {
            entity.setVirtualId(request.getVirtualId());
        }
        entity.setNombre(request.getNombre().trim());
        entity.setApellido(request.getApellido().trim());
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

        Card entityCard = entity.getCard();

        if(entityCard != null) {

            Card requestCard = request.getCard();

            entityCard.setEntidad(requestCard.getEntidad());
            entityCard.setSucursal(requestCard.getSucursal());
            entityCard.setUsuario(requestCard.getUsuario());
            entityCard.setNumero(requestCard.getNumero());
            entityCard.setLastModifiedDate(LocalDateTime.now());
        }

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

    @Override
    public Request findByVirtualId(Long virtualId) {
        return requestRepository.findByVirtualId(virtualId);
    }

    @Override
    public Request findByCardNumero(String numero) {
        return requestRepository.findByCardNumero(numero);
    }

    @Override
    public Integer findCountDeliveredCards(LocalDate date) {
        return requestRepository.findCountDeliveredCards(date);
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
            case "c_received":
                return TARJETA_RECIBIDA;
            case "c_delivered":
                return TARJETA_ENTREGADA;
            case "c_reject":
                return TARJETA_RECHAZADA;
            case "drop":
                return BAJA;
        }

        return null;
    }
}

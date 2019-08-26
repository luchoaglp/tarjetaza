package com.tarjetaza.service;

import com.tarjetaza.domain.WebRequest;

import java.util.List;

public interface WebRequestService {

    List<WebRequest> findAll();

    WebRequest findById(Long id);

    List<WebRequest> findAllByOrderByIdAsc();

    WebRequest save(WebRequest webRequest);

}

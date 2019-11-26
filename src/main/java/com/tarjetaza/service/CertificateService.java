package com.tarjetaza.service;

import com.tarjetaza.domain.Certificate;

import java.util.List;

public interface CertificateService {

    List<Certificate> findAll();

    Certificate save(Certificate certificate);

    Certificate findById(Long id);

    Certificate edit(Certificate certificate);
}

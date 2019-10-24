package com.tarjetaza.service.impl;

import com.tarjetaza.domain.Certificate;
import com.tarjetaza.repository.CertificateRepository;
import com.tarjetaza.service.CertificateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }


    @Override
    public List<Certificate> findAll() {
        return certificateRepository.findAll();
    }

    @Override
    public Certificate save(Certificate certificate) {
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate findById(Long id) {
        return certificateRepository.findById(id).orElse(null);
    }

    @Override
    public Certificate edit(Certificate certificate) {

        Certificate entity = findById(certificate.getId());

        entity.setNumero(certificate.getNumero().trim());
        entity.setLastModifiedDate(LocalDateTime.now());

        certificateRepository.save(entity);

        return entity;
    }
}

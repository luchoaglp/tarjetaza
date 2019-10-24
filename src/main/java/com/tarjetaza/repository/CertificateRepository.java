package com.tarjetaza.repository;

import com.tarjetaza.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {


}

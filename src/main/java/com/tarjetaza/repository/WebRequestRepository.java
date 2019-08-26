package com.tarjetaza.repository;

import com.tarjetaza.domain.WebRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebRequestRepository extends JpaRepository<WebRequest, Long> {


    List<WebRequest> findAllByOrderByIdAsc();
}

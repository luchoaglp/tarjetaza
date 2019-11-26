package com.tarjetaza.repository;

import com.tarjetaza.domain.DuplicateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DuplicateRequestRepository extends JpaRepository<DuplicateRequest, Long> {

    List<DuplicateRequest> findAllByOrderByIdAsc();

}

package com.tarjetaza.repository;

import com.tarjetaza.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByOrderByIdAsc();

    boolean existsByCuitCuil(String cuitCuil);
}

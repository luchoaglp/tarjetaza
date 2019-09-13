package com.tarjetaza.repository;

import com.tarjetaza.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {


    List<Request> findAllByOrderByIdAsc();
}

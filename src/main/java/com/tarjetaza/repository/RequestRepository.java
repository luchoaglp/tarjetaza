package com.tarjetaza.repository;

import com.tarjetaza.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByOrderByIdAsc();

    boolean existsByCuitCuil(String cuitCuil);
    boolean existsByVirtualId(Long virtualId);

    Request findByCuitCuil(String cuitCuil);
    Request findByVirtualId(Long virtualId);

    @Query("FROM Request r " +
            "WHERE r.requestState != 2 " +
            "AND r.requestState != 5 " +
            "AND r.requestState != 6 " +
            "AND r.requestState != 7 " +
            "ORDER BY r.id DESC")
    List<Request> findInProcessOrderByIdAsc();

    @Query("FROM Request r " +
            "WHERE r.requestState = 5" +
            "ORDER BY r.id DESC")
    List<Request> findWithCardOrderByIdAsc();

    Request findByCardNumero(String numero);

    @Query("SELECT COUNT(r) FROM Request r " +
            "WHERE r.requestState = 5")
    Integer findCountDeliveredCards();
}

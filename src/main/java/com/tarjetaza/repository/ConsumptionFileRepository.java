package com.tarjetaza.repository;

import com.tarjetaza.domain.ConsumptionFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ConsumptionFileRepository extends JpaRepository<ConsumptionFile, Long> {

    List<ConsumptionFile> findAllByOrderByIdDesc();

    @Query("SELECT SUM(c.amount) FROM ConsumptionFile c " +
            "WHERE c.processDate < :dateTo " +
            "AND c.processDate >= :dateFrom")
    Integer findConsumptionByDates(@Param("dateFrom") LocalDate dateFrom,
                                   @Param("dateTo") LocalDate dateTo);

}

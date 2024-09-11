package com.sakura.repository;

import com.sakura.Entities.Venta;
import java.time.LocalDate;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface VentaRepository extends BaseRepository<Venta, Long> {

    List<Venta> findAllByClienteId(Long id);
    
    @Query(value = "select * from venta V where V.fecha >= :fecha1 and V.fecha <= :fecha2", nativeQuery = true)
    List<Venta> findByFecha(@Param("fecha1") LocalDate fecha1, @Param("fecha2") LocalDate fecha2);
}
package com.sakura.repository;

import com.sakura.Entities.MasComprado;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MasCompradoRepository extends BaseRepository<MasComprado, Long> {
    
    @Override
    public List<MasComprado> findAll();
    
    @Query(value= "select * from mascomprado M where M.fecha_desde= :fechaDesde and M.fecha_hasta= :fechaHasta", nativeQuery=true)
    List<MasComprado> findByFechas(@Param("fechaDesde") LocalDate fechaDesde, @Param("fechaHasta") LocalDate fechaHasta);
}

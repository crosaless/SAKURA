
package com.sakura.repository;

import com.sakura.Entities.Provincia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long>{
    
    @Query(value = "select * from provincia p where p.fecha_hora_baja is null", nativeQuery = true)
    List<Provincia> findAllDisponibles();
    
    @Query(value = "select * from provincia p where p.id = :id", nativeQuery = true)
    Provincia getReferenceById(@Param("id") Long id);
}

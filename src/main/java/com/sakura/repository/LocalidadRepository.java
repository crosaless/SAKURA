package com.sakura.repository;

import com.sakura.Entities.Localidad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad, Long>{
    
    //traer solo las localidades habilitadas
    @Query(value = "select * from localidad l where l.fecha_hora_baja is null", nativeQuery = true)
    List<Localidad> findAllDisponibles();
    
    //traer localidades relacionadas a una provincia
    @Query(value = "select * from localidad l where l.provincia_id = :id", nativeQuery = true)
    List<Localidad> findByRelation(@Param("id") Long id);
    
    @Query(value = "select * from localidad l where l.id = :id", nativeQuery = true)
    Localidad findByID(@Param("id") Long id);
    
    @Query(value = "select * from localidad l where l.cod_pos = :id", nativeQuery = true)
    Localidad findByCP(@Param("id") Long id);
    
    @Query(value = "select * from localidad l where l.cod_pos = :cp or l.nombre_localidad like %:nombreLocalidad%", nativeQuery = true)
    List<Localidad> findByNombreOrCP(Long cp, String nombreLocalidad);
    
}

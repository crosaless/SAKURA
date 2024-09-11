package com.sakura.repository;


import com.sakura.Entities.Puntuacion;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntuacionRepository extends BaseRepository<Puntuacion, Long>{
    
    @Override
    List<Puntuacion> findAll();
    
    @Query(value = "SELECT * FROM puntuacion p WHERE p.producto_id = :idProducto", nativeQuery = true)
    List<Puntuacion> findByProduct(@Param("idProducto") Long idProducto);
    
    @Query(value = "SELECT * FROM puntuacion p WHERE p.cliente_id = :idCliente and p.producto_id = :idProducto", nativeQuery = true)
    Puntuacion findByClienteAndProduct(@Param("idCliente") Long idCliente,@Param("idProducto") Long idProducto );
}


package com.sakura.repository;

import com.sakura.Entities.EstadoVenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoVentaRepository extends BaseRepository<EstadoVenta, Long>{
    
    @Query(value = "select * from estado_venta e where e.nombre like %:nombre%", nativeQuery = true)
    EstadoVenta findByState(@Param("nombre") String nombre);
    
    
}

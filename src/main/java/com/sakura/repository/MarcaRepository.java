
package com.sakura.repository;

import com.sakura.Entities.Marca;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends BaseRepository<Marca,Long>{
    
    
    @Override
    List<Marca> findAll();
    
    @Query(value = "select * from marca m where m.fecha_hora_baja is null", nativeQuery = true)
    List<Marca> findAllDisponibles();
    
    Optional<Marca> findByNombre(String nombre);
    
    
}

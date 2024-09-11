
package com.sakura.repository;

import com.sakura.Entities.Carrito;
import java.util.Optional;

import org.springframework.stereotype.Repository;


@Repository
public interface CarritoRepository extends BaseRepository<Carrito, Long> {

    Optional<Carrito> findByClienteId(Long id);
}

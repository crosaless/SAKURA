package com.sakura.repository;

import com.sakura.Entities.Categoria;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends BaseRepository<Categoria, Long> {

    Optional<Categoria> findByNombre(String nombre);

    @Override
    List<Categoria> findAll();

}

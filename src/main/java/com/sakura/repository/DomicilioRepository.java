package com.sakura.repository;

import com.sakura.Entities.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {
    
    //@Query(value = "select * from domicilio d where d") no le veo sentido a hacer una busqueda por id a menos q lo busque para editarlo
}

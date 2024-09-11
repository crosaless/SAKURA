/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sakura.repository;

import com.sakura.Entities.TalleRopa;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface TalleRopaRepository extends BaseRepository<TalleRopa,Long>{

    public boolean existsByNombre(String talle);

    public Optional<TalleRopa> findByNombre(String talle);
    
}

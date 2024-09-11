/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sakura.repository;

import com.sakura.Entities.Vista;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface VistaRepository extends BaseRepository<Vista,Long>{
    
    Optional<Vista> findByNombreVista(String nombreVista);
}

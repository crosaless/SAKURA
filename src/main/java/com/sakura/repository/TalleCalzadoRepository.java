/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sakura.repository;

import com.sakura.Entities.TalleCalzado;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface TalleCalzadoRepository extends BaseRepository<TalleCalzado,Long>{

    public boolean existsByNumero(String talle);

    public Optional<TalleCalzado> findByNumero(String talle);
    
}

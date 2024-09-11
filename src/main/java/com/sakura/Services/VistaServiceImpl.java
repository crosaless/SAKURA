/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Services;

import com.sakura.Entities.Vista;
import com.sakura.Services.IService.VistaService;
import com.sakura.repository.VistaRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VistaServiceImpl extends BaseServiceImpl<Vista, Long, VistaRepository>
        implements VistaService{
    
    @Autowired
    public VistaServiceImpl(VistaRepository repository) {
        super(repository);
    }

    @Override
    public void crearVista(String nombre) {
       this.repository.save(new Vista(nombre));
    }

    @Override
    public void editarVista(Long id, String nombre) {
        this.repository.findById(id).get().setNombreVista(nombre);
    }

    @Override
    public void darBajaVista(Long id) {
        this.repository.findById(id).get().setFechaHoraBaja(LocalDateTime.now());

    }

    @Override
    public Vista findByNombreVista(String nombre) {
        return this.repository.findByNombreVista(nombre).get();
    }
    
   
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sakura.Services.IService;

import com.sakura.Entities.Vista;

/**
 *
 * @author marci
 */
public interface VistaService extends BaseService<Vista, Long> {

    public void crearVista(String nombre);

    public void editarVista(Long id, String nombre);

    public void darBajaVista(Long id);
    public Vista findByNombreVista(String nombre);
}

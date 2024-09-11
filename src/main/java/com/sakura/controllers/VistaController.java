/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.controllers;

import com.sakura.Entities.Vista;
import com.sakura.Services.IService.VistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/vistas")
public class VistaController extends BaseControllerImpl<Vista,Long,VistaService>{
    @Autowired
    public VistaController(VistaService service) {
        super(service);
    }
    
    
    @GetMapping("/crear")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crearVista(@RequestParam String nombre) throws Exception {
        try {
            this.service.crearVista(nombre);
            return ResponseEntity.ok("Se ha creado una nueva vista exitosamente");
        } catch (Exception e) {       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se ha producido un error al intentar agregar una nueva vista");
        }

    }
    
    @GetMapping("/editar")
    @ResponseBody
    public ResponseEntity<?> editarVista(@RequestParam Long id,@RequestParam String nombre) throws Exception {
        try {
            this.service.editarVista(id,nombre);
            return ResponseEntity.ok("Se ha editado la vista exitosamente");
        } catch (Exception e) {       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se ha producido un error al intentar editar vista");
        }

    }
    
    @GetMapping("/baja")
    @ResponseBody
    public ResponseEntity<?> darBajaVista(@RequestParam Long id) throws Exception {
        try {
            this.service.darBajaVista(id);
            return ResponseEntity.ok("Se ha dado de baja la vista exitosamente");
        } catch (Exception e) {       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se ha producido un error al intentar dar de baja la vista");
        }

    }
}

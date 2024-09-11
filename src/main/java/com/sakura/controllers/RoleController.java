/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.controllers;

import com.sakura.DTO.DTORol;
import com.sakura.Entities.Role;
import com.sakura.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/roles")
public class RoleController extends BaseControllerImpl<Role,Long,RoleService>{

    @Autowired
    public RoleController(RoleService service) {
        super(service);
    }
    
    
    @PostMapping("/crear")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addRole(@RequestBody DTORol dtoRol) throws Exception {
        try {
            this.service.addRole(dtoRol);
            return ResponseEntity.ok("Se ha creado un nuevo rol exitosamente");
        } catch (Exception e) {       
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Se ha producido un error al intentar agregar un nuevo rol");
        }

    }
    
    @PostMapping("/editar/rol")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> editarRol(@RequestBody DTORol dtoRol) throws Exception {
        try {
           this.service.editarRol(dtoRol);
            return ResponseEntity.ok("Se ha actualizado el rol exitosamente");
        } catch (Exception e) {       
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se ha producido un error al intentar actualizar rol");
        }

    }
    
    @GetMapping("/editar/rol")
    @ResponseBody
    public ResponseEntity<?> darDeBaja(@RequestParam Long id){
        try{
            this.service.darDebaja(id);
            return ResponseEntity.ok("Se ha dado de baja el rol exitosamente");
        }catch(Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Se ha producido un error al intentar dar de baja el rol");
        }
    }
}

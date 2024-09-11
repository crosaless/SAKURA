/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Services;

import com.sakura.DTO.DTORol;
import com.sakura.Entities.Role;
import com.sakura.Entities.Vista;
import com.sakura.Services.IService.IRoleService;
import com.sakura.repository.RoleRepository;
import com.sakura.repository.VistaRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService extends BaseServiceImpl<Role,Long,RoleRepository> implements IRoleService{
    
    VistaRepository vista_repository;
    
    @Autowired
    public RoleService(RoleRepository repository, VistaRepository vista_repository) {
        super(repository);
        this.vista_repository = vista_repository;
    }
    
    @Transactional
    public void editarRol(DTORol dtoRol)throws Exception{
        System.out.println("rol: "+dtoRol.getNombre());
        Role rol = this.findById(dtoRol.getId());
        if((!rol.getName().equals(dtoRol.getNombre()))&& (!dtoRol.getNombre().isBlank())){
            repository.updateRole(rol.getId(), dtoRol.getNombre());
        }
        this.repository.deleteFromJoinTable(rol.getId());
    
        for(String vista : dtoRol.getPermisos()){
            System.out.println("rol: "+dtoRol.getNombre());
            Optional<Vista> permiso = this.vista_repository.findByNombreVista(vista);
            if(permiso.isPresent()){
                repository.updateVista(rol.getId(),permiso.get().getId());
            }
           
        }
      
    }
    
    public void darDebaja(Long id) throws Exception{
        this.repository.findById(id).get().setFechaHoraBaja(LocalDateTime.now());
    }

    public void addRole(DTORol dtoRol) {
        ArrayList<Vista> permisos = new ArrayList<Vista>();
        for(String p : dtoRol.getPermisos()){
            Optional<Vista> permiso = this.vista_repository.findByNombreVista(p);
            if(permiso.isPresent()){
                permisos.add(permiso.get());
            }
        }
        this.repository.save(new Role(dtoRol.getNombre(),permisos));
    }

    public Role findByName(String name) {
        return this.repository.findByName(name).get();
    }
    
    

    
}

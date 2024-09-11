/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Services;

import com.sakura.Entities.Role;
import com.sakura.Entities.User;
import com.sakura.Entities.Vista;
import com.sakura.Services.IService.VistaService;
import com.sakura.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthService {
    
    @Autowired
    UserRepository userRepository;
    @Autowired
    VistaService vistaService;
    
        //Verifica que el recurso al que quiere acceder un usuario pertenezca al usuario logueado
    public void validateUserMatching(Long id) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String usernameToken = userDetails.getUsername();
        String usernameId = this.userRepository.findById(id).get().getUsername();
        if (!usernameToken.equals(usernameId)) {
            throw new Exception();
        }
    }
    
    public void validateUserPermissions(String viewName) throws Exception{
        boolean hasPermission = false;
        UserDetails userDetails;
        try{
            userDetails = (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        }
        catch(Exception e){
            throw new Exception("Usuario no logueado");
        }
        String username = userDetails.getUsername();
        User user = this.userRepository.findByUsername(username).get();
        Vista vista = this.vistaService.findByNombreVista(viewName);
        
        for(Role userRole : user.getRoles()){
            if(userRole.getVistas().contains(vista)){
                hasPermission = true;
                break;
            }
        }
        
        if(!hasPermission){
            throw new Exception("El rol de usuario logueado no tiene los permisos para acceder a la vista solicitada");
        }
            
    }
    
    
    
}

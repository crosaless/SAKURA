
package com.sakura.security.services;

import com.sakura.Entities.Role;
import com.sakura.Entities.User;
import com.sakura.Entities.Cliente;
import com.sakura.Entities.Carrito;
import com.sakura.repository.CarritoRepository;
import com.sakura.Entities.DetalleCarrito;
import com.sakura.repository.ClienteRepository;
import com.sakura.repository.RoleRepository;
import com.sakura.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserFactory {
  private static UserFactory userFactory;
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private RoleRepository roleRepository ;
  
  @Autowired
  private ClienteRepository clienteRepository;
  
  @Autowired
  private CarritoRepository carritoRepository;
    
    @Autowired
    protected UserFactory(){
        userFactory = this;
    }
    
    public static UserFactory getUserFactory(){
       if(userFactory == null){
          return new UserFactory();
       }
       return userFactory;
    }
    
@Transactional
    public User createUser(String username, String password, String email, Set<String> strRoles){
        
    User user;
    boolean esCliente = false;
    Set<Role> roles = new HashSet<>();
    
    if (strRoles == null) {
      Role userRole = roleRepository.findByName("cliente")
          .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
      roles.add(userRole);
      esCliente = true;
    } else {
        for(String strRol : strRoles ){
            System.out.println(strRol);
            switch (strRol) {

            case "cliente":
                
                Role clienteRol = roleRepository.findByName("cliente")
                        .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
                roles.add(clienteRol);
               esCliente = true;
                
                break;
                //Cliente cliente = new Cliente(username, email, password, clienteRol);
                //clienteRepository.save(cliente);
                //this.carritoRepository.save( new Carrito(cliente,new ArrayList<DetalleCarrito>()));
                //return cliente;

            default:
              Role userRole = roleRepository.findByName(strRol)
                  .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
              roles.add(userRole);
              break;
            }
        }
      
    }
    if(esCliente){
        Cliente cliente = new Cliente(username, email, password,roles);
        System.out.println(cliente.toString());
        clienteRepository.save(cliente);
        this.carritoRepository.save( new Carrito(cliente,new ArrayList<DetalleCarrito>()));
        return cliente;
        }
    else{
        user = new User(username, email, password,  roles);
        userRepository.save(user);
        return user;
        }
    }
}

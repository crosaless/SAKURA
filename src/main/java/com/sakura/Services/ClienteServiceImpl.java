
package com.sakura.Services;

import com.sakura.DTO.DTOPerfilCliente;
import com.sakura.Entities.Cliente;
import com.sakura.Entities.Domicilio;
import com.sakura.Entities.Localidad;
import com.sakura.Entities.Provincia;
import com.sakura.Entities.User;
import com.sakura.repository.ClienteRepository;
import com.sakura.repository.DomicilioRepository;
import com.sakura.repository.LocalidadRepository;
import com.sakura.repository.ProvinciaRepository;
import com.sakura.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl {
    
    @Autowired
    ClienteRepository RepoClientes;
    @Autowired
    UserRepository RepoUser;
    @Autowired
    DomicilioRepository RepoDomicilio;
    @Autowired
    ProvinciaRepository RepoProvincia;
    @Autowired
    LocalidadRepository RepoLocalidad;
    
    public Page<Cliente> findAll(Pageable p) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Cliente> findAll() throws Exception {
        return this.RepoClientes.findAll();
    }

    public Cliente findById(Long id) throws Exception {
       return this.RepoClientes.getReferenceById(id);
    }


    public Cliente save(Cliente entity) throws Exception {
        return this.RepoClientes.save(entity);
    }
    
    public DTOPerfilCliente dtoPerfil(Long id) throws Exception{
        System.out.println("Estoy en el metodo q crea el dto del cliente");
        Cliente cliente = findById(id);
        System.out.println("Encontre el cliente");
        Domicilio domicilio = cliente.getDomicilio();
        System.out.println("tengo el domicilio del cliente");
        Localidad localidad = domicilio.getLocalidad();
        System.out.println("Tengo la localidad del cliente");
        Provincia provincia = localidad.getProvincia();
        System.out.println("Tengo la provincia");
        System.out.println("Voy a buscar el usuario relacionado al cliente");
        User user = this.RepoUser.getReferenceById(id);
        System.out.println("Existe el usuario, lo tengo");
        
        DTOPerfilCliente c = DTOPerfilCliente.builder()
                .CodigoPostal(localidad.getCodigoPostal())
                .nombreLocalidad(localidad.getNombreLocalidad())
                .idLocalidad(localidad.getId())
                .idDomicilio(domicilio.getId())
                .referenciaDomicilio(domicilio.getReferencia())
                .nroCalle(domicilio.getNumero())
                .nombrecalle(domicilio.getCalle())
                .idProvincia(provincia.getId())
                .nombreProvincia(provincia.getNombreProvincia())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .idUser(user.getId())
                .build();
    return c;
    }
    
    public Cliente updateInfo(DTOPerfilCliente cliente) throws Exception {
        //System.out.println("Estoy en el metodo de actualizar info personal:"+ cliente);
        //System.out.println("el id del cliente:"+ cliente.getIdUser());
        //System.out.println("el username del cliente:"+ cliente.getUsername());
        //System.out.println("el id de la localidad:"+ cliente.getIdLocalidad());

        //llega el dto 
        //buscar cliente por id
        //setear valores nuevos
        //guardar cliente
        try {
            Cliente c = this.RepoClientes.getReferenceById(cliente.getIdUser());
            //recupero el user relacionado al cliente para actualizar username, password and 
            Optional<User> u = this.RepoUser.findById(cliente.getIdUser());
            
            if(u.isPresent()){
                User usuario = u.get();
                usuario.setEmail(cliente.getEmail());
                usuario.setUsername(cliente.getUsername());
                usuario.setPassword(cliente.getPassword());
                 System.out.println("Actualice el usuario"); 
               this.RepoUser.save(usuario);
            }
            
            //Provincia provincia = RepoProvincia.getReferenceById(cliente.getIdProvincia());
            //Busco la localidad a setear
            Localidad localidad = RepoLocalidad.findByCP(cliente.getIdLocalidad());
            
            
            System.out.println("recupere la localidad");
            //busco el domicilio del cliente
            Domicilio domicilio = RepoDomicilio.getReferenceById(cliente.getIdDomicilio());
            
            System.out.println("recupere el domicilio con ID:"+ cliente.getIdDomicilio());
            
            //actualizo los datos del domicilio y le seteo la localidad nueva
            System.out.println("Nombre de la calle nueva:"+ cliente.getNombrecalle());
            domicilio.setCalle(cliente.getNombrecalle());
            System.out.println("nro de la calle nueva:"+ cliente.getNroCalle());
            domicilio.setNumero(cliente.getNroCalle());
            System.out.println("referencia del domcilio:"+ cliente.getReferenciaDomicilio());
            domicilio.setReferencia(cliente.getReferenciaDomicilio());
            domicilio.setLocalidad(localidad);
            RepoDomicilio.save(domicilio);
               
            
            c.setDomicilio(domicilio);
            //System.out.println("setie el domicilio al cliente:"+ domicilio.getCalle()+domicilio.getReferencia());
            System.out.println("guarde el cliente con la info nueva");
            return RepoClientes.save(c);
        } catch (Exception e) {
            throw new Exception();
        }

    }

 
    
    
}

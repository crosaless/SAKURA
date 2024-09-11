/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sakura.Services;

import com.sakura.DTO.DTOLocalidad;
import com.sakura.DTO.DTOMostrarProvincia;
import com.sakura.DTO.DTOProvincia;
import com.sakura.Entities.Localidad;
import com.sakura.Entities.Provincia;
import com.sakura.repository.LocalidadRepository;
import com.sakura.repository.ProvinciaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaServiceImpl extends BaseServiceImpl<Provincia, Long, ProvinciaRepository> implements ProvinciaService {

    @Autowired
    LocalidadRepository repoLocalidad;
    public ProvinciaServiceImpl(ProvinciaRepository repository) {
        super(repository);
    }
    
    
    public List<DTOProvincia> ProvinciasDisponibles(){
        List<Provincia> provincias = repository.findAllDisponibles();
        System.out.println("Recupere todas las provincias disponibles");
        List<DTOProvincia> listaProvincias = new ArrayList<>();
        
        for(Provincia provincia: provincias){
            DTOProvincia pr = new DTOProvincia();
            pr.setIdProvincia(provincia.getId());
            pr.setNombreProvincia(provincia.getNombreProvincia());
            listaProvincias.add(pr);
        }
    return listaProvincias;
    }
    
    public DTOMostrarProvincia EdicionProvincia(Long id){
        
        Provincia p = repository.getReferenceById(id);
        System.out.println("Recupere todas las provincias disponibles");
        DTOMostrarProvincia Provincia = new DTOMostrarProvincia();
        
        
            //busco las localidades relacionadas a la provincia actual segun id
            System.out.println("estoy loopeando las provincias para encontrar sus localidades");
            
            Provincia.setNombreProvincia(p.getNombreProvincia());
            Provincia.setIdProvincia(p.getId());
            
            List<Localidad> localidades = repoLocalidad.findByRelation(p.getId());
            
            List<DTOLocalidad> l = new ArrayList<>();
            for(Localidad localidad : localidades){
                DTOLocalidad loc = new DTOLocalidad();
                loc.setCodigoPostal(localidad.getCodigoPostal());
                loc.setNombreLocalidad(localidad.getNombreLocalidad());
                loc.setNombreProvincia(p.getNombreProvincia());
                loc.setId_provincia(p.getId());
                l.add(loc);
            }
            Provincia.setLocalidades(l);
        
        System.out.println("Termine de armar la lista de provincia con sus localidades");
       return Provincia;
        
       
    }
    
    
    public void update(Long id, String nombre){
        
        Provincia provincia = repository.getReferenceById(id);
        if (provincia != null) {
           provincia.setNombreProvincia(nombre);
            repository.save(provincia);
        }
    }
    
    public Provincia create(String nombre){
         Provincia nuevaprovincia = Provincia.builder()
         .NombreProvincia(nombre)
         .build();
         //ver lo de setear las localidades
         
         repository.save(nuevaprovincia);
         
        return nuevaprovincia;
    }
    
    public void eliminar(Long id){
        Provincia p = repository.getReferenceById(id);
        p.setFechaHoraBaja(LocalDate.now());
        repository.save(p);
        repository.delete(p);
    }
    
    
    
    
    
    
    
    @Override
    public Page<Provincia> findAll(Pageable p) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Provincia> findAll() throws Exception {
            return repository.findAllDisponibles();
    }

    @Override
    public Provincia findById(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Provincia save(Provincia entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Provincia update(Long id, Provincia entity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Provincia delete(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}


package com.sakura.Services;

import com.sakura.DTO.DTOMostrarProvincia;
import com.sakura.DTO.DTOLocalidad;
import com.sakura.DTO.DTOProvincia;
import com.sakura.Entities.Localidad;
import com.sakura.Entities.Provincia;
import com.sakura.repository.LocalidadRepository;
import com.sakura.repository.ProvinciaRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<Localidad, Long, LocalidadRepository> 
implements LocalidadService{
    
    @Autowired
    ProvinciaRepository RepoProvincia;
    
    @Autowired
    public LocalidadServiceImpl(LocalidadRepository repository) {
        super(repository);
    }
    
    public List<DTOProvincia> mostrarProvincias(){
        List<Provincia> p = RepoProvincia.findAllDisponibles();
        List<DTOProvincia> listaProvincias = new ArrayList<>();
        
        for(Provincia provincia : p){
            System.out.println("estoy loopeando las provincias");
            DTOProvincia pr = new DTOProvincia();
            pr.setNombreProvincia(provincia.getNombreProvincia());
            pr.setIdProvincia(provincia.getId());
        
            listaProvincias.add(pr);
        }
        return listaProvincias;
    }
    
    public List<DTOMostrarProvincia> mostrarLugares(){
        System.out.println("Estoy en el metodo´para armar la lista de provincias con localidades");
        List<Provincia> p = RepoProvincia.findAllDisponibles();
        System.out.println("Recupere todas las provincias disponibles");
        List<DTOMostrarProvincia> listaProvincias = new ArrayList<>();
        
        for(Provincia provincia : p){
            //busco las localidades relacionadas a la provincia actual segun id
            System.out.println("estoy loopeando las provincias para encontrar sus localidades");
            DTOMostrarProvincia pr = new DTOMostrarProvincia();
            pr.setNombreProvincia(provincia.getNombreProvincia());
            pr.setIdProvincia(provincia.getId());
            
            List<Localidad> localidades = repository.findByRelation(provincia.getId());
            
            List<DTOLocalidad> l = new ArrayList<>();
            for(Localidad localidad : localidades){
                DTOLocalidad loc = new DTOLocalidad();
                loc.setCodigoPostal(localidad.getCodigoPostal());
                loc.setNombreLocalidad(localidad.getNombreLocalidad());
                loc.setNombreProvincia(provincia.getNombreProvincia());
                loc.setId_provincia(provincia.getId());
                l.add(loc);
            }
            pr.setLocalidades(l);
            listaProvincias.add(pr);
        }
        System.out.println("Termine de armar la lista de provincias y localidades");
       return listaProvincias;
    }
    
    public List<DTOLocalidad> mostrarLocalidades(){
        //busco las localidades disponibles para mostrar
        List<Localidad> m = repository.findAllDisponibles();
        //creo la lista de dtomarca
        List<DTOLocalidad> listaLocalidades = new ArrayList<DTOLocalidad>();
        
        for(Localidad lo : m){
            System.out.println("Empiezo a loopear localidades");
            System.out.println("localidad encontrada: " + lo.getNombreLocalidad());
            Provincia p = lo.getProvincia();
            DTOLocalidad localidad = DTOLocalidad.builder()
                    .CodigoPostal(lo.getCodigoPostal())
                    .NombreLocalidad(lo.getNombreLocalidad())
                    .id_provincia(p.getId())
                    .nombreProvincia(p.getNombreProvincia())
                    .build();
            listaLocalidades.add(localidad);
        }
    return listaLocalidades;
    }
    
    public DTOLocalidad EdicionLocalidad(Long cp) throws Exception{
    
        Localidad localidad = repository.findByCP(cp);
        Provincia p=localidad.getProvincia();
        
        DTOLocalidad edicionLocalidad = DTOLocalidad.builder()
                .CodigoPostal(localidad.getCodigoPostal())
                .NombreLocalidad(localidad.getNombreLocalidad())
                .id_provincia(p.getId())
                .nombreProvincia(p.getNombreProvincia())
                .build();
        
    return edicionLocalidad;
    }
    
    public void update(Long oldCodigoPostal, String nombre, Long idProvincia, Long newCodigoPostal){
        
        Localidad localidad = repository.findByCP(oldCodigoPostal);
        if (localidad != null) {
            localidad.setCodigoPostal(newCodigoPostal);
            localidad.setNombreLocalidad(nombre);
            Provincia provincia = RepoProvincia.getReferenceById(idProvincia);
            localidad.setProvincia(provincia);
            repository.save(localidad);
        }
    }
    
    
     public Localidad create (Long cp, String nombreLocalidad, Long idProvincia){
         Provincia provincia = RepoProvincia.getReferenceById(idProvincia);
         
         Localidad nuevaLocalidad = Localidad
                .builder()
                .CodigoPostal(cp)
                .NombreLocalidad(nombreLocalidad)
                .provincia(provincia)
                .build();
        return this.repository.save(nuevaLocalidad);
    }
    
     public Localidad findByCP(Long cp){
         return repository.findByCP(cp);
     }
     
}

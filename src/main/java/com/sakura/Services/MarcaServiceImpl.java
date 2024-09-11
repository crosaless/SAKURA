package com.sakura.Services;

import com.sakura.DTO.DTOMostrarMarca;
import com.sakura.Entities.Marca;
import com.sakura.repository.MarcaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MarcaServiceImpl extends BaseServiceImpl<Marca, Long, MarcaRepository> implements MarcaService{
    
       
    @Autowired
    MarcaServiceImpl(MarcaRepository repository) {
        super(repository);
    }
    
    public Marca create (String nombre, String imagen){
        Marca nuevaMarca = Marca
                .builder()
                .nombre(nombre)
                .imagen(imagen)
                .build();
        return this.repository.save(nuevaMarca);
    }
    /*
    public Marca update(Long id, String nombre) throws Exception{
    try {
            
            Optional<Marca> marca = this.repository.findById(id);
            if(marca.isPresent()){
                Marca m = marca.get();
                
                m.setNombre(nombre);
                return repository.save(m);
            }
        } catch (Exception e) {
            throw new Exception();
        }   
    }*/
    
    
    
   
    
    public Marca findByname(String nombre){
        Optional<Marca> m = repository.findByNombre(nombre);
        
            if (m.isPresent()) {
                Marca marca = m.get();
            return marca;     
}
    return null;
    }
    
    public List<DTOMostrarMarca> builDTOMarcas(){
        //busco las marcas disponibles para mostrar
        List<Marca> m = this.repository.findAllDisponibles();
        //creo la lista de dtomarca
        List<DTOMostrarMarca> listaMarcas = new ArrayList<DTOMostrarMarca>();
        
        for(Marca marca : m){
            System.out.println("Empiezo a loopear marcas");
            System.out.println("Marca encontrada: " + marca.getNombre());
            DTOMostrarMarca marcaDTO = DTOMostrarMarca.builder()
                    .id(marca.getId())
                    .nombre(marca.getNombre())
                    .imagen(marca.getImagen())
                    .build();
            listaMarcas.add(marcaDTO);
        }
    return listaMarcas;
    }
    
    
    
    public DTOMostrarMarca EdicionMarca(Long id) throws Exception{
    
        Marca marca = this.findById(id);
        
        DTOMostrarMarca edicionMarca = DTOMostrarMarca.builder()
                        .id(marca.getId())
                        .imagen(marca.getImagen())
                        .nombre(marca.getNombre())
                        .build();
        
    return edicionMarca;
    }
}

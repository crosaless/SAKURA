package com.sakura.Services;

import com.sakura.DTO.DTOCategoria;
import com.sakura.Entities.Categoria;
import com.sakura.Entities.TipoTalle;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakura.Services.BaseServiceImpl;
import com.sakura.repository.CategoriaRepository;
import com.sakura.Services.IService.CategoriaService;

@Service
@Transactional
public class CategoriaServiceImpl
        extends BaseServiceImpl<Categoria, Long, CategoriaRepository>
        implements CategoriaService {

    @Autowired
    CategoriaServiceImpl(CategoriaRepository repository) {
        super(repository);
    }

    public Categoria create(DTOCategoria categoria) throws Exception {
        String tipoTalle = categoria.getTipo_talle();
        System.out.println(tipoTalle);
        if(!tipoTalle.equals(TipoTalle.ROPA.name()) && !tipoTalle.equals(TipoTalle.CALZADO.name())){
            throw new Exception("El tipo de talle especificado no es correcto");
        }
        Categoria nCategoria = Categoria
                .builder()
                .nombre(categoria.getNombre())
                .tipoTalle(tipoTalle)
                .build();
        return this.repository.save(nCategoria);
    }
    
    public void actualizar(Long id,DTOCategoria dto){
       Categoria categoria = repository.findById(id).get();
       categoria.setNombre(dto.getNombre());
         if(dto.getTipo_talle().equals(TipoTalle.ROPA.name()) || dto.getTipo_talle().equals(TipoTalle.CALZADO.name())){
             categoria.setTipoTalle(dto.getTipo_talle());
        }
       
    }


}

package com.sakura.controllers;

import com.sakura.DTO.DTOCategoria;
import com.sakura.controllers.IControllers.CategoriaController;
import com.sakura.Services.CategoriaServiceImpl;
import com.sakura.Entities.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/categorias")
public class CategoriaControllerImpl
        extends BaseControllerImpl<Categoria, Long, CategoriaServiceImpl>
        implements CategoriaController {

    @Autowired
    public CategoriaControllerImpl(CategoriaServiceImpl service) {
        super(service);
    }
    
    @PostMapping("/nueva")
    public ResponseEntity<?> nuevaCategoria(@RequestBody DTOCategoria categoria){
        try{
            System.out.println(categoria.getNombre());
            service.create(categoria);
            return ResponseEntity.ok().body("La categoria se ha creado correctamente");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Se ha producido un error al intentar crear una nueva categoria");
        }
    }
    
    @PostMapping("/actualizar")
    public ResponseEntity<?> actualizar(@RequestParam Long id,@RequestBody DTOCategoria categoria){
        try{
            service.actualizar(id,categoria);
            return ResponseEntity.ok().body("La categoria se ha actualizado correctamente");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Se ha producido un error al intentar actualizar categoria");
        }
    }
    
    @PostMapping("/eliminar")
    public ResponseEntity<?> eliminar(@RequestParam Long id){
        try{
            service.delete(id);
            return ResponseEntity.ok().body("La categoria se elimno exitosamente");
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Se ha producido un error al intentar borrar la categoria");
        }
    
    }

}

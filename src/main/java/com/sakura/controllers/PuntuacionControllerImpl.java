package com.sakura.controllers;

import com.sakura.DTO.DTOProductoMUI;
import com.sakura.DTO.DTOPuntuacion;
import com.sakura.DTO.DTOPuntuacionCantidad;
import com.sakura.Entities.Producto;
import com.sakura.Entities.Puntuacion;
import com.sakura.Services.PuntuacionServiceImpl;
import com.sakura.repository.PuntuacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/maslikeados")
public class PuntuacionControllerImpl extends BaseControllerImpl<Puntuacion, Long, PuntuacionServiceImpl>
implements PuntuacionController{
    
    PuntuacionServiceImpl ServicePuntuacion;
    @Autowired
    public PuntuacionControllerImpl(PuntuacionServiceImpl service) {
        super(service);
    }
    
    @PostMapping(path = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void save(DTOPuntuacion entity) throws Exception {
        this.service.save(entity);
    }
    

    /*@PostMapping("/admin/reportes/maslikeados")
    public String verPuntuacionProducto(@RequestBody DTOProductoMUI p){
        try{
        
          DTOPuntuacionCantidad resultado = ServicePuntuacion.calcularPuntuacion(p);
        
         return "Puntuaciones calculadas correctamente";
        }catch(Exception e){
        return "se ha producido un error";
        }
       
   Que el frontend acceda directamente al metodo del servicio
    }*/
}

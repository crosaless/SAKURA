package com.sakura.Services;

import com.sakura.DTO.DTOPuntuacion;
import com.sakura.Entities.Producto;
import com.sakura.Entities.Puntuacion;
import com.sakura.Services.IService.BaseService;
import java.util.List;


public interface PuntuacionService extends BaseService<Puntuacion, Long> {
   
    public Puntuacion save(DTOPuntuacion puntuacion) throws Exception; //puntuacion unitaria de un cliente para un producto

    public List<Puntuacion> findByProducto(Producto p); //en la vista de detalle como admin ves cantidad de MG y No Mg
}

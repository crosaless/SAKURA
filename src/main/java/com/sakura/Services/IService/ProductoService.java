package com.sakura.Services.IService;

import com.sakura.Entities.Producto;
import java.util.List;

import com.sakura.Services.IService.BaseService;
import com.sakura.DTO.DTOCreateProducto;
import com.sakura.DTO.DTOTalleStock;
import com.sakura.Entities.Talle;

public interface ProductoService extends BaseService<Producto, Long> {

    public Producto save(DTOCreateProducto producto_nuevo) throws Exception;

    public List<Producto> findSimilar(Producto p);

    public void actualizarStock(Long id, List<DTOTalleStock> lista_dtos) throws Exception;
    
    public void inicializarTalles(Producto producto) throws Exception;
    
}

package com.sakura.Services.IService;

import com.sakura.DTO.DTOAddItem;
import com.sakura.Entities.Carrito;
import org.springframework.transaction.annotation.Transactional;

public interface CarritoService extends BaseService<Carrito, Long> {
    // todo: Set as DTO
    public void addItem(long idUser, DTOAddItem dtoAdd) throws Exception;
    
    Carrito findByUsuarioId(Long id);
    @Transactional
    void vaciarCarrito(Carrito carrito);
}
